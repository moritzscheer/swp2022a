package de.uol.swp.server.lobby.chat;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import de.uol.swp.common.lobby.chat.message.SendTextChatMessageRequest;
import de.uol.swp.common.user.Session;
import de.uol.swp.common.user.User;
import de.uol.swp.server.AbstractService;
import de.uol.swp.server.usermanagement.AuthenticationService;
import de.uol.swp.server.usermanagement.UserService;

import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

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
     * @param bus the EvenBus used throughout the server
     * @since 2019-10-08
     */
    @Inject
    public TextChatService(AuthenticationService authenticationService, EventBus bus) {
        super(bus);
        eventBus = bus;
        this.authenticationService = authenticationService;
        channelList = new HashMap<>();
        instance = this;
    }

    public void joinUser(UUID channel, User user) {
        Optional<Session> session = authenticationService.getSession(user);
        if (session.isPresent()) {
            channelList.get(channel).addUser(session.get());
        }
    }

    public void dropUser(UUID channel, User user) {
        Optional<Session> session = authenticationService.getSession(user);
        if (session.isPresent()) {
            channelList.get(channel).removeUser(session.get());
        }
    }

    @Subscribe
    private void onSendTextChatMessageRequest(SendTextChatMessageRequest message) {
        TextChatChannel channel = channelList.get(message.getChannel().getUUID());
        String sender = message.getSender().getUsername();
        String text = message.getMessage();

        channel.addUserTextMessage(sender, text);
    }

    public void SendServerTextMessage(UUID channelID, String message) {
        TextChatChannel channel = channelList.get(channelID);

        channel.addServerTextMessage(message);
    }

    public UUID createTextChatChannel() {
        UUID id = UUID.randomUUID();
        TextChatChannel channel = new TextChatChannel(id, eventBus);
        channelList.put(id, channel);
        return id;
    }
}
