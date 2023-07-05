package de.uol.swp.common.lobby.message;

import de.uol.swp.common.lobby.dto.LobbyDTO;
import de.uol.swp.common.user.UserDTO;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class UserLeftLobbyMessageTest {

    private final UserDTO user = new UserDTO("Player1", "pw", "ml");
    private final UserDTO newOwner = new UserDTO("Player1", "pw", "ml");
    private final UUID randomUUID = UUID.randomUUID();
    private final LobbyDTO lobbyDTO = new LobbyDTO(123, "testLobby", user, "pw", true, randomUUID, false);

    @Test
    public void testConstructorAndGetters() {

        UserLeftLobbyMessage message =
                new UserLeftLobbyMessage(lobbyDTO.getLobbyID(), lobbyDTO.getName(), user, newOwner);

        Assertions.assertEquals(lobbyDTO.getLobbyID(), message.getLobbyID());
        Assertions.assertEquals(lobbyDTO.getName(), message.getName());
        Assertions.assertEquals(user, message.getUser());
        Assertions.assertEquals(newOwner, message.getNewOwner());
    }

    @Test
    public void testGetNewOwner() {

        UserLeftLobbyMessage message =
                new UserLeftLobbyMessage(lobbyDTO.getLobbyID(), lobbyDTO.getName(), user, newOwner);

        Assertions.assertEquals(newOwner, message.getNewOwner());
    }

    @Test
    public void testGetLobbyID() {

        UserLeftLobbyMessage message =
                new UserLeftLobbyMessage(lobbyDTO.getLobbyID(), lobbyDTO.getName(), user, newOwner);

        Assertions.assertEquals(lobbyDTO.getLobbyID(), message.getLobbyID());
    }

    @Test
    public void testGetName() {
        UserLeftLobbyMessage message =
                new UserLeftLobbyMessage(lobbyDTO.getLobbyID(), lobbyDTO.getName(), user, newOwner);

        Assertions.assertEquals(lobbyDTO.getName(), message.getName());
    }

    @Test
    public void testGetUser() {

        UserLeftLobbyMessage message =
                new UserLeftLobbyMessage(lobbyDTO.getLobbyID(), lobbyDTO.getName(), user, newOwner);

        Assertions.assertEquals(user, message.getUser());
    }

    @Test
    public void testEqualsAndHashCode() {

        UserLeftLobbyMessage message1 = new UserLeftLobbyMessage(lobbyDTO.getLobbyID(), lobbyDTO.getName(), user, newOwner);

        UserLeftLobbyMessage message2 = new UserLeftLobbyMessage(lobbyDTO.getLobbyID(), lobbyDTO.getName(), user, newOwner);

        Assertions.assertEquals(message1, message2);

        Assertions.assertEquals(message1.hashCode(), message2.hashCode());
    }
}
