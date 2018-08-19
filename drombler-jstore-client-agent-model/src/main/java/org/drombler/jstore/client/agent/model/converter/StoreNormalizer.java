package org.drombler.jstore.client.agent.model.converter;

import org.drombler.jstore.protocol.json.Store;

public class StoreNormalizer {
    public void normalizeStore(Store store) {
        if (!store.getEndpoint().endsWith("/")) {
            store.setEndpoint(store.getEndpoint() + "/");
        }
    }
}
