package de.uol.swp.server.chat;

import com.google.common.eventbus.EventBus;

import de.uol.swp.common.chat.TextChatChannelDTO;
import de.uol.swp.common.chat.TextChatMessage;
import de.uol.swp.common.chat.message.NewTextChatMessageMessage;
import de.uol.swp.common.user.Session;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;


/** Class to create a chat channel for the lobbies and main menu
 *
 * @author Tommy Dang, Finn Oldeboershuis
 * @since 2022-12-08
 */
public class TextChatChannel {
    private UUID ID;
    private ArrayList<TextChatMessage> chatHistory;
    private ArrayList<Session> loggedInUsers;
    private EventBus eventBus;

    /** Adds before the name in chat a timestamp
     *
     * @author Tommy Dang
     * @since 2023-05-02
     */
    private String getCurrentTimeStamp() {
        return new SimpleDateFormat("HH:mm:ss").format(new Date());
    }

    /** Adds before the name in chat a timestamp
     *
     * @author Tommy Dang
     * @since 2023-05-02
     */
    public void addUserTextMessage(String sender, String message) {
        String timeStamp = getCurrentTimeStamp();
        TextChatMessage text =
                new TextChatMessage(message, "<" + sender + ">", "[" + timeStamp + "] ");
        chatHistory.add(text);
        sendTextToUsers(text);
    }

    /** Adds before the name in chat a timestamp
     *
     * @author Tommy Dang
     * @since 2023-05-02
     */
    public void addServerTextMessage(String message) {
        String timeStamp = getCurrentTimeStamp();
        TextChatMessage text = new TextChatMessage(message, "[Server]", "[" + timeStamp + "] ");
        chatHistory.add(text);
        sendTextToUsers(text);
    }

    /** Adds before the name in chat a timestamp
     *
     * @author Tommy Dang
     * @since 2023-05-02
     */
    private void sendTextToUsers(TextChatMessage text) {
        NewTextChatMessageMessage message =
                new NewTextChatMessageMessage(new TextChatChannelDTO(ID), text);
        message.setReceiver(loggedInUsers);
        eventBus.post(message);
    }

    /** Adds before the name in chat a timestamp
     *
     * @author Tommy Dang
     * @since 2023-05-02
     */
    public void addUser(Session user) {
        loggedInUsers.add(user);
    }

    /** Adds before the name in chat a timestamp
     *
     * @author Tommy Dang
     * @since 2023-05-02
     */
    public void removeUser(Session user) {
        if (loggedInUsers.contains(user)) {
            loggedInUsers.remove((user));
        }
    }

    /** Adds before the name in chat a timestamp
     *
     * @author Tommy Dang
     * @since 2023-05-02
     */
    TextChatChannel(UUID id, EventBus eventBus) {
        ID = id;
        this.eventBus = eventBus;
        chatHistory = new ArrayList<>();
        loggedInUsers = new ArrayList<>();
    }
}
