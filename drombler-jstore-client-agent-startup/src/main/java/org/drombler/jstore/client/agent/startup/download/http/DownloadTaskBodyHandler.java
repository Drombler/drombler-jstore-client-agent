package org.drombler.jstore.client.agent.startup.download.http;


import org.drombler.jstore.client.agent.startup.download.DownloadId;
import org.drombler.jstore.client.agent.startup.download.DownloadTask;

import java.net.http.HttpResponse;
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
    public HttpResponse.BodySubscriber<DownloadTask<K>> apply(HttpResponse.ResponseInfo responseInfo) {
        OptionalLong contentLength = responseInfo.headers().firstValueAsLong("Content-Length");
        Path filePath = downloadTempDirPath.resolve(downloadId.toString());
        HttpResponse.BodyHandlers.ofFile(filePath);
//        return new DownloadTaskBodySubscriber<>(downloadId, downloadTempDirPath);
        return null;
    }
}
