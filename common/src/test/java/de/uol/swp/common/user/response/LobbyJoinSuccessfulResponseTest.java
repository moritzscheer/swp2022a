package de.uol.swp.common.user.response;

import static org.junit.jupiter.api.Assertions.*;

import de.uol.swp.common.user.User;
import de.uol.swp.common.user.UserDTO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LobbyJoinSuccessfulResponseTest {

    private LobbyJoinSuccessfulResponse response;
    private UserDTO user;

    /**
     * Sets up the test environment
     *
     * @author WKempel
     * @since 2023-06-17
     */
    @BeforeEach
    public void setUp() {
        user = new UserDTO("user", "password", "email");
        response = new LobbyJoinSuccessfulResponse(user);
    }

    /**
     * Tests the getUser method
     *
     * @author WKempel
     * @result The getUser method should return the correct user
     * @see de.uol.swp.common.user.response.LobbyJoinSuccessfulResponse
     * @since 2023-06-17
     */
    @Test
    public void testGetUsers() {
        User actualUser = response.getUser();
        assertEquals(user, actualUser);
    }

    /**
     * Tests the getUsername method
     *
     * @author WKempel
     * @result The getUsername method should return the correct username
     * @see de.uol.swp.common.user.response.LobbyJoinSuccessfulResponse
     * @since 2023-06-17
     */
    @Test
    public void testEqualSameObjectThanTrue() {
        assertEquals(response, response);
    }

    /**
     * Tests the equals method
     *
     * @author WKempel
     * @result The equals method should return true if the LobbyJoinSuccessfulResponses are equal
     * @see de.uol.swp.common.user.response.LobbyJoinSuccessfulResponse
     * @since 2023-06-17
     */
    @Test
    public void testEqualsSameValuesThanTrue() {
        LobbyJoinSuccessfulResponse other = new LobbyJoinSuccessfulResponse(user);
        assertEquals(response, other);
    }

    /**
     * Tests the equals method
     *
     * @author WKempel
     * @result The equals method should return false if the LobbyJoinSuccessfulResponses are not equal
     * @see de.uol.swp.common.user.response.LobbyJoinSuccessfulResponse
     * @since 2023-06-17
     */
    @Test
    public void testEqualsDifferentTypeThanFalse() {
        assertNotEquals("not a LobbyJoinSuccessfulResponse object", response);
    }

    /**
     * Tests the equals method
     *
     * @author WKempel
     * @result The equals method should return false if the LobbyJoinSuccessfulResponses are null
     * @see de.uol.swp.common.user.response.LobbyJoinSuccessfulResponse
     * @since 2023-06-17
     */
    @Test
    public void testEqualsNullThanFalse() {
        assertNotEquals(null, response);
    }
}
