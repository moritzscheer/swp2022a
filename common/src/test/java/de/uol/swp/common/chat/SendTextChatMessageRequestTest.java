package de.uol.swp.common.chat;

import static org.junit.jupiter.api.Assertions.*;

import de.uol.swp.common.chat.message.SendTextChatMessageRequest;

import org.junit.jupiter.api.Test;

import java.util.UUID;

public class SendTextChatMessageRequestTest {

    @Test
    void constructorAndGetters_withValidData() {

        UUID channelId = UUID.randomUUID();
        String message = "test!";

        SendTextChatMessageRequest request = new SendTextChatMessageRequest(channelId, message);

        assertEquals(channelId, request.getChannel().getUUID());
        assertEquals(message, request.getMessage());
        assertTrue(request.authorizationNeeded());
    }

    @Test
    void equals_withEqualObjects() {

        UUID channelId = UUID.randomUUID();
        String message = "test!";

        SendTextChatMessageRequest request1 = new SendTextChatMessageRequest(channelId, message);
        SendTextChatMessageRequest request2 = new SendTextChatMessageRequest(channelId, message);

        assertEquals(request1, request2);
    }

    @Test
    void hashCode_withEqualObjects() {

        UUID channelId = UUID.randomUUID();
        String message = "test!";

        SendTextChatMessageRequest request1 = new SendTextChatMessageRequest(channelId, message);
        SendTextChatMessageRequest request2 = new SendTextChatMessageRequest(channelId, message);

        assertEquals(request1.hashCode(), request2.hashCode());
    }
}
