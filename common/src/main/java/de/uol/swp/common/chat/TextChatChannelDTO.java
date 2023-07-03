package de.uol.swp.common.chat;

import java.io.Serializable;
import java.util.UUID;
/**
 * Class to store the text channel id (UUID)
 *
 * @author Finn Oldeborshuis
 * @since 2023-01-06
 */
public class TextChatChannelDTO implements Serializable {
    private final UUID channelID;
    /**
     * Constructor of the TextChatChannelDTO class
     *
     * @param channelID ID of the lobby
     *
     * @author Finn Oldeborshuis
     * @since 2022-12-16
     */
    public TextChatChannelDTO(UUID channelID) {
        this.channelID = channelID;
    }
    /**
     * Gets the channel ID
     *
     * @author Finn Oldeborshuis
     * @since 2022-12-16
     */
    public UUID getUUID() {
        return channelID;
    }
}
