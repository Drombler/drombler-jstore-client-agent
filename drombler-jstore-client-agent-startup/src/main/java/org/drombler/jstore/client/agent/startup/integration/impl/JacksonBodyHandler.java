package org.drombler.jstore.client.agent.startup.integration.impl;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.http.HttpHeaders;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandler;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class JacksonBodyHandler<T> implements BodyHandler<T> {
    private final ObjectMapper objectMapper;
    private final Class<T> type;

    public JacksonBodyHandler(ObjectMapper objectMapper, Class<T> type) {
        this.objectMapper = objectMapper;
        this.type = type;
        BodyHandler<String> stringBodyHandler = BodyHandlers.ofString();
    }

    @Override
    public HttpResponse.BodySubscriber<T> apply(HttpResponse.ResponseInfo responseInfo) {
        Charset contentCharset = getContentCharset(responseInfo.headers());
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
