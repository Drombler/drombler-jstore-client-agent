package org.drombler.jstore.client.service.app.task;

import org.drombler.jstore.client.service.app.ApplicationInfo;
import org.drombler.jstore.client.service.app.task.model.ApplicationCleanInfo;

import java.util.Set;
import java.util.concurrent.Callable;

public class ApplicationCleaner implements Callable<ApplicationCleanInfo> {
    public ApplicationCleaner(Set<ApplicationInfo> unusedApplications) {

    }

    @Override
    public ApplicationCleanInfo call() throws Exception {
        return null;
    }
}
