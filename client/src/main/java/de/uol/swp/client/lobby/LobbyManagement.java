package de.uol.swp.client.lobby;

import com.google.common.eventbus.Subscribe;

import de.uol.swp.client.lobby.game.LobbyGame;
import de.uol.swp.client.AbstractPresenter;
import de.uol.swp.client.lobby.game.presenter.GamePresenter;
import de.uol.swp.client.lobby.lobby.presenter.LobbyPresenter;
import de.uol.swp.client.tab.event.ChangeElementEvent;
import de.uol.swp.common.game.dto.GameDTO;
import de.uol.swp.common.game.message.GetMapDataResponse;
import de.uol.swp.common.lobby.dto.LobbyDTO;
import de.uol.swp.common.lobby.message.UserJoinedLobbyMessage;
import de.uol.swp.common.lobby.message.UserLeftLobbyMessage;
import de.uol.swp.common.lobby.response.LobbyDroppedSuccessfulResponse;
import de.uol.swp.common.user.User;
import de.uol.swp.common.user.UserDTO;
import de.uol.swp.common.user.response.LoginSuccessfulResponse;

import javafx.scene.Parent;

import java.util.HashMap;
import java.util.Map;

/**
 * Class that manages the Presenter and Parents of the Lobby and Game views
 *
 * @author Moritz Scheer
 * @since 2023-01-05
 */
public class LobbyManagement extends AbstractPresenter {

    private User loggedInUser;

    // give access of GamePresenter/LobbyPresenter to LobbyManagement
    private final Map<Integer, LobbyGame> lobbyGameMap = new HashMap<>();
    private LobbyPresenter currentLobbyPresenter;
    private GamePresenter currentGamePresenter;

    private static LobbyManagement instance;
    public static LobbyManagement getInstance() {
        return  instance;
    }

    public LobbyManagement(){
        instance = this;
    }


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

    /**
     * Method to set up a game view
     *
     * <p>This method opens the init method in the GamePresenter and saves it with the parent in the
     * lobbyMap HashMap.
     *
     * @param lobby the LobbyDTO Object containing all the information of the lobby
     * @param user the UserDTO Object containing all the information of the user
     * @param lobbyParent the Parent object of the lobby view
     * @author Moritz Scheer
     * @since 2023-03-09
     */
    public void setupLobby(LobbyDTO lobby, UserDTO user, Parent lobbyParent) {
        currentLobbyPresenter.setInformation(lobby, user);
        lobbyGameMap.put(lobby.getLobbyID(), new LobbyGame(currentLobbyPresenter, lobbyParent));
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
        lobbyGameMap.remove(message.getLobbyID());
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
            LobbyPresenter a = lobbyGameMap.get(message.getLobbyID()).getLobbyPresenter();
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
            LobbyPresenter a = lobbyGameMap.get(message.getLobbyID()).getLobbyPresenter();
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
        LobbyPresenter a = lobbyGameMap.get(event.getLobbyID()).getLobbyPresenter();
        a.switchButtonDisableEffect();
    }

    // -----------------------------------------------------
    // game methods
    // -----------------------------------------------------

    /**
     * Method to set up a game view
     *
     * <p>This method opens the init method in the GamePresenter and saves it with the parent in the
     * lobbyMap HashMap.
     *
     * @param lobbyID the Integer identifier of the lobby
     * @param gameID gameID
     * @param gameParent the Parent object of the game view
     * @author Moritz Scheer
     * @since 2023-03-23
     */
    public LobbyGame setupLobbyGame(Integer lobbyID, Parent gameParent, Integer gameID) {
        lobbyGameMap.get(lobbyID).setGameView(currentGamePresenter, gameParent, gameID);
        return lobbyGameMap.get(lobbyID);
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
        return lobbyGameMap.get(lobbyID).getLobbyParent();
    }

    /**
     * Getter for the gameParent
     *
     * @author Moritz Scheer
     * @since 2023-03-09
     */
    public Parent getGameParent(Integer lobbyID) {
        return lobbyGameMap.get(lobbyID).getGameParent();
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
     * Handles GetMapDataMessage
     *
     * @param msg the GetMapDataMessage object seen on the EventBus
     * @see GetMapDataResponse
     * @author Maria Andrade
     * @since 2023-05-06
     */
    @Subscribe
    public void onGetMapDataResponse(GetMapDataResponse msg){
        GamePresenter a = lobbyGameMap.get(msg.getLobbyID()).getGamePresenter();
        a.reloadMap(msg);
        System.out.println("In Lobby Management");
        System.out.println("GamePresenter Vsajcbjsdb = " + a);
    }
}
