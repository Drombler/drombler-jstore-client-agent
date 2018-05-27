package org.drombler.jstore.client.agent.startup.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jdk.incubator.http.HttpClient;
import jdk.incubator.http.HttpRequest;
import jdk.incubator.http.HttpResponse;
import org.drombler.jstore.client.agent.startup.commons.http.StandardHttpHeaderFieldNames;
import org.drombler.jstore.client.agent.startup.commons.http.StandardMimeTypes;
import org.drombler.jstore.client.agent.startup.integration.impl.JacksonRequestBodyPublisher;
import org.drombler.jstore.protocol.json.ApplicationId;
import org.drombler.jstore.protocol.json.ApplicationVersionInfo;
import org.drombler.jstore.protocol.json.ApplicationVersionSearchRequest;
import org.drombler.jstore.protocol.json.ApplicationVersionSearchResponse;

import java.io.IOException;
import java.net.URI;
import java.util.List;

public class JStoreClient {
    private static final String V1_PATH_SEGMENT = "v1";
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;
    private final URI endpoint;

    public JStoreClient(HttpClient httpClient, ObjectMapper objectMapper, URI endpoint) {
        this.httpClient = httpClient;
        this.objectMapper = objectMapper;
        this.endpoint = endpoint;
    }

    public List<ApplicationVersionInfo> searchApplicationVersions(List<ApplicationId> applicationIds) throws JStoreClientException {
        ApplicationVersionSearchRequest requestPayload = new ApplicationVersionSearchRequest();
        requestPayload.setApplicationIds(applicationIds);

        String path = V1_PATH_SEGMENT + "/application-version-search";
        HttpRequest request = HttpRequest.newBuilder()
//                .uri(URI.create("http://jstore.drombler.com"))
                .uri(endpoint.resolve(path))
//                    .timeout(Duration.of(10, ChronoUnit.MINUTES))
                .setHeader(StandardHttpHeaderFieldNames.ACCEPT, StandardMimeTypes.APPLICATION_JSON)
                .setHeader(StandardHttpHeaderFieldNames.CONTENT_TYPE, StandardMimeTypes.APPLICATION_JSON) // TODO: required?
                .POST(createJacksonRequestBodyProcessor(requestPayload))
                .build();

        HttpResponse<String> response = send(request);


        if (response.statusCode() >= 200 && response.statusCode() < 300) {
            ApplicationVersionSearchResponse applicationVersionSearchResponse = readResponsePayload(response, ApplicationVersionSearchResponse.class);
            return applicationVersionSearchResponse.getApplicationVersionInfos();
        } else {
            throw new JStoreClientException(request.method() + " " + path + " call failed! Status code: " + response.statusCode(), response.statusCode());
        }
    }

    private <T> T readResponsePayload(HttpResponse<String> response, Class<T> valueType) throws JStoreClientException {
        try {
            return objectMapper.readValue(response.body(), valueType);
        } catch (IOException e) {
            throw new JStoreClientException(e.getMessage(), response.statusCode(), e);
        }
    }

    private HttpResponse<String> send(HttpRequest request) throws JStoreClientException {
        try {
            //       HttpResponse<ApplicationVersionSearchResponse> response = httpClient.send(request, new JacksonBodyHandler<>(objectMapper, ApplicationVersionSearchResponse.class));
            return httpClient.send(request, HttpResponse.BodyHandler.asString());
        } catch (IOException | InterruptedException e) {
            throw new JStoreClientException(e.getMessage(), e);
        }
    }

    private JacksonRequestBodyPublisher createJacksonRequestBodyProcessor(Object requestPayload) throws JStoreClientException {
        try {
            return new JacksonRequestBodyPublisher(objectMapper, requestPayload);
        } catch (JsonProcessingException e) {
            throw new JStoreClientException(e.getMessage(), e);
        }
    }
}
