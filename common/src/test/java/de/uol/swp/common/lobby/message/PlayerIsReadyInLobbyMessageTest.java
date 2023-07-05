package de.uol.swp.common.lobby.message;

import de.uol.swp.common.lobby.dto.LobbyDTO;
import de.uol.swp.common.user.UserDTO;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class PlayerIsReadyInLobbyMessageTest {

    private final UserDTO userDTO = new UserDTO("Player1", "pw", "ml");
    private final UUID randomUUID = UUID.randomUUID();
    private final LobbyDTO lobbyDTO =
            new LobbyDTO(123, "testLobby", userDTO, "pw", true, randomUUID, false);

    @Test
    public void testConstructorAndGetters() {
        boolean ready = true;

        PlayerReadyInLobbyMessage message =
                new PlayerReadyInLobbyMessage(lobbyDTO.getLobbyID(), userDTO, ready);

        Assertions.assertEquals(lobbyDTO.getLobbyID(), message.getLobbyID());
        Assertions.assertEquals(userDTO, message.getUser());
        Assertions.assertEquals(ready, message.isReady());
    }

    @Test
    public void testIsReady() {
        boolean ready = true;

        PlayerReadyInLobbyMessage message =
                new PlayerReadyInLobbyMessage(lobbyDTO.getLobbyID(), userDTO, ready);

        Assertions.assertTrue(message.isReady());

        message = new PlayerReadyInLobbyMessage(lobbyDTO.getLobbyID(), userDTO, false);

        Assertions.assertFalse(message.isReady());
    }

    @Test
    public void testGetUser() {
        boolean ready = true;

        PlayerReadyInLobbyMessage message =
                new PlayerReadyInLobbyMessage(lobbyDTO.getLobbyID(), userDTO, ready);

        Assertions.assertEquals(userDTO, message.getUser());
    }

    @Test
    public void testGetLobbyID() {
        boolean ready = true;

        PlayerReadyInLobbyMessage message =
                new PlayerReadyInLobbyMessage(lobbyDTO.getLobbyID(), userDTO, ready);

        Assertions.assertEquals(lobbyDTO.getLobbyID(), message.getLobbyID());
    }

    @Test
    public void testConstructorWithNullUser() {
        boolean ready = true;

        Assertions.assertThrows(
                NullPointerException.class,
                () -> {
                    new PlayerReadyInLobbyMessage(lobbyDTO.getLobbyID(), null, ready);
                });
    }

    @Test
    public void testConstructorWithNullLobbyID() {
        boolean ready = true;

        Assertions.assertThrows(
                NullPointerException.class,
                () -> {
                    new PlayerReadyInLobbyMessage(null, userDTO, ready);
                });
    }

    @Test
    public void testEqualsAndHashCode() {

        boolean ready1 = true;
        PlayerReadyInLobbyMessage message1 = new PlayerReadyInLobbyMessage(lobbyDTO.getLobbyID(), userDTO, ready1);

        PlayerReadyInLobbyMessage message2 = new PlayerReadyInLobbyMessage(lobbyDTO.getLobbyID(), userDTO, ready1);

        Assertions.assertEquals(message1, message2);

        Assertions.assertEquals(message1.hashCode(), message2.hashCode());
    }
}
