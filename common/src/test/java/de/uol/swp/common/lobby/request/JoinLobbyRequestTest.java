package de.uol.swp.common.lobby.request;

import de.uol.swp.common.user.UserDTO;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class JoinLobbyRequestTest {

    private final UserDTO user = new UserDTO("Player1", "pw", "ml");

    @Test
    public void testConstructorAndGetters() {
        Integer lobbyID = 123;
        String lobbyName = "LobbyName";
        String password = "secret";

        JoinLobbyRequest request = new JoinLobbyRequest(lobbyID, lobbyName, user, password);

        Assertions.assertEquals(lobbyID, request.getLobbyID());
        Assertions.assertEquals(lobbyName, request.getName());
        Assertions.assertEquals(user, request.getUser());
        Assertions.assertEquals(password, request.getPassword());
    }

    @Test
    public void testGetPassword() {
        Integer lobbyID = 123;
        String lobbyName = "LobbyName";
        String password = "secret";

        JoinLobbyRequest request = new JoinLobbyRequest(lobbyID, lobbyName, user, password);

        String retrievedPassword = request.getPassword();

        Assertions.assertEquals(password, retrievedPassword);
    }

    @Test
    public void testGetLobbyID() {
        Integer lobbyID = 123;
        String lobbyName = "LobbyName";
        String password = "secret";

        JoinLobbyRequest request = new JoinLobbyRequest(lobbyID, lobbyName, user, password);

        Integer retrievedLobbyID = request.getLobbyID();

        Assertions.assertEquals(lobbyID, retrievedLobbyID);
    }
}
