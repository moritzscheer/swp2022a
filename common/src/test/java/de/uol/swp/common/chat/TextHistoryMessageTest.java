package de.uol.swp.common.chat;

import static org.junit.jupiter.api.Assertions.assertEquals;

import de.uol.swp.common.chat.message.TextHistoryMessage;

import org.junit.jupiter.api.Test;

public class TextHistoryMessageTest {

    @Test
    void constructorAndGetters_withValidData() {

        int lobbyID = 123;
        String message = "This is a chat message.";

        TextHistoryMessage historyMessage = new TextHistoryMessage(lobbyID, message);

        assertEquals(lobbyID, historyMessage.getLobbyID());
        assertEquals(message, historyMessage.getMessage());
    }

    @Test
    void equals_withEqualObjects() {

        int lobbyID = 123;
        String message = "This is a chat message.";

        TextHistoryMessage historyMessage1 = new TextHistoryMessage(lobbyID, message);
        TextHistoryMessage historyMessage2 = new TextHistoryMessage(lobbyID, message);

        assertEquals(historyMessage1, historyMessage2);
    }
}
