
package org.drombler.jstore.client.service.model;


import org.drombler.jstore.client.service.model.json.StoreInfoType;
import org.drombler.jstore.client.service.model.json.StoreType;

import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * The application store
 * <p>
 * The application store which manages the applications. Vendor IDs are store specific.
 */
public class Store {

    /**
     * The application store info
     * <p>
     * The info about an application store.
     */
    private final StoreInfo storeInfo;
    private final Set<ApplicationId> applicationIds = new HashSet<>();

    public Store(StoreInfo storeInfo) {
        this.storeInfo = storeInfo;
    }


    /**
     * The application store info
     * <p>
     * The info about an application store.
     */
    public StoreInfo getStoreInfo() {
        return storeInfo;
    }


    public Set<ApplicationId> getApplicationIds() {
        return applicationIds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Store)) return false;
        Store store = (Store) o;
        return Objects.equals(storeInfo, store.storeInfo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(storeInfo);
    }

    @Override
    public String toString() {
        return "Store{" +
                "storeInfo=" + storeInfo +
                ", applicationIds=" + applicationIds +
                '}';
    }

    public static Store fromJSON(StoreType jsonStore) throws URISyntaxException {
        StoreInfoType jsonStoreInfo = jsonStore.getStoreInfo();
        StoreInfo storeInfo = StoreInfo.fromJSON(jsonStoreInfo);
        Store store = new Store(storeInfo);
        Set<ApplicationId> applicationIds = jsonStore.getApplicationIds().stream()
                .map(ApplicationId::fromJSON)
                .collect(Collectors.toSet());
        store.getApplicationIds().addAll(applicationIds);
        return store;
    }

    public void merge(Store other) {
        storeInfo.merge(other.storeInfo);
        applicationIds.addAll(other.applicationIds);
    }
}
