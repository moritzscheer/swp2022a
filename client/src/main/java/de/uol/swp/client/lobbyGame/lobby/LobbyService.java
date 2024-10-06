package de.uol.swp.client.lobbyGame.lobby;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import de.uol.swp.client.lobbyGame.LobbyGameManagement;
import de.uol.swp.client.lobbyGame.lobby.event.*;
import de.uol.swp.client.tab.event.ChangeElementEvent;
import de.uol.swp.common.game.request.StartGameRequest;
import de.uol.swp.common.lobby.message.MapChangedMessage;
import de.uol.swp.common.lobby.message.PlayerReadyInLobbyMessage;
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
@Singleton
public class LobbyService {

    private final EventBus eventBus;

    private static final Logger LOG = LogManager.getLogger(LobbyService.class);

    /**
     * Constructor
     *
     * @param eventBus The EventBus set in ClientModule
     * @see de.uol.swp.client.di.ClientModule
     * @since 2019-11-20
     */
    @Inject
    public LobbyService(EventBus eventBus) {
        this.eventBus = eventBus;
        this.eventBus.register(this);
    }

    ///////////////
    // Requests
    ///////////////

    /**
     * Posts a request to create a lobby on the EventBus
     *
     * @see de.uol.swp.common.lobby.request.CreateLobbyRequest
     * @author Moritz Scheer
     * @since 2022-11-23
     */
    @Subscribe
    public void onCreateNewLobbyEvent(CreateNewLobbyEvent msg) {
        LOG.info("onCreateNewLobbyEvent {}", msg.getName());
        CreateLobbyRequest createLobbyRequest =
                new CreateLobbyRequest(
                        msg.getName(), msg.getUser(), msg.getMultiplayer(), msg.getPassword());
        eventBus.post(createLobbyRequest);
    }

    /**
     * Posts a request to join a specified lobby on the EventBus
     *
     * @see de.uol.swp.common.lobby.request.JoinLobbyRequest
     * @author Moritz Scheer
     * @since 2022-11-27
     */
    @Subscribe
    public void joinLobby(UserJoinLobbyEvent event) {
        JoinLobbyRequest joinUserRequest =
                new JoinLobbyRequest(
                        event.getLobby().getLobbyID(),
                        event.getLobby().getName(),
                        event.getLoggedInUser(),
                        event.getPassword());
        eventBus.post(joinUserRequest);
    }

    /**
     * Posts a request to leave a specified lobby on the EventBus
     *
     * @see de.uol.swp.common.lobby.request.LeaveLobbyRequest
     * @author Daniel Merzo, Moritz Scheer
     * @since 2022-12-15
     */
    @Subscribe
    public void leaveLobby(LeaveLobbyEvent event) {
        LeaveLobbyRequest leaveUserRequest =
                new LeaveLobbyRequest(
                        event.getLobbyID(),
                        event.getLobbyName(),
                        event.getLoggedInUser(),
                        event.isMultiplayer());
        eventBus.post(leaveUserRequest);
    }

    /**
     * Posts a request to retrieve all lobbies on the EventBus
     *
     * @see de.uol.swp.common.lobby.request.RetrieveAllOnlineLobbiesRequest
     * @author Moritz Scheer
     * @since 2022-11-30
     */
    @Subscribe
    public void retrieveAllLobbies(UpdateLobbiesListEvent msg) {
        RetrieveAllOnlineLobbiesRequest retrieveAllLobbiesRequest =
                new RetrieveAllOnlineLobbiesRequest();
        eventBus.post(retrieveAllLobbiesRequest);
    }

    /**
     * Posts a request to start the game on the EventBus
     *
     * @param event To identify the lobby with a unique key
     * @see StartGameRequest
     * @author Moritz Scheer
     * @since 2023-03-09
     */
    @Subscribe
    public void onSetPlayerReadyEvent(SetPlayerReadyEvent event) {
        SetPlayerReadyInLobbyRequest setPlayerReadyInLobbyRequest =
                new SetPlayerReadyInLobbyRequest(
                        event.getLobbyID(), (UserDTO) event.getUser(), event.isReady());
        eventBus.post(setPlayerReadyInLobbyRequest);
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
        LobbyGameManagement.getInstance().setLoggingUser((UserDTO) message.getUser());
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
        LobbyGameManagement.getInstance().removeLobbyAndGame(message);
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
        LobbyGameManagement.getInstance().newUserJoined(message);
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
        LobbyGameManagement.getInstance().userLeftLobby(message);
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
        LobbyGameManagement.getInstance().changeElement(event);
    }

    /**
     * Handles when a user pressed a ready button in the lobby
     *
     * <p>If a PlayerReadyInLobbyMessage is posted to the EventBus this method is called.
     *
     * @param message the PlayerReadyInLobbyResponse object seen on the EventBus
     * @see de.uol.swp.common.lobby.message.PlayerReadyInLobbyMessage
     * @author Moritz Scheer
     * @since 2023-05-28
     */
    @Subscribe
    public void onPlayerReadyInLobbyMessage(PlayerReadyInLobbyMessage message) {
        LobbyGameManagement.getInstance().playerReadyInLobby(message);
    }

    /**
     * Updates the displayed map in the lobby when a MapChangedMessage is received
     *
     * @param mapChangedMessage The MapChangedMessage object
     * @see de.uol.swp.common.lobby.message.MapChangedMessage
     * @author Mathis Eilers
     * @since 2023-05-12
     */
    @Subscribe
    public void onMapChangedMessage(MapChangedMessage mapChangedMessage) {
        LobbyGameManagement.getInstance()
                .updateGameMap(mapChangedMessage.getLobbyID(), mapChangedMessage.getMap());
    }
}
