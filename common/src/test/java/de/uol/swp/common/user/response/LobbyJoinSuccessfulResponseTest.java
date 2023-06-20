package de.uol.swp.common.user.response;

import static org.junit.jupiter.api.Assertions.*;

import de.uol.swp.common.user.User;
import de.uol.swp.common.user.UserDTO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LobbyJoinSuccessfulResponseTest {

    private LobbyJoinSuccessfulResponse response;
    private UserDTO user;

    @BeforeEach
    void setUp() {
        user = new UserDTO("user", "password", "email");
        response = new LobbyJoinSuccessfulResponse(user);
    }

    @Test
    void getUser() {
        User actualUser = response.getUser();
        assertEquals(user, actualUser);
    }

    @Test
    void equals_sameObject_true() {
        assertTrue(response.equals(response));
    }

    @Test
    void equals_sameValues_true() {
        LobbyJoinSuccessfulResponse other = new LobbyJoinSuccessfulResponse(user);
        assertTrue(response.equals(other));
    }

    @Test
    void equals_differentType_false() {
        assertFalse(response.equals("not a LobbyJoinSuccessfulResponse object"));
    }

    @Test
    void equals_null_false() {
        assertFalse(response.equals(null));
    }
}
