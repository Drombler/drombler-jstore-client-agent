package org.drombler.jstore.client.agent.startup;

import com.fasterxml.jackson.databind.ObjectMapper;
import jdk.incubator.http.HttpClient;
import org.drombler.commons.client.startup.main.BootServiceStarter;
import org.drombler.commons.client.startup.main.DromblerClientConfiguration;
import org.drombler.jstore.client.agent.model.Store;
import org.drombler.jstore.client.agent.model.StoreRegistry;
import org.drombler.jstore.client.agent.model.json.SelectedApplicationsType;
import org.drombler.jstore.client.agent.model.json.StoreType;
import org.drombler.jstore.client.agent.startup.commons.HttpClientUtils;
import org.drombler.jstore.client.agent.startup.integration.JStoreClientRegistry;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.*;

public class UpdateSchedulerStarter implements BootServiceStarter {
    /**
     * The property name used to specify an URL to the configuration property file to be used for the created the framework instance.
     */
    public static final String SELECTED_APPLICATIONS_JSON_PROP = "selectedApplications.json.file";
    /**
     * The default name used for the configuration properties file.
     */
    public static final String SELECTED_APPLICATIONS_JSON_FILE_NAME = "selectedApplications.json";

    private final DromblerClientConfiguration configuration;
    private final ScheduledExecutorService scheduledExecutorService;
    private final StoreRegistry storeRegistry = new StoreRegistry();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final JStoreClientRegistry jStoreClientRegistry;
    private final HttpClient httpClient;

    public UpdateSchedulerStarter(DromblerClientConfiguration configuration) {
        this.configuration = configuration;
        this.scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        this.httpClient = HttpClientUtils.createHttpClient();
        this.jStoreClientRegistry = new JStoreClientRegistry(httpClient, objectMapper);
    }

    @Override
    public boolean init() throws Exception {
        mergeSelectedApplications(configuration.getInstallConfigDirPath());
        mergeSelectedApplications(configuration.getUserConfigDirPath());

        storeRegistry.getStores().stream()
                .map(Store::getStoreInfo)
                .forEach(jStoreClientRegistry::registerStore);


        return true;
    }

    private void mergeSelectedApplications(Path configDirPath) {
        Path configSelectedApplicationsJsonPath = configDirPath.resolve(SELECTED_APPLICATIONS_JSON_FILE_NAME);
        if (Files.exists(configSelectedApplicationsJsonPath)) {
            try {
                SelectedApplicationsType selectedApplicationsByConfigDir = objectMapper.readValue(configSelectedApplicationsJsonPath.toFile(), SelectedApplicationsType.class);
                mergeSelectedApplications(selectedApplicationsByConfigDir);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void mergeSelectedApplications(SelectedApplicationsType selectedApplicationsByConfigDir) {
        for (StoreType mergingJsonStore : selectedApplicationsByConfigDir.getStores()) {
            try {
                mergeStore(mergingJsonStore);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }

        }
    }

    private void mergeStore(StoreType mergingJsonStore) throws URISyntaxException {
        Store mergingStore = Store.fromJSON(mergingJsonStore);
        if (storeRegistry.containsStore(mergingStore)) {
            Store store = storeRegistry.getStore(mergingStore.getStoreInfo());
            store.merge(mergingStore);
        } else {
            storeRegistry.registerStore(mergingStore);
        }
    }


    @Override
    public void startAndWait() throws ExecutionException, InterruptedException {
        Updater updater = new Updater(storeRegistry, jStoreClientRegistry, httpClient);
        ScheduledFuture<?> scheduledFuture = scheduledExecutorService.scheduleWithFixedDelay(updater, 0, 24, TimeUnit.HOURS);
        scheduledFuture.get();
    }

    @Override
    public void stop() {
        scheduledExecutorService.shutdown(); // TODO: shutdownNow ?
    }

    @Override
    public boolean isActive() {
        return true;
    }

    @Override
    public boolean isRequired() {
        return true;
    }

    @Override
    public String getName() {
        return "Update Scheduler Starter";
    }

    @Override
    public boolean isRunning() {
        return !scheduledExecutorService.isShutdown();
    }
}
