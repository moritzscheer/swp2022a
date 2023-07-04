package de.uol.swp.client.lobbyGame.lobby;

import static org.junit.jupiter.api.Assertions.assertEquals;

import de.uol.swp.client.lobbyGame.lobby.event.SetPlayerReadyEvent;
import de.uol.swp.common.lobby.dto.LobbyDTO;
import de.uol.swp.common.user.UserDTO;

import org.junit.jupiter.api.Test;

import java.util.UUID;

/**
 * Test SetPlayerReadyEventTest
 *
 * @author WKempel
 * @see de.uol.swp.client.lobbyGame.lobby.SetPlayerReadyEventTest
 * @since 2023-06-26
 */
public class SetPlayerReadyEventTest {

    private final UserDTO user = new UserDTO("player1", "pw1", "ml1");
    private final UUID uuid = UUID.randomUUID();
    private final LobbyDTO lobbyDTO = new LobbyDTO(123, "testLobby", user, "pw", true, uuid);

    private final boolean isReady = true;

    @Test
    public void testConstructorAndGetters() {
        SetPlayerReadyEvent event = new SetPlayerReadyEvent(lobbyDTO.getLobbyID(), user, isReady);

        assertEquals(user, event.getUser());
        assertEquals(lobbyDTO.getLobbyID(), event.getLobbyID());
        assertEquals(isReady, event.isReady());
    }
}
