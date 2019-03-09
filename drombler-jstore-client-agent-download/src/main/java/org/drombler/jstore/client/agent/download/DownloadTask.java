package org.drombler.jstore.client.agent.download;


import java.net.http.HttpResponse;
import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;

public class DownloadTask<K> {
    private final DownloadId<K> id;
    private final CompletableFuture<HttpResponse<Path>> response;

    public DownloadTask(DownloadId<K> id, CompletableFuture<HttpResponse<Path>> response) {
        this.id = id;
        this.response = response;
    }

    public DownloadId<K> getId() {
        return id;
    }

    public CompletableFuture<HttpResponse<Path>> getResponse() {
        return response;
    }
}
