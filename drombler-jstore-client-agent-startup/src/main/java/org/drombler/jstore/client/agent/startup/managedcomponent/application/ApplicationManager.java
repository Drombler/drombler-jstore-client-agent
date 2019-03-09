package org.drombler.jstore.client.agent.startup.managedcomponent.application;

import org.drombler.jstore.protocol.json.Application;
import org.drombler.jstore.protocol.json.Store;

import java.nio.file.Path;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApplicationManager {

    private final Path applicationInstallDirPath;

    private final Map<String, List<Application>> applications = new HashMap<>();

    public ApplicationManager(Path applicationInstallDirPath) {
        this.applicationInstallDirPath = applicationInstallDirPath;
    }

    public List<Application> getInstalledApplications(Store store) {
        return applications.getOrDefault(store.getId(), Collections.emptyList());
    }
}
