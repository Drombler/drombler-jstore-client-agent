package org.drombler.jstore.client.agent.startup.app.task.model;

import org.drombler.jstore.protocol.json.Application;

import java.nio.file.Path;

public class ApplicationInstallation {
    private final Application application;
    private final Path applicationDirPath;

    public ApplicationInstallation(Application application, Path applicationDirPath) {
        this.application = application;
        this.applicationDirPath = applicationDirPath;
    }
}
