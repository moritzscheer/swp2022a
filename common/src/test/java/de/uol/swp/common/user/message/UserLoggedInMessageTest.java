package de.uol.swp.common.user.message;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class UserLoggedInMessageTest {

    private UserLoggedInMessage loggedInMessage;

    @BeforeEach
    void setUp() {
        loggedInMessage = new UserLoggedInMessage("testuser");
    }

    @Test
    void testGetUsername() {
        assertEquals("testuser", loggedInMessage.getUsername());
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
