package de.uol.swp.common.chat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

public class TextChatMessageTest {

    /**
     * Tests the constructor and getters of the TextChatMessage class.
     *
     * @author WKempel
     * @result The constructor and the getters should work without throwing an
     * @see de.uol.swp.common.chat.TextChatMessage
     * @since 2023-06-17
     */
    @Test
    public void testConstructorAndGetters() {

        String sender = "John";
        String content = "Hello, world!";
        String timestamp = "2021-06-01:00:00";
        TextChatMessage message = new TextChatMessage(sender, content, timestamp);

        assertEquals(sender, message.getMessage());
        assertEquals(content, message.getSenderString());
    }

    /**
     * Tests the equals method of the TextChatMessage class with different objects.
     *
     * @author WKempel
     * @result The equals method should return true
     * @see de.uol.swp.common.chat.TextChatMessage
     * @since 2023-06-17
     */
    @Test
    public void testEqualsWithDifferentObjects() {

        TextChatMessage message1 = new TextChatMessage("Tommy", "test!", "2021-06-01:00:00");
        TextChatMessage message2 = new TextChatMessage("Waldemar", "test!", "2021-06-01:00:00");

        assertNotEquals(message1, message2);
    }
}
