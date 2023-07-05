package de.uol.swp.client.lobbyGame.lobby;

import static org.junit.jupiter.api.Assertions.assertEquals;

import de.uol.swp.client.lobbyGame.lobby.event.UserJoinLobbyEvent;
import de.uol.swp.common.lobby.dto.LobbyDTO;
import de.uol.swp.common.user.UserDTO;

import org.junit.jupiter.api.Test;

import java.util.UUID;

/**
 * Test UserJoinLobbyEventTest
 *
 * @author WKempel
 * @see de.uol.swp.client.lobbyGame.lobby.UserJoinLobbyEventTest
 * @since 2023-06-26
 */
public class UserJoinLobbyEventTest {

    private final UserDTO user = new UserDTO("player1", "pw1", "ml1");
    private final UUID uuid = UUID.randomUUID();
    private final LobbyDTO lobbyDTO = new LobbyDTO(123, "testLobby", user, "pw", true, uuid, false);

    @Test
    public void testConstructorAndGetters() {
        UserJoinLobbyEvent event = new UserJoinLobbyEvent(lobbyDTO, user, lobbyDTO.getPassword());

        assertEquals(user, event.getLoggedInUser());
        assertEquals(lobbyDTO, event.getLobby());
        assertEquals(lobbyDTO.getPassword(), event.getPassword());
    }
}
