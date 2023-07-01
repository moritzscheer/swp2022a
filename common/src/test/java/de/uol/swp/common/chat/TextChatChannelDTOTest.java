package de.uol.swp.common.chat;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import java.util.UUID;

public class TextChatChannelDTOTest {

    private final UUID channelID = UUID.randomUUID();

    @Test
    void constructorAndGetters_withValidData() {

        TextChatChannelDTO channelDTO = new TextChatChannelDTO(channelID);

        assertEquals(channelID, channelDTO.getUUID());
    }
}
