package de.uol.swp.common.user.message;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserLoggedOutMessageTest {

    private UserLoggedOutMessage loggedOutMessage;

    /**
     * Sets up the test environment
     *
     * @author WKempel
     * @since 2023-06-17
     */
    @BeforeEach
    void setUp() {
        loggedOutMessage = new UserLoggedOutMessage("testuser");
    }

    /**
     * Tests the getUsername method
     *
     * @author WKempel
     * @result The getUsername method should return the correct username
     * @see de.uol.swp.common.user.message.UserLoggedOutMessage
     * @since 2023-06-17
     */
    @Test
    void testGetUsername() {
        assertEquals("testuser", loggedOutMessage.getUsername());
    }

    /**
     * Tests the equals method
     *
     * @author WKempel
     * @result The equals method should return true if the UserLoggedOutMessages are equal and false if they are not
     * @see de.uol.swp.common.user.message.UserLoggedOutMessage
     * @since 2023-06-17
     */
    @Test
    void testEquals() {
        UserLoggedInMessage message1 = new UserLoggedInMessage("testuser");
        UserLoggedInMessage message2 = new UserLoggedInMessage("differentUser");

        // Same object
        assertEquals(message1, message1);
        // Different objects
        assertNotEquals(message1, message2);
    }
}
