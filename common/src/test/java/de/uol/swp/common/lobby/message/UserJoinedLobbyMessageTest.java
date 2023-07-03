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

    /**
     * Tests the constructor and getters
     *
     * @author WKempel
     * @result The getters should return the correct lobbyDTO, lobbyName and userDTO
     * @see de.uol.swp.common.lobby.message.UserJoinedLobbyMessage
     * @since 2023-06-16
     */
    @Test
    public void testConstructorAndGetters() {

        UserJoinedLobbyMessage message =
                new UserJoinedLobbyMessage(lobbyDTO.getLobbyID(), lobbyDTO.getName(), userDTO);

        // because the constructor creates a new LobbyDTO, the LobbyDTOs are not equal
        Assertions.assertNotEquals(lobbyDTO, message.getLobbyID());
        Assertions.assertEquals(lobbyDTO.getName(), message.getName());
        Assertions.assertEquals(userDTO, message.getUser());
    }

    /**
     * Tests the getLobbyID method
     *
     * @author WKempel
     * @result The getLobby method should return the correct lobbyID
     * @see de.uol.swp.common.lobby.message.UserJoinedLobbyMessage
     * @since 2023-06-16
     */
    @Test
    public void testGetLobbyID() {

        UserJoinedLobbyMessage message =
                new UserJoinedLobbyMessage(lobbyDTO.getLobbyID(), lobbyDTO.getName(), userDTO);

        Assertions.assertEquals(lobbyDTO.getLobbyID(), message.getLobbyID());
    }

    /**
     * Tests the getName method
     *
     * @author WKempel
     * @result The getName method should return the correct name of the lobby
     * @see de.uol.swp.common.lobby.message.UserJoinedLobbyMessage
     * @since 2023-06-16
     */
    @Test
    public void testGetName() {

        UserJoinedLobbyMessage message =
                new UserJoinedLobbyMessage(lobbyDTO.getLobbyID(), lobbyDTO.getName(), userDTO);

        Assertions.assertEquals(lobbyDTO.getName(), message.getName());
    }

    /**
     * Tests the getUser method
     *
     * @author WKempel
     * @result The getUser method should return the correct userDTO
     * @see de.uol.swp.common.lobby.message.UserJoinedLobbyMessage
     * @since 2023-06-16
     */
    @Test
    public void testGetUser() {

        UserJoinedLobbyMessage message =
                new UserJoinedLobbyMessage(lobbyDTO.getLobbyID(), lobbyDTO.getName(), userDTO);

        Assertions.assertEquals(userDTO, message.getUser());
    }

    /**
     * Tests the equals and hashCode method
     *
     * @author WKempel
     * @result The equals method should return true if the objects are equal
     * @see de.uol.swp.common.lobby.message.UserJoinedLobbyMessage
     * @since 2023-06-16
     */
    @Test
    public void testEqualsAndHashCode() {

        UserJoinedLobbyMessage message1 =
                new UserJoinedLobbyMessage(lobbyDTO.getLobbyID(), lobbyDTO.getName(), userDTO);

        UserJoinedLobbyMessage message2 =
                new UserJoinedLobbyMessage(lobbyDTO.getLobbyID(), lobbyDTO.getName(), userDTO);

        Assertions.assertEquals(message1, message2);

        Assertions.assertEquals(message1.hashCode(), message2.hashCode());
    }
}
