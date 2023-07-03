package de.uol.swp.common.game.message;

import static org.junit.jupiter.api.Assertions.*;

import de.uol.swp.common.user.UserDTO;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class GameOverMessageTest {

    private final UserDTO userWon = new UserDTO("Player1", "pw", "ml");
    private final UserDTO userNotWon = new UserDTO("Player2", "pw", "ml2");

    /**
     * Tests the getter of the LobbyID
     *
     * @author WKempel
     * @result The constructor and the getters should work without throwing an exception
     * @see GameOverMessage
     * @since 2023-06-14
     */
    @Test
    public void testGetLobbyID() {
        int lobbyID = 123;
        GameOverMessage message = new GameOverMessage(lobbyID, userWon);

        assertEquals(lobbyID, message.getLobbyID());
    }

    /**
     * Tests the getter of the UserWon
     *
     * @author WKempel
     * @result The constructor and the getters should work without throwing an exception
     * @see GameOverMessage
     * @since 2023-06-14
     */
    @Test
    public void testGetUserWon() {
        int lobbyID = 123;
        GameOverMessage message = new GameOverMessage(lobbyID, userWon);

        assertEquals(userWon, message.getUserWon());
    }

    /**
     * Tests the getter of the UserWon
     *
     * @author WKempel
     * @result The constructor and the getters should work without throwing an exception
     * @see GameOverMessage
     * @since 2023-06-14
     */
    @Test
    public void testGetUserNotWon() {
        int lobbyID = 123;
        GameOverMessage message = new GameOverMessage(lobbyID, userWon);

        assertNotEquals(userNotWon, message.getUserWon());
    }

    /**
     * Tests the constructor with a negative value for the lobbyID
     *
     * @author WKempel
     * @throws IllegalArgumentException
     * @see GameOverMessage
     * @since 2023-06-14
     */
    @Test
    public void testGetLobbyIDWithNegativeValue() {
        int lobbyID = -1;
        RoundIsOverMessage message = new RoundIsOverMessage(lobbyID, new ArrayList<>());

        Assertions.assertThrows(IllegalArgumentException.class, message::getLobbyID);
    }
}
