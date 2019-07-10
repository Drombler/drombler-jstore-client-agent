package org.drombler.jstore.client.agent.startup;

import org.drombler.commons.client.startup.main.DromblerClientConfiguration;
import org.drombler.commons.client.startup.main.MissingPropertyException;
import org.drombler.commons.client.startup.main.cli.CommandLineArgs;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class JStoreClientAgentConfiguration extends DromblerClientConfiguration {
    private final Path managedComponentsInstallDirPath;

    public JStoreClientAgentConfiguration(CommandLineArgs commandLineArgs) throws URISyntaxException, IOException, MissingPropertyException {
        super(commandLineArgs);
        String managedComponentsInstallDirPathString = getUserConfigProps().getProperty("jstore.managedcomponents.installdir", System.getProperty("user.home") + "/jstore-managedcomponents");
        System.out.println("Managed components install dir: " + managedComponentsInstallDirPathString);
        this.managedComponentsInstallDirPath = Paths.get(managedComponentsInstallDirPathString);
        if (!Files.exists(managedComponentsInstallDirPath)) {
            Files.createDirectories(managedComponentsInstallDirPath);
        }
    }

    public Path getManagedComponentsInstallDirPath() {
        return managedComponentsInstallDirPath;
    }
}
