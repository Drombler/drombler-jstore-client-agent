package org.drombler.jstore.client.agent.startup.app.task;

import org.drombler.jstore.client.agent.model.Store;
import org.drombler.jstore.client.agent.model.StoreRegistry;
import org.drombler.jstore.client.agent.startup.app.task.model.ApplicationInfoUpdateInfo;
import org.drombler.jstore.client.agent.startup.integration.JStoreClient;
import org.drombler.jstore.client.agent.startup.integration.JStoreClientException;
import org.drombler.jstore.client.agent.startup.integration.JStoreClientRegistry;
import org.drombler.jstore.protocol.json.ApplicationId;
import org.drombler.jstore.protocol.json.ApplicationVersionInfo;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

/**
 * Updates the info about available application versions of the selected applications.
 */
public class ApplicationInfoUpdater implements Callable<ApplicationInfoUpdateInfo> {

    private final StoreRegistry storeRegistry;
    private final JStoreClientRegistry jStoreClientRegistry;

    public ApplicationInfoUpdater(StoreRegistry storeRegistry, JStoreClientRegistry jStoreClientRegistry) {
        this.storeRegistry = storeRegistry;
        this.jStoreClientRegistry = jStoreClientRegistry;
    }

    @Override
    public ApplicationInfoUpdateInfo call() {
        for (Store store : storeRegistry.getStores()) {
            JStoreClient jStoreClient = jStoreClientRegistry.getStoreClient(store.getStoreInfo());
            try {
                List<ApplicationVersionInfo> applicationVersionInfos = jStoreClient.searchApplicationVersions(getApplicationIds(store));
                System.out.println(applicationVersionInfos);
            } catch (JStoreClientException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    private List<ApplicationId> getApplicationIds(Store store) {
        return store.getApplicationIds().stream()
                .map(applicationId -> {
                    ApplicationId id = new ApplicationId();
                    id.setVendorId(applicationId.getVendorId());
                    id.setVendorApplicationId(applicationId.getVendorApplicationId());
                    return id;
                })
                .collect(Collectors.toList());

    }
}
