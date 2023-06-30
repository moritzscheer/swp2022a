package de.uol.swp.common.lobby.response;

import de.uol.swp.common.lobby.dto.LobbyDTO;
import de.uol.swp.common.user.UserDTO;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class LobbyLeftSuccessfulResponseTest {

    private final UUID uuid = UUID.randomUUID();
    private final UserDTO user = new UserDTO("Player1", "pw", "ml");
    private final LobbyDTO lobby = new LobbyDTO(123, "lobbyTest", user, "pw", true, uuid);

    /**
     * Tests the constructor and the getters
     *
     * @author WKempel
     * @result The getters should return the correct lobbyName, user and lobby
     * @see de.uol.swp.common.lobby.response.LobbyLeftSuccessfulResponse
     * @since 2023-06-17
     */
    @Test
    public void testConstructorAndGetter() {

        LobbyLeftSuccessfulResponse response = new LobbyLeftSuccessfulResponse(lobby, user);

        Assertions.assertEquals(lobby.getName(), response.getName());
        Assertions.assertEquals(user, response.getUser());
        Assertions.assertNotEquals(lobby, response.getLobby());
    }
}
