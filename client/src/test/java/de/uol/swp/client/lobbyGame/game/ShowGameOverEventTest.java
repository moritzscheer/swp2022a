package de.uol.swp.client.lobbyGame.game;

import static org.junit.jupiter.api.Assertions.assertEquals;

import de.uol.swp.client.lobbyGame.game.events.ShowGameOverEvent;
import de.uol.swp.common.lobby.dto.LobbyDTO;
import de.uol.swp.common.user.UserDTO;

import org.junit.jupiter.api.Test;

import java.util.UUID;

/**
 * Test ShowGameOverEventTest
 *
 * @author WKempel
 * @see ShowGameOverEventTest
 * @since 2023-06-26
 */
public class ShowGameOverEventTest {

    private final UserDTO userWon = new UserDTO("player1", "pw1", "ml1");
    private final UUID uuid = UUID.randomUUID();
    private final LobbyDTO lobbyDTO =
            new LobbyDTO(123, "testLobby", userWon, "pw", true, uuid, false);

    @Test
    public void testConstructorAndGetters() {
        ShowGameOverEvent event =
                new ShowGameOverEvent(lobbyDTO.getLobbyID(), userWon, null, null, "", false);

        assertEquals(lobbyDTO.getLobbyID(), event.getLobbyID());
        assertEquals(userWon, event.getUserWon());
    }
}
