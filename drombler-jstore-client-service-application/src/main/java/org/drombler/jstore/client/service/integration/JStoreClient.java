package org.drombler.jstore.client.service.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import jdk.incubator.http.HttpClient;
import jdk.incubator.http.HttpRequest;
import jdk.incubator.http.HttpResponse;
import org.drombler.jstore.client.service.commons.http.StandardHttpHeaderFieldNames;
import org.drombler.jstore.client.service.commons.http.StandardMimeTypes;
import org.drombler.jstore.protocol.json.ApplicationId;
import org.drombler.jstore.protocol.json.ApplicationVersionInfo;
import org.drombler.jstore.protocol.json.ApplicationVersionSearchRequest;
import org.drombler.jstore.protocol.json.ApplicationVersionSearchResponse;

import java.net.URI;
import java.util.List;

public class JStoreClient {
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public JStoreClient(HttpClient httpClient, ObjectMapper objectMapper) {
        this.httpClient = httpClient;
        this.objectMapper = objectMapper;
    }

    public List<ApplicationVersionInfo> searchApplicationVersions(List<ApplicationId> applicationIds) throws Exception {
        ApplicationVersionSearchRequest requestPayload = new ApplicationVersionSearchRequest();
        requestPayload.setApplicationIds(applicationIds);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://jstore.drombler.com"))
//                    .timeout(Duration.of(10, ChronoUnit.MINUTES))
                .setHeader(StandardHttpHeaderFieldNames.ACCEPT, StandardMimeTypes.APPLICATION_JSON)
                .setHeader(StandardHttpHeaderFieldNames.CONTENT_TYPE, StandardMimeTypes.APPLICATION_JSON)
                .POST(new JacksonRequestBodyProcessor(objectMapper, requestPayload))
                .build();

//       HttpResponse<ApplicationVersionSearchResponse> response = httpClient.send(request, new JacksonBodyHandler<>(objectMapper, ApplicationVersionSearchResponse.class));
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandler.asString());

        if (response.statusCode() >= 200 && response.statusCode() < 300) {
            ApplicationVersionSearchResponse applicationVersionSearchResponse = objectMapper.readValue(response.body(), ApplicationVersionSearchResponse.class);
            return applicationVersionSearchResponse.getApplicationVersionInfos();
        } else {
            throw new Exception();
        }
    }
}
