package org.drombler.jstore.client.agent.startup.managedcomponent.jre;

import java.nio.file.Path;

public class JREManager {
    private final Path jreInstallDirPath;

    public JREManager(Path jreInstallDirPath) {
        this.jreInstallDirPath = jreInstallDirPath;
    }
}
