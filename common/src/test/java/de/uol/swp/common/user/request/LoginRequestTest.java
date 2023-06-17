package de.uol.swp.common.user.request;

import de.uol.swp.common.user.User;
import de.uol.swp.common.user.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LoginRequestTest {

    private LoginRequest loginRequest;

    @BeforeEach
    void setUp() {
        loginRequest = new LoginRequest("username", "password");
    }

    @Test
    void testGetUsername() {
        String username = loginRequest.getUsername();
        assertEquals("username", username);
    }

    @Test
    void testSetUsername() {
        loginRequest.setUsername("newUsername");
        String newUsername = loginRequest.getUsername();
        assertEquals("newUsername", newUsername);
    }

    @Test
    void testGetPassword() {
        String password = loginRequest.getPassword();
        assertEquals("password", password);
    }

    @Test
    void testSetPassword() {
        loginRequest.setPassword("newPassword");
        String newPassword = loginRequest.getPassword();
        assertEquals("newPassword", newPassword);
    }

    @Test
    void testAuthorizationNeeded() {
        assertFalse(loginRequest.authorizationNeeded());
    }

    @Test
    void testEquals() {

        LoginRequest request1 = new LoginRequest(loginRequest.getUsername(), loginRequest.getPassword());
        LoginRequest request2 = new LoginRequest(loginRequest.getUsername(), loginRequest.getPassword());
        LoginRequest request3 = new LoginRequest("user3", "password");

        // Same object
        assertEquals(request1, request1);
        // Equal objects
        assertEquals(request1, request2);
        // Different objects
        assertNotEquals(request1, request3);
    }

}
