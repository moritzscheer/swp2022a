package de.uol.swp.common.request;

import de.uol.swp.common.game.request.GetMapDataRequest;
import de.uol.swp.common.lobby.dto.LobbyDTO;
import de.uol.swp.common.user.UserDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class GetMapDataRequestTest {


    private final UserDTO userDTO = new UserDTO("Player1","pw","ml");
    private final UUID chatID = UUID.randomUUID();
    private LobbyDTO lobbyDTO = new LobbyDTO(123,"testLobby",userDTO,"pw",false,chatID);


    @Test
    public void testConstructorWithNullLobby() {
        lobbyDTO = null;

        Assertions.assertThrows(NullPointerException.class, () -> {
            new GetMapDataRequest(lobbyDTO);
        });
    }

}
