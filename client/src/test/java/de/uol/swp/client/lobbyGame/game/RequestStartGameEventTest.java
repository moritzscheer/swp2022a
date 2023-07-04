package de.uol.swp.client.lobbyGame.game;

import static org.junit.jupiter.api.Assertions.assertEquals;

import de.uol.swp.client.lobbyGame.game.events.RequestStartGameEvent;
import de.uol.swp.common.lobby.dto.LobbyDTO;
import de.uol.swp.common.user.UserDTO;

import org.junit.jupiter.api.Test;

import java.util.UUID;

/**
 * Test RequestStartGameEventTest
 *
 * @author WKempel
 * @see RequestStartGameEventTest
 * @since 2023-06-26
 */
public class RequestStartGameEventTest {

    private final int numberBots = 3;
    private final int numberCheckpoints = 3;
    private final UserDTO loggedInUser = new UserDTO("player1", "pw1", "ml1");
    private final UUID uuid = UUID.randomUUID();
    private final LobbyDTO lobbyDTO =
            new LobbyDTO(123, "testLobby", loggedInUser, "pw", true, uuid);

    @Test
    public void testConstructorAndGetters() {
        RequestStartGameEvent event =
                new RequestStartGameEvent(numberBots, numberCheckpoints, lobbyDTO);

        assertEquals(numberBots, event.getNumberBots());
        assertEquals(numberCheckpoints, event.getNumberCheckpoints());
        assertEquals(lobbyDTO, event.getLobbyDTO());
    }
}
