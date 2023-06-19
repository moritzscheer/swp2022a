package de.uol.swp.common.user.request;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RetrieveAllUsersInLobbyRequestTest {

    private LoginRequest loginRequest;

    @BeforeEach
    void setUp() {
        loginRequest = new LoginRequest("username", "password");
    }

    @Test
    void testGetUsername() {
        assertEquals("username", loginRequest.getUsername());
    }

    @Test
    void testGetPassword() {
        assertEquals("password", loginRequest.getPassword());
    }

    @Test
    void testAuthorizationNeeded() {
        assertFalse(loginRequest.authorizationNeeded());
    }

    @Test
    void testSetUsername() {
        loginRequest.setUsername("newUsername");
        assertEquals("newUsername", loginRequest.getUsername());
    }

    @Test
    void testSetPassword() {
        loginRequest.setPassword("newPassword");
        assertEquals("newPassword", loginRequest.getPassword());
    }

    @Test
    void testEquals() {
        LoginRequest request1 = new LoginRequest("username", "password");
        LoginRequest request2 = new LoginRequest("username", "password");
        LoginRequest request3 = new LoginRequest("otherUsername", "password");

        // Same object
        assertEquals(request1, request1);
        // Equal objects
        assertEquals(request1, request2);
        // Different objects
        assertNotEquals(request1, request3);
    }
}
