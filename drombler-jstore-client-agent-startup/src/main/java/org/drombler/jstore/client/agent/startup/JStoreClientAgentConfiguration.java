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

    public JStoreClientAgentConfiguration(CommandLineArgs commandLineArgs) throws URISyntaxException, IOException, MissingPropertyException {
        super(commandLineArgs);
        String applicationsInstallDirPathString = getUserConfigProps().getProperty("jstore.applications.installdir", "${user.home}/jstore-applications");
        Path applicationsInstallDirPath = Paths.get(applicationsInstallDirPathString);
        if (!Files.exists(applicationsInstallDirPath)) {
            Files.createDirectories(applicationsInstallDirPath);
        }
    }
}
