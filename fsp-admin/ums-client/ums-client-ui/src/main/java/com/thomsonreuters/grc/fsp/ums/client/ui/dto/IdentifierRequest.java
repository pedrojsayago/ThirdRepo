package com.thomsonreuters.grc.fsp.ums.client.ui.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Generic Query class used to map one or more identifier arguments from REST requests.
 *
 * @author Bhaskar Rao <Bhaskar.Rao@thomsonreuters.com>
 * @since 04/09/12
 *
 */
public class IdentifierRequest {

    /**
     * List of string identifiers
     */
    private List<String> identifiers = new ArrayList<>();

    /**
     * If the entities represented by the identifiers to be lazily loaded
     */
    private Boolean lazy = Boolean.FALSE;

    /**
     * Getter for {@code identifiers}
     *
     * @return - list of identifiers
     */
    public List<String> getIdentifiers() {
        return identifiers;
    }

    /**
     * Setter for {@code identifiers}
     *
     * @param identifiers - list of identifiers
     */
    public void setIdentifiers(List<String> identifiers) {
        this.identifiers = identifiers;
    }

    /**
     * Getter for {@code lazy}
     *
     * @return - true if lazy false otherwise
     */
    public Boolean isLazy() {
        return lazy;
    }

    /**
     * Setter for {@code lazy}
     *
     * @param lazy - true if lazy false otherwise
     */
    public void setLazy(Boolean lazy) {
        this.lazy = lazy;
    }
}
