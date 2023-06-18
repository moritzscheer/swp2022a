package de.uol.swp.common.user.request;

import de.uol.swp.common.user.User;
import de.uol.swp.common.user.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RegisterUserRequestTest {

    private RegisterUserRequest registerUserRequest;

    @BeforeEach
    void setUp() {
        UserDTO user = new UserDTO("username", "password", "email");
        registerUserRequest = new RegisterUserRequest(user);
    }

    @Test
    void testGetUser() {
        User user = registerUserRequest.getUser();
        assertNotNull(user);
        assertEquals("username", user.getUsername());
        assertEquals("password", user.getPassword());
    }

    @Test
    void testAuthorizationNeeded() {
        assertFalse(registerUserRequest.authorizationNeeded());
    }

    @Test
    void testEquals() {
        UserDTO user1 = new UserDTO("username", "password", "email");
        UserDTO user2 = new UserDTO("username", "password", "email");
        UserDTO user3 = new UserDTO("username2", "password", "email");


        RegisterUserRequest request1 = new RegisterUserRequest(user1);
        RegisterUserRequest request2 = new RegisterUserRequest(user2);
        RegisterUserRequest request3 = new RegisterUserRequest(user3);

        assertEquals(request1, request1); // Same object
        assertEquals(request1, request2); // Equal objects
        assertNotEquals(request1, request3); // Different objects
    }
}
