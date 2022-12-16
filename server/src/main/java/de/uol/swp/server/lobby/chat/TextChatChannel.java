package de.uol.swp.server.lobby.chat;

import com.google.common.eventbus.EventBus;
import de.uol.swp.common.lobby.chat.TextChatChannelDTO;
import de.uol.swp.common.lobby.chat.TextChatMessage;
import de.uol.swp.common.lobby.chat.message.NewTextChatMessageMessage;
import de.uol.swp.common.user.Session;

import java.util.ArrayList;
import java.util.UUID;

class TextChatChannel {
    private UUID ID;
    private ArrayList<TextChatMessage> chatHistory;
    private ArrayList<Session> loggedInUsers;
    private EventBus eventBus;


    public void addUserTextMessage(String sender, String message){
        TextChatMessage text = new TextChatMessage(message, "<" + sender + ">");
        chatHistory.add(text);
        sendTextToUsers(text);
    }

    public void addServerTextMessage(String message){
        TextChatMessage text = new TextChatMessage(message, "[Server]");
        chatHistory.add(text);
        sendTextToUsers(text);
    }

    private void sendTextToUsers(TextChatMessage text) {
        NewTextChatMessageMessage message = new NewTextChatMessageMessage(new TextChatChannelDTO(ID), text);
        message.setReceiver(loggedInUsers);
        eventBus.post(message);
    }

    public void addUser(Session user){
        loggedInUsers.add(user);
    }

    public void removeUser(Session user){
        if(loggedInUsers.contains(user)){
            loggedInUsers.remove((user));
        }
    }

    TextChatChannel(UUID id, EventBus eventBus){
        ID = id;
        this.eventBus = eventBus;
        chatHistory = new ArrayList<>();
    }
}
