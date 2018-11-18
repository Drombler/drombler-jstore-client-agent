package org.drombler.jstore.client.agent.startup.commons;


import java.io.IOException;
import java.net.CookieManager;
import java.net.HttpCookie;
import java.net.ProxySelector;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public class HttpClientUtils {


    public static void downloadFile(HttpClient httpClient, URI uri, Path targetFile) throws InterruptedException, ExecutionException, TimeoutException, IOException {
//        HttpCookie oraclelicense = httpClient.cookieManager().get().getCookieStore().getCookies().stream()
//                .filter(httpCookie -> httpCookie.getName().equals("oraclelicense"))
//                .findFirst().get();
        HttpCookie oraclelicense = new HttpCookie("oraclelicense", "accept-securebackup-cookie");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
//                    .timeout(Duration.of(10, ChronoUnit.MINUTES))
                .setHeader("Cookie", oraclelicense.getName() + "=" + oraclelicense.getValue())
                .GET()
                .build();
//            CompletableFuture<HttpResponse<Path>> completableFuture = httpClient.sendAsync(request, HttpResponse.BodyHandler.asFile(targetFile));
//            HttpResponse<Path> pathHttpResponse = completableFuture.get(10, TimeUnit.MINUTES);
        HttpResponse<Path> pathHttpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofFile(targetFile));
//        HttpResponse<Path> pathHttpResponse = completableFuture.get(10, TimeUnit.MINUTES);
        System.out.println(pathHttpResponse.statusCode());
        Path body = pathHttpResponse.body();
        System.out.println(Files.size(body));
        System.out.println(pathHttpResponse.request().headers().map());

    }

    public static HttpClient createHttpClient() {
        CookieManager cookieManager = new CookieManager();
        return HttpClient
                .newBuilder()
                .proxy(ProxySelector.getDefault())
                .cookieHandler(cookieManager)
                .followRedirects(HttpClient.Redirect.ALWAYS)
                .build();
    }
}
