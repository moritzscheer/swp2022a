package de.uol.swp.common.lobby.Exception;

import de.uol.swp.common.lobby.exception.LobbyLeftExceptionResponse;
import de.uol.swp.common.user.UserDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LobbyLeftExceptionResponseTest {

    private final UserDTO userDTO = new UserDTO("Player1","pw","ml");

    @Test
    public void testConstructorAndGetters() {
        String lobbyName = "Test Lobby";
        String message = "Lobby left failed";

        LobbyLeftExceptionResponse response = new LobbyLeftExceptionResponse(lobbyName, userDTO, message);

        Assertions.assertEquals(lobbyName, response.getName());
        Assertions.assertEquals(userDTO, response.getUser());
        Assertions.assertEquals(message, response.getMessage());
    }

    @Test
    public void testToString() {
        String lobbyName = "Test Lobby";
        String message = "Lobby left failed";

        LobbyLeftExceptionResponse response = new LobbyLeftExceptionResponse(lobbyName, userDTO, message);

        String expectedString = "LobbyExceptionResponse " + message;
        Assertions.assertEquals(expectedString, response.toString());
    }
}
