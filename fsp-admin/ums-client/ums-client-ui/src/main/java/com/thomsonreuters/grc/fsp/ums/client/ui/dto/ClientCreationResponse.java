package com.thomsonreuters.grc.fsp.ums.client.ui.dto;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Response of ClientCreation operation; includes the ids for the newly generated Client group
 * and its Admin user.
 */
public class ClientCreationResponse {

    /**
     * Id of the newly generated Client group.
     */
    private String clientId;

    /**
     * Id of the Admin user for that group.
     */
    private String userId;


    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
