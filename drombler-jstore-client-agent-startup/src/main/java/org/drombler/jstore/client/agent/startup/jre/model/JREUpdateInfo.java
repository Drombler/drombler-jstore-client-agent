package org.drombler.jstore.client.agent.startup.jre.model;

import org.drombler.jstore.protocol.json.UpgradableJRE;

import java.nio.file.Path;

public class JREUpdateInfo {
    private final UpgradableJRE upgradableJRE;
    private final Path tmpFilePath;

    public JREUpdateInfo(UpgradableJRE upgradableJRE, Path tmpFilePath) {
        this.upgradableJRE = upgradableJRE;
        this.tmpFilePath = tmpFilePath;
    }

    public UpgradableJRE getUpgradableJRE() {
        return upgradableJRE;
    }

    public Path getTmpFilePath() {
        return tmpFilePath;
    }
}
