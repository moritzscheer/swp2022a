package de.uol.swp.common.user.request;

import static org.junit.jupiter.api.Assertions.*;

import de.uol.swp.common.user.User;
import de.uol.swp.common.user.UserDTO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegisterUserRequestTest {

    private RegisterUserRequest registerUserRequest;

    /**
     * Sets up the test environment
     *
     * @author WKempel
     * @since 2023-06-17
     */
    @BeforeEach
    void setUp() {
        UserDTO user = new UserDTO("username", "password", "email");
        registerUserRequest = new RegisterUserRequest(user);
    }

    /**
     * Tests the getUser method
     *
     * @author WKempel
     * @result The getUser method should return the correct user
     * @see de.uol.swp.common.user.request.RegisterUserRequest
     * @since 2023-06-17
     */
    @Test
    void testGetUser() {
        User user = registerUserRequest.getUser();
        assertNotNull(user);
        assertEquals("username", user.getUsername());
        assertEquals("password", user.getPassword());
    }

    /**
     * Tests the authorizationNeeded method
     *
     * @author WKempel
     * @result The authorizationNeeded method should return false
     * @see de.uol.swp.common.user.request.RegisterUserRequest
     * @since 2023-06-17
     */
    @Test
    void testAuthorizationNeeded() {
        assertFalse(registerUserRequest.authorizationNeeded());
    }

    /**
     * Tests the equals method
     *
     * @author WKempel
     * @result The equals method should return true if the objects are equal and false if they are not
     * @see de.uol.swp.common.user.request.RegisterUserRequest
     * @since 2023-06-17
     */
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
