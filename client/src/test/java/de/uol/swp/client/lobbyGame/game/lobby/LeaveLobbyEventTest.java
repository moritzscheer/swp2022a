package de.uol.swp.client.lobbyGame.game.lobby;

import de.uol.swp.client.lobbyGame.lobby.event.LeaveLobbyEvent;
import de.uol.swp.common.lobby.dto.LobbyDTO;
import de.uol.swp.common.user.UserDTO;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LeaveLobbyEventTest {

    private final UserDTO user = new UserDTO("player1","pw1","ml1");
    private final UUID uuid = UUID.randomUUID();
    private final LobbyDTO lobbyDTO = new LobbyDTO(123,"testLobby", user,"pw",true,uuid);

    @Test
    public void testConstructorAndGetters() {
        LeaveLobbyEvent event = new LeaveLobbyEvent(user, lobbyDTO.getLobbyID(), lobbyDTO.getName(), lobbyDTO.isMultiplayer());

        assertEquals(user, event.getLoggedInUser());
        assertEquals(lobbyDTO.getLobbyID(), event.getLobbyID());
        assertEquals(lobbyDTO.getName(), event.getLobbyName());
        assertEquals(lobbyDTO.isMultiplayer(), event.isMultiplayer());
    }
}