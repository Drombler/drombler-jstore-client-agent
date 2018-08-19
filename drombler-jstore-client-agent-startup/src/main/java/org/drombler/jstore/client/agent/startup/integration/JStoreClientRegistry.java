package org.drombler.jstore.client.agent.startup.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import jdk.incubator.http.HttpClient;
import org.drombler.jstore.protocol.json.Store;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class JStoreClientRegistry {
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;
    private final Map<String, JStoreClient> jStoreClientMap = new HashMap<>();

    public JStoreClientRegistry(HttpClient httpClient, ObjectMapper objectMapper) {
        this.httpClient = httpClient;
        this.objectMapper = objectMapper;
    }

    public void registerStore(Store store) throws URISyntaxException {
        String endpoint = store.getEndpoint();
        JStoreClient client = createClient(endpoint);
        jStoreClientMap.put(endpoint, client);
    }

    private JStoreClient createClient(String endpoint) throws URISyntaxException {
        return new JStoreClient(httpClient, objectMapper, new URI(endpoint));

    }

    public JStoreClient getStoreClient(Store store) {
        return jStoreClientMap.get(store.getEndpoint());
    }
}
