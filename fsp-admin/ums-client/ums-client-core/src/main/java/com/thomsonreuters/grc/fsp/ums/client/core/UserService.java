package com.thomsonreuters.grc.fsp.ums.client.core;

import com.thomsonreuters.grc.fsp.ums.domain.User;

/**
 * User Service Definition
 */
public interface UserService {

    /**
     * Method returns the user for the given identifier
     *
     * @param identifier - user identifier
     * @return - {@link User}
     */
    User getUser(String identifier);

    /**
     * Method retrieves user with the specified user name
     *
     * @param username - user name of the user to be fetched
     * @return - {@link User}
     */
    User getUserByName(String username);

    /**
     * Create a new user.
     *
     * @param user - the user to be created
     *
     * @return - the new {@link User} object
     */
    User createUser(User user);
}
