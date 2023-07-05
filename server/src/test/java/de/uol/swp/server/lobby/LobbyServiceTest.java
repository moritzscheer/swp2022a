package de.uol.swp.server.lobby;

import static org.junit.jupiter.api.Assertions.*;

import com.google.common.eventbus.EventBus;

import de.uol.swp.common.exception.LobbyDoesNotExistException;
import de.uol.swp.common.game.Map;
import de.uol.swp.common.lobby.Lobby;
import de.uol.swp.common.lobby.dto.LobbyDTO;
import de.uol.swp.common.lobby.request.*;
import de.uol.swp.common.message.AbstractServerMessage;
import de.uol.swp.common.message.Message;
import de.uol.swp.common.user.UserDTO;
import de.uol.swp.server.chat.TextChatService;
import de.uol.swp.server.usermanagement.AuthenticationService;
import de.uol.swp.server.usermanagement.UserManagement;
import de.uol.swp.server.usermanagement.store.MainMemoryBasedUserStore;

import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

@SuppressWarnings("UnstableApiUsage")
public class LobbyServiceTest {
    static final EventBus bus = new EventBus();
    static final AuthenticationService authenticationService =
            new AuthenticationService(bus, new UserManagement(new MainMemoryBasedUserStore()));
    static final TextChatService textChatService = new TextChatService(authenticationService, bus);
    LobbyManagement lobbyManagement = new LobbyManagement();
    final LobbyService lobbyService = new LobbyService(lobbyManagement, authenticationService, bus);

    static final UserDTO user = new UserDTO("Marco", "Marco2", "Marco2@Grawunder.com");
    static final UserDTO notInLobbyUser = new UserDTO("Tommy", "Tommy2", "Tommy2@Dang.de");

    static UUID textChannelUUID = textChatService.createTextChatChannel();
    static final Lobby lobby = new LobbyDTO(1, "lobby1", user, "password", true, textChannelUUID, false);

    // ------------------------------------------
    // onCreateLobbyRequest tests
    // ------------------------------------------

    @Test
    void CreateLobby() {
        final CreateLobbyRequest request = new CreateLobbyRequest("lobby1", user, true, "password");

        // The post will lead to a call of a UserService function
        bus.post(request);

        assertNotNull(lobbyManagement.getLobbies());
        assertEquals(1, lobbyManagement.getLobbies().size());
    }

    @Test
    void CreateLobbyWithSameName() {
        final CreateLobbyRequest request = new CreateLobbyRequest("lobby1", user, true, "password");

        // The post will lead to a call of a UserService function
        bus.post(request);

        assertEquals(1, lobbyManagement.getLobbies().size());
    }

    // ------------------------------------------
    // onJoinLobbyRequest tests
    // ------------------------------------------

    @Test
    void JoinLobby() {
        final CreateLobbyRequest request = new CreateLobbyRequest("lobby1", user, true, "password");
        final JoinLobbyRequest request2 =
                new JoinLobbyRequest(1, "lobby1", notInLobbyUser, "password");

        bus.post(request);
        bus.post(request2);

        assertEquals(2, lobbyManagement.getLobby(1).get().getUsers().size());
        assertEquals(true, lobbyManagement.getLobby(1).get().getUsers().contains(notInLobbyUser));
    }

    @Test
    void JoinLobbyWithSameUser() {
        final CreateLobbyRequest request = new CreateLobbyRequest("lobby1", user, true, "password");
        final JoinLobbyRequest request2 =
                new JoinLobbyRequest(1, "lobby1", notInLobbyUser, "password");
        final JoinLobbyRequest request3 =
                new JoinLobbyRequest(1, "lobby1", notInLobbyUser, "password");

        bus.post(request);
        bus.post(request2);
        bus.post(request3);

        assertEquals(2, lobbyManagement.getLobby(1).get().getUsers().size());
        assertTrue(lobbyManagement.getLobby(1).get().getUsers().contains(notInLobbyUser));
    }

    @Test
    void JoinLobbyWithFalsePassword() {
        final CreateLobbyRequest request = new CreateLobbyRequest("lobby1", user, true, "password");
        final JoinLobbyRequest request2 = new JoinLobbyRequest(1, "lobby1", notInLobbyUser, "1234");

        bus.post(request);
        bus.post(request2);

        assertEquals(1, lobbyManagement.getLobby(1).get().getUsers().size());
        assertFalse(lobbyManagement.getLobby(1).get().getUsers().contains(notInLobbyUser));
    }

    // ------------------------------------------
    // onLeaveLobbyRequest tests
    // ------------------------------------------

    @Test
    void leaveMultiplayerLobby() {
        final CreateLobbyRequest request = new CreateLobbyRequest("lobby1", user, true, "password");
        final JoinLobbyRequest request2 =
                new JoinLobbyRequest(1, "lobby1", notInLobbyUser, "password");
        final LeaveLobbyRequest request3 = new LeaveLobbyRequest(1, "lobby1", user, true);

        bus.post(request);
        bus.post(request2);
        bus.post(request3);

        assertFalse(lobbyManagement.getLobbies().isEmpty());
        assertEquals(1, lobbyManagement.getLobby(1).get().getUsers().size());
    }

    @Test
    void leaveSingleplayerLobby() {
        final CreateLobbyRequest request = new CreateLobbyRequest("lobby1", user, false, null);
        final LeaveLobbyRequest request2 = new LeaveLobbyRequest(1, "lobby1", user, false);

        bus.post(request);
        bus.post(request2);

        assertTrue(lobbyManagement.getLobbies().isEmpty());
    }

    @Test
    void dropLobby() {
        final CreateLobbyRequest request = new CreateLobbyRequest("lobby1", user, true, "password");
        final LeaveLobbyRequest request2 = new LeaveLobbyRequest(1, "lobby1", user, true);

        bus.post(request);
        bus.post(request2);

        assertTrue(lobbyManagement.getLobbies().isEmpty());
    }

    // ------------------------------------------
    // onRetrieveAllOnlineLobbiesRequest tests
    // ------------------------------------------

    @Test
    void onRetrieveAllOnlineLobbiesRequest() {
        final RetrieveAllOnlineLobbiesRequest request = new RetrieveAllOnlineLobbiesRequest();

        bus.post(request);
    }

    @Test
    public void testOnSetPlayerReadyInLobbyRequest() {
        final CreateLobbyRequest request = new CreateLobbyRequest("lobby1", user, true, "password");
        final JoinLobbyRequest request2 =
                new JoinLobbyRequest(1, "lobby1", notInLobbyUser, "password");
        final SetPlayerReadyInLobbyRequest request3 = new SetPlayerReadyInLobbyRequest(1, user, true);

        bus.post(request);
        bus.post(request2);
        bus.post(request3);

        assertEquals(2, lobbyManagement.getLobby(1).get().getUsers().size());
        assertTrue(lobbyManagement.getLobby(1).get().getUsers().contains(notInLobbyUser));
        assertTrue(request3.isReady(), "Player is ready");
    }

    @Test
    public void testOnSetPlayerIsNotReadyInLobbyRequest() {
        final CreateLobbyRequest request = new CreateLobbyRequest("lobby1", user, true, "password");
        final JoinLobbyRequest request2 =
                new JoinLobbyRequest(1, "lobby1", notInLobbyUser, "password");
        final SetPlayerReadyInLobbyRequest request3 = new SetPlayerReadyInLobbyRequest(1, user, false);

        bus.post(request);
        bus.post(request2);
        bus.post(request3);

        assertEquals(2, lobbyManagement.getLobby(1).get().getUsers().size());
        assertTrue(lobbyManagement.getLobby(1).get().getUsers().contains(notInLobbyUser));
        assertFalse(request3.isReady(), "Player is not ready");
    }

    @Test
    public void testOnMapChangeRequest() {
        Map map = new Map();
        final CreateLobbyRequest request = new CreateLobbyRequest("lobby1", user, true, "password");
        final JoinLobbyRequest request2 =
                new JoinLobbyRequest(1, "lobby1", notInLobbyUser, "password");
        final MapChangeRequest request3 = new MapChangeRequest(1, user, map);

        bus.post(request);
        bus.post(request2);
        bus.post(request3);

        assertEquals(2, lobbyManagement.getLobby(1).get().getUsers().size());
        assertTrue(lobbyManagement.getLobby(1).get().getUsers().contains(notInLobbyUser));
        assertNull(lobbyManagement.getLobby(1).get().getMapName());
    }

}
