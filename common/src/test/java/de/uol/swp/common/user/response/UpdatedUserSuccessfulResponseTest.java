package de.uol.swp.common.user.response;

import static org.junit.jupiter.api.Assertions.*;

import de.uol.swp.common.user.User;
import de.uol.swp.common.user.UserDTO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UpdatedUserSuccessfulResponseTest {

    private UpdatedUserSuccessfulResponse response;
    private UserDTO updatedUser;

    @BeforeEach
    void setUp() {
        updatedUser = new UserDTO("username", "password", "email");
        response = new UpdatedUserSuccessfulResponse(updatedUser);
    }

    @Test
    void getUpdatedUser() {
        User actualUpdatedUser = response.getUpdatedUser();
        assertEquals(updatedUser, actualUpdatedUser);
    }

    @Test
    void equals_sameObject_true() {
        assertTrue(response.equals(response));
    }

    @Test
    void equals_sameValues_true() {
        UpdatedUserSuccessfulResponse other = new UpdatedUserSuccessfulResponse(updatedUser);
        assertTrue(response.equals(other));
    }

    @Test
    void equals_differentType_false() {
        assertFalse(response.equals("not an UpdatedUserSuccessfulResponse object"));
    }

    @Test
    void equals_null_false() {
        assertFalse(response.equals(null));
    }
}
