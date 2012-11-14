package com.thomsonreuters.grc.fsp.ums.client.core.dto;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Request details for client on-boarding.
 */
public class ClientRequest {

    /**
     * Name of the Client group.
     */
    private String clientName;

    /**
     * Status of the Client.
     */
    private String status;

    /**
     * Salesforce ID of Client.
     */
    private String salesForceId;

    /**
     * Allowed number of users for the Client.
     */
    private String numberOfUser;

    /**
     * Allowed number of screenings for the Client.
     */
    private String numberOfScreenings;

    /**
     * Allowed number of ongoing screenings for the Client.
     */
    private String numberOfOGS;

    /**
     * Name of the compliance leader.
     */
    private String complianceLeader;

    /**
     * e-Mail of the compliance leader.
     */
    private String complianceLeaderEmail;

    /**
     * Name of the Client Admin.
     */
    private String clientAdmin;

    /**
     * e-Mail of the Client Admin.
     */
    private String clientAdminEmail;

    //Subscription Details

    /**
     * Mode of Subscription.
     */
    private String mode;

    /**
     * Period of Subscription.
     */
    private String dateRange;

    /**
     * Products to be Subscribed.
     */
    private String products;

    /**
     * Type of Subscription.
     */
    private String subscriptionType;

    //WatchList

    /**
     * Type of WatchList Screening.
     */
    private String wlScreeningTypes;

    /**
     * Minimum Score of Threshold.
     */
    private String minScoreThreshold;

    /**
     * Inherit OGS from Initial Search.
     */
    private String inheritOGS;

    /**
     * Other Paid Subscriptions.
     */
    private String paidSubscriptions;

    //EiDV

    /**
     * Countries to be subscribed.
     */
    private String countrySubscriptions;

    /**
     * Sub Account Information.
     */
    private String subAccountInfo;


    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSalesForceId() {
        return salesForceId;
    }

    public void setSalesForceId(String salesForceId) {
        this.salesForceId = salesForceId;
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

    public String getComplianceLeader() {
        return complianceLeader;
    }

    public void setComplianceLeader(String complianceLeader) {
        this.complianceLeader = complianceLeader;
    }

    public String getComplianceLeaderEmail() {
        return complianceLeaderEmail;
    }

    public void setComplianceLeaderEmail(String complianceLeaderEmail) {
        this.complianceLeaderEmail = complianceLeaderEmail;
    }

    public String getClientAdmin() {
        return clientAdmin;
    }

    public void setClientAdmin(String clientAdmin) {
        this.clientAdmin = clientAdmin;
    }

    public String getClientAdminEmail() {
        return clientAdminEmail;
    }

    public void setClientAdminEmail(String clientAdminEmail) {
        this.clientAdminEmail = clientAdminEmail;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getDateRange() {
        return dateRange;
    }

    public void setDateRange(String dateRange) {
        this.dateRange = dateRange;
    }

    public String getProducts() {
        return products;
    }

    public void setProducts(String products) {
        this.products = products;
    }

    public String getSubscriptionType() {
        return subscriptionType;
    }

    public void setSubscriptionType(String subscriptionType) {
        this.subscriptionType = subscriptionType;
    }

    public String getWlScreeningTypes() {
        return wlScreeningTypes;
    }

    public void setWlScreeningTypes(String wlScreeningTypes) {
        this.wlScreeningTypes = wlScreeningTypes;
    }

    public String getMinScoreThreshold() {
        return minScoreThreshold;
    }

    public void setMinScoreThreshold(String minScoreThreshold) {
        this.minScoreThreshold = minScoreThreshold;
    }

    public String getInheritOGS() {
        return inheritOGS;
    }

    public void setInheritOGS(String inheritOGS) {
        this.inheritOGS = inheritOGS;
    }

    public String getPaidSubscriptions() {
        return paidSubscriptions;
    }

    public void setPaidSubscriptions(String paidSubscriptions) {
        this.paidSubscriptions = paidSubscriptions;
    }

    public String getCountrySubscriptions() {
        return countrySubscriptions;
    }

    public void setCountrySubscriptions(String countrySubscriptions) {
        this.countrySubscriptions = countrySubscriptions;
    }

    public String getSubAccountInfo() {
        return subAccountInfo;
    }

    public void setSubAccountInfo(String subAccountInfo) {
        this.subAccountInfo = subAccountInfo;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
