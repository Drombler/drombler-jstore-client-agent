package org.drombler.jstore.client.agent.startup.jre;

import org.drombler.jstore.client.agent.startup.download.DownloadManager;
import org.drombler.jstore.client.agent.startup.integration.JStoreClient;
import org.drombler.jstore.client.agent.startup.integration.JStoreClientException;
import org.drombler.jstore.client.agent.startup.jre.model.JREUpdateInfo;
import org.drombler.jstore.protocol.json.SelectedJRE;
import org.drombler.jstore.protocol.json.SystemInfo;
import org.drombler.jstore.protocol.json.UpgradableJRE;

import java.util.List;
import java.util.concurrent.Callable;

/**
 * Updates the info about available JRE versions.
 */
public class JREUpdater implements Callable<JREUpdateInfo> {
    private final JStoreClient jStoreClient;
    private final SelectedJRE selectedJRE;
    private final SystemInfo systemInfo;
    private final DownloadManager downloadManager;

    public JREUpdater(JStoreClient jStoreClient, SelectedJRE selectedJRE, SystemInfo systemInfo, DownloadManager downloadManager) {
        this.jStoreClient = jStoreClient;
        this.selectedJRE = selectedJRE;
        this.systemInfo = systemInfo;
        this.downloadManager = downloadManager;
    }

    @Override
    public JREUpdateInfo call() throws JStoreClientException {
        List<UpgradableJRE> jre = jStoreClient.getJRE(selectedJRE, systemInfo);
        return null;
    }
}
