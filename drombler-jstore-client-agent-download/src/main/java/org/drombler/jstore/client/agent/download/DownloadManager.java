package org.drombler.jstore.client.agent.download;


import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;

public class DownloadManager {
    private final Path downloadTempDirPath;
    private final HttpClient httpClient;
    private final DownloadHistory downloadHistory;

    public DownloadManager(Path downloadTempDirPath, HttpClient httpClient, DownloadHistory downloadHistory) {
        this.downloadTempDirPath = downloadTempDirPath;
        this.httpClient = httpClient;
        this.downloadHistory = downloadHistory;
    }

//    public <K> DownloadTask<K> downloadFile(DownloadId<K> id, HttpRequest request) {
//        CompletableFuture<HttpResponse<Path>> response = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofFileDownload(downloadTempDirPath));
//    }

    public <K> DownloadTask<K> downloadFile(DownloadId<K> id, HttpRequest request, String fileName) {
        CompletableFuture<HttpResponse<Path>> response = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofFile(downloadTempDirPath.resolve(fileName)));
        DownloadTask<K> task = new DownloadTask<>(id, response);
        downloadHistory.addDownloadTask(task);
        return task;
    }

    // TODO: check SecurityManager. See HttpResponse.BodyHandler.asFileDownload
//    public  <K> HttpResponse.BodyHandler<DownloadTask<K>> asDownloadTask(DownloadId<K> id){
//        return new DownloadTaskBodyHandler<>(id, downloadTempDirPath);
//
//    }

}
