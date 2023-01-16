package de.uol.swp.common.chat;

import java.io.Serializable;
import java.util.UUID;

public class TextChatChannelDTO implements Serializable {
    private final UUID channelID;

    public TextChatChannelDTO(UUID channelID) {
        this.channelID = channelID;
    }

    public UUID getUUID() {
        return channelID;
    }
}
