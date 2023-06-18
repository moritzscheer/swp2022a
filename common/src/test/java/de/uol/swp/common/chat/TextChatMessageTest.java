package de.uol.swp.common.chat;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class TextChatMessageTest {

    @Test
    void constructorAndGetters_withValidData() {

        String sender = "John";
        String content = "Hello, world!";
        String timestamp = "2021-06-01:00:00";
        TextChatMessage message = new TextChatMessage(sender, content, timestamp);

        assertEquals(sender, message.getMessage());
        assertEquals(content, message.getSenderString());
    }


    @Test
    void equals_withDifferentObjects() {

        TextChatMessage message1 = new TextChatMessage("Tommy", "test!", "2021-06-01:00:00");
        TextChatMessage message2 = new TextChatMessage("Waldemar", "test!", "2021-06-01:00:00");

        assertNotEquals(message1, message2);
    }
}
