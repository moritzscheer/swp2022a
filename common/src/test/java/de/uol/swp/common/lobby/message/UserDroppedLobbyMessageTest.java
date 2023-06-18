package de.uol.swp.common.lobby.message;

import de.uol.swp.common.lobby.dto.LobbyDTO;
import de.uol.swp.common.user.UserDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class UserDroppedLobbyMessageTest {

    private final UserDTO userDTO = new UserDTO("Player1","pw","ml");
    private final UUID randomUUID = UUID.randomUUID();
    private final LobbyDTO lobbyDTO = new LobbyDTO(123,"testLobby",userDTO,"pw",true,randomUUID);

    @Test
    public void testConstructorAndGetters() {

        UserDroppedLobbyMessage message = new UserDroppedLobbyMessage(lobbyDTO, lobbyDTO.getName(), userDTO);

        // because the constructor creates a new LobbyDTO, the LobbyDTOs are not equal
        Assertions.assertNotEquals(lobbyDTO, message.getLobby());
        Assertions.assertEquals(lobbyDTO.getName(), message.getName());
        Assertions.assertEquals(userDTO, message.getUser());
    }

    @Test
    public void testGetLobby() {

        UserDroppedLobbyMessage message = new UserDroppedLobbyMessage(lobbyDTO, lobbyDTO.getName(), userDTO);

        // because the constructor creates a new LobbyDTO, the LobbyDTOs are not equal
        Assertions.assertNotEquals(lobbyDTO, message.getLobby());
    }

    @Test
    public void testGetName() {

        UserDroppedLobbyMessage message = new UserDroppedLobbyMessage(lobbyDTO, lobbyDTO.getName(), userDTO);

        Assertions.assertEquals(lobbyDTO.getName(), message.getName());
    }

    @Test
    public void testGetUser() {

        UserDroppedLobbyMessage message = new UserDroppedLobbyMessage(lobbyDTO, lobbyDTO.getName(), userDTO);

        Assertions.assertEquals(userDTO, message.getUser());
    }

    @Test
    public void testEqualsAndHashCode() {

        UserDroppedLobbyMessage message1 = new UserDroppedLobbyMessage(lobbyDTO, lobbyDTO.getName(), userDTO);

        UserDroppedLobbyMessage message2 = new UserDroppedLobbyMessage(lobbyDTO, lobbyDTO.getName(), userDTO);

        Assertions.assertEquals(message1, message2);

        Assertions.assertEquals(message1.hashCode(), message2.hashCode());
    }
}
