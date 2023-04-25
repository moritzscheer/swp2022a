package de.uol.swp.client.chat;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import de.uol.swp.client.chat.messages.NewTextChatMessageReceived;
import de.uol.swp.common.chat.TextChatMessage;
import de.uol.swp.common.chat.message.NewTextChatMessageMessage;
import de.uol.swp.common.chat.message.SendTextChatMessageRequest;

import java.util.ArrayList;
import java.util.UUID;

@SuppressWarnings("UnstableApiUsage")
public class TextChatChannel {
    private UUID ID;
    private ArrayList<TextChatMessage> chatHistory;
    private EventBus eventBus;

    public String getChatString() {
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < chatHistory.size(); i++) {
            TextChatMessage textMessage = chatHistory.get(i);
            out.append(textMessage.getSenderString()).append(" ").append(textMessage.getMessage());
            if (i < chatHistory.size() - 1) {
                out.append("\n");
            }
        }
        return out.toString();
    }

    public TextChatChannel(UUID id, EventBus eventBus) {
        ID = id;
        this.eventBus = eventBus;
        chatHistory = new ArrayList<>();
        eventBus.register(this);
    }

    public void sendTextMessage(String text) {
        SendTextChatMessageRequest messageRequest = new SendTextChatMessageRequest(ID, text);
        eventBus.post(messageRequest);
    }

    @Subscribe
    public void onNewTextChatMessageMessage(NewTextChatMessageMessage message) {
        if (!message.getChannel().getUUID().equals(ID)) return;
        chatHistory.add(message.getMessage());
        eventBus.post(new NewTextChatMessageReceived());
    }
}
