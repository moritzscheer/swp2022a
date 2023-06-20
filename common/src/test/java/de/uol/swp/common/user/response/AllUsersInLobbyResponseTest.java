package de.uol.swp.common.user.response;

import static org.junit.jupiter.api.Assertions.*;

import de.uol.swp.common.user.UserDTO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AllUsersInLobbyResponseTest {

    private AllUsersInLobbyResponse response;
    private List<UserDTO> users;
    private String lobbyName;

    @BeforeEach
    void setUp() {
        lobbyName = "Lobby 1";

        UserDTO user1 = new UserDTO("user1", "password1", "email1");
        UserDTO user2 = new UserDTO("user2", "password2", "email2");
        UserDTO user3 = new UserDTO("user3", "password3", "email3");

        users =
                Arrays.asList(
                        UserDTO.createWithoutPassword(user1),
                        UserDTO.createWithoutPassword(user2),
                        UserDTO.createWithoutPassword(user3));

        response = new AllUsersInLobbyResponse(lobbyName, new ArrayList<>(users));
    }

    @Test
    void getUsers() {
        List<UserDTO> actualUsers = response.getUsers();
        assertEquals(users, actualUsers);
    }

    @Test
    void getLobbyName() {
        String actualLobbyName = response.getLobbyName();
        assertEquals(lobbyName, actualLobbyName);
    }

    @Test
    void equals_sameObject_true() {
        assertTrue(response.equals(response));
    }

    @Test
    void equals_sameValues_true() {
        List<UserDTO> otherUsers = new ArrayList<>(users);
        AllUsersInLobbyResponse other =
                new AllUsersInLobbyResponse(lobbyName, new ArrayList<>(otherUsers));
        assertTrue(response.equals(other));
    }

    @Test
    void equals_differentType_false() {
        assertFalse(response.equals("not an AllUsersInLobbyResponse object"));
    }

    @Test
    void equals_null_false() {
        assertFalse(response.equals(null));
    }
}
