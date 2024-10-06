package de.uol.swp.client.lobbyGame.lobby;

import static org.junit.jupiter.api.Assertions.assertEquals;

import de.uol.swp.client.lobbyGame.lobby.event.ShowLobbyViewEvent;
import de.uol.swp.common.lobby.dto.LobbyDTO;
import de.uol.swp.common.user.UserDTO;

import org.junit.jupiter.api.Test;

import java.util.UUID;

/**
 * Test ShowLobbyViewEventTest
 *
 * @author WKempel
 * @see de.uol.swp.client.lobbyGame.lobby.ShowLobbyViewEventTest
 * @since 2023-06-26
 */
public class ShowLobbyViewEventTest {

    private final UserDTO user = new UserDTO("player1", "pw1", "ml1");
    private final UUID uuid = UUID.randomUUID();
    private final LobbyDTO lobbyDTO = new LobbyDTO(123, "testLobby", user, "pw", true, uuid, false);

    @Test
    public void testConstructorAndGetters() {
        ShowLobbyViewEvent event = new ShowLobbyViewEvent(lobbyDTO.getLobbyID());

        assertEquals(lobbyDTO.getLobbyID(), event.getLobbyID());
    }
}
