package org.drombler.jstore.client.service;

import jdk.incubator.http.HttpClient;
import org.drombler.jstore.client.service.jre.windows.WindowsJRE8Installer;

import java.net.CookieManager;
import java.net.ProxySelector;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.machinepublishers.jbrowserdriver.Timezone;
import com.machinepublishers.jbrowserdriver.JBrowserDriver;
import com.machinepublishers.jbrowserdriver.Settings;
import com.machinepublishers.jbrowserdriver.UserAgent;
import org.drombler.jstore.client.service.jre.windows.WindowsJRE8Installer2;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 *
 * @author puce
 */
public class JStoreClientServiceApplication {

    public static void main(String[] args) throws IOException, InterruptedException, ExecutionException, TimeoutException {
        CookieManager cookieManager = new CookieManager();
        HttpClient httpClient = HttpClient
                .newBuilder()
                .proxy(ProxySelector.getDefault())
                .cookieManager(cookieManager)
                .followRedirects(HttpClient.Redirect.ALWAYS)
                .build();
//
//        WindowsJRE8Installer windowsJRE8Installer = new WindowsJRE8Installer(httpClient);
//        windowsJRE8Installer.installJRE(Paths.get("target", "jstore", "jre"));

        JBrowserDriver driver = new JBrowserDriver(Settings.builder()
                .saveAttachments(true)
                .logJavascript(true)
                .timezone(Timezone.EUROPE_ZURICH)
                .build());
        try {
            System.out.println(driver.attachmentsDir());
            Path path = driver.attachmentsDir().toPath();
            WindowsJRE8Installer2 windowsJRE8Installer2 = new WindowsJRE8Installer2(driver, httpClient);
            Path installationDirPath = Paths.get("target", "jstore", "jre");
            Files.createDirectories(installationDirPath);

            windowsJRE8Installer2.installJRE(installationDirPath);

            try (DirectoryStream<Path> stream = Files.newDirectoryStream(path)) {
                stream.forEach(filePath -> {
                    try {
                        Files.move(filePath, installationDirPath);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        } finally {

        // Close the browser. Allows this thread to terminate.

        driver.quit();
        }
    }
}
