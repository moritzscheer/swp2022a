package de.uol.swp.common.chat;

import static org.junit.jupiter.api.Assertions.*;

import de.uol.swp.common.chat.message.SendTextChatMessageRequest;

import org.junit.jupiter.api.Test;

import java.util.UUID;

public class SendTextChatMessageRequestTest {

    /**
     * Tests the constructor and the getters with valid data
     *
     * @author WKempel
     * @result The constructor and the getters should work without throwing an exception
     * @see de.uol.swp.common.chat.message.SendTextChatMessageRequest
     * @see de.uol.swp.common.chat.TextChatChannelDTO
     * @see de.uol.swp.common.chat.TextChatMessage
     * @see java.util.UUID
     * @since 2023-06-17
     */
    @Test
    public void testConstructorAndGetters() {

        UUID channelId = UUID.randomUUID();
        String message = "test!";

        SendTextChatMessageRequest request = new SendTextChatMessageRequest(channelId, message);

        assertEquals(channelId, request.getChannel().getUUID());
        assertEquals(message, request.getMessage());
        assertTrue(request.authorizationNeeded());
    }

    /**
     * Tests the equals method with equal objects
     *
     * @author WKempel
     * @result The equals method should return true
     * @see de.uol.swp.common.chat.message.SendTextChatMessageRequest
     * @since 2023-06-17
     */
    @Test
    public void testEqualsWithEqualObjects() {

        UUID channelId = UUID.randomUUID();
        String message = "test!";

        SendTextChatMessageRequest request1 = new SendTextChatMessageRequest(channelId, message);
        SendTextChatMessageRequest request2 = new SendTextChatMessageRequest(channelId, message);

        assertEquals(request1, request2);
    }

    /**
     * Tests the equals method with unequal objects
     *
     * @author WKempel
     * @see de.uol.swp.common.chat.message.SendTextChatMessageRequest
     * @since 2023-06-17
     */
    @Test
    public void testHashCodeWithEqualObjects() {

        UUID channelId = UUID.randomUUID();
        String message = "test!";

        SendTextChatMessageRequest request1 = new SendTextChatMessageRequest(channelId, message);
        SendTextChatMessageRequest request2 = new SendTextChatMessageRequest(channelId, message);

        assertEquals(request1.hashCode(), request2.hashCode());
    }
}
