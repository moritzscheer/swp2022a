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
            new LobbyDTO(123, "testLobby", userDTO, "pw", true, randomUUID);

    /**
     * Tests the constructor and getters
     *
     * @author WKempel
     * @result The getters should return the correct lobbyID, userDTO and ready
     * @see de.uol.swp.common.lobby.message.PlayerReadyInLobbyMessage
     * @since 2023-06-16
     */
    @Test
    public void testConstructorAndGetters() {
        boolean ready = true;

        PlayerReadyInLobbyMessage message =
                new PlayerReadyInLobbyMessage(lobbyDTO.getLobbyID(), userDTO, ready);

        Assertions.assertEquals(lobbyDTO.getLobbyID(), message.getLobbyID());
        Assertions.assertEquals(userDTO, message.getUser());
        Assertions.assertEquals(ready, message.isReady());
    }

    /**
     * Tests the isReady method
     *
     * @author WKempel
     * @result The isReady method should return true if the player is ready and false if the player
     *     is not ready
     * @see de.uol.swp.common.lobby.message.PlayerReadyInLobbyMessage
     * @since 2023-06-16
     */
    @Test
    public void testIsReady() {
        boolean ready = true;

        PlayerReadyInLobbyMessage message =
                new PlayerReadyInLobbyMessage(lobbyDTO.getLobbyID(), userDTO, ready);

        Assertions.assertTrue(message.isReady());

        message = new PlayerReadyInLobbyMessage(lobbyDTO.getLobbyID(), userDTO, false);

        Assertions.assertFalse(message.isReady());
    }

    /**
     * Tests the getUser method
     *
     * @author WKempel
     * @result The getUser method should return the correct userDTO
     * @see de.uol.swp.common.lobby.message.PlayerReadyInLobbyMessage
     * @since 2023-06-16
     */
    @Test
    public void testGetUser() {
        boolean ready = true;

        PlayerReadyInLobbyMessage message =
                new PlayerReadyInLobbyMessage(lobbyDTO.getLobbyID(), userDTO, ready);

        Assertions.assertEquals(userDTO, message.getUser());
    }

    /**
     * Tests the getLobbyID method
     *
     * @author WKempel
     * @result The getLobbyID method should return the correct lobbyID
     * @see de.uol.swp.common.lobby.message.PlayerReadyInLobbyMessage
     * @since 2023-06-16
     */
    @Test
    public void testGetLobbyID() {
        boolean ready = true;

        PlayerReadyInLobbyMessage message =
                new PlayerReadyInLobbyMessage(lobbyDTO.getLobbyID(), userDTO, ready);

        Assertions.assertEquals(lobbyDTO.getLobbyID(), message.getLobbyID());
    }

    /**
     * Tests the constructor with null as value for the userDTO
     *
     * @author WKempel
     * @result The constructor should throw a NullPointerException
     * @see de.uol.swp.common.lobby.message.PlayerReadyInLobbyMessage
     * @since 2023-06-16
     */
    @Test
    public void testConstructorWithNullUser() {
        boolean ready = true;

        Assertions.assertThrows(
                NullPointerException.class,
                () -> {
                    new PlayerReadyInLobbyMessage(lobbyDTO.getLobbyID(), null, ready);
                });
    }

    /**
     * Tests the constructor with null as value for the lobbyID
     *
     * @author WKempel
     * @result The constructor should throw a NullPointerException
     * @see de.uol.swp.common.lobby.message.PlayerReadyInLobbyMessage
     * @since 2023-06-16
     */
    @Test
    public void testConstructorWithNullLobbyID() {
        boolean ready = true;

        Assertions.assertThrows(
                NullPointerException.class,
                () -> {
                    new PlayerReadyInLobbyMessage(null, userDTO, ready);
                });
    }

    /**
     * Tests the equals and hashCode method
     *
     * @author WKempel
     * @result The equals and hashCode method should return true if the objects are equal
     * @see de.uol.swp.common.lobby.message.PlayerReadyInLobbyMessage
     * @since 2023-06-18
     */
    @Test
    public void testEqualsAndHashCode() {

        boolean ready1 = true;
        PlayerReadyInLobbyMessage message1 =
                new PlayerReadyInLobbyMessage(lobbyDTO.getLobbyID(), userDTO, ready1);

        PlayerReadyInLobbyMessage message2 =
                new PlayerReadyInLobbyMessage(lobbyDTO.getLobbyID(), userDTO, ready1);

        Assertions.assertEquals(message1, message2);

        Assertions.assertEquals(message1.hashCode(), message2.hashCode());
    }
}
