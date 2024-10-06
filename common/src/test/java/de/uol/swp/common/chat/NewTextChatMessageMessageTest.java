package de.uol.swp.common.chat;

import static org.junit.jupiter.api.Assertions.assertEquals;

import de.uol.swp.common.chat.message.NewTextChatMessageMessage;

import org.junit.jupiter.api.Test;

import java.util.UUID;

public class NewTextChatMessageMessageTest {

    private final UUID channelID = UUID.randomUUID();

    private final TextChatChannelDTO channel = new TextChatChannelDTO(channelID);

    /**
     * Tests the constructor and the getters
     *
     * @author WKempel
     * @result The constructor and the getters should work without throwing an exception
     * @see de.uol.swp.common.chat.message.NewTextChatMessageMessage
     * @see de.uol.swp.common.chat.TextChatChannelDTO
     * @see de.uol.swp.common.chat.TextChatMessage
     * @since 2023-06-17
     */
    @Test
    public void testConstructorAndGetter() {

        TextChatMessage message = new TextChatMessage("senderId", "senderName", "Hello, World!");

        NewTextChatMessageMessage msg = new NewTextChatMessageMessage(channel, message);

        assertEquals(channel, msg.getChannel());
        assertEquals(message, msg.getMessage());
    }
}
