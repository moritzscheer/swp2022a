package de.uol.swp.client.tab;

import static org.junit.jupiter.api.Assertions.assertEquals;

import de.uol.swp.client.tab.event.ChangeElementEvent;
import de.uol.swp.common.lobby.dto.LobbyDTO;
import de.uol.swp.common.user.UserDTO;

import org.junit.jupiter.api.Test;

import java.util.UUID;

/**
 * Test ShowNodeEventTest
 *
 * @author WKempel
 * @see de.uol.swp.client.tab.ChangeElementEventTest
 * @since 2023-06-26
 */
public class ChangeElementEventTest {

    private final UserDTO user = new UserDTO("player1", "pw1", "ml1");
    private final UUID uuid = UUID.randomUUID();
    private final LobbyDTO lobbyDTO = new LobbyDTO(123, "testLobby", user, "pw", true, uuid, false);

    @Test
    public void testConstructorAndGetters() {
        ChangeElementEvent event = new ChangeElementEvent(lobbyDTO.getLobbyID());

        assertEquals(lobbyDTO.getLobbyID(), event.getLobbyID());
    }
}
