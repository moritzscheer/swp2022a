package de.uol.swp.common.user.request;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LoginRequestTest {

    private LoginRequest loginRequest;

    /**
     * Sets up the test environment
     *
     * @author WKempel
     * @since 2023-06-17
     */
    @BeforeEach
    void setUp() {
        loginRequest = new LoginRequest("username", "password");
    }

    /**
     * Tests the getUsername method
     *
     * @author WKempel
     * @result The getUsername method should return the correct username
     * @see de.uol.swp.common.user.request.LoginRequest
     * @since 2023-06-17
     */
    @Test
    void testGetUsername() {
        String username = loginRequest.getUsername();
        assertEquals("username", username);
    }

    /**
     * Tests the setUsername method
     *
     * @author WKempel
     * @result The setUsername method should set the correct username
     * @see de.uol.swp.common.user.request.LoginRequest
     * @since 2023-06-17
     */
    @Test
    void testSetUsername() {
        loginRequest.setUsername("newUsername");
        String newUsername = loginRequest.getUsername();
        assertEquals("newUsername", newUsername);
    }

    /**
     * Tests the getPassword method
     *
     * @author WKempel
     * @result The getPassword method should return the correct password
     * @see de.uol.swp.common.user.request.LoginRequest
     * @since 2023-06-17
     */
    @Test
    void testGetPassword() {
        String password = loginRequest.getPassword();
        assertEquals("password", password);
    }

    /**
     * Tests the setPassword method
     *
     * @author WKempel
     * @result The setPassword method should set the correct password
     * @see de.uol.swp.common.user.request.LoginRequest
     * @since 2023-06-17
     */
    @Test
    void testSetPassword() {
        loginRequest.setPassword("newPassword");
        String newPassword = loginRequest.getPassword();
        assertEquals("newPassword", newPassword);
    }

    /**
     * Tests the authorizationNeeded method
     *
     * @author WKempel
     * @result The authorizationNeeded method should return false
     * @see de.uol.swp.common.user.request.LoginRequest
     * @since 2023-06-17
     */
    @Test
    void testAuthorizationNeeded() {
        assertFalse(loginRequest.authorizationNeeded());
    }

    /**
     * Tests the equals method
     *
     * @author WKempel
     * @result The equals method should return true if the objects are equal and false if they are not
     * @see de.uol.swp.common.user.request.LoginRequest
     * @since 2023-06-17
     */
    @Test
    void testEquals() {

        LoginRequest request1 =
                new LoginRequest(loginRequest.getUsername(), loginRequest.getPassword());
        LoginRequest request2 =
                new LoginRequest(loginRequest.getUsername(), loginRequest.getPassword());
        LoginRequest request3 = new LoginRequest("user3", "password");

        // Same object
        assertEquals(request1, request1);
        // Equal objects
        assertEquals(request1, request2);
        // Different objects
        assertNotEquals(request1, request3);
    }
}
