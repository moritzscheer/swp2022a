package de.uol.swp.common.message;

import de.uol.swp.common.chat.TextChatChannelDTO;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class TextChatChannelDTOTest {

    @Test
    public void testGetUUID() {
        UUID channelID = UUID.randomUUID();
        TextChatChannelDTO channelDTO = new TextChatChannelDTO(channelID);
        assertEquals(channelID, channelDTO.getUUID());
    }

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

    @Test
    public void testChannelIDNotNull() {
        TextChatChannelDTO channelDTO = new TextChatChannelDTO(UUID.randomUUID());
        assertNotNull(channelDTO.getUUID());
    }

    @Test
    public void testUniqueChannelID() {
        TextChatChannelDTO channelDTO1 = new TextChatChannelDTO(UUID.randomUUID());
        TextChatChannelDTO channelDTO2 = new TextChatChannelDTO(UUID.randomUUID());

        assertNotEquals(channelDTO1.getUUID(), channelDTO2.getUUID());
    }
}
