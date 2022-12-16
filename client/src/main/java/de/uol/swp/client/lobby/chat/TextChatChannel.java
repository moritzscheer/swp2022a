package de.uol.swp.client.lobby.chat;

import com.google.common.eventbus.EventBus;
import de.uol.swp.common.lobby.chat.TextChatMessage;
import de.uol.swp.common.lobby.chat.message.SendTextChatMessageRequest;

import java.util.ArrayList;
import java.util.UUID;

public class TextChatChannel {
    private UUID ID;
    private ArrayList<TextChatMessage> chatHistory;
    private EventBus eventBus;

    public String getChatString(){
        String out = "";
        for (int i = 0; i < chatHistory.size(); i++) {
            TextChatMessage textMessage = chatHistory.get(i);
            out += textMessage.getSenderString() + " " + textMessage.getMessage();
            if (i < chatHistory.size() - 1){
                out += "\n";
            }
        }
        return out;
    }

    TextChatChannel(UUID id, EventBus eventBus){
        ID = id;
        this.eventBus = eventBus;
        chatHistory = new ArrayList<>();
    }

    public void sendTextMessage(String text){
        SendTextChatMessageRequest messageRequest = new SendTextChatMessageRequest();

    }
}
