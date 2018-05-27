package org.drombler.jstore.client.agent.startup;

import jdk.incubator.http.HttpClient;
import org.drombler.jstore.client.agent.model.StoreRegistry;
import org.drombler.jstore.client.agent.startup.app.ApplicationInfo;
import org.drombler.jstore.client.agent.startup.app.task.ApplicationCleaner;
import org.drombler.jstore.client.agent.startup.app.task.ApplicationInfoUpdater;
import org.drombler.jstore.client.agent.startup.app.task.ApplicationUpdater;
import org.drombler.jstore.client.agent.startup.app.task.model.ApplicationUpdateInfo;
import org.drombler.jstore.client.agent.startup.integration.JStoreClientRegistry;
import org.drombler.jstore.client.agent.startup.jre.JRECleaner;
import org.drombler.jstore.client.agent.startup.jre.JREInfoUpdater;
import org.drombler.jstore.client.agent.startup.jre.model.JREUpdateInfo;
import org.drombler.jstore.client.agent.startup.jre.oracle.OracleJREUpdater;

import java.util.List;
import java.util.Set;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class Updater implements Runnable {
    private final ExecutorService threadPoolExecutor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    private final JStoreClientRegistry jStoreClientRegistry;
    private final HttpClient httpClient;
    private final StoreRegistry storeRegistry;

    public Updater(StoreRegistry storeRegistry, JStoreClientRegistry jStoreClientRegistry, HttpClient httpClient) {
        this.storeRegistry = storeRegistry;
        this.jStoreClientRegistry = jStoreClientRegistry;
        this.httpClient = httpClient;
    }

    @Override
    public void run() {

        try {
            updateApplicationInfo();
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
        }

        try {
            updateJREInfo();
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
        }

        try {
            updateApplications();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            updateJREs();
        } catch (InterruptedException e) {
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


    private void updateApplicationInfo() throws InterruptedException, ExecutionException, TimeoutException {
        Set<ApplicationInfo> selectedApplications = getSelectedApplications();
        ApplicationInfoUpdater updater = new ApplicationInfoUpdater(storeRegistry, jStoreClientRegistry);
        Future<?> future = threadPoolExecutor.submit(updater);
        future.get(1200, TimeUnit.SECONDS);
    }

    private Set<ApplicationInfo> getSelectedApplications() {
        return null;
    }

    private void updateJREInfo() throws InterruptedException, ExecutionException, TimeoutException {
        Set<String> jreSet = getRequiredJREs();
        JREInfoUpdater updater = new JREInfoUpdater(jreSet);
        Future<?> future = threadPoolExecutor.submit(updater);
        future.get(1200, TimeUnit.SECONDS);
    }

    private Set<String> getRequiredJREs() {
        return null;
    }

    private void updateApplications() throws InterruptedException {
        Set<ApplicationInfo> selectedButOutdatedOracleApplicationVersions = getSelectedButOutdatedOracleApplicationVersions();
        List<ApplicationUpdater> applicationUpdaters = selectedButOutdatedOracleApplicationVersions.stream()
                .map(ApplicationUpdater::new)
                .collect(Collectors.toList());
        List<Future<ApplicationUpdateInfo>> futureList = threadPoolExecutor.invokeAll(applicationUpdaters, 20 * 60 * applicationUpdaters.size(), TimeUnit.SECONDS);
//CompletableFuture.runAsync()
    }

    private Set<ApplicationInfo> getSelectedButOutdatedOracleApplicationVersions() {
        return null;
    }

    private void updateJREs() throws InterruptedException {
        Set<String> requiredButOutdatedOracleJREVersions = getRequiredButOutdatedOracleJREVersions();
        List<OracleJREUpdater> jreUpdaters = requiredButOutdatedOracleJREVersions.stream()
                .map(version -> new OracleJREUpdater(httpClient, version))
                .collect(Collectors.toList());
        List<Future<JREUpdateInfo>> futureList = threadPoolExecutor.invokeAll(jreUpdaters, 20 * 60 * jreUpdaters.size(), TimeUnit.SECONDS);


    }

    private Set<String> getRequiredButOutdatedOracleJREVersions() {
        return null;
    }


    private void cleanApplications() throws InterruptedException, ExecutionException, TimeoutException {
        Set<ApplicationInfo> unusedApplications = getUnusedApplications();
        ApplicationCleaner cleaner = new ApplicationCleaner(unusedApplications);
        Future<?> future = threadPoolExecutor.submit(cleaner);
        future.get(1200, TimeUnit.SECONDS);
    }

    private Set<ApplicationInfo> getUnusedApplications() {
        return null;
    }

    private void cleanJREs() throws InterruptedException, ExecutionException, TimeoutException {
        Set<String> unusedJREVersions = getUnusedJREVersions();
        JRECleaner cleaner = new JRECleaner(unusedJREVersions);
        Future<?> future = threadPoolExecutor.submit(cleaner);
        future.get(1200, TimeUnit.SECONDS);
    }

    private Set<String> getUnusedJREVersions() {
        return null;
    }

}
