package org.drombler.jstore.client.agent.startup;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.drombler.commons.client.startup.main.BootServiceStarter;
import org.drombler.jstore.client.agent.model.PreSelectedApplicationRegistry;
import org.drombler.jstore.client.agent.model.converter.StoreNormalizer;
import org.drombler.jstore.client.agent.startup.commons.HttpClientUtils;
import org.drombler.jstore.client.agent.startup.integration.JStoreClientRegistry;
import org.drombler.jstore.client.agent.startup.managedcomponent.application.ApplicationManager;
import org.drombler.jstore.client.agent.startup.managedcomponent.jre.JREManager;
import org.drombler.jstore.protocol.StoreRegistry;
import org.drombler.jstore.protocol.json.Agent;
import org.drombler.jstore.protocol.json.PreSelectedApplication;
import org.drombler.jstore.protocol.json.Store;
import org.drombler.jstore.protocol.json.StoreConfiguration;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.*;

public class UpdateSchedulerStarter implements BootServiceStarter {
    /**
     * The property name used to specify an URL to the configuration property file to be used for the created the framework instance.
     */
    public static final String AGENT_JSON_PROP = "agent.file";
    /**
     * The default name used for the configuration properties file.
     */
    public static final String AGENT_FILE_NAME = "agent.json";

    private final JStoreClientAgentConfiguration configuration;
    private final ScheduledExecutorService scheduledExecutorService;
    private final StoreRegistry storeRegistry = new StoreRegistry();
    private final PreSelectedApplicationRegistry preSelectedApplicationRegistry = new PreSelectedApplicationRegistry();
    private final ApplicationManager applicationManager;
    private final JREManager jreManager;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final JStoreClientRegistry jStoreClientRegistry;
    private final HttpClient httpClient;

    public UpdateSchedulerStarter(JStoreClientAgentConfiguration configuration) throws IOException {
        this.configuration = configuration;
        this.scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        this.httpClient = HttpClientUtils.createHttpClient();
        this.jStoreClientRegistry = new JStoreClientRegistry(httpClient, objectMapper);
        this.applicationManager = new ApplicationManager(configuration.getManagedComponentsInstallDirPath());
        this.jreManager = new JREManager(configuration.getManagedComponentsInstallDirPath());
    }

    @Override
    public boolean init() {
        mergeAgent(configuration.getInstallConfigDirPath());
        mergeAgent(configuration.getUserConfigDirPath());

        storeRegistry.getStores().stream()
                .forEach(store -> {
                    try {
                        jStoreClientRegistry.registerStore(store);
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
                });


        return true;
    }

    private void mergeAgent(Path configDirPath) {
        Path agentFilePath = configDirPath.resolve(AGENT_FILE_NAME);
        if (Files.exists(agentFilePath)) {
            try {
                Agent agent = objectMapper.readValue(agentFilePath.toFile(), Agent.class);
                mergeStoreConfigurations(agent.getStoreConfigurations());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void mergeStoreConfigurations(List<StoreConfiguration> storeConfigurations) {
        storeConfigurations.forEach(this::mergeStoreConfiguration);
    }

    private void mergeStoreConfiguration(StoreConfiguration storeConfiguration) {
        mergeStore(storeConfiguration.getStore());
        mergePreSelectedApplications(storeConfiguration.getStore(), storeConfiguration.getPreSelectedApplications());
    }


    private void mergeStore(Store mergingStore) {
        StoreNormalizer storeNormalizer = new StoreNormalizer();
        storeNormalizer.normalizeStore(mergingStore);

        if (storeRegistry.containsStore(mergingStore.getId())) {
            Store store = storeRegistry.getStore(mergingStore.getId());
            mergeStores(store, mergingStore);
        } else {
            storeRegistry.registerStore(mergingStore);
        }
    }

    private void mergePreSelectedApplications(Store store, List<PreSelectedApplication> preSelectedApplications) {
        preSelectedApplications.forEach(mergingPreSelectedApplication -> {
            if (!preSelectedApplicationRegistry.containsPreSelectedApplication(store, mergingPreSelectedApplication.getApplicationId())) {
                preSelectedApplicationRegistry.registerPreSelectedApplication(store, mergingPreSelectedApplication);
            } else {
                PreSelectedApplication preSelectedApplication = preSelectedApplicationRegistry.getPreSelectedApplication(store, mergingPreSelectedApplication.getApplicationId());
                mergePreSelectedApplication(preSelectedApplication, mergingPreSelectedApplication);
            }
        });
    }

    private void mergePreSelectedApplication(PreSelectedApplication preSelectedApplication, PreSelectedApplication mergingPreSelectedApplication) {
        if (mergingPreSelectedApplication.getAutodownload() != null) {
            preSelectedApplication.setAutodownload(mergingPreSelectedApplication.getAutodownload());
        }
        if (mergingPreSelectedApplication.getAutoinstall() != null) {
            preSelectedApplication.setAutoinstall(mergingPreSelectedApplication.getAutoinstall());
        }
    }

    public void mergeStores(Store store, Store mergingStore) {
        if (!store.getEndpoint().equals(mergingStore.getEndpoint())) {
            store.setEndpoint(mergingStore.getEndpoint());
        }
    }

    @Override
    public void startAndWait() throws ExecutionException, InterruptedException, IOException {
        Updater updater = new Updater(storeRegistry, jStoreClientRegistry, preSelectedApplicationRegistry, applicationManager);
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
