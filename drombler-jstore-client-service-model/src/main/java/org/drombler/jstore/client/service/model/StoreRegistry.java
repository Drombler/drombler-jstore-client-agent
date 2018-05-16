package org.drombler.jstore.client.service.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class StoreRegistry {

    private final Map<StoreInfo, Store> stores = new HashMap<>();

    public boolean containsStore(Store store) {
        return stores.containsKey(store.getStoreInfo());
    }

    public void registerStore(Store store) {
        if (stores.containsKey(store.getStoreInfo())) {
            throw new IllegalArgumentException("Store with StoreInfo " + store.getStoreInfo() + " already registered!");
        }
        stores.put(store.getStoreInfo(), store);
    }

    public Collection<Store> getStores() {
        return stores.values();
    }

    public Store getStore(StoreInfo storeInfo) {
        return stores.get(storeInfo);
    }
}
