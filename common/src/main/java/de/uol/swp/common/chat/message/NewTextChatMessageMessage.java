package de.uol.swp.common.chat.message;

import de.uol.swp.common.chat.TextChatChannelDTO;
import de.uol.swp.common.chat.TextChatMessage;
import de.uol.swp.common.message.AbstractServerMessage;

/**
 * Class used to send new text messages to clients
 *
 * <p>An Object of this class is used in the TextChatService when the client sends a text message of
 * de.uol.swp.server.chat.TextChatService
 *
 * @author Finn Oldeborshuis
 * @since 2022-12-16
 */
public class NewTextChatMessageMessage extends AbstractServerMessage {
    private final TextChatChannelDTO channel;
    private final TextChatMessage message;
    /**
     * Constructor of this Class
     *
     * @param channel channel of messages send
     * @param message content of the message
     * @author Finn Oldeborshuis
     * @since 2022-12-16
     */
    public NewTextChatMessageMessage(TextChatChannelDTO channel, TextChatMessage message) {
        this.channel = channel;
        this.message = message;
    }
    /**
     * Gets the TextChatMessage
     *
     * @author Finn Oldeborshuis
     * @since 2022-12-16
     */
    public TextChatMessage getMessage() {
        return message;
    }
    /**
     * Gets the TextChatChannel
     *
     * @author Finn Oldeborshuis
     * @since 2022-12-16
     */
    public TextChatChannelDTO getChannel() {
        return channel;
    }
}
