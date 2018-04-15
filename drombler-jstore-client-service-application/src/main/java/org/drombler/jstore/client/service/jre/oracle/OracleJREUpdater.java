package org.drombler.jstore.client.service.jre.oracle;

import org.drombler.jstore.client.service.jre.JREUpdateInfo;

import java.util.concurrent.Callable;

/**
 * Updates an Oracle JRE.
 */
public class OracleJREUpdater implements Callable<JREUpdateInfo> {
    public OracleJREUpdater(String version) {

    }

    @Override
    public JREUpdateInfo call()  {
        return null;
    }
}
