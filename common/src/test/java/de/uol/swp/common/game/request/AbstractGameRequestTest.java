package de.uol.swp.common.game.request;

import de.uol.swp.common.lobby.dto.LobbyDTO;
import de.uol.swp.common.user.UserDTO;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class AbstractGameRequestTest {

    private final UserDTO userDTO = new UserDTO("Player1", "pw", "ml");
    private final UUID chatID = UUID.randomUUID();
    private LobbyDTO lobbyDTO = new LobbyDTO(123, "testLobby", userDTO, "pw", false, chatID);
    private LobbyDTO lobbyDTO2 = new LobbyDTO(123,"testLobby2",userDTO,"pw",false,chatID);

    @Test
    public void testConstructorAndGetters() {

        AbstractGameRequest request = new AbstractGameRequest(lobbyDTO.getName(), lobbyDTO);

        Assertions.assertEquals(lobbyDTO.getName(), request.getName());
        Assertions.assertEquals(lobbyDTO, request.getLobby());
    }

    @Test
    public void testSetters() {

        String name = "Updated Game";
        AbstractGameRequest request = new AbstractGameRequest(lobbyDTO.getName(), lobbyDTO);

        request.setName(name);
        request.setLobby(lobbyDTO);

        Assertions.assertEquals(name, request.getName());
        Assertions.assertEquals(lobbyDTO, request.getLobby());
    }

    @Test
    public void testConstructorWithNullName() {
        String name = null;

        Assertions.assertThrows(
                NullPointerException.class,
                () -> {
                    new AbstractGameRequest(name, lobbyDTO);
                });
    }

    @Test
    public void testConstructorWithNullLobby() {
        String name = "Test Game";
        lobbyDTO = null;

        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> {
                    new AbstractGameRequest(name, lobbyDTO);
                });
    }

    @Test
    public void testSetterWithNullName() {
        AbstractGameRequest request = new AbstractGameRequest(lobbyDTO.getName(), lobbyDTO);
        String name = null;

        Assertions.assertThrows(
                NullPointerException.class,
                () -> {
                    request.setName(name);
                });
    }

    @Test
    public void testSetterWithNullLobby() {
        AbstractGameRequest request = new AbstractGameRequest(lobbyDTO.getName(), lobbyDTO);
        lobbyDTO = null;

        Assertions.assertThrows(
                NullPointerException.class,
                () -> {
                    request.setLobby(lobbyDTO);
                });
    }

    @Test
    public void testEqualsAndHashCode() {

        AbstractGameRequest request1 = new AbstractGameRequest(lobbyDTO.getName(), lobbyDTO);
        AbstractGameRequest request2 = new AbstractGameRequest(lobbyDTO2.getName(), lobbyDTO2);

        Assertions.assertEquals(request1, request1);
        Assertions.assertEquals(request1.hashCode(), request1.hashCode());

        Assertions.assertNotEquals(request1, request2);
        Assertions.assertNotEquals(request1.hashCode(), request2.hashCode());
    }
}
