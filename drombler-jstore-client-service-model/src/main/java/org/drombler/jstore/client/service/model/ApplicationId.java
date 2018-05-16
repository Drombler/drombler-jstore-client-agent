
package org.drombler.jstore.client.service.model;


import org.drombler.jstore.client.service.model.json.ApplicationIdType;

import java.util.Comparator;
import java.util.Objects;

/**
 * The application id
 * <p>
 * The application id which uniquely identifies an application in a store.
 */
public class ApplicationId implements Comparable<ApplicationId> {

    /**
     * The vendor id
     * <p>
     * The vendor id of the store which uniquely identifies a vendor in the store.
     */
    private final String vendorId;
    /**
     * The vendor application id
     * <p>
     * The vendor specific application id which uniquely identifies an application of the vendor.
     */
    private final String vendorApplicationId;

    public ApplicationId(String vendorId, String vendorApplicationId) {
        this.vendorId = vendorId;
        this.vendorApplicationId = vendorApplicationId;
    }


    /**
     * The vendor id
     * <p>
     * The vendor id of the store which uniquely identifies a vendor in the store.
     */
    public String getVendorId() {
        return vendorId;
    }


    /**
     * The vendor application id
     * <p>
     * The vendor specific application id which uniquely identifies an application of the vendor.
     */
    public String getVendorApplicationId() {
        return vendorApplicationId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(vendorId, vendorApplicationId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ApplicationId)) return false;
        ApplicationId that = (ApplicationId) o;
        return Objects.equals(vendorId, that.vendorId) &&
                Objects.equals(vendorApplicationId, that.vendorApplicationId);
    }

    private static final Comparator<ApplicationId> NATURAL_ORDER_COMPARATOR = Comparator
            .comparing(ApplicationId::getVendorId)
            .thenComparing(ApplicationId::getVendorApplicationId);

    @Override
    public int compareTo(ApplicationId o) {
        return NATURAL_ORDER_COMPARATOR.compare(this, o);
    }

    @Override
    public String toString() {
        return "ApplicationId{" +
                "vendorId='" + vendorId + '\'' +
                ", vendorApplicationId='" + vendorApplicationId + '\'' +
                '}';
    }

    public static ApplicationId fromJSON(ApplicationIdType jsonApplicationId) {
        return new ApplicationId(jsonApplicationId.getVendorId(), jsonApplicationId.getVendorApplicationId());
    }

}
