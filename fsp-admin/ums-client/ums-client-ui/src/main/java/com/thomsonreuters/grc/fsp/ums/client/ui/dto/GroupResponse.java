package com.thomsonreuters.grc.fsp.ums.client.ui.dto;

import java.util.LinkedList;
import java.util.List;

/**
 * Data transfer object for Group
 *
 * @author Bhaskar Rao <Bhaskar.Rao@thomsonreuters.com>
 * @since 29/08/12
 */
public class GroupResponse {

    /**
     * Group identifier
     */
    private String id;

    /**
     * Name of the group
     */
    private String name;

    /**
     * Indicates the presence of child groups
     */
    private Boolean hasChildren = Boolean.FALSE;

    /**
     * List of sub groups associated with this group
     */
    private List<GroupResponse> children = new LinkedList<>();

    /**
     * Getter for {@code id}
     *
     * @return - the group identifier
     */
    public String getId() {
        return id;
    }

    /**
     * Setter for {@code id}
     *
     * @param id - the group identifier
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Getter for {@code name}
     *
     * @return - the group name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for {@code name}
     *
     * @param name - the group name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for {@code hasChildren}
     *
     * @return - True if the group has children, false otherwise
     */
    public Boolean getHasChildren() {
        return hasChildren;
    }

    /**
     * Setter for {@code id}
     *
     * @param hasChildren - boolean value true if there are children
     */
    public void setHasChildren(Boolean hasChildren) {
        this.hasChildren = hasChildren;
    }

    /**
     * Getter for {@code children}
     *
     * @return the list of sub groups
     */
    public List<GroupResponse> getChildren() {
        return children;
    }

    /**
     * Setter for {@code children}
     *
     * @param children - list of sub groups
     */
    public void setChildren(List<GroupResponse> children) {
        this.children = children;
    }
}
