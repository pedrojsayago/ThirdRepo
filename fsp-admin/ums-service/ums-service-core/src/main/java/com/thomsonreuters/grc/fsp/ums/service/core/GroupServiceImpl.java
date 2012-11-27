package com.thomsonreuters.grc.fsp.ums.service.core;

import com.mysema.query.types.Predicate;
import com.thomsonreuters.grc.fsp.common.base.exception.ObjectNotFoundException;
import com.thomsonreuters.grc.fsp.common.base.type.ums.GroupType;
import com.thomsonreuters.grc.fsp.ums.client.core.GroupService;
import com.thomsonreuters.grc.fsp.ums.dl.repository.GroupRepository;
import com.thomsonreuters.grc.fsp.ums.domain.Group;
import com.thomsonreuters.grc.fsp.ums.domain.QGroup;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.util.Assert.*;

/**
 * Group Service Implementation
 */
@Transactional
public class GroupServiceImpl implements GroupService {

    /**
     * Logger
     */

fkdjslfkdslfjslkjfhdkjhfkdhf;kdhfkjh shfjshfjsfkdsh jsh fhhsjl hs fhs khs;khs;jh f
    private final Logger logger = LoggerFactory.getLogger(GroupServiceImpl.class);

    /**
     * Delimiter for paths
     */
    private static final String PATH_DELIMITER = "/";

    /**
     * Group repository
     */
    private GroupRepository groupRepository;

    @Required
    public void setGroupRepository(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Group addGroup(String parentGroupId, String groupName) {
        hasText(parentGroupId);
        hasText(groupName);

        logger.debug("add group with name {} and parent {}", groupName, parentGroupId);

        // load parent
        Group parent = loadGroupById(parentGroupId);
        notNull(parent.getPath());
        notNull(parent.getDepth());

        // validate if the group name is unique among siblings
        validateGroupName(parentGroupId, groupName);

        // Set the leaf to false in parent group
        if (parent.isLeaf()) {
            parent.setLeaf(false);
            parent = groupRepository.save(parent);
        }

        Group child = new Group.Builder().withName(groupName).withParent(parent).build();

        return saveGroup(child);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Group addClientGroup(String groupName) {
        hasText(groupName);

        Group clientGroup = new Group.Builder().build();
        clientGroup.setName(groupName);

        return createClient(clientGroup);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public Group getGroup(String groupId) {
        return loadGroupById(groupId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<Group> getChildren(String groupId) {
        hasText(groupId);
        List<Group> children = groupRepository.findByParentId(groupId);

        return children;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<String> getClientHierarchy(String groupId) {
        hasText(groupId);

        Group group = loadGroupById(groupId);
        hasText(group.getPath(), "Invalid path for group");

        String clientId = StringUtils.substringBetween(group.getPath(), PATH_DELIMITER);
        hasText(clientId, "Invalid path for group");

        StringBuilder pathPrefix = new StringBuilder(PATH_DELIMITER);
        pathPrefix.append(clientId);
        logger.debug("loading hierarchy for prefix {}", pathPrefix);

        List<String> groupIds = groupRepository.findByPathStartingWith(pathPrefix.toString());

        return groupIds;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Group> loadClients() {
        List<Group> clients = groupRepository.findByType(GroupType.CLIENT);
        logger.debug("found client groups : " + clients.size());

        return clients;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Group createClient(Group clientGroup) {
        validateGroupName(null, clientGroup.getName());

        // Specific fields for Group of type CLIENT
        clientGroup.setType(GroupType.CLIENT);
        clientGroup.setLeaf(true);

        return saveGroup(clientGroup);
    }

    /**
     * Utility method to load group by ID
     *
     * @param groupId - group identifier
     * @return - {@link Group}
     */
    @Transactional(readOnly = true)
    private Group loadGroupById(String groupId) {
        hasText(groupId);
        logger.debug("fetching group with id {} ", groupId);

        Group group = groupRepository.findOne(groupId);
        if (group == null) {
            throw new ObjectNotFoundException(String.format("Group %s not found", groupId));
        }

        return group;
    }

    /**
     * Saves a group, ensuring that the path and depth are updated correctly.
     *
     * @param group - the {@link Group} to save
     * @return - the updated {@link Group}
     */
    private Group saveGroup(Group group) {
        // set the depth and path if they are not defined
        if (group.getDepth() == null) {
            group.setDepth(0);
        }
        if (group.getPath() == null) {
            group.setPath(PATH_DELIMITER);
        }

        // TODO: The following save method should be removed when we have the ID generation in place
        //  Path generation will use the Ids
        group = groupRepository.save(group);

        // Update path and depth
        updatePath(group);
        updateDepth(group);

        logger.debug("saving group with path {} and depth as {}", group.getPath(), group.getDepth());
        group = groupRepository.save(group);

        return group;
    }

    /**
     * The method derives the path for a new group using the parent group's path and  id.
     *
     * If the parent group is null (a root level group) then the path is set as PATH_DELIMITER
     *
     * The generated path will be of following the format :
     *
     * {@code <PATH_DELIMITER><PARENT_GROUP_PATH><PATH_DELIMITER><GROUP_ID><PATH_DELIMITER>}
     *
     * Ex: If parent group' path is /1/2/ and groupID as 3 then the generated path will be /1/2/3/
     *
     * @param group -group to be persisted
     */
    @Transactional(readOnly = true)
    private void updatePath(Group group) {
        StringBuilder pathBuilder;
        String path;

        // If the Group is a 'root' level group
        if (group.getParent() == null) {
            pathBuilder = new StringBuilder(PATH_DELIMITER);
            pathBuilder.append(group.getId());
            pathBuilder.append(PATH_DELIMITER);

            path = pathBuilder.toString();
            logger.debug("Setting path for root group");

        } else {
            notNull(group.getParent().getPath(), "Parent group is invalid");

            pathBuilder = new StringBuilder(group.getParent().getPath());
            pathBuilder.append(group.getId());
            pathBuilder.append(PATH_DELIMITER);

            path = pathBuilder.toString();
        }
        group.setPath(path);
    }

    /**
     * Updates the depth of the group. If the group is the root of a hierarchy then the depth set
     * to 1. For child groups,the depth is set as the parent group's depth + 1
     *
     * @param group - group to be persisted
     */
    @Transactional(readOnly = true)
    private void updateDepth(Group group) {
        if (group.getParent() == null) {
            group.setDepth(1);
        } else {
            notNull(group.getParent().getDepth(), "Parent group is invalid");
            group.setDepth(group.getParent().getDepth() + 1);
        }
        logger.debug("depth is {}", group.getDepth());
    }

    /**
     * Method checks if the parent group already has a child with the given name
     *
     * @param parentGroupId  parent group identifier
     * @param childGroupName group name of the new child group
     */
    @Transactional(readOnly = true)
    private void validateGroupName(String parentGroupId, String childGroupName) {

        Predicate parentPredicate = null;
        if (parentGroupId == null) {
            parentPredicate = QGroup.group.parent.id.isNull();
        } else {
            parentPredicate = QGroup.group.parent.id.eq(parentGroupId);
        }
        Predicate groupPredicate = QGroup.group.name.eq(childGroupName).and(parentPredicate);
        Group group = groupRepository.findOne(groupPredicate);
        isNull(group, "Group with the given name already exists");
    }
}
