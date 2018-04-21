package org.drombler.jstore.client.service.jre.oracle.windows.browserdriver;

import com.machinepublishers.jbrowserdriver.JBrowserDriver;
import jdk.incubator.http.HttpClient;
import jdk.incubator.http.HttpRequest;
import jdk.incubator.http.HttpResponse;

import java.io.IOException;
import java.net.HttpCookie;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public class HttpClientUtils {

    public static void importCookies(HttpClient httpClient, JBrowserDriver driver) {
        driver.manage().getCookies().forEach(cookie -> {
            HttpCookie httpCookie = new HttpCookie(cookie.getName(), cookie.getValue());
            httpCookie.setDomain(cookie.getDomain());
            httpCookie.setPath(cookie.getPath());
            httpCookie.setHttpOnly(cookie.isHttpOnly());
            httpCookie.setSecure(cookie.isSecure());
            httpClient.cookieManager().get().getCookieStore().add(null, httpCookie);
        });
        System.out.println(httpClient.cookieManager().get().getCookieStore().getCookies());
        HttpCookie oraclelicense = httpClient.cookieManager().get().getCookieStore().getCookies().stream()
                .filter(httpCookie -> httpCookie.getName().equals("oraclelicense"))
                .findFirst().get();
        System.out.println(oraclelicense.hasExpired());
    }

    public static void downloadFile(HttpClient httpClient, URI uri, Path targetFile) throws InterruptedException, ExecutionException, TimeoutException, IOException {
        HttpCookie oraclelicense = httpClient.cookieManager().get().getCookieStore().getCookies().stream()
                .filter(httpCookie -> httpCookie.getName().equals("oraclelicense"))
                .findFirst().get();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
//                    .timeout(Duration.of(10, ChronoUnit.MINUTES))
                .setHeader("Cookie", oraclelicense.getName() + "=" + oraclelicense.getValue())
                .GET()
                .build();
//            CompletableFuture<HttpResponse<Path>> completableFuture = httpClient.sendAsync(request, HttpResponse.BodyHandler.asFile(targetFile));
//            HttpResponse<Path> pathHttpResponse = completableFuture.get(10, TimeUnit.MINUTES);
        HttpResponse<Path> pathHttpResponse = httpClient.send(request, HttpResponse.BodyHandler.asFile(targetFile));
//        HttpResponse<Path> pathHttpResponse = completableFuture.get(10, TimeUnit.MINUTES);
        System.out.println(pathHttpResponse.statusCode());
        Path body = pathHttpResponse.body();
        System.out.println(Files.size(body));
        System.out.println(pathHttpResponse.finalRequest().headers().map());

    }
}
