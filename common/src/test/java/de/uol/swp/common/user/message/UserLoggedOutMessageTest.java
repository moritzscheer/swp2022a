package de.uol.swp.common.user.message;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserLoggedOutMessageTest {

    private UserLoggedOutMessage loggedOutMessage;

    @BeforeEach
    void setUp() {
        loggedOutMessage = new UserLoggedOutMessage("testuser");
    }

    @Test
    void testGetUsername() {
        assertEquals("testuser", loggedOutMessage.getUsername());
    }

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
