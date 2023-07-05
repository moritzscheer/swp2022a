package de.uol.swp.common.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class LobbyDoesNotExistExceptionTest {

    protected int lobbyID;
    protected String message = "Lobby does not exist";

    /**
     * Tests the constructor.
     *
     * @author WKempel
     * @result The constructor should work without throwing an exception
     * @see de.uol.swp.common.exception.LobbyDoesNotExistException
     * @since 2023-06-17
     */
    @Test
    public void testConstructor() {
        LobbyDoesNotExistException lobbyDoesNotExistException =
                new LobbyDoesNotExistException(message, lobbyID);
        assertEquals(message, lobbyDoesNotExistException.getMessage());
    }
}
