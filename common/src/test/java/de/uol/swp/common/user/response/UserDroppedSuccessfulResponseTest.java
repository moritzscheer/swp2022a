package de.uol.swp.common.user.response;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserDroppedSuccessfulResponseTest {

    private UserDroppedSuccessfulResponse response;
    private String userDropped;

    @BeforeEach
    void setUp() {
        userDropped = "username";
        response = new UserDroppedSuccessfulResponse(userDropped);
    }

    @Test
    void getUsername() {
        String actualUsername = response.getUsername();
        assertEquals(userDropped, actualUsername);
    }

    @Test
    void equals_sameObject_true() {
        assertTrue(response.equals(response));
    }

    @Test
    void equals_sameValues_true() {
        UserDroppedSuccessfulResponse other = new UserDroppedSuccessfulResponse(userDropped);
        assertTrue(response.equals(other));
    }

    @Test
    void equals_differentType_false() {
        assertFalse(response.equals("not a UserDroppedSuccessfulResponse object"));
    }

    @Test
    void equals_null_false() {
        assertFalse(response.equals(null));
    }
}
