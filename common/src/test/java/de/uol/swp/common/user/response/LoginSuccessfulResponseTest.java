package de.uol.swp.common.user.response;

import static org.junit.jupiter.api.Assertions.*;

import de.uol.swp.common.user.User;
import de.uol.swp.common.user.UserDTO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class LoginSuccessfulResponseTest {

    private LoginSuccessfulResponse response;
    private UserDTO user;
    private UUID chatID;

    @BeforeEach
    void setUp() {
        user = new UserDTO("user1", "password1", "email1");
        chatID = UUID.randomUUID();
        response = new LoginSuccessfulResponse(user, chatID);
    }

    @Test
    void getUser() {
        User actualUser = response.getUser();
        assertEquals(user, actualUser);
    }

    @Test
    void getChatID() {
        UUID actualChatID = response.getChatID();
        assertEquals(chatID, actualChatID);
    }

    @Test
    void equals_sameObject_true() {
        assertTrue(response.equals(response));
    }

    @Test
    void equals_sameValues_true() {
        LoginSuccessfulResponse other = new LoginSuccessfulResponse(user, chatID);
        assertTrue(response.equals(other));
    }

    @Test
    void equals_differentType_false() {
        assertFalse(response.equals("not a LoginSuccessfulResponse object"));
    }

    @Test
    void equals_null_false() {
        assertFalse(response.equals(null));
    }
}
