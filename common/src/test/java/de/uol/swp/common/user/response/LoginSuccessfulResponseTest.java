package de.uol.swp.common.user.response;

import static org.junit.jupiter.api.Assertions.*;

import de.uol.swp.common.user.User;
import de.uol.swp.common.user.UserDTO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class LoginSuccessfulResponseTest {

    private LoginSuccessfulResponse response;
    private UserDTO user;
    private UUID chatID;

    /**
     * Sets up the test environment
     *
     * @author WKempel
     * @since 2023-06-17
     */
    @BeforeEach
    public void setUp() {
        user = new UserDTO("user1", "password1", "email1");
        chatID = UUID.randomUUID();
        response = new LoginSuccessfulResponse(user, chatID);
    }

    /**
     * Tests the getUser method
     *
     * @author WKempel
     * @result The getUser method should return the correct user
     * @see de.uol.swp.common.user.response.LoginSuccessfulResponse
     * @since 2023-06-17
     */
    @Test
    public void testGetUsers() {
        User actualUser = response.getUser();
        assertEquals(user, actualUser);
    }

    /**
     * Tests the getChatID method
     *
     * @author WKempel
     * @result The getChatID method should return the correct chatID
     * @see de.uol.swp.common.user.response.LoginSuccessfulResponse
     * @since 2023-06-17
     */
    @Test
    public void testGetChatID() {
        UUID actualChatID = response.getChatID();
        assertEquals(chatID, actualChatID);
    }

    /**
     * Tests the equals method
     *
     * @author WKempel
     * @result The equals method should return true if the LoginSuccessfulResponses are equal
     * @see de.uol.swp.common.user.response.LoginSuccessfulResponse
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
     * @result The equals method should return true if the LoginSuccessfulResponses are equal
     * @see de.uol.swp.common.user.response.LoginSuccessfulResponse
     * @since 2023-06-17
     */
    @Test
    public void testEqualsSameValuesThanTrue() {
        LoginSuccessfulResponse other = new LoginSuccessfulResponse(user, chatID);
        assertEquals(response, other);
    }

    /**
     * Tests the equals method
     *
     * @author WKempel
     * @result The equals method should return false if the LoginSuccessfulResponses are not equal
     * @see de.uol.swp.common.user.response.LoginSuccessfulResponse
     * @since 2023-06-17
     */
    @Test
    public void testEqualsDifferentTypeThanFalse() {
        assertNotEquals("not a LoginSuccessfulResponse object", response);
    }

    /**
     * Tests the equals method
     *
     * @author WKempel
     * @result The equals method should return false if the LoginSuccessfulResponses are null
     */
    @Test
    public void testEqualsNullThanFalse() {
        assertNotEquals(null, response);
    }
}
