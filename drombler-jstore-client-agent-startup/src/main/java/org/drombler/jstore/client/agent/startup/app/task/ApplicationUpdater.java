package org.drombler.jstore.client.agent.startup.app.task;

import org.drombler.jstore.client.agent.model.ApplicationId;
import org.drombler.jstore.client.agent.model.StoreInfo;
import org.drombler.jstore.client.agent.startup.app.ApplicationInfo;
import org.drombler.jstore.client.agent.startup.app.task.model.ApplicationUpdateInfo;
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


    public ApplicationUpdater(ApplicationInfo applicationInfo) {

    }

    @Override
    public ApplicationUpdateInfo call() throws Exception {
        updateApplication();
        return null;
    }

    private void updateApplication() throws IOException {
        StoreInfo storeInfo = null;
        ApplicationId applicationId = null;
        Path downloadedApplication = downloadApplication();
        Path applicationTmpDirPath = unzipApplicationInTmp(downloadedApplication);
        Path applicationPath = null;
        backupApplication(null, null);
        deleteOldApplication();
        moveNewApplication(applicationTmpDirPath, storeInfo, applicationId);
        deleteDownloadedApplication(downloadedApplication);
    }


    private Path downloadApplication() {
//Files.createTempFile();
//        File file;
//        file.deleteOnExit();
        return null;
    }

    private Path unzipApplicationInTmp(Path downloadedApplication) throws IOException {
        ApplicationId applicationId = null;
        URI jarURI = JarFiles.getJarURI((URI) null);
        try (FileSystem zipFileSystem = JarFiles.newJarFileSystem(jarURI)) {
            Path tempDirectory = Files.createTempDirectory(applicationId.toFormattedString());
            CopyFileVisitor.copy(zipFileSystem.getPath("/"), tempDirectory);
        }
        return null;
    }

    private void backupApplication(StoreInfo storeInfo, ApplicationId applicationId) throws IOException {
        URI backupZipURI = JarFiles.getJarURI((URI) null);
        try (FileSystem zipFileSystem = JarFiles.newJarFileSystem(backupZipURI)) {
            Path dir = null;
            CopyFileVisitor.copy(dir, zipFileSystem.getPath("/"));
        }
    }

    private void deleteOldApplication() {
// delete dir recursively
    }

    private void moveNewApplication(Path applicationTmpDirPath, StoreInfo storeInfo, ApplicationId applicationId) {
//Files.move(applicationTmpDirPath);
    }

    private void deleteDownloadedApplication(Path downloadedApplication) throws IOException {
        Files.delete(downloadedApplication);
    }

}
