package de.uol.swp.client.lobby.game;

import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import de.uol.swp.client.SceneManager;
import de.uol.swp.client.lobby.game.presenter.GamePresenter;
import de.uol.swp.client.lobby.lobby.presenter.LobbyPresenter;
import de.uol.swp.common.game.dto.GameDTO;
import de.uol.swp.common.game.message.GetMapDataMessage;
import de.uol.swp.common.game.message.StartGameMessage;
import de.uol.swp.common.game.response.ProgramCardDataResponse;
import de.uol.swp.common.lobby.dto.LobbyDTO;
import de.uol.swp.common.user.User;
import de.uol.swp.common.user.response.LoginSuccessfulResponse;
import javafx.scene.Parent;

import java.util.HashMap;
import java.util.Map;

public class GameManagement {

    private Parent lobbyParent;
    private Parent gameParent;
    private LobbyPresenter lobbyPresenter;
    private SceneManager sceneManager;
    private GamePresenter currentGamePresenter;
    private int currentLobbyId;
    private int currentGameId;
    private User loggedInUser;
    private final Map<Integer, GameDTO> gameMap = new HashMap<>();
    private final Map<Integer, LobbyDTO> gameIdToLobby = new HashMap<>();
    @Inject private GameService gameService;

    /**
     * Constructor to save the lobbyPresenter and lobbyParent in the given attributes *
     *
     * @param lobbyPresenter the Presenter of the lobby view
     * @param lobbyParent the Parent Object of the lobby view
     * @author Moritz Scheer
     * @since 2023-03-09
     */
    public GameManagement(LobbyPresenter lobbyPresenter, Parent lobbyParent) {
        this.lobbyPresenter = lobbyPresenter;
        this.lobbyParent = lobbyParent;
    }

    /**
     * Method to save the gamePresenter and gameParent in the given attributes *
     *
     * @param gamePresenter the Presenter of the game view
     * @param gameParent the Parent Object of the game view
     * @author Moritz Scheer
     * @since 2023-03-09
     */
    public void setGameView(GamePresenter gamePresenter, Parent gameParent, int lobbyId, int gameId) {
        this.currentGamePresenter = gamePresenter;
        this.gameParent = gameParent;
        this.currentLobbyId = lobbyId;
        this.currentGameId = gameId;
    }

    // -----------------------------------------------------
    // parents
    // -----------------------------------------------------

    /**
     * Getter for the lobbyParent variable
     *
     * @author Moritz Scheer
     * @since 2023-03-09
     */
    public Parent getLobbyParent() {
        return lobbyParent;
    }

    /**
     * Getter for the gameParent variable
     *
     * @author Moritz Scheer
     * @since 2023-03-09
     */
    public Parent getGameParent() {
        return gameParent;
    }

    // -----------------------------------------------------
    // presenter
    // -----------------------------------------------------

    /**
     * Getter for the gamePresenter variable
     *
     * @author Moritz Scheer
     * @since 2023-03-09
     */
    public GamePresenter getCurrentGamePresenter() {
        return currentGamePresenter;
    }

    /**
     * Getter for the lobbyPresenter variable
     *
     * @author Moritz Scheer
     * @since 2023-03-09
     */
    public LobbyPresenter getLobbyPresenter() {
        return lobbyPresenter;
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
    @Subscribe
    public void onStartGameMessage(StartGameMessage msg){
        // save game and id
        gameMap.put(
                msg.getGameID(),
                msg.getGame()
        );
        gameIdToLobby.put(
                msg.getGameID(),
                msg.getLobby()
        );
        setGameView(currentGamePresenter, gameParent, msg.getLobbyID(), msg.getGameID());
        gameService.getMapData(msg.getGameID());
    }

    /**
     * Handles GetMapDataMessage
     *
     * @param msg the GetMapDataMessage object seen on the EventBus
     * @see de.uol.swp.common.game.message.GetMapDataMessage
     * @author Maria Andrade
     * @since 2023-05-06
     */
    @Subscribe
    public void onGetMapDataMessage(GetMapDataMessage msg){
         // TODO: handle board
        // TODO: change view to gameView
        currentGamePresenter.init(msg.getLobbyID(), gameIdToLobby.get(msg.getGameID()) , msg.getBoardImageIds(), msg.getGameID());
        sceneManager.showGameScreen(msg.getLobbyID());
    }

    /**
     * Handles ProgramCardDataResponse
     *
     * @param msg the ProgramCardDataResponse object seen on the EventBus
     * @see de.uol.swp.common.game.response.ProgramCardDataResponse
     * @author Maria Andrade
     * @since 2023-05-06
     */
    @Subscribe
    public void onProgramCardDataResponse(ProgramCardDataResponse msg){
        msg.getPlayerID(); // to which player these cards are
        //if(msg.getPlayerID() == loggedInUser) // check if it is the correct one
        msg.getAssignedProgramCards(); // TODO: handle cards, CardDTO contains id and priority, both are in cards.json

    }

    public int getCurrentLobbyId() {
        return currentLobbyId;
    }

    public int getCurrentGameId() {
        return currentGameId;
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }
}
