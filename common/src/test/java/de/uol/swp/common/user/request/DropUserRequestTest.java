package de.uol.swp.common.user.request;

import static org.junit.jupiter.api.Assertions.*;

import de.uol.swp.common.user.User;
import de.uol.swp.common.user.UserDTO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DropUserRequestTest {

    private UserDTO user;
    private DropUserRequest dropUserRequest;

    /**
     * Sets up the test environment
     *
     * @author WKempel
     * @since 2023-06-17
     */
    @BeforeEach
    void setUp() {
        user = new UserDTO("username", "password", "email");
        dropUserRequest = new DropUserRequest(user);
    }

    /**
     * Tests the getUser method
     *
     * @author WKempel
     * @result The getUser method should return the correct user
     * @see de.uol.swp.common.user.request.DropUserRequest
     * @since 2023-06-17
     */
    @Test
    void testGetUser() {
        User retrievedUser = dropUserRequest.getUser();
        assertEquals(user, retrievedUser);
    }

    /**
     * Tests the getUsername method
     *
     * @author WKempel
     * @result The getUsername method should return the correct username
     * @see de.uol.swp.common.user.request.DropUserRequest
     * @since 2023-06-17
     */
    @Test
    void testGetUsername() {
        String username = dropUserRequest.getUsername();
        assertEquals("username", username);
    }

    /**
     * Tests the authorizationNeeded method
     *
     * @author WKempel
     * @result The authorizationNeeded method should return false
     * @see de.uol.swp.common.user.request.DropUserRequest
     * @since 2023-06-17
     */
    @Test
    void testAuthorizationNeeded() {
        assertFalse(dropUserRequest.authorizationNeeded());
    }

    /**
     * Tests the equals method
     *
     * @author WKempel
     * @result The equals method should return true if the DropUserRequests are equal and false if
     *     they are not
     * @see de.uol.swp.common.user.request.DropUserRequest
     * @since 2023-06-17
     */
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
