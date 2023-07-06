package de.uol.swp.common.lobby.response;

import de.uol.swp.common.lobby.dto.LobbyDTO;
import de.uol.swp.common.user.UserDTO;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class LobbyCreatedSuccessfulResponseTest {

    private final UUID uuid = UUID.randomUUID();
    private final UserDTO user = new UserDTO("Player1", "pw", "ml");
    private final UserDTO user2 = new UserDTO("Player2", "pw2", "ml2");
    private final LobbyDTO lobby = new LobbyDTO(123, "lobbyTest", user, "pw", true, uuid, false);
    private final LobbyDTO lobby2 = new LobbyDTO(123, "lobbyTest2", user2, "pw", true, uuid, false);

    /**
     * Tests the constructor and the getters
     *
     * @author WKempel
     * @result The getters should return the correct lobbyName, user and lobby
     * @see de.uol.swp.common.lobby.response.LobbyCreatedSuccessfulResponse
     * @since 2023-06-17
     */
    @Test
    public void testConstructorAndGetter() {

        LobbyCreatedSuccessfulResponse response = new LobbyCreatedSuccessfulResponse(lobby, user);

        Assertions.assertEquals(lobby.getName(), response.getName());
        Assertions.assertEquals(user, response.getUser());
        Assertions.assertEquals(lobby, response.getLobby());
    }

    /**
     * Tests the constructor and the getters with null
     *
     * @author WKempel
     * @result The getter should throw a NullPointerException
     * @see de.uol.swp.common.lobby.response.LobbyCreatedSuccessfulResponse
     * @since 2023-06-17
     */
    @Test
    public void testConstructorAndGetterNull() {

        Assertions.assertThrows(
                NullPointerException.class, () -> new LobbyCreatedSuccessfulResponse(null, null));
    }
}
