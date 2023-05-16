package de.uol.swp.client.lobby;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;

import de.uol.swp.client.tab.event.ChangeElementEvent;
import de.uol.swp.common.lobby.message.UserJoinedLobbyMessage;
import de.uol.swp.common.lobby.message.UserLeftLobbyMessage;
import de.uol.swp.common.lobby.request.*;
import de.uol.swp.common.lobby.response.LobbyDroppedSuccessfulResponse;
import de.uol.swp.common.user.UserDTO;
import de.uol.swp.common.user.response.LoginSuccessfulResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Classes that manages lobbies
 *
 * @author Marco Grawunder
 * @since 2019-11-20
 */
@SuppressWarnings("UnstableApiUsage")
public class LobbyService {

    private final EventBus eventBus;

    private static final Logger LOG = LogManager.getLogger(LobbyService.class);

    @Inject
    private final LobbyGameManagement lobbyGameManagement;

    /**
     * Constructor
     *
     * @param eventBus        The EventBus set in ClientModule
     * @param lobbyGameManagement
     * @see de.uol.swp.client.di.ClientModule
     * @since 2019-11-20
     */
    @Inject
    public LobbyService(EventBus eventBus, LobbyGameManagement lobbyGameManagement) {
        this.eventBus = eventBus;
        this.lobbyGameManagement = lobbyGameManagement;
        this.eventBus.register(this);
    }

    ///////////////
    // Requests
    ///////////////

    /**
     * Posts a request to create a lobby on the EventBus
     *
     * @param name Name chosen for the new lobby
     * @param user User who wants to create the new lobby
     * @see de.uol.swp.common.lobby.request.CreateLobbyRequest
     * @author Moritz Scheer
     * @since 2022-11-23
     */
    public void createNewLobby(String name, UserDTO user, Boolean multiplayer, String password) {
        CreateLobbyRequest createLobbyRequest =
                new CreateLobbyRequest(name, user, multiplayer, password);
        eventBus.post(createLobbyRequest);
    }

    /**
     * Posts a request to join a specified lobby on the EventBus
     *
     * @param name Name of the lobby the user wants to join
     * @param user User who wants to join the lobby
     * @see de.uol.swp.common.lobby.request.JoinLobbyRequest
     * @author Moritz Scheer
     * @since 2022-11-27
     */
    public void joinLobby(Integer lobbyID, String name, UserDTO user, String password) {
        JoinLobbyRequest joinUserRequest = new JoinLobbyRequest(lobbyID, name, user, password);
        eventBus.post(joinUserRequest);
    }

    /**
     * Posts a request to leave a specified lobby on the EventBus
     *
     * @param name Name of the lobby the user wants to join
     * @param user User who wants to join the lobby
     * @param lobbyID To identify the lobby with a unique key
     * @param multiplayer Boolean value to query if the user is in the multiplayer
     * @see de.uol.swp.common.lobby.request.LeaveLobbyRequest
     * @author Daniel Merzo, Moritz Scheer
     * @since 2022-12-15
     */
    public void leaveLobby(Integer lobbyID, String name, UserDTO user, Boolean multiplayer) {
        LeaveLobbyRequest leaveUserRequest =
                new LeaveLobbyRequest(lobbyID, name, user, multiplayer);
        eventBus.post(leaveUserRequest);
    }

    /**
     * Posts a request to retrieve all lobbies on the EventBus
     *
     * @see de.uol.swp.common.lobby.request.RetrieveAllOnlineLobbiesRequest
     * @author Moritz Scheer
     * @since 2022-11-30
     */
    public void retrieveAllLobbies() {
        RetrieveAllOnlineLobbiesRequest retrieveAllLobbiesRequest =
                new RetrieveAllOnlineLobbiesRequest();
        eventBus.post(retrieveAllLobbiesRequest);
    }


    /////////////////////
    // Responses/Messages
    /////////////////////

    /**
     * Handles successful login
     *
     * <p>If a LoginSuccessfulResponse is posted to the EventBus the loggedInUser of this client is
     * set to the one in the message received.
     *
     * @param message the LoginSuccessfulResponse object seen on the EventBus
     * @see de.uol.swp.common.user.response.LoginSuccessfulResponse
     * @author Moritz Scheer
     * @since 2022-12-27
     */
    @Subscribe
    public void onLoginSuccessfulResponse(LoginSuccessfulResponse message) {
        lobbyGameManagement.setLoggingUser((UserDTO) message.getUser());
    }

    /**
     * Handles dropped lobbies
     *
     * <p>If a new LobbyDroppedSuccessfulResponse object is posted to the EventBus the
     * LobbyPresenter method is called in the lobbyPresenter with the given lobbyID.
     *
     * @param message the UserLeftLobbyMessage object seen on the EventBus
     * @see de.uol.swp.common.lobby.message.UserLeftLobbyMessage
     * @author Moritz Scheer
     * @since 2023-01-05
     */
    @Subscribe
    public void onLobbyDroppedSuccessfulResponse(LobbyDroppedSuccessfulResponse message) {
        lobbyGameManagement.removeLobby(message);
    }

    /**
     * Handles joined users
     *
     * <p>If a new UserJoinedLobbyMessage object is posted to the EventBus the userJoinedLobby
     * method is called in the lobbyPresenter with the given lobbyID.
     *
     * @param message the UserJoinedLobbyMessage object seen on the EventBus
     * @see de.uol.swp.common.lobby.message.UserJoinedLobbyMessage
     * @author Moritz Scheer
     * @since 2023-01-05
     */
    @Subscribe
    public void onUserJoinedLobbyMessage(UserJoinedLobbyMessage message) {
        lobbyGameManagement.newUserJoined(message);
    }

    /**
     * Handles left users
     *
     * <p>If a new UserLeftLobbyMessage object is posted to the EventBus the userLeftLobby method is
     * called in the lobbyPresenter with the given lobbyID.
     *
     * @param message the UserLeftLobbyMessage object seen on the EventBus
     * @see de.uol.swp.common.lobby.message.UserLeftLobbyMessage
     * @author Moritz Scheer
     * @since 2023-01-05
     */
    @Subscribe
    public void onUserLeftLobbyMessage(UserLeftLobbyMessage message) {
        lobbyGameManagement.userLeftLobby(message);
    }

    /**
     * Handles when window in the tab gets open
     *
     * <p>If a ChangeElementEvent is posted to the EventBus this method is called.
     *
     * @param event the ChangeElementEvent object seen on the EventBus
     * @see de.uol.swp.client.tab.event.ChangeElementEvent
     * @author Moritz Scheer
     * @since 2023-01-05
     */
    @Subscribe
    public void onChangeElementEvent(ChangeElementEvent event) {
        lobbyGameManagement.changeElement(event);
    }

}
