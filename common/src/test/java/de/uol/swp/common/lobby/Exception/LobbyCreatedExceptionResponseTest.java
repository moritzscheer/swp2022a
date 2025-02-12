package de.uol.swp.common.lobby.Exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import de.uol.swp.common.lobby.exception.LobbyCreatedExceptionResponse;

import org.junit.jupiter.api.Test;

public class LobbyCreatedExceptionResponseTest {

    /**
     * Tests the constructor and the getters
     *
     * @author WKempel
     * @result The method should return the message "Lobby creation failed"
     * @see de.uol.swp.common.lobby.exception.LobbyCreatedExceptionResponse
     * @since 2023-06-15
     */
    @Test
    public void testConstructorAndGetters() {
        String message = "Lobby creation failed";
        LobbyCreatedExceptionResponse response = new LobbyCreatedExceptionResponse(message);

        assertEquals(message, response.getMessage());
    }

    /**
     * Tests the equals method
     *
     * @author WKempel
     * @result The method should return true
     * @see de.uol.swp.common.lobby.exception.LobbyCreatedExceptionResponse
     * @since 2023-06-15
     */
    @Test
    public void testEquals() {
        String message = "Lobby creation failed";
        LobbyCreatedExceptionResponse response1 = new LobbyCreatedExceptionResponse(message);
        LobbyCreatedExceptionResponse response2 = new LobbyCreatedExceptionResponse(message);

        assertEquals(response1, response2);
    }

    /**
     * Tests the equals method
     *
     * @author WKempel
     * @result The method should return true because the objects are not equal
     * @see de.uol.swp.common.lobby.exception.LobbyCreatedExceptionResponse
     * @since 2023-06-15
     */
    @Test
    public void testNotEquals() {
        String message = "Lobby creation failed";
        LobbyCreatedExceptionResponse response1 = new LobbyCreatedExceptionResponse(message);
        LobbyCreatedExceptionResponse response3 =
                new LobbyCreatedExceptionResponse("Different message");

        assertNotEquals(response1, response3);
    }

    /**
     * Tests the hashCode method
     *
     * @author WKempel
     * @result The method should return true
     * @see de.uol.swp.common.lobby.exception.LobbyCreatedExceptionResponse
     * @since 2023-06-18
     */
    @Test
    public void testEqualsAndHashCode() {
        LobbyCreatedExceptionResponse response1 = new LobbyCreatedExceptionResponse("Test");
        LobbyCreatedExceptionResponse response2 = new LobbyCreatedExceptionResponse("Test");
        LobbyCreatedExceptionResponse response3 = new LobbyCreatedExceptionResponse("Different");

        assertEquals(response1, response2);
        assertEquals(response1.hashCode(), response2.hashCode());

        assertNotEquals(response1, response3);
        assertNotEquals(response1.hashCode(), response3.hashCode());
    }
}
