package de.uol.swp.common.chat.message;

import de.uol.swp.common.chat.TextChatChannelDTO;
import de.uol.swp.common.message.AbstractRequestMessage;

import java.util.UUID;

public class SendTextChatMessageRequest extends AbstractRequestMessage {
    TextChatChannelDTO channel;
    String message;

    public SendTextChatMessageRequest(UUID channelID, String message){
        channel = new TextChatChannelDTO(channelID);
        this.message = message;
    }

    public TextChatChannelDTO getChannel() {
        return channel;
    }

    public String getMessage(){
        return message;
    }

    @Override
    public boolean authorizationNeeded() {
        return true;
    }
}
