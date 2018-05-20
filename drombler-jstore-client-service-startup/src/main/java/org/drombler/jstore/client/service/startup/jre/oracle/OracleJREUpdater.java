package org.drombler.jstore.client.service.startup.jre.oracle;

import jdk.incubator.http.HttpClient;
import org.drombler.jstore.client.service.startup.jre.model.JREUpdateInfo;
import org.drombler.jstore.client.service.startup.jre.oracle.windows.WindowsJRE8Installer2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * Updates an Oracle JRE.
 */
public class OracleJREUpdater implements Callable<JREUpdateInfo> {
    private final HttpClient httpClient;

    public OracleJREUpdater(HttpClient httpClient, String version) {

        this.httpClient = httpClient;
    }

    @Override
    public JREUpdateInfo call() throws IOException, InterruptedException, ExecutionException, TimeoutException {
        //
//        WindowsJRE8Installer windowsJRE8Installer = new WindowsJRE8Installer(httpClient);
//        windowsJRE8Installer.installJRE(Paths.get("target", "jstore", "jre"));

//        JBrowserDriver driver = new JBrowserDriver(Settings.builder()
//                .saveAttachments(true)
//                .logJavascript(true)
//                .timezone(Timezone.EUROPE_ZURICH)
//                .build());

//            System.out.println(driver.attachmentsDir());
//            Path path = driver.attachmentsDir().toPath();
        WindowsJRE8Installer2 windowsJRE8Installer2 = new WindowsJRE8Installer2(httpClient);
        Path installationDirPath = Paths.get("target", "jstore", "jre");
        Files.createDirectories(installationDirPath);

        windowsJRE8Installer2.installJRE(installationDirPath);

//            try (DirectoryStream<Path> stream = Files.newDirectoryStream(path)) {
//                stream.forEach(filePath -> {
//                    try {
//                        Files.move(filePath, installationDirPath);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                });
//            }
        return null;
    }
}
