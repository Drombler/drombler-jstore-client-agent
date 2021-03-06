package org.drombler.jstore.client.agent.startup;

import org.drombler.jstore.client.agent.model.PreSelectedApplicationRegistry;
import org.drombler.jstore.client.agent.startup.app.ApplicationInfo;
import org.drombler.jstore.client.agent.startup.app.task.ApplicationCleaner;
import org.drombler.jstore.client.agent.startup.app.task.ApplicationInfoUpdater;
import org.drombler.jstore.client.agent.startup.app.task.ApplicationUpdater;
import org.drombler.jstore.client.agent.startup.app.task.model.ApplicationInfoUpdateInfo;
import org.drombler.jstore.client.agent.startup.app.task.model.ApplicationUpdateInfo;
import org.drombler.jstore.client.agent.startup.integration.JStoreClient;
import org.drombler.jstore.client.agent.startup.integration.JStoreClientRegistry;
import org.drombler.jstore.client.agent.startup.jre.JRECleaner;
import org.drombler.jstore.client.agent.startup.jre.JREInfoUpdater;
import org.drombler.jstore.client.agent.startup.jre.JREUpdater;
import org.drombler.jstore.client.agent.startup.jre.model.JREInfoUpdateInfo;
import org.drombler.jstore.client.agent.startup.jre.model.JREUpdateInfo;
import org.drombler.jstore.client.agent.startup.managedcomponent.application.ApplicationManager;
import org.drombler.jstore.protocol.StoreRegistry;
import org.drombler.jstore.protocol.json.JreInfo;
import org.drombler.jstore.protocol.json.SelectedJRE;
import org.drombler.jstore.protocol.json.Store;
import org.drombler.jstore.protocol.json.SystemInfo;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class Updater implements Runnable {
    private final ExecutorService threadPoolExecutor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    private final JStoreClientRegistry jStoreClientRegistry;
    private final PreSelectedApplicationRegistry preSelectedApplicationRegistry;
    private final ApplicationManager applicationManager;
    private final StoreRegistry storeRegistry;
    private final SystemInfo systemInfo;

    public Updater(StoreRegistry storeRegistry, JStoreClientRegistry jStoreClientRegistry, PreSelectedApplicationRegistry preSelectedApplicationRegistry, ApplicationManager applicationManager) {
        this.storeRegistry = storeRegistry;
        this.jStoreClientRegistry = jStoreClientRegistry;
        this.preSelectedApplicationRegistry = preSelectedApplicationRegistry;
        this.applicationManager = applicationManager;
        this.systemInfo = createSystemInfo();
    }

    private SystemInfo createSystemInfo() {
        SystemInfo systemInfo = new SystemInfo();
        systemInfo.setOsName(System.getProperty("os.name"));
        systemInfo.setOsArch(System.getProperty("os.arch"));
        systemInfo.setOsVersion(System.getProperty("os.version"));
        // TODO implement
//        systemInfo.setHeadless();
//        systemInfo.setLabels();
        return systemInfo;
    }

    @Override
    public void run() {

        try {
            ApplicationInfoUpdateInfo applicationInfoUpdateInfo = updateApplicationInfo();
            updateApplications(applicationInfoUpdateInfo);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
        }

        try {
            JREInfoUpdateInfo jreInfoUpdateInfo = updateJREInfo();
            updateJREs(jreInfoUpdateInfo);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
        }

        try {
            cleanApplications();
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
        }
        try {
            cleanJREs();
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
        }
    }


    private ApplicationInfoUpdateInfo updateApplicationInfo() throws InterruptedException, ExecutionException, TimeoutException {
        ApplicationInfoUpdater updater = new ApplicationInfoUpdater(storeRegistry, jStoreClientRegistry, preSelectedApplicationRegistry, applicationManager, systemInfo);
        Future<ApplicationInfoUpdateInfo> future = threadPoolExecutor.submit(updater);
        return future.get(1200, TimeUnit.SECONDS);
    }


    private JREInfoUpdateInfo updateJREInfo() throws InterruptedException, ExecutionException, TimeoutException {
        // TODO: get list from installed applications
//        Set<String> jreSet = getRequiredJREs();
        List<SelectedJRE> selectedJREs = Arrays.asList(getSelectedJRE());

        JREInfoUpdater updater = new JREInfoUpdater(jStoreClientRegistry.getStoreClient(storeRegistry.getStores().iterator().next()), selectedJREs, systemInfo);
        Future<JREInfoUpdateInfo> future = threadPoolExecutor.submit(updater);
        return future.get(1200, TimeUnit.SECONDS);
    }

    private Set<String> getRequiredJREs() {
        // TODO: implement
        return new HashSet<>();
    }

    private void updateApplications(ApplicationInfoUpdateInfo applicationInfoUpdateInfo) throws InterruptedException {
        for (Store store : storeRegistry.getStores()) {
            JStoreClient jStoreClient = jStoreClientRegistry.getStoreClient(store);
            List<ApplicationUpdater> applicationUpdaters = applicationInfoUpdateInfo.getUpgradableApplicationInfo(store).stream()
                    .map(upgradableApplication -> new ApplicationUpdater(jStoreClient, upgradableApplication))
                    .collect(Collectors.toList());
            List<Future<ApplicationUpdateInfo>> futureList = threadPoolExecutor.invokeAll(applicationUpdaters, 20 * 60 * applicationUpdaters.size(), TimeUnit.SECONDS);
//CompletableFuture.runAsync()
        }
    }

    private Set<ApplicationInfo> getSelectedButOutdatedOracleApplicationVersions() {
        // TODO: implement
        return new HashSet<>();
    }

    private void updateJREs(JREInfoUpdateInfo jreInfoUpdateInfo) throws InterruptedException {

        // TODO: iterate
        Store store = storeRegistry.getStores().iterator().next();
        JStoreClient jStoreClient = jStoreClientRegistry.getStoreClient(store);


        List<JREUpdater> jreUpdaters = jreInfoUpdateInfo.getUpgradableJREs().stream()
//                .map(version -> new OracleJREUpdater(httpClient, version))
                .map(upgradableJRE -> new JREUpdater(jStoreClient, upgradableJRE))
                .collect(Collectors.toList());
        List<Future<CompletableFuture<JREUpdateInfo>>> futureList = threadPoolExecutor.invokeAll(jreUpdaters, 20 * 60 * jreUpdaters.size(), TimeUnit.SECONDS);

    }

    private SelectedJRE getSelectedJRE() {
        // TODO: get from update info
        SelectedJRE selectedJRE = new SelectedJRE();
        JreInfo jreInfo = new JreInfo();
        jreInfo.setJreVendorId("oracle");
        jreInfo.setJavaSpecificationVersion("11");
        selectedJRE.setJreInfo(jreInfo);
        return selectedJRE;
    }

    private Set<SelectedJRE> getRequiredButOutdatedOracleJREVersions() {
        // TODO: implement
        return new HashSet<>(Arrays.asList(getSelectedJRE()));
    }


    private void cleanApplications() throws InterruptedException, ExecutionException, TimeoutException {
        Set<ApplicationInfo> unusedApplications = getUnusedApplications();
        ApplicationCleaner cleaner = new ApplicationCleaner(unusedApplications);
        Future<?> future = threadPoolExecutor.submit(cleaner);
        future.get(1200, TimeUnit.SECONDS);
    }

    private Set<ApplicationInfo> getUnusedApplications() {
        // TODO: implement
        return new HashSet<>();
    }

    private void cleanJREs() throws InterruptedException, ExecutionException, TimeoutException {
        Set<String> unusedJREVersions = getUnusedJREVersions();
        JRECleaner cleaner = new JRECleaner(unusedJREVersions);
        Future<?> future = threadPoolExecutor.submit(cleaner);
        future.get(1200, TimeUnit.SECONDS);
    }

    private Set<String> getUnusedJREVersions() {
        // TODO: implement
        return new HashSet<>();
    }

}
