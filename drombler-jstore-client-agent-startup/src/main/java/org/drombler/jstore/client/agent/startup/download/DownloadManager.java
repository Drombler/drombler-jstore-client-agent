package org.drombler.jstore.client.agent.startup.download;


import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;

public class DownloadManager {
    private final Path downloadTempDirPath;
    private final HttpClient httpClient;

    public DownloadManager(Path downloadTempDirPath, HttpClient httpClient) {
        this.downloadTempDirPath = downloadTempDirPath;
        this.httpClient = httpClient;
    }

    public <K> DownloadTask<K> downloadFile(DownloadId<K> id, HttpRequest request, String fileName) {
//        CompletableFuture<HttpResponse<Path>> response = httpClient.sendAsync(request, HttpResponse.BodyHandler.asFileDownload(downloadTempDirPath));
        CompletableFuture<HttpResponse<Path>> response = null;
//        HttpResponse<Path> response = null;
//        try {
        response = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofFile(downloadTempDirPath.resolve(fileName)));
        return new DownloadTask<K>(id, response);
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

    // TODO: check SecurityManager. See HttpResponse.BodyHandler.asFileDownload
//    public  <K> HttpResponse.BodyHandler<DownloadTask<K>> asDownloadTask(DownloadId<K> id){
//        return new DownloadTaskBodyHandler<>(id, downloadTempDirPath);
//
//    }

}
