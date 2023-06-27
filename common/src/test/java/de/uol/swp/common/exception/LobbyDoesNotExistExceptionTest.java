package de.uol.swp.common.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LobbyDoesNotExistExceptionTest {

    protected int lobbyID;
    protected String message = "Lobby does not exist";

    @Test
    public void testConstructor() {
        LobbyDoesNotExistException lobbyDoesNotExistException = new LobbyDoesNotExistException(message, lobbyID);
        assertEquals(message, lobbyDoesNotExistException.getMessage());
    }
}
