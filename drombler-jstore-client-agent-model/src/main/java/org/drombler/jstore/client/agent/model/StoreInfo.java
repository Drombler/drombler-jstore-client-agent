
package org.drombler.jstore.client.agent.model;


import org.drombler.jstore.client.agent.model.json.StoreInfoType;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Comparator;
import java.util.Objects;


/**
 * The application store info
 * <p>
 * The info about an application store.
 */
public class StoreInfo implements Comparable<StoreInfo> {

    /**
     * The store id
     * <p>
     * The store id of the store which uniquely identifies an application store.
     */
    private final String storeId;
    /**
     * The endpoint of the application store
     * <p>
     * The endpoint of the REST resources of the application store.
     */
    private URI endpoint;

    public StoreInfo(String storeId) {
        this.storeId = storeId;
    }


    /**
     * The store id
     * <p>
     * The store id of the store which uniquely identifies an application store.
     */
    public String getStoreId() {
        return storeId;
    }


    /**
     * The endpoint of the application store
     * <p>
     * The endpoint of the REST resources of the application store.
     */
    public URI getEndpoint() {
        return endpoint;
    }

    /**
     * The endpoint of the application store
     * <p>
     * The endpoint of the REST resources of the application store.
     */
    public void setEndpoint(URI endpoint) {
        this.endpoint = endpoint;
    }

    @Override
    public int hashCode() {
        return Objects.hash(storeId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StoreInfo)) return false;
        StoreInfo storeInfo = (StoreInfo) o;
        return Objects.equals(storeId, storeInfo.storeId);
    }

    private static final Comparator<StoreInfo> NATURAL_ORDER_COMPARATOR = Comparator.comparing(StoreInfo::getStoreId);

    @Override
    public int compareTo(StoreInfo o) {
        return NATURAL_ORDER_COMPARATOR.compare(this, o);
    }

    @Override
    public String toString() {
        return "StoreInfo{" +
                "storeId='" + storeId + '\'' +
                ", endpoint=" + endpoint +
                '}';
    }

    public static StoreInfo fromJSON(StoreInfoType jsonStoreInfo) throws URISyntaxException {
        StoreInfo storeInfo = new StoreInfo(jsonStoreInfo.getStoreId());
        String endpoint = jsonStoreInfo.getEndpoint().endsWith("/") ? jsonStoreInfo.getEndpoint() : jsonStoreInfo.getEndpoint() + "/";
        storeInfo.setEndpoint(new URI(endpoint));
        return storeInfo;
    }

    public void merge(StoreInfo storeInfo) {
        if (!storeInfo.equals(this)) {
            throw new IllegalArgumentException("Not representing the same store! This StoreInfo: " + toString()
                    + " ; Other StoreInfo: " + storeInfo);
        }
        if (!getEndpoint().equals(storeInfo.getEndpoint())) {
            setEndpoint(storeInfo.getEndpoint());
        }
    }
}
