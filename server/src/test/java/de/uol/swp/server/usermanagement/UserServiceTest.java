package de.uol.swp.server.usermanagement;

import static org.junit.jupiter.api.Assertions.*;

import com.google.common.eventbus.EventBus;

import de.uol.swp.common.message.ResponseMessage;
import de.uol.swp.common.user.User;
import de.uol.swp.common.user.UserDTO;
import de.uol.swp.common.user.exception.DropUserExceptionMessage;
import de.uol.swp.common.user.request.DropUserRequest;
import de.uol.swp.common.user.request.RegisterUserRequest;
import de.uol.swp.common.user.request.UpdateUserRequest;
import de.uol.swp.common.user.response.UpdatedUserSuccessfulResponse;
import de.uol.swp.common.user.response.UserDroppedSuccessfulResponse;
import de.uol.swp.server.usermanagement.store.MainMemoryBasedUserStore;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("UnstableApiUsage")
class UserServiceTest {

    static final User userToRegister = new UserDTO("Marco", "Marco", "Marco@Grawunder.com");
    static final User userWithSameName = new UserDTO("Marco", "Marco2", "Marco2@Grawunder.com");

    final EventBus bus = new EventBus();
    final MainMemoryBasedUserStore userStore = new MainMemoryBasedUserStore();
    final UserManagement userManagement = new UserManagement(userStore);
    final UserService userService = new UserService(bus, userManagement);



    @Test
    void registerUserTest() {
        final RegisterUserRequest request = new RegisterUserRequest(userToRegister);

        // The post will lead to a call of a UserService function
        bus.post(request);

        // can only test, if something in the state has changed
        final User loggedInUser =
                userManagement.login(userToRegister.getUsername(), userToRegister.getPassword());

        assertNotNull(loggedInUser);
        assertEquals(userToRegister, loggedInUser);
    }

    @Test
    void registerSecondUserWithSameName() {
        final RegisterUserRequest request = new RegisterUserRequest(userToRegister);
        final RegisterUserRequest request2 = new RegisterUserRequest(userWithSameName);

        bus.post(request);
        bus.post(request2);

        final User loggedInUser =
                userManagement.login(userToRegister.getUsername(), userToRegister.getPassword());

        // old user should be still in the store
        assertNotNull(loggedInUser);
        assertEquals(userToRegister, loggedInUser);

        // old user should not be overwritten!
        assertNotEquals(loggedInUser.getEMail(), userWithSameName.getEMail());
    }

    @Test
    public void testOnDropUserRequest() {
        final RegisterUserRequest request = new RegisterUserRequest(userToRegister);
        final DropUserRequest dropRequest = new DropUserRequest(userToRegister);
        userStore.createUser(userToRegister.getUsername(), userToRegister.getPassword(), userToRegister.getEMail());

        bus.post(request);
        bus.post(dropRequest);

        UserDroppedSuccessfulResponse response = new UserDroppedSuccessfulResponse(dropRequest.getUser().getUsername());

        assertEquals(response.getUsername(), dropRequest.getUser().getUsername());
        assertNotNull(response);
        assertTrue(true);
    }

    @Test
    public void testOnUpdateUserRequest() {
        final RegisterUserRequest request = new RegisterUserRequest(userToRegister);
        final UpdateUserRequest updateRequest = new UpdateUserRequest(userToRegister);
        userStore.createUser(userToRegister.getUsername(), userToRegister.getPassword(), userToRegister.getEMail());

        bus.post(request);
        bus.post(updateRequest);

        UpdatedUserSuccessfulResponse response = new UpdatedUserSuccessfulResponse(updateRequest.getUser());

        assertEquals(response.getUpdatedUser(), updateRequest.getUser());
        assertNotNull(response);
        assertTrue(true);
    }
}
