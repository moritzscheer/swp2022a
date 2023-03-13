package de.uol.swp.client.lobby;

import com.google.common.eventbus.Subscribe;
import de.uol.swp.client.AbstractPresenter;
import de.uol.swp.client.lobby.cards.presenter.CardsPresenter;
import de.uol.swp.client.lobby.game.presenter.GamePresenter;
import de.uol.swp.client.lobby.lobby.presenter.LobbyPresenter;
import de.uol.swp.client.tab.event.ChangeElementEvent;
import de.uol.swp.common.lobby.dto.LobbyDTO;
import de.uol.swp.common.lobby.message.UserJoinedLobbyMessage;
import de.uol.swp.common.lobby.message.UserLeftLobbyMessage;
import de.uol.swp.common.lobby.response.CardsSubmittedResponse;
import de.uol.swp.common.lobby.response.LobbyDroppedSuccessfulResponse;
import de.uol.swp.common.user.User;
import de.uol.swp.common.user.UserDTO;
import de.uol.swp.common.user.response.LoginSuccessfulResponse;
import javafx.scene.Parent;

import java.util.HashMap;
import java.util.Map;

public class LobbyManagement extends AbstractPresenter {

    private User loggedInUser;
    private final Map<Integer, Game> lobbyMap = new HashMap<>();
    private LobbyPresenter currentLobbyPresenter;
    private CardsPresenter currentCardsPresenter;
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

    // -----------------------------------------------------
    // lobby methods
    // -----------------------------------------------------

    public void setupLobby(LobbyDTO lobby, UserDTO user, Parent lobbyParent) {
        currentLobbyPresenter.setInformation(lobby, user);
        lobbyMap.put(lobby.getLobbyID(), new Game(currentLobbyPresenter, lobbyParent));
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
            LobbyPresenter a = lobbyMap.get(message.getLobbyID()).getLobbyPresenter();
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
            LobbyPresenter a = lobbyMap.get(message.getLobbyID()).getLobbyPresenter();
            a.userLeftLobby(message);
            if (!lobbyMap.get(message.getLobbyID()).getStatus().equals("lobby")) {}
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
        LobbyPresenter a = (LobbyPresenter) lobbyMap.get(event.getLobbyID()).getLobbyPresenter();
        a.switchButtonDisableEffect();
    }

    // -----------------------------------------------------
    // game methods
    // -----------------------------------------------------

    public void setupGame(Integer lobbyID, Parent gameParent, Parent cardsParent) {
        currentCardsPresenter.init(lobbyID);
        lobbyMap.get(lobbyID).setCardsView(currentCardsPresenter, cardsParent);
        lobbyMap.get(lobbyID).setGameView(currentGamePresenter, gameParent);
    }

    /**
     * Handles when cards has been submitted
     *
     * <p>If a CardsSubmittedResponse is posted to the EventBus this method is called.
     *
     * @param msg the CardsSubmittedResponse object seen on the EventBus
     * @see CardsSubmittedResponse
     * @author Moritz Scheer
     * @since 2023-03-09
     */
    @Subscribe
    public void onCardsSubmitted(CardsSubmittedResponse msg) {
        GamePresenter a = lobbyMap.get(msg.getLobbyID()).getGamePresenter();
        int gridSize = 12;
        a.init(msg.getLobbyID());
    }

    // -----------------------------------------------------
    // getter and setter
    // -----------------------------------------------------

    /**
     * Getter for the lobbyParent
     *
     * @author Moritz Scheer
     * @since 2023-03-09
     */
    public Parent getLobbyParent(Integer lobbyID) {
        return lobbyMap.get(lobbyID).getLobbyParent();
    }

    /**
     * Getter for the cardsParent
     *
     * @author Moritz Scheer
     * @since 2023-03-09
     */
    public Parent getCardsParent(Integer lobbyID) {
        return lobbyMap.get(lobbyID).getCardsParent();
    }

    /**
     * Getter for the gameParent
     *
     * @author Moritz Scheer
     * @since 2023-03-09
     */
    public Parent getGameParent(Integer lobbyID) {
        return lobbyMap.get(lobbyID).getGameParent();
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
    public void setNextCardsPresenter(CardsPresenter currentCardsPresenter) {
        this.currentCardsPresenter = currentCardsPresenter;
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

    public void readJSON() {

    }
}
