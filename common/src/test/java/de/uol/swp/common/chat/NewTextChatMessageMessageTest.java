package de.uol.swp.common.chat;

import static org.junit.jupiter.api.Assertions.assertEquals;

import de.uol.swp.common.chat.message.NewTextChatMessageMessage;

import org.junit.jupiter.api.Test;

import java.util.UUID;

public class NewTextChatMessageMessageTest {

    private final UUID channelID = UUID.randomUUID();

    private final TextChatChannelDTO channel = new TextChatChannelDTO(channelID);

    @Test
    void getters() {

        TextChatMessage message = new TextChatMessage("senderId", "senderName", "Hello, World!");

        NewTextChatMessageMessage msg = new NewTextChatMessageMessage(channel, message);

        assertEquals(channel, msg.getChannel());
        assertEquals(message, msg.getMessage());
    }
}
