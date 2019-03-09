package org.drombler.jstore.client.agent.startup.app.task.model;

import org.drombler.jstore.protocol.json.UpgradableApplication;

import java.nio.file.Path;

public class ApplicationUpdateInfo {
    private final UpgradableApplication upgradableApplication;
    private final Path tmpFilePath;

    public ApplicationUpdateInfo(UpgradableApplication upgradableApplication, Path tmpFilePath) {
        this.upgradableApplication = upgradableApplication;
        this.tmpFilePath = tmpFilePath;
    }
}
