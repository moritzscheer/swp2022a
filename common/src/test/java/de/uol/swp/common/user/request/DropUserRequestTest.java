package de.uol.swp.common.user.request;

import de.uol.swp.common.user.User;
import de.uol.swp.common.user.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DropUserRequestTest {

    private UserDTO user;
    private DropUserRequest dropUserRequest;

    @BeforeEach
    void setUp() {
        user = new UserDTO("username", "password", "email");
        dropUserRequest = new DropUserRequest(user);
    }

    @Test
    void testGetUser() {
        User retrievedUser = dropUserRequest.getUser();
        assertEquals(user, retrievedUser);
    }

    @Test
    void testGetUsername() {
        String username = dropUserRequest.getUsername();
        assertEquals("username", username);
    }

    @Test
    void testAuthorizationNeeded() {
        assertFalse(dropUserRequest.authorizationNeeded());
    }

    @Test
    void testEquals() {
        User user1 = new UserDTO("username", "password", "email");
        User user2 = new UserDTO("username", "password", "email");
        User user3 = new UserDTO("username3", "password", "email");

        DropUserRequest request1 = new DropUserRequest(user1);
        DropUserRequest request2 = new DropUserRequest(user2);
        DropUserRequest request3 = new DropUserRequest(user3);

        // Same object
        assertEquals(request1, request1);
        // Equal objects
        assertEquals(request1, request2);
        // Different objects
        assertNotEquals(request1, request3);
    }
}
