package org.drombler.jstore.client.service.app.task;

import org.drombler.jstore.client.service.app.ApplicationInfo;
import org.drombler.jstore.client.service.app.task.model.ApplicationUpdateInfo;

import java.util.concurrent.Callable;

/**
 * Updates an application.
 */
public class ApplicationUpdater implements Callable<ApplicationUpdateInfo> {


    public ApplicationUpdater(ApplicationInfo applicationInfo) {

    }

    @Override
    public ApplicationUpdateInfo call() throws Exception {
        return null;
    }
}
