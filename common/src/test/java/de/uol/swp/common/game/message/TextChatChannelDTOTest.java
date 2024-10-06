package de.uol.swp.common.game.message;

import static org.junit.jupiter.api.Assertions.*;

import de.uol.swp.common.chat.TextChatChannelDTO;

import org.junit.jupiter.api.Test;

import java.util.UUID;

public class TextChatChannelDTOTest {

    /**
     * Tests the getUUID method
     *
     * @author WKempel
     * @result The method should return the correct UUID
     * @see de.uol.swp.common.chat.TextChatChannelDTO
     * @see java.util.UUID
     * @since 2023-06-12
     */
    @Test
    public void testGetUUID() {
        UUID channelID = UUID.randomUUID();
        TextChatChannelDTO channelDTO = new TextChatChannelDTO(channelID);
        assertEquals(channelID, channelDTO.getUUID());
    }

    /**
     * Tests the equals method
     *
     * @author WKempel
     * @result The method should return true if the objects are equal and false if they are not
     * @see de.uol.swp.common.chat.TextChatChannelDTO
     * @see java.util.UUID
     * @since 2023-06-12
     */
    @Test
    public void testEquals() {
        UUID channelID1 = UUID.randomUUID();
        UUID channelID2 = UUID.randomUUID();

        TextChatChannelDTO channelDTO1 = new TextChatChannelDTO(channelID1);
        TextChatChannelDTO channelDTO2 = channelDTO1;
        TextChatChannelDTO channelDTO3 = new TextChatChannelDTO(channelID2);

        assertEquals(channelDTO1, channelDTO2);
        assertNotEquals(channelDTO1, channelDTO3);
    }

    /**
     * Tests the ChannelID for not null
     *
     * @author WKempel
     * @result The method should return true if the ChannelID is not null
     * @see de.uol.swp.common.chat.TextChatChannelDTO
     * @since 2023-06-12
     */
    @Test
    public void testChannelIDNotNull() {
        TextChatChannelDTO channelDTO = new TextChatChannelDTO(UUID.randomUUID());
        assertNotNull(channelDTO.getUUID());
    }

    /**
     * Tests the ChannelID for uniqueness
     *
     * @author WKempel
     * @result The method should return true if the ChannelID is not equal
     * @see de.uol.swp.common.chat.TextChatChannelDTO
     * @since 2023-06-12
     */
    @Test
    public void testUniqueChannelID() {
        TextChatChannelDTO channelDTO1 = new TextChatChannelDTO(UUID.randomUUID());
        TextChatChannelDTO channelDTO2 = new TextChatChannelDTO(UUID.randomUUID());

        assertNotEquals(channelDTO1.getUUID(), channelDTO2.getUUID());
    }
}
