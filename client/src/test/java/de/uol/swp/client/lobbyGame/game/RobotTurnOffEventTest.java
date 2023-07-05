package de.uol.swp.client.lobbyGame.game;

import static org.junit.jupiter.api.Assertions.assertEquals;

import de.uol.swp.client.lobbyGame.game.events.RobotTurnOffEvent;
import de.uol.swp.common.lobby.dto.LobbyDTO;
import de.uol.swp.common.user.UserDTO;

import org.junit.jupiter.api.Test;

import java.util.UUID;

/**
 * Test RobotTurnOffEventTest
 *
 * @author WKempel
 * @see RobotTurnOffEventTest
 * @since 2023-06-26
 */
public class RobotTurnOffEventTest {

    private final UserDTO loggedInUser = new UserDTO("player1", "pw1", "ml1");
    private final UUID uuid = UUID.randomUUID();
    private final LobbyDTO lobbyDTO =
            new LobbyDTO(123, "testLobby", loggedInUser, "pw", true, uuid, false);

    @Test
    public void testConstructorAndGetters() {
        RobotTurnOffEvent event = new RobotTurnOffEvent(lobbyDTO.getLobbyID(), loggedInUser);

        assertEquals(lobbyDTO.getLobbyID(), event.getLobbyID());
        assertEquals(loggedInUser, event.getLoggedInUser());
    }
}
