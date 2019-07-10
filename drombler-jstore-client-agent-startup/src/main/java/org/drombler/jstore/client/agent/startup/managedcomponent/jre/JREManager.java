package org.drombler.jstore.client.agent.startup.managedcomponent.jre;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class JREManager {
    private final Path jreInstallDirPath;

    public JREManager(Path managedComponentsInstallDirPath) throws IOException {
        this.jreInstallDirPath = managedComponentsInstallDirPath.resolve("jres");
        if (!Files.exists(jreInstallDirPath)) {
            Files.createDirectories(jreInstallDirPath);
        }
    }
}
