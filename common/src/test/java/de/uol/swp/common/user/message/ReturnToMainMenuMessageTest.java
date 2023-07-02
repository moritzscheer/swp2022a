package de.uol.swp.common.user.message;

import static org.junit.jupiter.api.Assertions.*;

import de.uol.swp.common.user.UserDTO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReturnToMainMenuMessageTest {

    private ReturnToMainMenuMessage message;
    private UserDTO user;

    @BeforeEach
    void setUp() {
        user = new UserDTO("username", "password", "email");
        message = new ReturnToMainMenuMessage(user);
    }

    @Test
    void getLoggedInUser() {
        assertEquals(user, message.getLoggedInUser());
    }

    @Test
    void equals_sameObject_true() {
        assertTrue(message.equals(message));
    }

    @Test
    void equals_sameValues_true() {
        ReturnToMainMenuMessage other = new ReturnToMainMenuMessage(user);
        assertTrue(message.equals(other));
    }

    @Test
    void equals_differentType_false() {
        assertFalse(message.equals("not a ReturnToMainMenuRequest object"));
    }

    @Test
    void equals_null_false() {
        assertFalse(message.equals(null));
    }
}
