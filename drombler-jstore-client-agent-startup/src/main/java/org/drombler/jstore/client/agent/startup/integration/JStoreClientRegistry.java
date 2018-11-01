package org.drombler.jstore.client.agent.startup.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.drombler.jstore.client.agent.startup.download.DownloadManager;
import org.drombler.jstore.protocol.json.Store;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class JStoreClientRegistry {
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;
    private final Map<String, JStoreClient> jStoreClientMap = new HashMap<>();
    private final DownloadManager downloadManager;

    public JStoreClientRegistry(HttpClient httpClient, ObjectMapper objectMapper) throws IOException {
        this.httpClient = httpClient;
        this.objectMapper = objectMapper;
        this.downloadManager = new DownloadManager(Files.createTempDirectory("jstore"), httpClient);
    }

    public void registerStore(Store store) throws URISyntaxException {
        String endpoint = store.getEndpoint();
        JStoreClient client = createClient(endpoint);
        jStoreClientMap.put(endpoint, client);
    }

    private JStoreClient createClient(String endpoint) throws URISyntaxException {
        return new JStoreClient(httpClient, objectMapper, new URI(endpoint), downloadManager);

    }

    public JStoreClient getStoreClient(Store store) {
        return jStoreClientMap.get(store.getEndpoint());
    }
}
