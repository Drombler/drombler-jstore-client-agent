package org.drombler.jstore.client.agent.model;

import org.drombler.jstore.protocol.json.ApplicationId;
import org.drombler.jstore.protocol.json.PreSelectedApplication;
import org.drombler.jstore.protocol.json.Store;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class PreSelectedApplicationRegistry {
    private final Map<String, Map<ApplicationId, PreSelectedApplication>> preSelectedApplicationMap = new HashMap<>();

    public boolean containsPreSelectedApplication(Store store, ApplicationId applicationId) {
        return preSelectedApplicationMap.containsKey(store.getId())
                && preSelectedApplicationMap.get(store.getId()).containsKey(applicationId);
    }

    public Collection<PreSelectedApplication> getPreSelectedApplication(Store store) {
        return preSelectedApplicationMap.getOrDefault(store.getId(), Collections.emptyMap()).values();
    }

    public PreSelectedApplication getPreSelectedApplication(Store store, ApplicationId applicationId) {
        if (preSelectedApplicationMap.containsKey(store.getId())) {
            Map<ApplicationId, PreSelectedApplication> applicationIdPreSelectedApplicationMap = preSelectedApplicationMap.get(store.getId());
            if (applicationIdPreSelectedApplicationMap.containsKey(applicationId)) {
                return applicationIdPreSelectedApplicationMap.get(applicationId);
            }
        }
        return null;
    }

    public void registerPreSelectedApplication(Store store, PreSelectedApplication preSelectedApplication) {
        if (!preSelectedApplicationMap.containsKey(store.getId())) {
            preSelectedApplicationMap.put(store.getId(), new HashMap<>());
        }
        Map<ApplicationId, PreSelectedApplication> applicationIdPreSelectedApplicationMap = preSelectedApplicationMap.get(store.getId());
        if (applicationIdPreSelectedApplicationMap.containsKey(preSelectedApplication.getApplicationId())) {
            throw new IllegalArgumentException("PreSelectedApplication with application id " + preSelectedApplication.getApplicationId() + " already registered!");
        }
        applicationIdPreSelectedApplicationMap.put(preSelectedApplication.getApplicationId(), preSelectedApplication);
    }

}
