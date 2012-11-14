package com.thomsonreuters.grc.fsp.ums.client.core.dto;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Response details of client groups.
 */
public class ClientResponse {

    /**
     * Name of the Client.
     */
    private String client;

    /**
     * Status of the Client.
     * TODO Remove this hard coding when this field actually exists on the domain object.
     */
    private String status = "Active";

    /**
     * Saleforce ID of Client.
     */
    private String salesForceID;

    /**
     * Allowed number of users for the Client.
     */
    private String numberOfUser;

    /**
     * Allowed number of screenings for the Client.
     */
    private String numberOfScreenings;

    /**
     * Allowed number of OGS for the Client.
     */
    private String numberOfOGS;

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSalesForceID() {
        return salesForceID;
    }

    public void setSalesForceID(String salesForceID) {
        this.salesForceID = salesForceID;
    }

    public String getNumberOfUser() {
        return numberOfUser;
    }

    public void setNumberOfUser(String numberOfUser) {
        this.numberOfUser = numberOfUser;
    }

    public String getNumberOfScreenings() {
        return numberOfScreenings;
    }

    public void setNumberOfScreenings(String numberOfScreenings) {
        this.numberOfScreenings = numberOfScreenings;
    }

    public String getNumberOfOGS() {
        return numberOfOGS;
    }

    public void setNumberOfOGS(String numberOfOGS) {
        this.numberOfOGS = numberOfOGS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
