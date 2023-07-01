package de.uol.swp.common.user.request;

import static org.junit.jupiter.api.Assertions.*;

import de.uol.swp.common.user.UserDTO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReturnToMainMenuRequestTest {

    private ReturnToMainMenuRequest request;
    private UserDTO user;

    @BeforeEach
    void setUp() {
        user = new UserDTO("username", "password", "email");
        request = new ReturnToMainMenuRequest(user);
    }

    @Test
    void getLoggedInUser() {
        assertEquals(user, request.getLoggedInUser());
    }

    @Test
    void equals_sameObject_true() {
        assertTrue(request.equals(request));
    }

    @Test
    void equals_sameValues_true() {
        ReturnToMainMenuRequest other = new ReturnToMainMenuRequest(user);
        assertTrue(request.equals(other));
    }

    @Test
    void equals_differentType_false() {
        assertFalse(request.equals("not a ReturnToMainMenuRequest object"));
    }

    @Test
    void equals_null_false() {
        assertFalse(request.equals(null));
    }
}
