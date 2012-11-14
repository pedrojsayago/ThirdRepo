package com.thomsonreuters.grc.fsp.ums.service.ui;

import com.thomsonreuters.grc.fsp.ums.client.core.GroupService;
import com.thomsonreuters.grc.fsp.ums.client.core.UserService;
import com.thomsonreuters.grc.fsp.ums.client.ui.dto.ClientCreationResponse;
import com.thomsonreuters.grc.fsp.ums.client.core.dto.ClientRequest;
import com.thomsonreuters.grc.fsp.ums.client.core.dto.ClientResponse;
import com.thomsonreuters.grc.fsp.ums.client.core.exception.CreateClientException;
import com.thomsonreuters.grc.fsp.ums.client.ui.GroupUIService;
import com.thomsonreuters.grc.fsp.ums.client.ui.dto.GroupResponse;
import com.thomsonreuters.grc.fsp.ums.client.ui.dto.IdentifierRequest;
import com.thomsonreuters.grc.fsp.ums.domain.Group;
import com.thomsonreuters.grc.fsp.ums.domain.User;
import com.thomsonreuters.grc.fsp.ums.service.ui.util.Mapper;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.text.MessageFormat;
import java.util.List;

import static org.springframework.util.Assert.notEmpty;
import static org.springframework.util.Assert.notNull;

/**
 * Group UI Service Implementation
 */
@Transactional
public class GroupUIServiceImpl implements GroupUIService {

    /**
     * Logger
     */
    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * Constant for group zero
     * <p/>
     * This is used to create a dummy parent to link sub-groups from different tree
     * structures. (used for the initial request to load groups for a user)
     */
    private static final String GROUP_ZERO_ID = "0";

    /**
     * UMS group service
     */
    private GroupService groupService;

    /**
     * UMS user service
     */
    private UserService userService;

    /**
     * Object mapper
     */
    private Mapper mapper;

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public GroupResponse getScreeningGroup(IdentifierRequest identifierRequest) {
        GroupResponse groupDTO = null;
        logger.debug("identifierRequest :{}", ToStringBuilder.reflectionToString(identifierRequest));

        notNull(identifierRequest);
        notEmpty(identifierRequest.getIdentifiers());

        String groupId = identifierRequest.getIdentifiers().get(0);
        notNull(groupId);

        // Load just the required group and immediate children, if Lazy is set to true
        if (identifierRequest.isLazy()) {
            groupDTO = lazyLoadGroups(groupId);
        }

        return groupDTO;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<ClientResponse> getClients() {
        List<Group> listOfClients = groupService.loadClients();
        logger.debug(MessageFormat.format("Number of client groups {1}", listOfClients.size()));
        /*
            TODO: Status of the client is made default Active in {@see ClientResponse},
            hence should be removed when we have clear information on Client status
         */
        List<ClientResponse> clientResponse = mapper.mapAsList(listOfClients, ClientResponse.class);

        return clientResponse;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClientCreationResponse createClient(ClientRequest clientRequest) {

        Assert.notNull(clientRequest, "Client request should not be null");
        Assert.hasLength(clientRequest.getClientName(), "Client name should not be empty");

        ClientCreationResponse clientCreationResponse = null;

        //convert dto to domain
        Group domainClientGroup = mapper.map(clientRequest, Group.class);

        //construct user object
        User domainUser = new User.Builder().build();
        // TODO Populate the user's name when these fields exist on the domain object.
        domainUser.setUsername(clientRequest.getClientAdminEmail());
        try {
            //create the client
            Group savedGroup = groupService.createClient(domainClientGroup);

            //set the user values and call service to create new user
            domainUser.setClient(savedGroup);
            User savedUser = userService.createUser(domainUser);

            //construct a client response
            clientCreationResponse = new ClientCreationResponse();
            clientCreationResponse.setClientId(savedGroup.getId());
            clientCreationResponse.setUserId(savedUser.getId());
        } catch (CreateClientException createClientException) {
            logger.error("Client Exception Thrown", createClientException);
            //TODO error message to be send to front end
        }
        return clientCreationResponse;
    }

    /**
     * Method loads the immediate children for a given group identifier
     * <p/>
     * In case of group Id '0' or the initial request a dummy parent group with all the groups
     * the user has access to is returned.
     *
     * @param groupId - group identifier
     * @return - {@link GroupResponse}
     */
    private GroupResponse lazyLoadGroups(String groupId) {
        GroupResponse parent;

        if (GROUP_ZERO_ID.equals(groupId)) {
            logger.debug("Fetching groups for user");

            // The initial request - fetch the groups for the authenticated user.
            // With Security in place, we should just be able to load the groups
            // from the User Detail object

            // For now, use a dummy user
            User user = userService.getUserByName("msrrmadmin");
            notNull(user, "User not found");

            List<GroupResponse> groupDTOs = mapper.mapAsList(user.getGroups(), GroupResponse.class);

            // The first request will have a dummy parent as 'Group Zero'
            // We only need to send the first level or the immediate children
            for (GroupResponse groupDTO : groupDTOs) {
                groupDTO.setChildren(null);
            }

            parent = new GroupResponse();
            parent.setId(GROUP_ZERO_ID);
            parent.setName(GROUP_ZERO_ID);
            parent.setHasChildren(Boolean.TRUE);
            parent.setChildren(groupDTOs);

        } else {
            logger.debug("Fetching group {}", groupId);
            Group parentGroup = groupService.getGroup(groupId);
            parent = mapper.map(parentGroup, GroupResponse.class);
        }

        return parent;
    }

    @Required
    public void setGroupService(GroupService groupService) {
        this.groupService = groupService;
    }

    @Required
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Required
    public void setMapper(Mapper mapper) {
        this.mapper = mapper;
    }
}
