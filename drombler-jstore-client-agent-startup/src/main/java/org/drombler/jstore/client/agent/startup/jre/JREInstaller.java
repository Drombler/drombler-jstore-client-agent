package org.drombler.jstore.client.agent.startup.jre;

import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * @author puce
 */


public interface JREInstaller {
    void installJRE(Path installationDirPath) throws InterruptedException, ExecutionException, TimeoutException, IOException;

    void uninstallJRE(Path installationDirPath);
}
