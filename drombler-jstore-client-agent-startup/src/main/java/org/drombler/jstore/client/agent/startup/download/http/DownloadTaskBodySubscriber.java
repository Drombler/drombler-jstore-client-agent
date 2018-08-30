package org.drombler.jstore.client.agent.startup.download.http;

import jdk.incubator.http.HttpResponse;
import org.drombler.jstore.client.agent.startup.download.DownloadId;
import org.drombler.jstore.client.agent.startup.download.DownloadTask;

import java.nio.ByteBuffer;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Flow;

public class DownloadTaskBodySubscriber<K> implements HttpResponse.BodySubscriber<DownloadTask<K>> {
    private final DownloadId<K> downloadId;
    private final Path downloadTempDirPath;

    public DownloadTaskBodySubscriber(DownloadId<K> downloadId, Path downloadTempDirPath) {
        this.downloadId = downloadId;
        this.downloadTempDirPath = downloadTempDirPath;
    }

    @Override
    public CompletionStage<DownloadTask<K>> getBody() {
        return null;
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
