package de.uol.swp.server.chat;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import de.uol.swp.common.chat.message.SendTextChatMessageRequest;
import de.uol.swp.common.user.Session;
import de.uol.swp.common.user.User;
import de.uol.swp.server.AbstractService;
import de.uol.swp.server.usermanagement.AuthenticationService;
import de.uol.swp.server.usermanagement.UserService;

import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

/**
 * The TextChatService class is responsible for managing the text chat channels.
 *
 * @author Finn
 * @see de.uol.swp.server.usermanagement.UserService
 * @see com.google.common.eventbus.EventBus
 * @see de.uol.swp.server.usermanagement.AuthenticationService
 * @see de.uol.swp.server.AbstractService
 * @since 2022-12-08
 */
@SuppressWarnings("UnstableApiUsage")
@Singleton
public class TextChatService extends AbstractService {
    private HashMap<UUID, TextChatChannel> channelList;
    private UserService userService;
    private AuthenticationService authenticationService;
    private EventBus eventBus;

    private static TextChatService instance;

    public static TextChatService getInstance() {
        return instance;
    }

    /**
     * Constructor
     *
     * @author Finn
     * @param bus the EvenBus used throughout the server
     * @see com.google.common.eventbus.EventBus
     * @see de.uol.swp.server.usermanagement.AuthenticationService
     * @since 2022-12-08
     */
    @Inject
    public TextChatService(AuthenticationService authenticationService, EventBus bus) {
        super(bus);
        eventBus = bus;
        this.authenticationService = authenticationService;
        channelList = new HashMap<>();
        instance = this;
    }

    /**
     * The method let join a user to a channel.
     *
     * @author Finn
     * @see java.util.UUID
     * @see de.uol.swp.common.user.User
     * @since 2022-12-16
     */
    public void joinUser(UUID channel, User user) {
        Optional<Session> session = authenticationService.getSession(user);
        session.ifPresent(value -> channelList.get(channel).addUser(value));
    }

    /**
     * @author Finn
     * @see java.util.UUID
     * @see de.uol.swp.common.user.User
     * @since 2022-12-16
     */
    public void dropUser(UUID channel, User user) {
        Optional<Session> session = authenticationService.getSession(user);
        session.ifPresent(value -> channelList.get(channel).removeUser(value));
    }

    /**
     * The onSendTextChatMessageRequest method is called when a user sends a message
     *
     * @author Finn
     * @see de.uol.swp.common.chat.message.SendTextChatMessageRequest
     * @see de.uol.swp.server.chat.TextChatChannel
     * @see com.google.common.eventbus.Subscribe
     * @see java.util.Optional
     * @see de.uol.swp.common.user.Session
     * @since 2022-12-16
     */
    @Subscribe
    public void onSendTextChatMessageRequest(SendTextChatMessageRequest message) {
        if (!channelList.containsKey(message.getChannel().getUUID())) return;
        TextChatChannel channel = channelList.get(message.getChannel().getUUID());
        Optional<Session> senderSession = message.getSession();

        if (senderSession.isEmpty()) return;
        String sender = senderSession.get().getUser().getUsername();
        String text = message.getMessage();

        channel.addUserTextMessage(sender, text);
    }

    /**
     * The sendServerTextMessage sends a message to the server
     *
     * @author Finn
     * @see java.util.UUID
     * @see de.uol.swp.server.chat.TextChatChannel
     * @since 2022-12-16
     */
    public void sendServerTextMessage(UUID channelID, String message) {
        TextChatChannel channel = channelList.get(channelID);

        channel.addServerTextMessage(message);
    }

    /**
     * The method creates a new text chat channel
     *
     * @author Finn
     * @see java.util.UUID
     * @see de.uol.swp.server.chat.TextChatChannel
     * @since 2022-12-16
     */
    public UUID createTextChatChannel() {
        UUID id = UUID.randomUUID();
        TextChatChannel channel = new TextChatChannel(id, eventBus);
        channelList.put(id, channel);
        return id;
    }

    /**
     * The method closes a text chat channel
     *
     * @author Finn
     * @see java.util.UUID
     * @since 2022-12-19
     */
    public void closeTextChatChannel(UUID id) {
        channelList.remove(id);
    }
}
