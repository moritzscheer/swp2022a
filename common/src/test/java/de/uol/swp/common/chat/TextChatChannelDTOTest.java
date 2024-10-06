package de.uol.swp.common.chat;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import java.util.UUID;

public class TextChatChannelDTOTest {

    private final UUID channelID = UUID.randomUUID();

    /**
     * Tests the constructor and the getters
     *
     * @author WKempel
     * @result The constructor and the getters should work without throwing an exception
     * @see de.uol.swp.common.chat.TextChatChannelDTO
     * @since 2023-06-17
     */
    @Test
    public void testConstructorAndGetters() {

        TextChatChannelDTO channelDTO = new TextChatChannelDTO(channelID);

        assertEquals(channelID, channelDTO.getUUID());
    }
}
