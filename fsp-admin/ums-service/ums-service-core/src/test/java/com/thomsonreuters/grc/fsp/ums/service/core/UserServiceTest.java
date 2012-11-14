package com.thomsonreuters.grc.fsp.ums.service.core;

import com.thomsonreuters.grc.fsp.ums.dl.repository.UserRepository;
import com.thomsonreuters.grc.fsp.ums.domain.User;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test class for UserService
 *
 * @author Bhaskar Rao <Bhaskar.Rao@thomsonreuters.com>
 * @since 30/08/12
 */
public class UserServiceTest {

    // Class under test
    @InjectMocks
    private UserServiceImpl userService = new UserServiceImpl();

    @SuppressWarnings("unused")
    @Mock
    private UserRepository mockUserRepository;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void canCreateUserService() {
        assertNotNull(userService);
    }

    @Test
    public void canLoadUser() {
        final String USER_ID = "1";

        User user = new User.Builder().build();
        user.setUsername("user name");
        user.setId(USER_ID);

        when(mockUserRepository.findOne(USER_ID)).thenReturn(user);

        assertNotNull(userService.getUser(USER_ID));
        verify(mockUserRepository).findOne(USER_ID);
    }

    @Test
    public void canLoadUserByUsername() {
        String username = "username";

        User user = new User.Builder().build();
        user.setUsername("user name");

        when(mockUserRepository.findByUsername(username)).thenReturn(user);
        assertNotNull(userService.getUserByName("username"));
        verify(mockUserRepository).findByUsername(username);
    }

    /**
     * Test normal user creation.
     */
    @Test
    public void testCreateUserSuccess() {
        final User createdUser = DataUtil.createUser();

        when(mockUserRepository.save(createdUser)).thenReturn(createdUser);

        User user = userService.createUser(createdUser);

        verify(mockUserRepository).save(createdUser);

        assertNotNull("User should not be null.", user);
        assertEquals("User name should match.", DataUtil.CLIENT_ADMIN_EMAIL, user.getUsername());
    }
}
