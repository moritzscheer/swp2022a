package de.uol.swp.common.lobby.response;

import de.uol.swp.common.game.Map;
import de.uol.swp.common.lobby.dto.LobbyDTO;
import de.uol.swp.common.user.UserDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class LobbyJoinedSuccessfulResponseTest {

    private final UUID uuid = UUID.randomUUID();
    private final UserDTO user = new UserDTO("Player1","pw","ml");
    private final LobbyDTO lobby = new LobbyDTO(123,"lobbyTest", user, "pw", true, uuid);


    @Test
    public void testConstructorAndGetter() {

        LobbyJoinedSuccessfulResponse response = new LobbyJoinedSuccessfulResponse(lobby, user);

        // Assert
        Assertions.assertEquals(lobby.getName(), response.getName());
        Assertions.assertEquals(user, response.getUser());
        Assertions.assertEquals(lobby, response.getLobby());
        Assertions.assertEquals(lobby.getMap(), response.getMap());
    }

    @Test
    public void testGetMap() {
        LobbyJoinedSuccessfulResponse response = new LobbyJoinedSuccessfulResponse(lobby, user);
        Assertions.assertEquals(lobby.getMap(), response.getMap());
    }
}
