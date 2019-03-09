package org.drombler.jstore.client.agent.startup.jre;

import org.drombler.jstore.client.agent.download.DownloadTask;
import org.drombler.jstore.client.agent.startup.integration.JStoreClient;
import org.drombler.jstore.client.agent.startup.integration.JStoreClientException;
import org.drombler.jstore.client.agent.startup.jre.model.JREUpdateInfo;
import org.drombler.jstore.protocol.json.UpgradableJRE;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;

/**
 * Updates the info about available JRE versions.
 */
public class JREUpdater implements Callable<CompletableFuture<JREUpdateInfo>> {
    private final JStoreClient jStoreClient;
    private final UpgradableJRE upgradableJRE;

    public JREUpdater(JStoreClient jStoreClient, UpgradableJRE upgradableJRE) {
        this.jStoreClient = jStoreClient;
        this.upgradableJRE = upgradableJRE;
    }

    @Override
    public CompletableFuture<JREUpdateInfo> call() throws JStoreClientException {
        DownloadTask<UpgradableJRE> downloadTask = jStoreClient.getJRE(upgradableJRE);
        downloadTask.getResponse()
                .thenApply(response -> new JREUpdateInfo(upgradableJRE, response.body()))
                .exceptionally(ex -> {
                    System.out.println(ex);
                    return null;
                });
        return null;
    }
}
