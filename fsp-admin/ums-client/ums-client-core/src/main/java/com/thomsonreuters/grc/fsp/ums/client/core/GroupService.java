package com.thomsonreuters.grc.fsp.ums.client.core;

import com.thomsonreuters.grc.fsp.ums.domain.Group;

import java.util.List;

/**
 * Group Service Definition
 */
public interface GroupService {

    /**
     * Method adds a client group (root) with type as {@code GroupType}
     *
     * @param groupName - name of the new group
     *
     * @return - the newly created group {@link Group}
     */
    Group addClientGroup(String groupName);

    /**
     * Method adds a child group to the given parent group.
     *
     * @param parentGroupId - the parent group Id where the new group will be added as a child
     * @param groupName - name of the new group
     *
     * @return - the newly created group {@link Group}
     */
    Group addGroup(String parentGroupId, String groupName);

    /**
     * Method returns the group with the given identifier
     *
     * @param groupId - group identifier
     * @return - matching {@link Group} instance
     */
    Group getGroup(String groupId);

    /**
     * Returns all the immediate children of the given group identifier
     *
     * @param parentGroupId - parent group identifier
     * @return - {@link java.util.List<Group>} of groups
     */
    List<Group> getChildren(String parentGroupId);

    /**
     * Method returns identifiers of all the groups which are descendants of the client(root)
     * of which the given group is part of.
     *
     * @param groupId - existing group within the hierarchy
     * @return - {@link List} of {@link String} identifiers
     */
    List<String> getClientHierarchy(String groupId);

    /**
     * Load all available client {@link Group}.
     *
     * @return - {@link List} of client {@link Group} objects
     */
    List<Group> loadClients();

    /**
     * Create a new Client group.
     *
     * @param clientGroup - the client {@link Group} to be created
     *
     * @return - the new Client {@link Group} object
     */
    Group createClient(Group clientGroup);
}
