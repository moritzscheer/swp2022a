package de.uol.swp.common.game.message;

import static org.junit.jupiter.api.Assertions.*;

import de.uol.swp.common.user.UserDTO;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GameOverMessageTest {

    private final UserDTO userWon = new UserDTO("Player1", "pw", "ml");
    private final UserDTO userNotWon = new UserDTO("Player2", "pw", "ml2");

    @Test
    public void testGetLobbyID() {
        int lobbyID = 123;
        GameOverMessage message = new GameOverMessage(lobbyID, userWon);

        assertEquals(lobbyID, message.getLobbyID());
    }

    @Test
    public void testGetUserWon() {
        int lobbyID = 123;
        GameOverMessage message = new GameOverMessage(lobbyID, userWon);

        assertEquals(userWon, message.getUserWon());
    }

    @Test
    public void testGetUserNotWon() {
        int lobbyID = 123;
        GameOverMessage message = new GameOverMessage(lobbyID, userWon);

        assertNotEquals(userNotWon, message.getUserWon());
    }

    @Test
    public void testGetLobbyIDWithNegativeValue() {
        int lobbyID = -1;
        RoundIsOverMessage message = new RoundIsOverMessage(lobbyID);

        Assertions.assertThrows(IllegalArgumentException.class, message::getLobbyID);
    }
}
