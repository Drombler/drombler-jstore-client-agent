package org.drombler.jstore.client.agent.startup.app.task;


import org.drombler.jstore.client.agent.startup.app.task.model.ApplicationInfoUpdateInfo;
import org.drombler.jstore.client.agent.startup.integration.JStoreClient;
import org.drombler.jstore.client.agent.startup.integration.JStoreClientException;
import org.drombler.jstore.client.agent.startup.integration.JStoreClientRegistry;
import org.drombler.jstore.protocol.StoreRegistry;
import org.drombler.jstore.protocol.json.SelectedApplication;
import org.drombler.jstore.protocol.json.Store;
import org.drombler.jstore.protocol.json.SystemInfo;
import org.drombler.jstore.protocol.json.UpgradableApplication;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Updates the info about available application versions of the selected applications.
 */
public class ApplicationInfoUpdater implements Callable<ApplicationInfoUpdateInfo> {

    private final StoreRegistry storeRegistry;
    private final JStoreClientRegistry jStoreClientRegistry;
    private final SystemInfo systemInfo;

    public ApplicationInfoUpdater(StoreRegistry storeRegistry, JStoreClientRegistry jStoreClientRegistry, SystemInfo systemInfo) {
        this.storeRegistry = storeRegistry;
        this.jStoreClientRegistry = jStoreClientRegistry;
        this.systemInfo = systemInfo;
    }

    @Override
    public ApplicationInfoUpdateInfo call() {
        for (Store store : storeRegistry.getStores()) {
            JStoreClient jStoreClient = jStoreClientRegistry.getStoreClient(store);
            try {
                List<UpgradableApplication> applicationVersionInfos = jStoreClient.searchApplicationVersions(getSelectedApplications(store), systemInfo);
                System.out.println(applicationVersionInfos);
            } catch (JStoreClientException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    private List<SelectedApplication> getSelectedApplications(Store store) {
//        return store.getApplicationIds().stream()
//                .map(applicationId -> {
//                    ApplicationId id = new ApplicationId();
//                    id.setVendorId(applicationId.getVendorId());
//                    id.setVendorApplicationId(applicationId.getVendorApplicationId());
//                    return id;
//                })
//                .collect(Collectors.toList());
        return Collections.emptyList();

    }
}
