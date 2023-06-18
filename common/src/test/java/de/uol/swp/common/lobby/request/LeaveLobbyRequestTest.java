package de.uol.swp.common.lobby.request;

import de.uol.swp.common.user.UserDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LeaveLobbyRequestTest {

    private final UserDTO user = new UserDTO("Player1","pw","ml");

    @Test
    public void testConstructorAndGetters() {
        Integer lobbyID = 123;
        String lobbyName = "LobbyName";
        Boolean multiplayer = true;

        LeaveLobbyRequest request = new LeaveLobbyRequest(lobbyID, lobbyName, user, multiplayer);

        Assertions.assertEquals(lobbyID, request.getLobbyID());
        Assertions.assertEquals(lobbyName, request.getName());
        Assertions.assertEquals(user, request.getUser());
        Assertions.assertEquals(multiplayer, request.isMultiplayer());
    }

    @Test
    public void testGetLobbyID() {
        Integer lobbyID = 123;
        String lobbyName = "LobbyName";
        Boolean multiplayer = true;

        LeaveLobbyRequest request = new LeaveLobbyRequest(lobbyID, lobbyName, user, multiplayer);

        Integer retrievedLobbyID = request.getLobbyID();

        Assertions.assertEquals(lobbyID, retrievedLobbyID);
    }

    @Test
    public void testIsMultiplayer() {
        Integer lobbyID = 123;
        String lobbyName = "LobbyName";
        Boolean multiplayer = true;

        LeaveLobbyRequest request = new LeaveLobbyRequest(lobbyID, lobbyName, user, multiplayer);

        Boolean retrievedMultiplayer = request.isMultiplayer();

        Assertions.assertEquals(multiplayer, retrievedMultiplayer);
    }
}
