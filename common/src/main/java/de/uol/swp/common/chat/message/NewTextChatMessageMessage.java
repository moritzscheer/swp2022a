package de.uol.swp.common.chat.message;

import de.uol.swp.common.chat.TextChatChannelDTO;
import de.uol.swp.common.chat.TextChatMessage;
import de.uol.swp.common.message.AbstractServerMessage;

public class NewTextChatMessageMessage extends AbstractServerMessage {
    private final TextChatChannelDTO channel;
    private final TextChatMessage message;

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
