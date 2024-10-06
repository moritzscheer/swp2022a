package de.uol.swp.common.chat;

import static org.junit.jupiter.api.Assertions.assertEquals;

import de.uol.swp.common.chat.message.TextHistoryMessage;

import org.junit.jupiter.api.Test;

public class TextHistoryMessageTest {

    /**
     * Tests the constructor and getters.
     *
     * @author WKempel
     * @result The constructor and the getters should work without throwing an
     * @result message.
     * @see de.uol.swp.common.lobby.message.AbstractLobbyMessage
     * @since 2023-06-17
     */
    @Test
    public void testConstructorAndGetters() {

        int lobbyID = 123;
        String message = "This is a chat message.";

        TextHistoryMessage historyMessage = new TextHistoryMessage(lobbyID, message);

        assertEquals(lobbyID, historyMessage.getLobbyID());
        assertEquals(message, historyMessage.getMessage());
    }

    /**
     * Tests the equals method with equal objects.
     *
     * @author WKempel
     * @result The equals method should return true
     * @see de.uol.swp.common.lobby.message.AbstractLobbyMessage
     * @since 2023-06-17
     */
    @Test
    void testEqualsWithEqualObjects() {

        int lobbyID = 123;
        String message = "This is a chat message.";

        TextHistoryMessage historyMessage1 = new TextHistoryMessage(lobbyID, message);
        TextHistoryMessage historyMessage2 = new TextHistoryMessage(lobbyID, message);

        assertEquals(historyMessage1, historyMessage2);
    }
}
