package de.uol.swp.common.lobby.Exception;

import de.uol.swp.common.lobby.exception.LobbyCreatedExceptionResponse;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LobbyCreatedExceptionResponseTest {

    @Test
    public void testConstructorAndGetters() {
        String message = "Lobby creation failed";
        LobbyCreatedExceptionResponse response = new LobbyCreatedExceptionResponse(message);

        Assertions.assertEquals(message, response.getMessage());
    }

    @Test
    public void testEquals() {
        String message = "Lobby creation failed";
        LobbyCreatedExceptionResponse response1 = new LobbyCreatedExceptionResponse(message);
        LobbyCreatedExceptionResponse response2 = new LobbyCreatedExceptionResponse(message);

        Assertions.assertEquals(response1, response2);
    }

    @Test
    public void testNotEquals() {
        String message = "Lobby creation failed";
        LobbyCreatedExceptionResponse response1 = new LobbyCreatedExceptionResponse(message);
        LobbyCreatedExceptionResponse response3 =
                new LobbyCreatedExceptionResponse("Different message");

        Assertions.assertNotEquals(response1, response3);
    }
}
