package org.drombler.jstore.client.agent.startup.app.task.model;

import org.drombler.jstore.protocol.json.Store;
import org.drombler.jstore.protocol.json.UpgradableApplication;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApplicationInfoUpdateInfo {
    private final Map<String, List<UpgradableApplication>> upgradableApplicationMap = new HashMap<>();

    public void addUpgradableApplicationInfo(Store store, List<UpgradableApplication> applicationVersionInfos) {
        upgradableApplicationMap.put(store.getId(), applicationVersionInfos);
    }

    public List<UpgradableApplication> getUpgradableApplicationInfo(Store store) {
        return upgradableApplicationMap.get(store.getId());
    }
}
