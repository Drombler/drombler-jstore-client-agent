package org.drombler.jstore.client.service;

/**
 *
 * @author puce
 */
public class JStoreClientServiceApplication {

    public static void main(String[] args) {
        HttpClient httpClient = HttpClient
                .newBuilder()
                .proxy(ProxySelector.getDefault())
                .cookieManager(new CookieManager())
                .followRedirects(HttpClient.Redirect.ALWAYS)
                .build();
    }
}
