package org.drombler.jstore.client.service.app.task;

import org.drombler.jstore.client.service.app.ApplicationInfo;

import java.util.Set;
import java.util.concurrent.Callable;

/**
 * Updates the info about available application versions of the selected applications.
 */
public class ApplicationInfoUpdater implements Callable<ApplicationInfoUpdateInfo> {

    public ApplicationInfoUpdater(Set<ApplicationInfo> selectedApplications) {

    }

    @Override
    public ApplicationInfoUpdateInfo call() throws Exception {
        return null;
    }
}
