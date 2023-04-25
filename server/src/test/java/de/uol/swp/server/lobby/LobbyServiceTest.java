package de.uol.swp.server.lobby;

import static org.junit.jupiter.api.Assertions.*;

import com.google.common.eventbus.EventBus;

import de.uol.swp.common.lobby.Lobby;
import de.uol.swp.common.lobby.dto.LobbyDTO;
import de.uol.swp.common.lobby.request.CreateLobbyRequest;
import de.uol.swp.common.lobby.request.JoinLobbyRequest;
import de.uol.swp.common.lobby.request.LeaveLobbyRequest;
import de.uol.swp.common.lobby.request.RetrieveAllOnlineLobbiesRequest;
import de.uol.swp.common.user.UserDTO;
import de.uol.swp.server.chat.TextChatService;
import de.uol.swp.server.usermanagement.AuthenticationService;
import de.uol.swp.server.usermanagement.UserManagement;
import de.uol.swp.server.usermanagement.store.MainMemoryBasedUserStore;

import org.junit.jupiter.api.Test;

import java.util.UUID;

@SuppressWarnings("UnstableApiUsage")
public class LobbyServiceTest {
    static final EventBus bus = new EventBus();
    static final AuthenticationService authenticationService =
            new AuthenticationService(bus, new UserManagement(new MainMemoryBasedUserStore()));
    static final TextChatService textChatService = new TextChatService(authenticationService, bus);
    final LobbyManagement lobbyManagement = new LobbyManagement();
    final LobbyService lobbyService = new LobbyService(lobbyManagement, authenticationService, bus);

    static final UserDTO user = new UserDTO("Marco", "Marco2", "Marco2@Grawunder.com");
    static final UserDTO notInLobbyUser = new UserDTO("Tommy", "Tommy2", "Tommy2@Dang.de");

    static UUID textChannelUUID = textChatService.createTextChatChannel();
    static final Lobby lobby = new LobbyDTO(1, "lobby1", user, "password", true, textChannelUUID);

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
}
