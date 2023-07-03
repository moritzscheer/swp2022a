package de.uol.swp.common.chat.message;
import de.uol.swp.common.chat.TextChatChannelDTO;
import de.uol.swp.common.message.AbstractRequestMessage;
import java.util.UUID;

/**
 * Class used to send new request to the server to send new messages to clients
 *
 * <p>An Object of this class is used in the TextChatChannel when client sends a text
 * message to the Server from de.uol.swp.client.chat.TextChatServer
 *
 * @author Finn Oldeborshuis
 * @since 2022-12-08
 */
public class SendTextChatMessageRequest extends AbstractRequestMessage {
    TextChatChannelDTO channel;
    String message;
    /**
     * Constructor of this Class
     *
     * @param channelID channel of messages send
     * @param message text content of the message
     *
     * @author Finn Oldeborshuis
     * @since 2022-12-19
     */
    public SendTextChatMessageRequest(UUID channelID, String message) {
        channel = new TextChatChannelDTO(channelID);
        this.message = message;
    }

    public TextChatChannelDTO getChannel() {
        return channel;
    }
    /**
     * Gets the message as string
     *
     * @author Finn Oldeborshuis
     * @since 2022-12-16
     */
    public String getMessage() {
        return message;
    }
    /**
     * Sets that authorization is needed
     *
     * @author Finn Oldeborshuis
     * @since 2022-12-16
     */
    @Override
    public boolean authorizationNeeded() {
        return true;
    }
}
