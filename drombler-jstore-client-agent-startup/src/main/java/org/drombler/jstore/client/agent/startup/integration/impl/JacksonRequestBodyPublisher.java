package org.drombler.jstore.client.agent.startup.integration.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.http.HttpRequest.BodyPublisher;
import java.net.http.HttpRequest.BodyPublishers;
import java.nio.ByteBuffer;
import java.util.concurrent.Flow;

public class JacksonRequestBodyPublisher implements BodyPublisher {
    private final BodyPublisher stringBodyPublisher;

    public JacksonRequestBodyPublisher(ObjectMapper objectMapper, Object payLoad) throws JsonProcessingException {
        String serialized = objectMapper.writeValueAsString(payLoad);
        this.stringBodyPublisher = BodyPublishers.ofString(serialized);
    }

    @Override
    public long contentLength() {
        return stringBodyPublisher.contentLength();
    }

    @Override
    public void subscribe(Flow.Subscriber<? super ByteBuffer> subscriber) {
        stringBodyPublisher.subscribe(subscriber);
    }
}
