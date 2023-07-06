package de.uol.swp.client.lobbyGame.lobby;

import static org.junit.jupiter.api.Assertions.assertEquals;

import de.uol.swp.client.lobbyGame.lobby.event.CreateNewLobbyEvent;
import de.uol.swp.common.lobby.dto.LobbyDTO;
import de.uol.swp.common.user.UserDTO;

import org.junit.jupiter.api.Test;

import java.util.UUID;

/**
 * Test CreateNewLobbyEventTest
 *
 * @author WKempel
 * @see de.uol.swp.client.lobbyGame.lobby.CreateNewLobbyEventTest
 * @since 2023-06-26
 */
public class CreateNewLobbyEventTest {

    private final UserDTO user = new UserDTO("player1", "pw1", "ml1");
    private final UUID uuid = UUID.randomUUID();
    private final LobbyDTO lobbyDTO = new LobbyDTO(123, "testLobby", user, "pw", true, uuid, false);

    @Test
    public void testConstructorAndGetters() {
        CreateNewLobbyEvent event =
                new CreateNewLobbyEvent(
                        lobbyDTO.getName(), user, lobbyDTO.isMultiplayer(), lobbyDTO.getPassword());

        assertEquals(lobbyDTO.getName(), event.getName());
        assertEquals(user, event.getUser());
        assertEquals(lobbyDTO.isMultiplayer(), event.getMultiplayer());
        assertEquals(lobbyDTO.getPassword(), event.getPassword());
    }
}
