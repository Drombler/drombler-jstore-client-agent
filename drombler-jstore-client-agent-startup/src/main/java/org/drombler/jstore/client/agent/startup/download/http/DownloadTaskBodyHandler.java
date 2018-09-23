package org.drombler.jstore.client.agent.startup.download.http;

import jdk.incubator.http.HttpHeaders;
import jdk.incubator.http.HttpResponse;
import org.drombler.jstore.client.agent.startup.download.DownloadId;
import org.drombler.jstore.client.agent.startup.download.DownloadTask;

import java.nio.file.Path;
import java.util.OptionalLong;

public class DownloadTaskBodyHandler<K> implements HttpResponse.BodyHandler<DownloadTask<K>> {

    private final DownloadId<K> downloadId;
    private final Path downloadTempDirPath;

    public DownloadTaskBodyHandler(DownloadId<K> downloadId, Path downloadTempDirPath) {
        this.downloadId = downloadId;
        this.downloadTempDirPath = downloadTempDirPath;
    }

    @Override
    public HttpResponse.BodySubscriber<DownloadTask<K>> apply(int statusCode, HttpHeaders responseHeaders) {
        OptionalLong contentLength = responseHeaders.firstValueAsLong("Content-Length");
        Path filePath = downloadTempDirPath.resolve(downloadId.toString());
        HttpResponse.BodyHandler.asFile(filePath);
//        return new DownloadTaskBodySubscriber<>(downloadId, downloadTempDirPath);
        return null;
    }
}
