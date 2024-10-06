package de.uol.swp.common.lobby.Exception;

import de.uol.swp.common.lobby.exception.LobbyJoinedExceptionResponse;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LobbyJoinedExceptionResponseTest {

    /**
     * Tests the constructor and the getters
     *
     * @author WKempel
     * @result The method should return the message "Lobby join failed"
     * @see de.uol.swp.common.lobby.exception.LobbyJoinedExceptionResponse
     * @since 2023-06-15
     */
    @Test
    public void testConstructorAndGetters() {
        String message = "Lobby join failed";
        LobbyJoinedExceptionResponse response = new LobbyJoinedExceptionResponse(message);

        Assertions.assertEquals(message, response.getMessage());
    }

    /**
     * Tests the equals method
     *
     * @author WKempel
     * @result The method should return true
     * @see de.uol.swp.common.lobby.exception.LobbyJoinedExceptionResponse
     * @since 2023-06-15
     */
    @Test
    public void testEquals() {
        String message = "Lobby join failed";
        LobbyJoinedExceptionResponse response1 = new LobbyJoinedExceptionResponse(message);
        LobbyJoinedExceptionResponse response2 = new LobbyJoinedExceptionResponse(message);

        Assertions.assertEquals(response1, response2);
    }

    /**
     * Tests the equals method with different objects
     *
     * @author WKempel
     * @result The method should return true because the objects are not equal
     * @see de.uol.swp.common.lobby.exception.LobbyJoinedExceptionResponse
     * @since 2023-06-15
     */
    @Test
    public void testNotEquals() {
        String message = "Lobby join failed";
        LobbyJoinedExceptionResponse response1 = new LobbyJoinedExceptionResponse(message);
        LobbyJoinedExceptionResponse response3 =
                new LobbyJoinedExceptionResponse("Different message");

        Assertions.assertNotEquals(response1, response3);
    }
}
