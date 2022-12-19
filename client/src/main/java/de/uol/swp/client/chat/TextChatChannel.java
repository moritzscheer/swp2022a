package de.uol.swp.client.chat;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import de.uol.swp.common.chat.TextChatMessage;
import de.uol.swp.common.chat.message.NewTextChatMessageMessage;
import de.uol.swp.common.chat.message.SendTextChatMessageRequest;

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

    public TextChatChannel(UUID id, EventBus eventBus){
        ID = id;
        this.eventBus = eventBus;
        chatHistory = new ArrayList<>();
    }

    public void sendTextMessage(UUID channelID, String text){
        SendTextChatMessageRequest messageRequest = new SendTextChatMessageRequest(channelID, text);
        eventBus.post(messageRequest);
    }

    @Subscribe
    private void onNewTextChatMessageMessage(NewTextChatMessageMessage message){
        if(message.getChannel().getUUID() != ID) return;
        chatHistory.add(message.getMessage());
    }
}
