package org.drombler.jstore.client.service.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jdk.incubator.http.HttpRequest.BodyProcessor;

import java.nio.ByteBuffer;
import java.util.concurrent.Flow;

public class JacksonRequestBodyProcessor implements BodyProcessor {
    private final BodyProcessor stringBodyProcessor;

    public JacksonRequestBodyProcessor(ObjectMapper objectMapper, Object payLoad) throws JsonProcessingException {
        String serialized = objectMapper.writeValueAsString(payLoad);
        this.stringBodyProcessor = BodyProcessor.fromString(serialized);
    }

    @Override
    public long contentLength() {
        return stringBodyProcessor.contentLength();
    }

    @Override
    public void subscribe(Flow.Subscriber<? super ByteBuffer> subscriber) {
        stringBodyProcessor.subscribe(subscriber);
    }
}
