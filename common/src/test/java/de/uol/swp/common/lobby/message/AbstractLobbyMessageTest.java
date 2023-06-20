package de.uol.swp.common.lobby.message;

import de.uol.swp.common.user.UserDTO;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AbstractLobbyMessageTest {

    private final UserDTO userDTO = new UserDTO("Player1", "pw", "ml");
    private final UserDTO userDTO2 = new UserDTO("Player2","pw","ml");

    @Test
    public void testConstructorAndGetters() {
        String lobbyName = "Test Lobby";
        AbstractLobbyMessage message = new AbstractLobbyMessage(lobbyName, userDTO);

        Assertions.assertEquals(lobbyName, message.getName());
        Assertions.assertEquals(userDTO, message.getUser());
    }

    @Test
    public void testConstructorWithNullName() {
        Assertions.assertThrows(
                IllegalArgumentException.class, () -> new AbstractLobbyMessage(null, userDTO));
    }

    @Test
    public void testConstructorWithNullUser() {
        Assertions.assertThrows(
                IllegalArgumentException.class, () -> new AbstractLobbyMessage("Test Lobby", null));
    }

    @Test
    public void testSetters() {
        String lobbyName = "Test Lobby";

        AbstractLobbyMessage message = new AbstractLobbyMessage();

        message.setName(lobbyName);
        message.setUser(userDTO);

        Assertions.assertEquals(lobbyName, message.getName());
        Assertions.assertEquals(userDTO, message.getUser());
    }

    @Test
    public void testEqualsAndHashCode() {
        String lobbyName1 = "Test Lobby 1";
        AbstractLobbyMessage message1 = new AbstractLobbyMessage(lobbyName1, userDTO);

        String lobbyName2 = "Test Lobby 2";
        AbstractLobbyMessage message2 = new AbstractLobbyMessage(lobbyName2, userDTO2);

        AbstractLobbyMessage message3 = new AbstractLobbyMessage(lobbyName1, userDTO);

        Assertions.assertEquals(message1, message3);
        Assertions.assertNotEquals(message1, message2);

        Assertions.assertEquals(message1.hashCode(), message3.hashCode());
        Assertions.assertNotEquals(message1.hashCode(), message2.hashCode());
    }
}
