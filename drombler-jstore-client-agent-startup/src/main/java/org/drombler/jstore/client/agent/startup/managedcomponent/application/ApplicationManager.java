package org.drombler.jstore.client.agent.startup.managedcomponent.application;

import java.nio.file.Path;

public class ApplicationManager {

    private final Path applicationInstallDirPath;

    public ApplicationManager(Path applicationInstallDirPath) {
        this.applicationInstallDirPath = applicationInstallDirPath;
    }
}
