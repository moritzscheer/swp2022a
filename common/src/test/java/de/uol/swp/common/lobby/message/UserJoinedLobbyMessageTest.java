package de.uol.swp.common.lobby.message;

import de.uol.swp.common.lobby.dto.LobbyDTO;
import de.uol.swp.common.user.UserDTO;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class UserJoinedLobbyMessageTest {

    private final UserDTO userDTO = new UserDTO("Player1", "pw", "ml");
    private final UUID randomUUID = UUID.randomUUID();
    private final LobbyDTO lobbyDTO =
            new LobbyDTO(123, "testLobby", userDTO, "pw", true, randomUUID);

    @Test
    public void testConstructorAndGetters() {

        UserJoinedLobbyMessage message =
                new UserJoinedLobbyMessage(lobbyDTO.getLobbyID(), lobbyDTO.getName(), userDTO);

        // because the constructor creates a new LobbyDTO, the LobbyDTOs are not equal
        Assertions.assertNotEquals(lobbyDTO, message.getLobbyID());
        Assertions.assertEquals(lobbyDTO.getName(), message.getName());
        Assertions.assertEquals(userDTO, message.getUser());
    }

    @Test
    public void testGetLobbyID() {

        UserJoinedLobbyMessage message =
                new UserJoinedLobbyMessage(lobbyDTO.getLobbyID(), lobbyDTO.getName(), userDTO);

        Assertions.assertEquals(lobbyDTO.getLobbyID(), message.getLobbyID());
    }

    @Test
    public void testGetName() {

        UserJoinedLobbyMessage message =
                new UserJoinedLobbyMessage(lobbyDTO.getLobbyID(), lobbyDTO.getName(), userDTO);

        Assertions.assertEquals(lobbyDTO.getName(), message.getName());
    }

    @Test
    public void testGetUser() {

        UserJoinedLobbyMessage message =
                new UserJoinedLobbyMessage(lobbyDTO.getLobbyID(), lobbyDTO.getName(), userDTO);

        Assertions.assertEquals(userDTO, message.getUser());
    }
}
