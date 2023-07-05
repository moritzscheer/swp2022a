package de.uol.swp.client.lobbyGame;

import com.google.common.eventbus.EventBus;

import de.uol.swp.client.AbstractPresenter;
import de.uol.swp.client.chat.TextChatChannel;
import de.uol.swp.client.lobbyGame.game.events.RequestDistributeCardsEvent;
import de.uol.swp.client.lobbyGame.game.events.ShowGameOverEvent;
import de.uol.swp.client.lobbyGame.game.events.ShowGameViewEvent;
import de.uol.swp.client.lobbyGame.game.presenter.GamePresenter;
import de.uol.swp.client.lobbyGame.lobby.presenter.LobbyPresenter;
import de.uol.swp.client.tab.event.ChangeElementEvent;
import de.uol.swp.common.chat.message.TextHistoryMessage;
import de.uol.swp.common.game.dto.GameDTO;
import de.uol.swp.common.game.message.*;
import de.uol.swp.common.game.response.ProgramCardDataResponse;
import de.uol.swp.common.lobby.dto.LobbyDTO;
import de.uol.swp.common.lobby.message.PlayerReadyInLobbyMessage;
import de.uol.swp.common.lobby.message.UserJoinedLobbyMessage;
import de.uol.swp.common.lobby.message.UserLeftLobbyMessage;
import de.uol.swp.common.lobby.response.LobbyDroppedSuccessfulResponse;
import de.uol.swp.common.user.UserDTO;

import javafx.scene.Parent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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
    // Map<lobbyID, LobbyGamePresenterTuple>
    private final Map<Integer, LobbyGamePresenterTuple> lobbyIdToLobbyGamePresenterMap =
            new HashMap<>();

    // Map<lobbyID, GameDTO>
    private final Map<Integer, GameDTO> lobbyIdToGameDTOMap = new HashMap<>();

    // Map<lobbyID, LobbyDTO>
    private final Map<Integer, LobbyDTO> lobbyIdToLobbyDTOMap = new HashMap<>();

    private static LobbyGameManagement instance;

    public static LobbyGameManagement getInstance() {
        return instance;
    }

    /**
     * Default constructor
     *
     * @author Finn Oldeboershuis, Maria Andrade
     * @since 2023-03-19
     */
    public LobbyGameManagement(EventBus eventBus) {
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
     * @author Moritz Scheer
     * @since 2023-03-09
     */
    public void setupLobby(LobbyDTO lobby, UserDTO user) {
        lobbyIdToLobbyGamePresenterMap
                .get(lobby.getLobbyID())
                .getLobbyPresenter()
                .setInformation(lobby, user);
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
    public void removeLobbyAndGame(LobbyDroppedSuccessfulResponse message) {
        lobbyIdToLobbyGamePresenterMap.remove(message.getLobbyID());
        lobbyIdToLobbyDTOMap.remove(message.getLobbyID());
        lobbyIdToGameDTOMap.remove(message.getLobbyID());
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
            LobbyPresenter lobbyPresenter =
                    lobbyIdToLobbyGamePresenterMap.get(message.getLobbyID()).getLobbyPresenter();
            lobbyPresenter.userJoinedLobby(message);
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
            LobbyPresenter lobbyPresenter =
                    lobbyIdToLobbyGamePresenterMap.get(message.getLobbyID()).getLobbyPresenter();
            lobbyPresenter.userLeftLobby(message);
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
        LobbyPresenter lobbyPresenter =
                lobbyIdToLobbyGamePresenterMap.get(event.getLobbyID()).getLobbyPresenter();
        lobbyPresenter.switchButtonDisableEffect();
    }

    /**
     * Handles when a user pressed a ready or not ready button in the lobby
     *
     * <p>If a ChangeElementEvent is posted to the EventBus this method is called.
     *
     * @param message the PlayerReadyInLobbyResponse object seen on the EventBus
     * @see PlayerReadyInLobbyMessage
     * @author Moritz Scheer
     * @since 2023-05-28
     */
    public void playerReadyInLobby(PlayerReadyInLobbyMessage message) {
        LobbyPresenter lobbyPresenter =
                lobbyIdToLobbyGamePresenterMap.get(message.getLobbyID()).getLobbyPresenter();
        lobbyPresenter.updatePlayerReadyStatus(message);
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
        return lobbyIdToLobbyGamePresenterMap.get(lobbyID).getLobbyParent();
    }

    /**
     * Getter for the gameParent
     *
     * @author Moritz Scheer
     * @since 2023-03-09
     */
    public Parent getGameParent(Integer lobbyID) {
        return lobbyIdToLobbyGamePresenterMap.get(lobbyID).getGameParent();
    }

    /**
     * Setter for the currentLobbyPresenter variable
     *
     * @author Moritz Scheer
     * @since 2023-01-05
     */
    public void setThisLobbyPresenter(
            LobbyPresenter lobbyPresenter, Parent lobbyParent, int lobbyID) {
        lobbyIdToLobbyGamePresenterMap.put(
                lobbyID, new LobbyGamePresenterTuple(lobbyPresenter, lobbyParent));
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
     * @param gameParent the Parent object of the game view
     * @author Moritz Scheer
     * @since 2023-03-23
     */
    public LobbyGamePresenterTuple setLobbyGamePresenter(
            Integer lobbyID, Parent gameParent, GamePresenter thisLobbyGamePresenter) {
        lobbyIdToLobbyGamePresenterMap.get(lobbyID).setGameView(thisLobbyGamePresenter, gameParent);

        // init presenter after it was created and then call requests
        initPresenterAndStartRequests(lobbyID);

        return lobbyIdToLobbyGamePresenterMap.get(lobbyID);
    }

    /**
     * Method to initialize the GamePresenter and call the resquests for the map and cards, only
     * after it is certain that the GamePresenter was created
     *
     * @param lobbyID the Integer identifier of the lobby
     * @author Maria Andrade
     * @since 2023-05-18
     */
    public void initPresenterAndStartRequests(Integer lobbyID) {
        // after presenter is created, we must call init() with the data
        // initialize the board and the robots on board inside init method
        TextChatChannel lobbyChat =
                lobbyIdToLobbyGamePresenterMap.get(lobbyID).getLobbyPresenter().getTextChat();
        lobbyIdToLobbyGamePresenterMap
                .get(lobbyID)
                .getGamePresenter()
                .init(
                        lobbyID,
                        lobbyIdToLobbyDTOMap.get(lobbyID),
                        lobbyIdToGameDTOMap.get(lobbyID),
                        this.loggedInUser,
                        lobbyChat);

        // create request to get the cards
        eventBus.post(
                new RequestDistributeCardsEvent(
                        lobbyIdToLobbyDTOMap.get(lobbyID), this.loggedInUser));
    }

    /**
     * Handles RoundIsOverMessage
     *
     * @param msg the RoundIsOverMessage object seen on the EventBus
     * @see RoundIsOverMessage
     * @author Maria Andrade and Tommy Dang
     * @since 2023-06-13
     */
    public void restartRounds(RoundIsOverMessage msg) {
        GamePresenter gamePresenter =
                lobbyIdToLobbyGamePresenterMap.get(msg.getLobbyID()).getGamePresenter();
        // only keep sending messages if then lobby was not dropped, otherwise ignore
        if (!Objects.equals(gamePresenter, null)) {
            gamePresenter.resetCardsAndSlots();
            gamePresenter.setAllPlayersNotReady();
            gamePresenter.enableRobotButton();
            gamePresenter.respawnDeadRobots(msg.getRespawnRobots());
        } else LOG.warn("gamePresenter object was deleted! Ignoring RoundIsOverMessage!");
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
    public void startGame(StartGameMessage msg) {
        // save game and id
        lobbyIdToGameDTOMap.put(msg.getLobbyID(), msg.getGame());
        lobbyIdToLobbyDTOMap.put(msg.getLobbyID(), msg.getLobby());

        /**
         * this post is to create the gamePresenter and save the reference of it inside the
         * LobbyGame class LobbyManagement and GameManagement both need access to same reference For
         * this reason, both instantiate a hashmap lobbyIdToLobbyGamePresenterMap
         */
        eventBus.post(new ShowGameViewEvent(msg.getLobby()));
    }

    /**
     * Handles ProgramCardDataResponse
     *
     * @param msg the ProgramCardDataResponse object seen on the EventBus
     * @see ProgramCardDataResponse
     * @author Maria Andrade
     * @since 2023-05-18
     */
    public void showCardsToUser(ProgramCardDataResponse msg) {
        GamePresenter gamePresenter =
                lobbyIdToLobbyGamePresenterMap.get(msg.getLobbyID()).getGamePresenter();
        // only keep sending messages if then lobby was not dropped, otherwise ignore
        if (!Objects.equals(gamePresenter, null)) {
            gamePresenter.setReceivedCards(msg.getAssignedProgramCards(), msg.getFreeCards());
        } else LOG.warn("gamePresenter object was deleted! Ignoring ProgramCardDataResponse!");
    }

    /**
     * Handles RobotTurnedOffMessage
     *
     * @param msg the RobotTurnedOffMessage object seen on the EventBus
     * @see RobotTurnedOffMessage
     * @author Maria Andrade
     * @since 2023-06-13
     */
    public void sendMessageTurnedOffRobot(RobotTurnedOffMessage msg) {
        GamePresenter gamePresenter =
                lobbyIdToLobbyGamePresenterMap.get(msg.getLobbyID()).getGamePresenter();
        // only keep sending messages if then lobby was not dropped, otherwise ignore
        if (!Objects.equals(gamePresenter, null))
            gamePresenter.showRobotTurnedOff(msg.getTurnedOffUser());
        else LOG.warn("gamePresenter object was deleted! Ignoring RobotTurnedOffMessage!");
    }

    /**
     * Handles PlayerIsReadyMessage
     *
     * @param msg the PlayerIsReadyMessage object seen on the EventBus
     * @see PlayerIsReadyMessage
     * @author Maria Andrade
     * @since 2023-05-18
     */
    public void sendMessagePlayerIsReady(PlayerIsReadyMessage msg) {
        GamePresenter gamePresenter =
                lobbyIdToLobbyGamePresenterMap.get(msg.getLobbyID()).getGamePresenter();
        // only keep sending messages if then lobby was not dropped, otherwise ignore
        if (!Objects.equals(gamePresenter, null)) {
            gamePresenter.setPlayerReadyStatus(msg.getPlayerIsReady());
            gamePresenter.blockPlayerCardsAfterSubmit(msg.getPlayerIsReady()); // block cards
        } else LOG.warn("gamePresenter object was deleted! Ignoring PlayerIsReadyMessage!");
    }

    /**
     * Handles ShowAllPlayersCardsMessage
     *
     * @param msg the ShowAllPlayersCardsMessage object seen on the EventBus
     * @see ShowAllPlayersCardsMessage
     * @author Maria Andrade
     * @since 2023-05-18
     */
    public void sendMessageAllPlayersAreReady(ShowAllPlayersCardsMessage msg) {
        GamePresenter gamePresenter =
                lobbyIdToLobbyGamePresenterMap.get(msg.getLobbyID()).getGamePresenter();
        // only keep sending messages if then lobby was not dropped, otherwise ignore
        if (!Objects.equals(gamePresenter, null))
            gamePresenter.setPlayerCard(msg.getUserDTOCardDTOMap());
        else LOG.warn("gamePresenter object was deleted! Ignoring ShowAllPlayersCardsMessage!");
    }

    public void sendMessageBoardIsMoving(ShowBoardMovingMessage msg) {
        GamePresenter gamePresenter =
                lobbyIdToLobbyGamePresenterMap.get(msg.getLobbyID()).getGamePresenter();
        // only keep sending messages if then lobby was not dropped, otherwise ignore
        if (!Objects.equals(gamePresenter, null))
            gamePresenter.animateBoardElements(msg.getPlayersDTO());
        else LOG.warn("gamePresenter object was deleted! Ignoring ShowBoardMovingMessage!");
    }

    /**
     * Handles TextHistoryMessage
     *
     * @param msg the TextHistoryMessage object seen on the EventBus
     * @see TextHistoryMessage
     * @author Maria Andrade and Tommy Dang and Waldemar Kempel
     * @since 2023-06-02
     */
    public void updateHistory(TextHistoryMessage msg) {
        GamePresenter gamePresenter =
                lobbyIdToLobbyGamePresenterMap.get(msg.getLobbyID()).getGamePresenter();
        // only keep sending messages if then lobby was not dropped, otherwise ignore
        if (!Objects.equals(gamePresenter, null))
            gamePresenter.updateHistoryMessage(msg.getMessage());
        else LOG.warn("gamePresenter object was deleted! Ignoring TextHistoryMessage!");
    }

    /**
     * Updates the game map (the board on which the game is played)
     *
     * @param lobbyID The ID of the lobby which is meant to be updated
     * @param map The Map that is to be selected
     * @author Mathis Eilers
     * @since 2023-05-27
     */
    public void updateGameMap(int lobbyID, de.uol.swp.common.game.Map map) {
        lobbyIdToLobbyGamePresenterMap.get(lobbyID).getLobbyPresenter().updateMapDisplay(map);
    }

    /**
     * Handles RobotIsFinallyDead detected on the EventBus
     *
     * @param msg The GameOverMessage seen on the EventBus
     * @see de.uol.swp.common.game.message.RobotIsFinallyDead
     * @author Maria Eduarda
     * @since 2023-06-09
     */
    public void setRobotDied(RobotIsFinallyDead msg) {
        GamePresenter gamePresenter =
                lobbyIdToLobbyGamePresenterMap.get(msg.getLobbyID()).getGamePresenter();
        // only keep sending messages if then lobby was not dropped, otherwise ignore
        if (!Objects.equals(gamePresenter, null)) gamePresenter.setRobotDied(msg.getUserDied());
        else LOG.warn("gamePresenter object was deleted! Ignoring RobotIsFinallyDead!");
    }

    /**
     * Handles GameOverMessage detected on the EventBus
     *
     * @param msg The GameOverMessage seen on the EventBus
     * @see de.uol.swp.common.game.message.GameOverMessage
     * @author Daniel Merzo & Maria Eduarda
     * @since 2023-05-24
     */
    public void gameOver(GameOverMessage msg) {
        GamePresenter gamePresenter =
                lobbyIdToLobbyGamePresenterMap.get(msg.getLobbyID()).getGamePresenter();
        // only keep sending messages if then lobby was not dropped, otherwise ignore
        if (!Objects.equals(gamePresenter, null)) {
            gamePresenter.setUserWon(msg.getUserWon());
            eventBus.post(
                    new ShowGameOverEvent(
                            msg.getLobbyID(),
                            msg.getUserWon(),
                            gamePresenter.getUserWonImage(msg.getUserWon()),
                            loggedInUser,
                            lobbyIdToLobbyDTOMap.get(msg.getLobbyID()).getName(),
                            lobbyIdToLobbyDTOMap.get(msg.getLobbyID()).isMultiplayer()));

        } else LOG.warn("gamePresenter object was deleted! Ignoring RobotIsFinallyDead!");
    }

    /**
     * Handles game over after clicking button to stay in game view
     *
     * @author Maria Eduarda
     * @since 2023-07-04
     */
    public void gameOverAfterDialog(int lobbyID) {
        GamePresenter gamePresenter =
                lobbyIdToLobbyGamePresenterMap.get(lobbyID).getGamePresenter();
        // only keep sending messages if then lobby was not dropped, otherwise ignore
        if (!Objects.equals(gamePresenter, null)) {
            gamePresenter.gameOver();
        } else LOG.warn("gamePresenter object was deleted! Ignoring RobotIsFinallyDead!");
    }
}
