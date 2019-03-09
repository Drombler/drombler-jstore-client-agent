package org.drombler.jstore.client.agent.startup.app.task;


import org.drombler.jstore.client.agent.model.PreSelectedApplicationRegistry;
import org.drombler.jstore.client.agent.startup.app.task.model.ApplicationInfoUpdateInfo;
import org.drombler.jstore.client.agent.startup.integration.JStoreClient;
import org.drombler.jstore.client.agent.startup.integration.JStoreClientException;
import org.drombler.jstore.client.agent.startup.integration.JStoreClientRegistry;
import org.drombler.jstore.client.agent.startup.managedcomponent.application.ApplicationManager;
import org.drombler.jstore.protocol.StoreRegistry;
import org.drombler.jstore.protocol.json.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * Updates the info about available application versions of the selected applications.
 */
public class ApplicationInfoUpdater implements Callable<ApplicationInfoUpdateInfo> {

    private final StoreRegistry storeRegistry;
    private final JStoreClientRegistry jStoreClientRegistry;
    private final PreSelectedApplicationRegistry preSelectedApplicationRegistry;
    private final ApplicationManager applicationManager;
    private final SystemInfo systemInfo;

    public ApplicationInfoUpdater(StoreRegistry storeRegistry, JStoreClientRegistry jStoreClientRegistry, PreSelectedApplicationRegistry preSelectedApplicationRegistry, ApplicationManager applicationManager, SystemInfo systemInfo) {
        this.storeRegistry = storeRegistry;
        this.jStoreClientRegistry = jStoreClientRegistry;
        this.preSelectedApplicationRegistry = preSelectedApplicationRegistry;
        this.applicationManager = applicationManager;
        this.systemInfo = systemInfo;
    }

    @Override
    public ApplicationInfoUpdateInfo call() {
        ApplicationInfoUpdateInfo info = new ApplicationInfoUpdateInfo();
        for (Store store : storeRegistry.getStores()) {
            JStoreClient jStoreClient = jStoreClientRegistry.getStoreClient(store);
            try {
                List<UpgradableApplication> applicationVersionInfos = jStoreClient.searchApplicationVersions(getSelectedApplications(store), systemInfo);
                System.out.println(applicationVersionInfos);
                info.addUpgradableApplicationInfo(store, applicationVersionInfos);
            } catch (JStoreClientException e) {
                e.printStackTrace();
            }
        }

        return info;
    }

    private Collection<SelectedApplication> getSelectedApplications(Store store) {
//        return store.getApplicationIds().stream()
//                .map(applicationId -> {
//                    ApplicationId id = new ApplicationId();
//                    id.setVendorId(applicationId.getVendorId());
//                    id.setVendorApplicationId(applicationId.getVendorApplicationId());
//                    return id;
//                })
//                .collect(Collectors.toList());
        Map<ApplicationId, SelectedApplication> selectedApplicationMap = new HashMap<>();
        applicationManager.getInstalledApplications(store).forEach(application ->
                selectedApplicationMap.put(application.getApplicationId(), convertSelectedApplication(application)));
        preSelectedApplicationRegistry.getPreSelectedApplication(store).stream()
                .filter(PreSelectedApplication::getAutodownload)
                .filter(preSelectedApplication -> !selectedApplicationMap.containsKey(preSelectedApplication.getApplicationId()))
                .forEach(preSelectedApplication -> selectedApplicationMap.put(preSelectedApplication.getApplicationId(), convertSelectedApplication(preSelectedApplication)));
        return selectedApplicationMap.values();

    }

    private SelectedApplication convertSelectedApplication(Application application) {
        SelectedApplication selectedApplication = new SelectedApplication();
        selectedApplication.setApplicationId(application.getApplicationId());
        selectedApplication.setInstalledVersion(application.getVersion());
        return selectedApplication;
    }

    private SelectedApplication convertSelectedApplication(PreSelectedApplication preSelectedApplication) {
        SelectedApplication selectedApplication = new SelectedApplication();
        selectedApplication.setApplicationId(preSelectedApplication.getApplicationId());
        return selectedApplication;
    }
}
