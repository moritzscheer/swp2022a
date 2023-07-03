package de.uol.swp.common.lobby.Exception;

import de.uol.swp.common.lobby.exception.LobbyLeftExceptionResponse;
import de.uol.swp.common.user.UserDTO;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LobbyLeftExceptionResponseTest {

    private final UserDTO userDTO = new UserDTO("Player1", "pw", "ml");

    /**
     * Tests the constructor and getters
     *
     * @author WKempel
     * @result The getters should return the correct lobbyName, userDTO and message
     * @see de.uol.swp.common.lobby.exception.LobbyLeftExceptionResponse
     * @since 2023-06-15
     */
    @Test
    public void testConstructorAndGetters() {
        String lobbyName = "Test Lobby";
        String message = "Lobby left failed";

        LobbyLeftExceptionResponse response =
                new LobbyLeftExceptionResponse(lobbyName, userDTO, message);

        Assertions.assertEquals(lobbyName, response.getName());
        Assertions.assertEquals(userDTO, response.getUser());
        Assertions.assertEquals(message, response.getMessage());
    }

    /**
     * Tests the toString method
     *
     * @author WKempel
     * @result The method should return the correct string
     * @see de.uol.swp.common.lobby.exception.LobbyLeftExceptionResponse
     * @since 2023-06-15
     */
    @Test
    public void testToString() {
        String lobbyName = "Test Lobby";
        String message = "Lobby left failed";

        LobbyLeftExceptionResponse response =
                new LobbyLeftExceptionResponse(lobbyName, userDTO, message);

        String expectedString = "LobbyExceptionResponse " + message;
        Assertions.assertEquals(expectedString, response.toString());
    }

    /**
     * Tests the equals and hashCode methods
     *
     * @author WKempel
     * @result The methods should return true if the objects are equal
     * @see de.uol.swp.common.lobby.exception.LobbyLeftExceptionResponse
     * @since 2023-06-18
     */
    @Test
    public void testEqualsAndHashCode() {
        String lobbyName1 = "Test Lobby 1";
        String message1 = "Lobby left failed";
        LobbyLeftExceptionResponse response1 =
                new LobbyLeftExceptionResponse(lobbyName1, userDTO, message1);

        Assertions.assertEquals(response1, response1);

        Assertions.assertEquals(response1.hashCode(), response1.hashCode());
    }
}
