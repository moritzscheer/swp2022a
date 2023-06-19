package de.uol.swp.common.user.request;

import static org.junit.jupiter.api.Assertions.*;

import de.uol.swp.common.user.UserDTO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UpdateUserRequestTest {

    private UpdateUserRequest request;
    private UserDTO user;

    @BeforeEach
    void setUp() {
        user = new UserDTO("username", "password", "email");
        request = new UpdateUserRequest(user);
    }

    @Test
    void getUser() {
        assertEquals(user, request.getUser());
    }

    @Test
    void equals_sameObject_true() {
        assertTrue(request.equals(request));
    }

    @Test
    void equals_sameValues_true() {
        UpdateUserRequest other = new UpdateUserRequest(user);
        assertTrue(request.equals(other));
    }

    @Test
    void equals_differentType_false() {
        assertFalse(request.equals("not an UpdateUserRequest object"));
    }

    @Test
    void equals_null_false() {
        assertFalse(request.equals(null));
    }
}
