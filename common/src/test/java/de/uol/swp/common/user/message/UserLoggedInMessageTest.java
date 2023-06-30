package de.uol.swp.common.user.message;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserLoggedInMessageTest {

    private UserLoggedInMessage loggedInMessage;

    /**
     * Sets up the test environment
     *
     * @author WKempel
     * @since 2023-06-17
     */
    @BeforeEach
    public void setUp() {
        loggedInMessage = new UserLoggedInMessage("testuser");
    }

    /**
     * Tests the getUsername method
     *
     * @author WKempel
     * @result The getUsername method should return the correct username
     * @see de.uol.swp.common.user.message.UserLoggedInMessage
     * @since 2023-06-17
     */
    @Test
    public void testGetUsername() {
        assertEquals("testuser", loggedInMessage.getUsername());
    }

    /**
     * Tests the equals method
     *
     * @author WKempel
     * @result The equals method should return true if the UserLoggedInMessages are equal and false if they are not
     * @see de.uol.swp.common.user.message.UserLoggedInMessage
     * @since 2023-06-17
     */
    @Test
    public void testEquals() {
        UserLoggedInMessage message1 = new UserLoggedInMessage("testuser");
        UserLoggedInMessage message2 = new UserLoggedInMessage("differentUser");

        // Same object
        assertEquals(message1, message1);
        // Different objects
        assertNotEquals(message1, message2);
    }
}
