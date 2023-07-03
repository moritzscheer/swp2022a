package de.uol.swp.client.chat;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import de.uol.swp.client.chat.messages.NewTextChatMessageReceived;
import de.uol.swp.common.chat.TextChatMessage;
import de.uol.swp.common.chat.message.NewTextChatMessageMessage;
import de.uol.swp.common.chat.message.SendTextChatMessageRequest;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Class where the chat is managed and created
 *
 * @author Finn Oldeboershuis
 * @since 2022-12-16
 */
@SuppressWarnings("UnstableApiUsage")
public class TextChatChannel {
    private UUID ID;
    private ArrayList<TextChatMessage> chatHistory;
    private EventBus eventBus;

    /**
     * Method to get the chat history in the chat
     *
     * @author Finn Oldeboershuis and Tommy Dang
     * @result Returns the entire chat history of the lobby
     * @since 2023-06-06
     */
    public String getChatString() {
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < chatHistory.size(); i++) {
            TextChatMessage textMessage = chatHistory.get(i);
            out.append(textMessage.getTimeStamp())
                    .append(textMessage.getSenderString())
                    .append(" ")
                    .append(textMessage.getMessage());
            if (i < chatHistory.size() - 1) {
                out.append("\n");
            }
        }
        return out.toString();
    }

    /**
     * Constructor for the class
     *
     * @author Finn Oldeboershuis
     * @since 2022-12-16
     */
    public TextChatChannel(UUID id, EventBus eventBus) {
        ID = id;
        this.eventBus = eventBus;
        chatHistory = new ArrayList<>();
        eventBus.register(this);
    }

    /**
     * Method to send the text message of the user as a message
     *
     * @author Finn Oldeboershuis
     * @since 2022-12-16
     */
    public void sendTextMessage(String text) {
        SendTextChatMessageRequest messageRequest = new SendTextChatMessageRequest(ID, text);
        eventBus.post(messageRequest);
    }

    /**
     * Handles new text chat message received
     *
     * <p>If a NewTextChatMessageMessage is posted to the EventBus the loggedInUser of this client
     * updates the chat history.
     *
     * @author Finn Oldeboershuis
     * @since 2023-01-06
     */
    @Subscribe
    public void onNewTextChatMessageMessage(NewTextChatMessageMessage message) {
        if (!message.getChannel().getUUID().equals(ID)) return;
        chatHistory.add(message.getMessage());
        eventBus.post(new NewTextChatMessageReceived());
    }
}
