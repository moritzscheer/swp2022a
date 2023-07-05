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
    private final LobbyDTO lobbyDTO = new LobbyDTO(123, "testLobby", user, "pw", true, randomUUID);

    /**
     * Tests the constructor and getters
     *
     * @author WKempel
     * @result The getters should return the correct lobbyID, lobbyName, userDTO and newOwner
     * @see de.uol.swp.common.lobby.message.UserLeftLobbyMessage
     * @since 2023-06-16
     */
    @Test
    public void testConstructorAndGetters() {

        UserLeftLobbyMessage message =
                new UserLeftLobbyMessage(lobbyDTO.getLobbyID(), lobbyDTO.getName(), user, lobbyDTO);

        Assertions.assertEquals(lobbyDTO.getLobbyID(), message.getLobbyID());
        Assertions.assertEquals(lobbyDTO.getName(), message.getName());
        Assertions.assertEquals(user, message.getUser());
        Assertions.assertEquals(user, message.getLobby().getOwner());
    }

    /**
     * Tests the getLobby method
     *
     * @author WKempel and Moritz Scheer
     * @result The getLobby method should return the lobbyDTO
     * @see de.uol.swp.common.lobby.message.UserLeftLobbyMessage
     * @since 2023-06-16
     */
    @Test
    public void testGetLobby() {

        UserLeftLobbyMessage message =
                new UserLeftLobbyMessage(lobbyDTO.getLobbyID(), lobbyDTO.getName(), user, lobbyDTO);

        Assertions.assertEquals(user, message.getLobby().getOwner());
    }

    /**
     * Tests the getLobbyID method
     *
     * @author WKempel
     * @result The getLobbyID method should return the correct lobbyID
     * @see de.uol.swp.common.lobby.message.UserLeftLobbyMessage
     * @since 2023-06-16
     */
    @Test
    public void testGetLobbyID() {

        UserLeftLobbyMessage message =
                new UserLeftLobbyMessage(lobbyDTO.getLobbyID(), lobbyDTO.getName(), user, lobbyDTO);

        Assertions.assertEquals(lobbyDTO.getLobbyID(), message.getLobbyID());
    }

    /**
     * Tests the getName method
     *
     * @author WKempel
     * @result The getName method should return the correct lobbyName
     * @see de.uol.swp.common.lobby.message.UserLeftLobbyMessage
     * @since 2023-06-16
     */
    @Test
    public void testGetName() {
        UserLeftLobbyMessage message =
                new UserLeftLobbyMessage(lobbyDTO.getLobbyID(), lobbyDTO.getName(), user, lobbyDTO);

        Assertions.assertEquals(lobbyDTO.getName(), message.getName());
    }

    /**
     * Tests the getUser method
     *
     * @author WKempel
     * @result The getUser method should return the correct userDTO
     * @see de.uol.swp.common.lobby.message.UserLeftLobbyMessage
     * @since 2023-06-16
     */
    @Test
    public void testGetUser() {

        UserLeftLobbyMessage message =
                new UserLeftLobbyMessage(lobbyDTO.getLobbyID(), lobbyDTO.getName(), user, lobbyDTO);

        Assertions.assertEquals(user, message.getUser());
    }

    /**
     * Tests the equals and hashCode method
     *
     * @author WKempel
     * @result The equals and hashCode method should return true if the objects are the same
     * @see de.uol.swp.common.lobby.message.UserLeftLobbyMessage
     * @since 2023-06-18
     */
    @Test
    public void testEqualsAndHashCode() {

        UserLeftLobbyMessage message1 =
                new UserLeftLobbyMessage(lobbyDTO.getLobbyID(), lobbyDTO.getName(), user, lobbyDTO);

        UserLeftLobbyMessage message2 =
                new UserLeftLobbyMessage(lobbyDTO.getLobbyID(), lobbyDTO.getName(), user, lobbyDTO);

        Assertions.assertEquals(message1, message2);

        Assertions.assertEquals(message1.hashCode(), message2.hashCode());
    }
}
