package de.uol.swp.client.lobby;

import com.google.common.eventbus.Subscribe;
import de.uol.swp.client.AbstractPresenter;
import de.uol.swp.client.lobby.presenter.LobbyPresenter;
import de.uol.swp.client.tab.event.ChangeElementEvent;
import de.uol.swp.common.lobby.message.MapChangedMessage;
import de.uol.swp.common.lobby.message.UserJoinedLobbyMessage;
import de.uol.swp.common.lobby.message.UserLeftLobbyMessage;
import de.uol.swp.common.lobby.response.LobbyCreatedSuccessfulResponse;
import de.uol.swp.common.lobby.response.LobbyDroppedSuccessfulResponse;
import de.uol.swp.common.lobby.response.LobbyJoinedSuccessfulResponse;
import de.uol.swp.common.user.User;
import de.uol.swp.common.user.response.LoginSuccessfulResponse;

import java.util.HashMap;
import java.util.Map;

public class LobbyPresenterHandler extends AbstractPresenter {

    private User loggedInUser;
    private final Map<Integer, LobbyPresenter> lobbyMap = new HashMap<>();
    private LobbyPresenter currentLobbyPresenter;

    /**
     * Handles successful login
     *
     * If a LoginSuccessfulResponse is posted to the EventBus the loggedInUser
     * of this client is set to the one in the message received.
     *
     * @param message the LoginSuccessfulResponse object seen on the EventBus
     * @see de.uol.swp.common.user.response.LoginSuccessfulResponse
     * @author Moritz Scheer
     * @since 2022-12-27
     */
    @Subscribe
    public void onLoginSuccessfulResponse(LoginSuccessfulResponse message) {
        this.loggedInUser = message.getUser();
    }

    /**
     * Handles created lobbies
     *
     * If a new LobbyCreatedSuccessfulResponse object is posted to the EventBus the setInformation method is called
     * in the lobbyPresenter with the given lobbyID.
     *
     * @param message the LobbyCreatedSuccessfulResponse object seen on the EventBus
     * @see de.uol.swp.common.lobby.response.LobbyCreatedSuccessfulResponse
     * @author Moritz Scheer
     * @since 2023-01-05
     */
    @Subscribe
    public void onLobbyCreatedSuccessfulResponse(LobbyCreatedSuccessfulResponse message) {
        lobbyMap.put(message.getLobby().getLobbyID(), currentLobbyPresenter);
        lobbyMap.get(message.getLobby().getLobbyID()).setInformation(message.getLobby(), message.getUser());
    }

    /**
     * Handles joined lobbies
     *
     * If a new LobbyJoinedSuccessfulResponse object is posted to the EventBus the setInformation method is called
     * in the lobbyPresenter with the given lobbyID.
     *
     * @param message the LobbyJoinedSuccessfulResponse object seen on the EventBus
     * @see de.uol.swp.common.lobby.response.LobbyJoinedSuccessfulResponse
     * @author Moritz Scheer
     * @since 2023-01-05
     */
    @Subscribe
    public void onLobbyJoinedSuccessfulResponse(LobbyJoinedSuccessfulResponse message) {
        lobbyMap.put(message.getLobby().getLobbyID(), currentLobbyPresenter);
        lobbyMap.get(message.getLobby().getLobbyID()).setInformation(message.getLobby(), message.getUser());
        lobbyMap.get(message.getLobby().getLobbyID()).updateMapDisplay(message.getMap());
    }

    /**
     * Handles dropped lobbies
     *
     * If a new LobbyDroppedSuccessfulResponse object is posted to the EventBus the LobbyPresenter method is called
     * in the lobbyPresenter with the given lobbyID.
     *
     * @param message the UserLeftLobbyMessage object seen on the EventBus
     * @see de.uol.swp.common.lobby.message.UserLeftLobbyMessage
     * @author Moritz Scheer
     * @since 2023-01-05
     */
    @Subscribe
    public void onLobbyDroppedSuccessfulResponse(LobbyDroppedSuccessfulResponse message){
        lobbyMap.remove(message.getLobbyID());
    }

    /**
     * Handles joined users
     *
     * If a new UserJoinedLobbyMessage object is posted to the EventBus the userJoinedLobby method is called
     * in the lobbyPresenter with the given lobbyID.
     *
     * @param message the UserJoinedLobbyMessage object seen on the EventBus
     * @see de.uol.swp.common.lobby.message.UserJoinedLobbyMessage
     * @author Moritz Scheer
     * @since 2023-01-05
     */
    @Subscribe
    public void onUserJoinedLobbyMessage(UserJoinedLobbyMessage message) {
        if(!loggedInUser.equals(message.getUser())) {
            lobbyMap.get(message.getLobbyID()).userJoinedLobby(message);
        }
    }

    /**
     * Handles left users
     *
     * If a new UserLeftLobbyMessage object is posted to the EventBus the userLeftLobby method is called
     * in the lobbyPresenter with the given lobbyID.
     *
     * @param message the UserLeftLobbyMessage object seen on the EventBus
     * @see de.uol.swp.common.lobby.message.UserLeftLobbyMessage
     * @author Moritz Scheer
     * @since 2023-01-05
     */
    @Subscribe
    public void onUserLeftLobbyMessage(UserLeftLobbyMessage message){
        if(!loggedInUser.equals(message.getUser())) {
            lobbyMap.get(message.getLobbyID()).userLeftLobby(message);
        }
    }

    /**
     * Handles when window in the tab gets open
     *
     * If a ChangeElementEvent is posted to the EventBus this method is called.
     *
     * @param event the ChangeElementEvent object seen on the EventBus
     * @see de.uol.swp.client.tab.event.ChangeElementEvent
     * @author Moritz Scheer
     * @since 2023-01-05
     */
    @Subscribe
    public void onChangeElementEvent(ChangeElementEvent event) {
        lobbyMap.get(event.getLobbyID()).switchButtonDisableEffect();
    }

    /**
     * Setter for the currentLobbyPresenter variable
     *
     * @author Moritz Scheer
     * @since 2023-01-05
     */
    public void setNextLobbyPresenter(LobbyPresenter currentLobbyPresenter) {
        this.currentLobbyPresenter = currentLobbyPresenter;
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
    public void onMapChangedMessage(MapChangedMessage mapChangedMessage)
    {
        currentLobbyPresenter.updateMapDisplay(mapChangedMessage.getMap());
    }
}
