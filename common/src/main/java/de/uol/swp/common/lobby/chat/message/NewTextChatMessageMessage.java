package de.uol.swp.common.lobby.chat.message;

import de.uol.swp.common.lobby.chat.TextChatChannelDTO;
import de.uol.swp.common.lobby.chat.TextChatMessage;
import de.uol.swp.common.message.AbstractMessage;
import de.uol.swp.common.message.AbstractServerMessage;

public class NewTextChatMessageMessage extends AbstractServerMessage {
    private TextChatChannelDTO channel;
    private TextChatMessage message;

    public NewTextChatMessageMessage(TextChatChannelDTO channel, TextChatMessage message) {
        this.channel = channel;
        this.message = message;
    }

    public TextChatMessage getMessage() {
        return message;
    }

    public TextChatChannelDTO getChannel() {
        return channel;
    }
}
