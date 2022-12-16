package de.uol.swp.common.lobby.chat.message;

import de.uol.swp.common.lobby.chat.TextChatChannelDTO;
import de.uol.swp.common.message.AbstractRequestMessage;
import de.uol.swp.common.user.User;
import de.uol.swp.common.user.UserDTO;

public class SendTextChatMessageRequest extends AbstractRequestMessage {
    TextChatChannelDTO channel;
    UserDTO sender;
    String message;

    public SendTextChatMessageRequest(){

    }

    public TextChatChannelDTO getChannel() {
        return channel;
    }

    public User getSender(){
        return sender;
    }

    public String getMessage(){
        return message;
    }

    @Override
    public boolean authorizationNeeded() {
        return true;
    }
}
