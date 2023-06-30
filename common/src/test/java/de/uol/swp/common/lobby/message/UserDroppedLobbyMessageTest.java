package de.uol.swp.common.lobby.message;

import de.uol.swp.common.lobby.dto.LobbyDTO;
import de.uol.swp.common.user.UserDTO;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class UserDroppedLobbyMessageTest {

    private final UserDTO userDTO = new UserDTO("Player1", "pw", "ml");
    private final UUID randomUUID = UUID.randomUUID();
    private final LobbyDTO lobbyDTO =
            new LobbyDTO(123, "testLobby", userDTO, "pw", true, randomUUID);

    /**
     * Tests the constructor and getters
     *
     * @author WKempel
     * @result The getters should return the correct lobbyDTO userName and userDTO
     * @see de.uol.swp.common.lobby.message.UserDroppedLobbyMessage
     * @since 2023-06-16
     */
    @Test
    public void testConstructorAndGetters() {

        UserDroppedLobbyMessage message =
                new UserDroppedLobbyMessage(lobbyDTO, lobbyDTO.getName(), userDTO);

        // because the constructor creates a new LobbyDTO, the LobbyDTOs are not equal
        Assertions.assertNotEquals(lobbyDTO, message.getLobby());
        Assertions.assertEquals(lobbyDTO.getName(), message.getName());
        Assertions.assertEquals(userDTO, message.getUser());
    }

    /**
     * Tests the getLobby method
     *
     * @author WKempel
     * @result The getLobby method should return the correct lobbyDTO
     * @see de.uol.swp.common.lobby.message.UserDroppedLobbyMessage
     * @since 2023-06-16
     */
    @Test
    public void testGetLobby() {

        UserDroppedLobbyMessage message =
                new UserDroppedLobbyMessage(lobbyDTO, lobbyDTO.getName(), userDTO);

        // because the constructor creates a new LobbyDTO, the LobbyDTOs are not equal
        Assertions.assertNotEquals(lobbyDTO, message.getLobby());
    }

    /**
     * Tests the getName method
     *
     * @author WKempel
     * @result The getName method should return the correct name
     * @see de.uol.swp.common.lobby.message.UserDroppedLobbyMessage
     * @since 2023-06-16
     */
    @Test
    public void testGetName() {

        UserDroppedLobbyMessage message =
                new UserDroppedLobbyMessage(lobbyDTO, lobbyDTO.getName(), userDTO);

        Assertions.assertEquals(lobbyDTO.getName(), message.getName());
    }

    /**
     * Tests the getUser method
     *
     * @author WKempel
     * @result The getUser method should return the correct userDTO
     * @see de.uol.swp.common.lobby.message.UserDroppedLobbyMessage
     * @since 2023-06-16
     */
    @Test
    public void testGetUser() {

        UserDroppedLobbyMessage message =
                new UserDroppedLobbyMessage(lobbyDTO, lobbyDTO.getName(), userDTO);

        Assertions.assertEquals(userDTO, message.getUser());
    }

    /**
     * Tests the equals and the hashCode method
     *
     * @author WKempel
     * @result The equals method should return true if the objects are equal
     * @see de.uol.swp.common.lobby.message.UserDroppedLobbyMessage
     * @since 2023-06-16
     */
    @Test
    public void testEqualsAndHashCode() {

        UserDroppedLobbyMessage message1 = new UserDroppedLobbyMessage(lobbyDTO, lobbyDTO.getName(), userDTO);

        UserDroppedLobbyMessage message2 = new UserDroppedLobbyMessage(lobbyDTO, lobbyDTO.getName(), userDTO);

        Assertions.assertEquals(message1, message2);

        Assertions.assertEquals(message1.hashCode(), message2.hashCode());
    }
}
