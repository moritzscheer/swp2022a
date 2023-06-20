package de.uol.swp.common.lobby.Exception;

import de.uol.swp.common.lobby.exception.LobbyLeftExceptionResponse;
import de.uol.swp.common.user.UserDTO;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LobbyLeftExceptionResponseTest {

    private final UserDTO userDTO = new UserDTO("Player1", "pw", "ml");

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

    @Test
    public void testToString() {
        String lobbyName = "Test Lobby";
        String message = "Lobby left failed";

        LobbyLeftExceptionResponse response =
                new LobbyLeftExceptionResponse(lobbyName, userDTO, message);

        String expectedString = "LobbyExceptionResponse " + message;
        Assertions.assertEquals(expectedString, response.toString());
    }

    @Test
    public void testEqualsAndHashCode() {
        String lobbyName1 = "Test Lobby 1";
        String message1 = "Lobby left failed";
        LobbyLeftExceptionResponse response1 = new LobbyLeftExceptionResponse(lobbyName1, userDTO, message1);

        Assertions.assertEquals(response1, response1);

        Assertions.assertEquals(response1.hashCode(), response1.hashCode());
    }
}
