package de.uol.swp.client.lobby;

import com.google.common.eventbus.EventBus;
import de.uol.swp.client.lobby.game.LobbyGameTuple;
import de.uol.swp.client.AbstractPresenter;
import de.uol.swp.client.lobby.game.events.RequestMapDataEvent;
import de.uol.swp.client.lobby.game.events.ShowGameViewEvent;
import de.uol.swp.client.lobby.game.presenter.GamePresenter;
import de.uol.swp.client.lobby.lobby.presenter.LobbyPresenter;
import de.uol.swp.client.tab.event.ChangeElementEvent;
import de.uol.swp.common.game.dto.GameDTO;
import de.uol.swp.common.game.message.GetMapDataResponse;
import de.uol.swp.common.game.message.StartGameMessage;
import de.uol.swp.common.lobby.dto.LobbyDTO;
import de.uol.swp.common.lobby.message.UserJoinedLobbyMessage;
import de.uol.swp.common.lobby.message.UserLeftLobbyMessage;
import de.uol.swp.common.lobby.response.LobbyDroppedSuccessfulResponse;
import de.uol.swp.common.user.UserDTO;

import javafx.scene.Parent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * Class that manages the Presenter and Parents of the Lobby and Game views
 *
 * @author Moritz Scheer
 * @since 2023-01-05
 */
public class LobbyGameManagement extends AbstractPresenter {


    private final EventBus eventBus;

    private static final Logger LOG = LogManager.getLogger(LobbyGameManagement.class);

    private UserDTO loggedInUser;

    // give access of GamePresenter/LobbyPresenter to LobbyManagement
    private final Map<Integer, LobbyGameTuple> lobbyGameMap = new HashMap<>();
    private LobbyPresenter currentLobbyPresenter;
    private GamePresenter currentGamePresenter;

    private final Map<Integer, GameDTO> lobbyIdToGameDTOMap = new HashMap<>();
    private final Map<Integer, LobbyDTO> lobbyIdToLobbyDTOMap = new HashMap<>();

    private static LobbyGameManagement instance;
    public static LobbyGameManagement getInstance() {
        return  instance;
    }


    public LobbyGameManagement(EventBus eventBus){
        this.eventBus = eventBus;
        instance = this;
    }


    /**
     * Handles successful login
     *
     * <p>If a LoginSuccessfulResponse is posted to the EventBus the loggedInUser of this client is
     * set to the one in the message received.
     *
     * @param user UserDTO to be saved
     * @see de.uol.swp.common.user.response.LoginSuccessfulResponse
     * @author Moritz Scheer
     * @since 2022-12-27
     */
    public void setLoggingUser(UserDTO user) {
        this.loggedInUser = user;
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
        lobbyGameMap.put(lobby.getLobbyID(), new LobbyGameTuple(currentLobbyPresenter, lobbyParent));
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
    public void removeLobby(LobbyDroppedSuccessfulResponse message) {
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
    public void newUserJoined(UserJoinedLobbyMessage message) {
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
    public void userLeftLobby(UserLeftLobbyMessage message) {
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
    public void changeElement(ChangeElementEvent event) {
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
    public LobbyGameTuple setupLobbyGame(Integer lobbyID, Parent gameParent, Integer gameID) {
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

    //////////////////////
    // Responses/Messages
    //////////////////////

    /**
     * Handles StartGameMessage detected on the EventBus
     *
     * <p>If a StartGameMessage is detected on the EventBus, this method gets called.
     *
     * @param msg The StartGameMessage detected on the EventBus
     * @see de.uol.swp.common.game.message.StartGameMessage
     * @author Moritz Scheer & Maxim Erden & Maria Eduarda
     * @since 2023-02-28
     */
    public void startGame(StartGameMessage msg){
        // save game and id
        lobbyIdToGameDTOMap.put(
                msg.getGameID(),
                msg.getGame()
        );
        lobbyIdToLobbyDTOMap.put(
                msg.getGameID(),
                msg.getLobby()
        );

        /**this post is to create the gamePresenter and save the reference of it
         * inside the LobbyGame class
         * LobbyManagement and GameManagement both need access to same reference
         * For this reason, both instantiate a hashmap lobbyGameMap
         */
        eventBus.post(new ShowGameViewEvent(msg.getLobby(), msg.getGameID()));

        // create request to get the cards
        eventBus.post(new RequestMapDataEvent(msg.getLobby()));
        //gameService.getMapData(msg.getLobby());
    }

    /**
     * Handles GetMapDataMessage
     *
     * @param msg the GetMapDataMessage object seen on the EventBus
     * @see GetMapDataResponse
     * @author Maria Andrade
     * @since 2023-05-06
     */
    public void reloadMapData(GetMapDataResponse msg){
        GamePresenter a = lobbyGameMap.get(msg.getLobbyID()).getGamePresenter();
        a.reloadMap(msg);
    }
}
