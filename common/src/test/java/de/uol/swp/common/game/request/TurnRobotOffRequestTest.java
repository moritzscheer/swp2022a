package de.uol.swp.common.game.request;

import de.uol.swp.common.lobby.dto.LobbyDTO;
import de.uol.swp.common.user.UserDTO;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TurnRobotOffRequestTest {

    private final UUID uuid = UUID.randomUUID();
    private final UserDTO turnedOffUser = new UserDTO("testUser", "pw", "ml");
    private final LobbyDTO lobbyDTO = new LobbyDTO(123, "testLobby", turnedOffUser, "pw", true, uuid);


    @Test
    public void testConstructorAndGetters() {
        TurnRobotOffRequest turnRobotOffRequest = new TurnRobotOffRequest(lobbyDTO.getLobbyID(), turnedOffUser);

        assertEquals(lobbyDTO.getLobbyID(), turnRobotOffRequest.getLobbyID());
        assertEquals(turnedOffUser, turnRobotOffRequest.getLoggedInUser());
    }
}
