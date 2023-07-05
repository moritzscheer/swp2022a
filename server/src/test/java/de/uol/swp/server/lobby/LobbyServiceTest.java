package de.uol.swp.server.lobby;

import static org.junit.jupiter.api.Assertions.*;

import com.google.common.eventbus.EventBus;

import de.uol.swp.common.game.Map;
import de.uol.swp.common.lobby.Lobby;
import de.uol.swp.common.lobby.dto.LobbyDTO;
import de.uol.swp.common.lobby.request.*;
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
    LobbyManagement lobbyManagement = new LobbyManagement();
    final LobbyService lobbyService = new LobbyService(lobbyManagement, authenticationService, bus);

    static final UserDTO user = new UserDTO("Marco", "Marco2", "Marco2@Grawunder.com");
    static final UserDTO notInLobbyUser = new UserDTO("Tommy", "Tommy2", "Tommy2@Dang.de");

    static UUID textChannelUUID = textChatService.createTextChatChannel();
    static final Lobby lobby = new LobbyDTO(1, "lobby1", user, "password", true, textChannelUUID);

    // ------------------------------------------
    // onCreateLobbyRequest tests
    // ------------------------------------------

    /**
     * Tests if a lobby can be created
     *
     * @author Moritz
     * @see de.uol.swp.common.lobby.request.CreateLobbyRequest
     * @see de.uol.swp.server.lobby.LobbyService
     * @since 2023-02-20
     */
    @Test
    public void testCreateLobby() {
        final CreateLobbyRequest request = new CreateLobbyRequest("lobby1", user, true, "password");

        // The post will lead to a call of a UserService function
        bus.post(request);

        assertNotNull(lobbyManagement.getLobbies());
        assertEquals(1, lobbyManagement.getLobbies().size());
    }

    /**
     * Tests if a lobby can be created with the same name
     *
     * @author Moritz
     * @see de.uol.swp.common.lobby.request.CreateLobbyRequest
     * @see de.uol.swp.server.lobby.LobbyService
     * @since 2023-02-20
     */
    @Test
    public void testCreateLobbyWithSameName() {
        final CreateLobbyRequest request = new CreateLobbyRequest("lobby1", user, true, "password");

        // The post will lead to a call of a UserService function
        bus.post(request);

        assertEquals(1, lobbyManagement.getLobbies().size());
    }

    // ------------------------------------------
    // onJoinLobbyRequest tests
    // ------------------------------------------

    /**
     * Tests if a user can join a lobby
     *
     * @author Moritz
     * @see de.uol.swp.common.lobby.request.JoinLobbyRequest
     * @see de.uol.swp.common.lobby.request.CreateLobbyRequest
     * @see de.uol.swp.server.lobby.LobbyService
     * @since 2023-01-20
     */
    @Test
    public void testJoinLobby() {
        final CreateLobbyRequest request = new CreateLobbyRequest("lobby1", user, true, "password");
        bus.post(request);

        int lobbyID = lobbyManagement.getLobbies().get(0).getLobbyID();
        final JoinLobbyRequest request2 =
                new JoinLobbyRequest(lobbyID, "lobby1", notInLobbyUser, "password");

        bus.post(request2);

        assertEquals(2, lobbyManagement.getLobby(lobbyID).get().getUsers().size());
        assertTrue(lobbyManagement.getLobby(lobbyID).get().getUsers().contains(notInLobbyUser));
    }

    /**
     * Tests if lobby can be joined with the same user again
     *
     * @author Moritz
     * @see de.uol.swp.common.lobby.request.CreateLobbyRequest
     * @see de.uol.swp.common.lobby.request.JoinLobbyRequest
     * @since 2023-01-20
     */
    @Test
    public void testJoinLobbyWithSameUser() {
        final CreateLobbyRequest request = new CreateLobbyRequest("lobby1", user, true, "password");
        bus.post(request);
        int lobbyID = lobbyManagement.getLobbies().get(0).getLobbyID();

        final JoinLobbyRequest request2 =
                new JoinLobbyRequest(lobbyID, "lobby1", notInLobbyUser, "password");
        final JoinLobbyRequest request3 =
                new JoinLobbyRequest(lobbyID, "lobby1", notInLobbyUser, "password");

        bus.post(request2);
        bus.post(request3);

        assertEquals(2, lobbyManagement.getLobby(lobbyID).get().getUsers().size());
        assertTrue(lobbyManagement.getLobby(lobbyID).get().getUsers().contains(notInLobbyUser));
    }

    /**
     * Tests if a user can join a lobby with a false password
     *
     * @author Moritz
     * @see de.uol.swp.common.lobby.request.CreateLobbyRequest
     * @see de.uol.swp.common.lobby.request.JoinLobbyRequest
     * @since 2023-01-20
     */
    @Test
    public void testJoinLobbyWithFalsePassword() {
        final CreateLobbyRequest request = new CreateLobbyRequest("lobby1", user, true, "password");
        bus.post(request);
        int lobbyID = lobbyManagement.getLobbies().get(0).getLobbyID();
        final JoinLobbyRequest request2 =
                new JoinLobbyRequest(lobbyID, "lobby1", notInLobbyUser, "1234");

        bus.post(request2);

        assertEquals(1, lobbyManagement.getLobby(lobbyID).get().getUsers().size());
        assertFalse(lobbyManagement.getLobby(lobbyID).get().getUsers().contains(notInLobbyUser));
    }

    // ------------------------------------------
    // onLeaveLobbyRequest tests
    // ------------------------------------------

    /**
     * Tests if a user can leave a Multiple-lobby
     *
     * @author Moritz
     * @see de.uol.swp.common.lobby.request.CreateLobbyRequest
     * @see de.uol.swp.common.lobby.request.JoinLobbyRequest
     * @since 2023-01-20
     */
    @Test
    public void testLeaveMultiplayerLobby() {
        final CreateLobbyRequest request = new CreateLobbyRequest("lobby1", user, true, "password");
        bus.post(request);

        int lobbyID = lobbyManagement.getLobbies().get(0).getLobbyID();
        final JoinLobbyRequest request2 =
                new JoinLobbyRequest(lobbyID, "lobby1", notInLobbyUser, "password");
        final LeaveLobbyRequest request3 = new LeaveLobbyRequest(lobbyID, "lobby1", user, true);

        bus.post(request2);
        bus.post(request3);

        assertFalse(lobbyManagement.getLobbies().isEmpty());
        assertEquals(1, lobbyManagement.getLobby(lobbyID).get().getUsers().size());
    }

    /**
     * Tests if a user can leave a Single-lobby
     *
     * @author Moritz
     * @see de.uol.swp.common.lobby.request.CreateLobbyRequest
     * @see de.uol.swp.common.lobby.request.JoinLobbyRequest
     * @since 2023-01-20
     */
    @Test
    public void testLeaveSingleplayerLobby() {
        final CreateLobbyRequest request = new CreateLobbyRequest("lobby1", user, false, null);
        bus.post(request);

        int lobbyID = lobbyManagement.getLobbies().get(0).getLobbyID();
        final LeaveLobbyRequest request2 = new LeaveLobbyRequest(lobbyID, "lobby1", user, false);

        bus.post(request2);

        assertTrue(lobbyManagement.getLobbies().isEmpty());
    }

    /**
     * Tests if a user was dropped from a lobby
     *
     * @author Moritz
     * @see de.uol.swp.common.lobby.request.CreateLobbyRequest
     * @see de.uol.swp.common.lobby.request.LeaveLobbyRequest
     * @since 2023-01-20
     */
    @Test
    public void testDropLobby() {
        final CreateLobbyRequest request = new CreateLobbyRequest("lobby1", user, true, "password");
        bus.post(request);

        int lobbyID = lobbyManagement.getLobbies().get(0).getLobbyID();
        final LeaveLobbyRequest request2 = new LeaveLobbyRequest(lobbyID, "lobby1", user, true);

        bus.post(request2);

        assertTrue(lobbyManagement.getLobbies().isEmpty());
    }

    // ------------------------------------------
    // onRetrieveAllOnlineLobbiesRequest tests
    // ------------------------------------------

    /**
     * Tests the onRetrieveAllOnlineLobbiesRequest method
     *
     * @author Moritz
     * @see de.uol.swp.common.lobby.request.RetrieveAllOnlineLobbiesRequest
     * @since 2023-01-20
     */
    @Test
    public void testOnRetrieveAllOnlineLobbiesRequest() {
        final RetrieveAllOnlineLobbiesRequest request = new RetrieveAllOnlineLobbiesRequest();

        bus.post(request);
    }

    /**
     * Tests the onSetPlayerReadyInLobbyRequest method.
     *
     * @author WKempel
     * @see de.uol.swp.common.lobby.request.CreateLobbyRequest
     * @see de.uol.swp.common.lobby.request.JoinLobbyRequest
     * @see de.uol.swp.common.lobby.request.SetPlayerReadyInLobbyRequest
     * @since 2023-06-23
     */
    @Test
    public void testOnSetPlayerReadyInLobbyRequest() {
        final CreateLobbyRequest request = new CreateLobbyRequest("lobby1", user, true, "password");
        bus.post(request);

        int lobbyID = lobbyManagement.getLobbies().get(0).getLobbyID();
        final JoinLobbyRequest request2 =
                new JoinLobbyRequest(lobbyID, "lobby1", notInLobbyUser, "password");
        final SetPlayerReadyInLobbyRequest request3 =
                new SetPlayerReadyInLobbyRequest(lobbyID, user, true);

        bus.post(request2);
        bus.post(request3);

        assertEquals(2, lobbyManagement.getLobby(lobbyID).get().getUsers().size());
        assertTrue(lobbyManagement.getLobby(lobbyID).get().getUsers().contains(notInLobbyUser));
        assertTrue(request3.isReady(), "Player is ready");
    }

    /**
     * Tests the onSetPlayerIsNotReadyInLobbyRequest method.
     *
     * @author WKempel
     * @see de.uol.swp.common.lobby.request.CreateLobbyRequest
     * @see de.uol.swp.common.lobby.request.JoinLobbyRequest
     * @see de.uol.swp.common.lobby.request.SetPlayerReadyInLobbyRequest
     * @since 2023-06-23
     */
    @Test
    public void testOnSetPlayerIsNotReadyInLobbyRequest() {
        final CreateLobbyRequest request = new CreateLobbyRequest("lobby1", user, true, "password");
        bus.post(request);

        int lobbyID = lobbyManagement.getLobbies().get(0).getLobbyID();
        final JoinLobbyRequest request2 =
                new JoinLobbyRequest(lobbyID, "lobby1", notInLobbyUser, "password");
        final SetPlayerReadyInLobbyRequest request3 =
                new SetPlayerReadyInLobbyRequest(lobbyID, user, false);

        bus.post(request2);
        bus.post(request3);

        assertEquals(2, lobbyManagement.getLobby(lobbyID).get().getUsers().size());
        assertTrue(lobbyManagement.getLobby(lobbyID).get().getUsers().contains(notInLobbyUser));
        assertFalse(request3.isReady(), "Player is not ready");
    }

    /**
     * Tests the onMapChangeRequest method.
     *
     * @author WKempel
     * @see de.uol.swp.common.lobby.request.CreateLobbyRequest
     * @see de.uol.swp.common.lobby.request.JoinLobbyRequest
     * @see de.uol.swp.common.lobby.request.MapChangeRequest
     * @since 2023-06-23
     */
    @Test
    public void testOnMapChangeRequest() {
        Map map = new Map();
        final CreateLobbyRequest request = new CreateLobbyRequest("lobby1", user, true, "password");
        bus.post(request);

        int lobbyID = lobbyManagement.getLobbies().get(0).getLobbyID();
        final JoinLobbyRequest request2 =
                new JoinLobbyRequest(lobbyID, "lobby1", notInLobbyUser, "password");
        final MapChangeRequest request3 = new MapChangeRequest(lobbyID, user, map);

        bus.post(request2);
        bus.post(request3);

        assertEquals(2, lobbyManagement.getLobby(lobbyID).get().getUsers().size());
        assertTrue(lobbyManagement.getLobby(lobbyID).get().getUsers().contains(notInLobbyUser));
        assertNull(lobbyManagement.getLobby(lobbyID).get().getMapName());
    }
}
