package de.uol.swp.server.lobby.chat;

import com.google.common.eventbus.Subscribe;
import de.uol.swp.common.lobby.chat.message.SendTextChatMessageRequest;
import de.uol.swp.server.lobby.chat.internalMessages.CreateTextChatChannelServerMessage;
import de.uol.swp.server.lobby.chat.internalMessages.DropUserFromTextChatChannelServerMessage;
import de.uol.swp.server.lobby.chat.internalMessages.JoinUserToTextChatChannelServerMessage;

import java.util.HashMap;
import java.util.UUID;

public class TextChatService {
    private HashMap<UUID, TextChatChannel> channelList;

    @Subscribe
    private void onUserJoinTextChannel(JoinUserToTextChatChannelServerMessage message){

    }

    @Subscribe
    private void onUserLeaveTextChannel(DropUserFromTextChatChannelServerMessage message){

    }

    @Subscribe
    private void onRecieveSendTextChatMessageRequest(SendTextChatMessageRequest message){

    }

    @Subscribe
    private void onCreateTextChatChannel(CreateTextChatChannelServerMessage message){

    }
}
