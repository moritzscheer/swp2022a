package de.uol.swp.common.lobby.request;

import de.uol.swp.common.user.UserDTO;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SetPlayerReadyInLobbyRequestTest {

    private final UserDTO user = new UserDTO("Player1", "pw", "ml");

    @Test
    public void testGetters() {
        Integer lobbyID = 123;
        Boolean ready = true;

        SetPlayerReadyInLobbyRequest request =
                new SetPlayerReadyInLobbyRequest(lobbyID, user, ready);

        Assertions.assertEquals(lobbyID, request.getLobbyID());
        Assertions.assertEquals(ready, request.isReady());
    }
}
