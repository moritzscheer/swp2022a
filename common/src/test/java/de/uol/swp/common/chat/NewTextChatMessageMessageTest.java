package de.uol.swp.common.chat;

import de.uol.swp.common.chat.message.NewTextChatMessageMessage;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

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
