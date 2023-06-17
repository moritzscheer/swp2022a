package de.uol.swp.common.chat;

import de.uol.swp.common.chat.TextChatChannelDTO;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TextChatChannelDTOTest {

    private final UUID channelID = UUID.randomUUID();

    @Test
    void constructorAndGetters_withValidData() {

        TextChatChannelDTO channelDTO = new TextChatChannelDTO(channelID);

        assertEquals(channelID, channelDTO.getUUID());
    }
}
