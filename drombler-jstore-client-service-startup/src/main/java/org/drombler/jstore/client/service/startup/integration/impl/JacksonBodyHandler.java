package org.drombler.jstore.client.service.startup.integration.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import jdk.incubator.http.HttpHeaders;
import jdk.incubator.http.HttpResponse;
import jdk.incubator.http.HttpResponse.BodyHandler;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class JacksonBodyHandler<T> implements BodyHandler<T> {
    private final ObjectMapper objectMapper;
    private final Class<T> type;

    public JacksonBodyHandler(ObjectMapper objectMapper, Class<T> type) {
        this.objectMapper = objectMapper;
        this.type = type;
        BodyHandler<String> stringBodyHandler = BodyHandler.asString();
    }

    @Override
    public HttpResponse.BodySubscriber<T> apply(int statusCode, HttpHeaders responseHeaders) {
        Charset contentCharset = getContentCharset(responseHeaders);
        return new JacksonResponseBodySubscriber<>(objectMapper, type, contentCharset);
    }

    private Charset getContentCharset(HttpHeaders responseHeaders) {
        String charsetName = responseHeaders.firstValue("Content-encoding").orElse(StandardCharsets.UTF_8.name());
        try {
            return Charset.forName(charsetName);
        } catch (RuntimeException ex) {
            return StandardCharsets.UTF_8;
        }
    }
}
