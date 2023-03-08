package de.uol.swp.client.lobby;

import com.google.common.eventbus.Subscribe;

import de.uol.swp.client.AbstractPresenter;
import de.uol.swp.client.game.presenter.GamePresenter;
import de.uol.swp.client.lobby.presenter.LobbyPresenter;
import de.uol.swp.client.tab.event.ChangeElementEvent;
import de.uol.swp.common.lobby.message.StartGameMessage;
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
    private final Map<Integer,  AbstractPresenter> lobbyMap = new HashMap<>();
    private LobbyPresenter currentLobbyPresenter;
    private GamePresenter currentGamePresenter;

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
        this.loggedInUser = message.getUser();
    }

    /**
     * Handles created lobbies
     *
     * <p>If a new LobbyCreatedSuccessfulResponse object is posted to the EventBus the
     * setInformation method is called in the lobbyPresenter with the given lobbyID.
     *
     * @param message the LobbyCreatedSuccessfulResponse object seen on the EventBus
     * @see de.uol.swp.common.lobby.response.LobbyCreatedSuccessfulResponse
     * @author Moritz Scheer
     * @since 2023-01-05
     */
    @Subscribe
    public void onLobbyCreatedSuccessfulResponse(LobbyCreatedSuccessfulResponse message) {
        lobbyMap.put(message.getLobby().getLobbyID(), currentLobbyPresenter);
        LobbyPresenter a = (LobbyPresenter) lobbyMap.get(message.getLobby().getLobbyID());
        a.setInformation(message.getLobby(), message.getUser());
        lobbyMap.replace(message.getLobby().getLobbyID(), a);
    }

    /**
     * Handles joined lobbies
     *
     * <p>If a new LobbyJoinedSuccessfulResponse object is posted to the EventBus the setInformation
     * method is called in the lobbyPresenter with the given lobbyID.
     *
     * @param message the LobbyJoinedSuccessfulResponse object seen on the EventBus
     * @see de.uol.swp.common.lobby.response.LobbyJoinedSuccessfulResponse
     * @author Moritz Scheer
     * @since 2023-01-05
     */
    @Subscribe
    public void onLobbyJoinedSuccessfulResponse(LobbyJoinedSuccessfulResponse message) {
        lobbyMap.put(message.getLobby().getLobbyID(), currentLobbyPresenter);
        LobbyPresenter a = (LobbyPresenter) lobbyMap.get(message.getLobby().getLobbyID());
        a.setInformation(message.getLobby(), message.getUser());
        lobbyMap.replace(message.getLobby().getLobbyID(), a);
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
        lobbyMap.remove(message.getLobbyID());
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
        if (!loggedInUser.equals(message.getUser())) {
            LobbyPresenter a = (LobbyPresenter) lobbyMap.get(message.getLobbyID());
            a.userJoinedLobby(message);
        }
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
        if (!loggedInUser.equals(message.getUser())) {
            LobbyPresenter a = (LobbyPresenter) lobbyMap.get(message.getLobbyID());
            a.userLeftLobby(message);
        }
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
        LobbyPresenter a = (LobbyPresenter) lobbyMap.get(event.getLobbyID());
        a.switchButtonDisableEffect();
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
     * Setter for the currentGamePresenter variable
     *
     * @author Moritz Scheer & Maxim Erden
     * @since 2023-02-28
     */
    public void setNextGamePresenter(GamePresenter currentGamePresenter) {
        this.currentGamePresenter = currentGamePresenter;
    }

    /**
     * Handles when a game is started
     *
     * If a StartGameResponse is posted to the EventBus this method is called.
     *
     * @author Moritz Scheer & Maxim Erden
     * @since 2023-02-28
     */
    @Subscribe
    public void onStartGameMessage(StartGameMessage msg) {
        lobbyMap.replace(msg.getLobbyID(), currentGamePresenter);
        GamePresenter a = (GamePresenter) lobbyMap.get(msg.getLobbyID());
        a.init();
    }
}
