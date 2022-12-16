package de.uol.swp.common.lobby.chat;

import java.util.UUID;

public class TextChatChannelDTO {
    private UUID channelID;

    public TextChatChannelDTO(UUID channelID) {
        this.channelID = channelID;
    }

    public UUID getUUID(){
        return channelID;
    }
}
