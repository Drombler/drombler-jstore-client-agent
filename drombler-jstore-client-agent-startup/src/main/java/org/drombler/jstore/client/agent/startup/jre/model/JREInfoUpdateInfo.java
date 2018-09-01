package org.drombler.jstore.client.agent.startup.jre.model;

import org.drombler.jstore.protocol.json.UpgradableJRE;

import java.util.List;

public class JREInfoUpdateInfo {
    private List<UpgradableJRE> upgradableJREs;

    public void setUpgradableJREs(List<UpgradableJRE> upgradableJREs) {
        this.upgradableJREs = upgradableJREs;
    }

    public List<UpgradableJRE> getUpgradableJREs() {
        return upgradableJREs;
    }
}
