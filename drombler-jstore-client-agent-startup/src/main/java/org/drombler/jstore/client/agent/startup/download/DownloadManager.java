package org.drombler.jstore.client.agent.startup.download;

import java.nio.file.Path;

public class DownloadManager {
    private final Path downloadTempDirPath;

    public DownloadManager(Path downloadTempDirPath) {
        this.downloadTempDirPath = downloadTempDirPath;
    }
}
