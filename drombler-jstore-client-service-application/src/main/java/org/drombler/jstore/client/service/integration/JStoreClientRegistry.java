package org.drombler.jstore.client.service.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import jdk.incubator.http.HttpClient;
import org.drombler.jstore.client.service.model.StoreInfo;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class JStoreClientRegistry {
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;
    private final Map<StoreInfo, JStoreClient> jStoreClientMap = new HashMap<>();

    public JStoreClientRegistry(HttpClient httpClient, ObjectMapper objectMapper) {
        this.httpClient = httpClient;
        this.objectMapper = objectMapper;
    }

    public void registerStore(StoreInfo storeInfo) {
        JStoreClient client = createClient(storeInfo.getEndpoint());
        jStoreClientMap.put(storeInfo, client);
    }

    private JStoreClient createClient(URI endpoint) {
        return new JStoreClient(httpClient, objectMapper, endpoint);

    }


    public JStoreClient getStoreClient(StoreInfo storeInfo) {
        return jStoreClientMap.get(storeInfo);
    }
}
