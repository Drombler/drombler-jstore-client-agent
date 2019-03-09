package org.drombler.jstore.client.agent.startup.app.task;

import org.drombler.jstore.client.agent.download.DownloadTask;
import org.drombler.jstore.client.agent.startup.app.task.model.ApplicationUpdateInfo;
import org.drombler.jstore.client.agent.startup.integration.JStoreClient;
import org.drombler.jstore.protocol.json.ApplicationId;
import org.drombler.jstore.protocol.json.Store;
import org.drombler.jstore.protocol.json.UpgradableApplication;
import org.softsmithy.lib.nio.file.CopyFileVisitor;
import org.softsmithy.lib.nio.file.JarFiles;

import java.io.IOException;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.Callable;

/**
 * Updates an application.
 */
public class ApplicationUpdater implements Callable<ApplicationUpdateInfo> {


    private final JStoreClient jStoreClient;
    private final UpgradableApplication upgradableApplication;

    public ApplicationUpdater(JStoreClient jStoreClient, UpgradableApplication upgradableApplication) {
        this.jStoreClient = jStoreClient;
        this.upgradableApplication = upgradableApplication;
    }

    @Override
    public ApplicationUpdateInfo call() throws Exception {
        updateApplication();
        return null;
    }

    private void updateApplication() throws IOException {
        Store store = null;
        ApplicationId applicationId = null;
        Path downloadedApplication = downloadApplication();
        Path applicationTmpDirPath = unzipApplicationInTmp(downloadedApplication);
        Path applicationPath = null;
        backupApplication(null, null);
        deleteOldApplication();
        moveNewApplication(applicationTmpDirPath, store, applicationId);
        deleteDownloadedApplication(downloadedApplication);
    }


    private Path downloadApplication() {
        DownloadTask<UpgradableApplication> downloadTask = jStoreClient.getApplication(upgradableApplication);
        downloadTask.getResponse()
                .thenApply(response -> new ApplicationUpdateInfo(upgradableApplication, response.body()))
                .exceptionally(ex -> {
                    System.out.println(ex);
                    return null;
                });
        return null;
    }

    private Path unzipApplicationInTmp(Path downloadedApplication) throws IOException {
        ApplicationId applicationId = null;
        URI jarURI = JarFiles.getJarURI((URI) null);
        try (FileSystem zipFileSystem = JarFiles.newJarFileSystem(jarURI)) {
//            Path tempDirectory = Files.createTempDirectory(applicationId.toFormattedString());
//            CopyFileVisitor.copy(zipFileSystem.getPath("/"), tempDirectory);
        }
        return null;
    }

    private void backupApplication(Store store, ApplicationId applicationId) throws IOException {
        URI backupZipURI = JarFiles.getJarURI((URI) null);
        try (FileSystem zipFileSystem = JarFiles.newJarFileSystem(backupZipURI)) {
            Path dir = null;
            CopyFileVisitor.copy(dir, zipFileSystem.getPath("/"));
        }
    }

    private void deleteOldApplication() {
// delete dir recursively
    }

    private void moveNewApplication(Path applicationTmpDirPath, Store store, ApplicationId applicationId) {
//Files.move(applicationTmpDirPath);
    }

    private void deleteDownloadedApplication(Path downloadedApplication) throws IOException {
        Files.delete(downloadedApplication);
    }

}
