package org.drombler.jstore.client.agent.startup.integration.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import jdk.incubator.http.HttpResponse.BodySubscriber;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Flow;

public class JacksonResponseBodySubscriber<T> implements BodySubscriber<T> {

    String content;
    private ObjectMapper objectMapper;
    private Class<T> type;
    private final CompletionStage<T> completionStage = new CompletableFuture<>();

    public JacksonResponseBodySubscriber(ObjectMapper objectMapper, Class<T> type, Charset charset) {
        this.objectMapper = objectMapper;
        this.type = type;
    }

    @Override
    public CompletionStage<T> getBody() {
        try {

            T payload = objectMapper.readValue(content, type);
//            return payload;
            return completionStage;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void onSubscribe(Flow.Subscription subscription) {

    }

    @Override
    public void onNext(List<ByteBuffer> item) {

    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onComplete() {

    }
}
