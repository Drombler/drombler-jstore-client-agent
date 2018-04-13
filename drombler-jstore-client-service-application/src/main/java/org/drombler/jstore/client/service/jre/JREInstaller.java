package org.drombler.jstore.client.service.jre;

import java.nio.file.Path;

/**
 *
 * @author puce
 */


public interface JREInstaller {
    void installJRE(Path installationDirPath);
    void uninstallJRE(Path installationDirPath);
}
