package de.uol.swp.common.lobby.Exception;

import de.uol.swp.common.lobby.exception.LobbyJoinedExceptionResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LobbyJoinedExceptionResponseTest {

    @Test
    public void testConstructorAndGetters() {
        String message = "Lobby join failed";
        LobbyJoinedExceptionResponse response = new LobbyJoinedExceptionResponse(message);

        Assertions.assertEquals(message, response.getMessage());
    }

    @Test
    public void testEquals() {
        String message = "Lobby join failed";
        LobbyJoinedExceptionResponse response1 = new LobbyJoinedExceptionResponse(message);
        LobbyJoinedExceptionResponse response2 = new LobbyJoinedExceptionResponse(message);

        Assertions.assertEquals(response1, response2);

    }

    @Test
    public void testNotEquals() {
        String message = "Lobby join failed";
        LobbyJoinedExceptionResponse response1 = new LobbyJoinedExceptionResponse(message);
        LobbyJoinedExceptionResponse response3 = new LobbyJoinedExceptionResponse("Different message");

        Assertions.assertNotEquals(response1, response3);
    }
}
