package de.uol.swp.common.chat;

import java.util.UUID;

public class TextChatChannelDTO {
    private final UUID channelID;

    public TextChatChannelDTO(UUID channelID) {
        this.channelID = channelID;
    }

    public UUID getUUID(){
        return channelID;
    }
}
