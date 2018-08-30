package org.drombler.jstore.client.agent.startup.download.http;

import jdk.incubator.http.HttpHeaders;
import jdk.incubator.http.HttpResponse;
import org.drombler.jstore.client.agent.startup.download.DownloadId;
import org.drombler.jstore.client.agent.startup.download.DownloadTask;

import java.nio.file.Path;

public class DownloadTaskBodyHandler<K> implements HttpResponse.BodyHandler<DownloadTask<K>> {

    private final DownloadId<K> downloadId;
    private final Path downloadTempDirPath;

    public DownloadTaskBodyHandler(DownloadId<K> downloadId, Path downloadTempDirPath) {
        this.downloadId = downloadId;
        this.downloadTempDirPath = downloadTempDirPath;
    }

    @Override
    public HttpResponse.BodySubscriber<DownloadTask<K>> apply(int statusCode, HttpHeaders responseHeaders) {
        return HttpResponse.BodyHandler.asFile(downloadTempDirPath.resolve(downloadId.toString()));
//        return new DownloadTaskBodySubscriber<>(downloadId, downloadTempDirPath);
    }
}
