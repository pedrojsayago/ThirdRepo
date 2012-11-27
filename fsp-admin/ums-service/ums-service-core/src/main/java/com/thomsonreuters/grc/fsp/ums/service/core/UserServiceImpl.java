package com.thomsonreuters.grc.fsp.ums.service.core;

import com.thomsonreuters.grc.fsp.ums.client.core.UserService;
import com.thomsonreuters.grc.fsp.ums.dl.repository.UserRepository;
import com.thomsonreuters.grc.fsp.ums.domain.User;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * User Service Implementation
 */
@Transactional
public class UserServiceImpl implements UserService {

    /**
     * User repository
     */
    private UserRepository userRepository;

    @Required
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public User getUser(String identifier) {
        Assert.notNull(identifier, "User Identifier is null");

        User user = userRepository.findOne(identifier);

        return user;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public User getEstEsMiMierdaMetodo(String identifier) {
        Assert.notNull(identifier, "User Identifier is null");

        User user = userRepository.findOne(identifier);

        return user;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public User getUserByName(String username) {
        Assert.notNull(username, "User Identifier is null");

        User user = userRepository.findByUsername(username);

        return user;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User createUser(User user) {
        Assert.notNull(user, "User is null");

        User savedUser = userRepository.save(user);

        return savedUser;
    }
}
