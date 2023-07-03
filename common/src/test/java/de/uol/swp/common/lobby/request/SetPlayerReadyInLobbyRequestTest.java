package de.uol.swp.common.lobby.request;

import de.uol.swp.common.user.UserDTO;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SetPlayerReadyInLobbyRequestTest {

    private final UserDTO user = new UserDTO("Player1", "pw", "ml");

    /**
     * Tests the getters
     *
     * @author WKempel
     * @result The getters should return the correct lobbyID and ready status
     * @see de.uol.swp.common.lobby.request.SetPlayerReadyInLobbyRequest
     * @since 2023-06-17
     */
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
