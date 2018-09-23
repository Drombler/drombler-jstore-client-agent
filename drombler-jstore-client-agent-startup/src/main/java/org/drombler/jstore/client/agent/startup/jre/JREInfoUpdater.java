package org.drombler.jstore.client.agent.startup.jre;

import org.drombler.jstore.client.agent.startup.integration.JStoreClient;
import org.drombler.jstore.client.agent.startup.jre.model.JREInfoUpdateInfo;
import org.drombler.jstore.protocol.json.SelectedJRE;
import org.drombler.jstore.protocol.json.SystemInfo;
import org.drombler.jstore.protocol.json.UpgradableJRE;

import java.util.List;
import java.util.concurrent.Callable;

/**
 * Updates the info about available JRE versions.
 */
public class JREInfoUpdater implements Callable<JREInfoUpdateInfo> {
    private final JStoreClient jStoreClient;
    private final List<SelectedJRE> selectedJREs;
    private final SystemInfo systemInfo;

    public JREInfoUpdater(JStoreClient jStoreClient, List<SelectedJRE> selectedJREs, SystemInfo systemInfo) {
        this.jStoreClient = jStoreClient;
        this.selectedJREs = selectedJREs;
        this.systemInfo = systemInfo;
    }

    @Override
    public JREInfoUpdateInfo call() throws Exception {
        List<UpgradableJRE> upgradableJREs = jStoreClient.startJreVersionSearch(selectedJREs, systemInfo);
        JREInfoUpdateInfo jreInfoUpdateInfo = new JREInfoUpdateInfo();
        jreInfoUpdateInfo.setUpgradableJREs(upgradableJREs);
        return jreInfoUpdateInfo;
    }
}
