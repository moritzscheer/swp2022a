package de.uol.swp.client.lobbyGame.game;

import de.uol.swp.client.lobbyGame.game.events.RequestDistributeCardsEvent;
import de.uol.swp.client.lobbyGame.game.events.RequestMapDataEvent;
import de.uol.swp.common.lobby.dto.LobbyDTO;
import de.uol.swp.common.user.UserDTO;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RequestMapDataEventTest {

    private final UserDTO loggedInUser = new UserDTO("player1","pw1","ml1");
    private final UUID uuid = UUID.randomUUID();

    @Test
    public void testConstructorAndGetters() {
        LobbyDTO lobby = new LobbyDTO(123,"testLobby",loggedInUser,"pw",true,uuid);

        RequestMapDataEvent event = new RequestMapDataEvent(lobby);

        assertEquals(lobby, event.getLobbyDTO());
    }
}