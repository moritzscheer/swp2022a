package de.uol.swp.client.lobby.game;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import de.uol.swp.client.lobby.LobbyManagement;
import de.uol.swp.client.lobby.game.events.ShowGameViewEvent;
import de.uol.swp.client.lobby.game.presenter.GamePresenter;
import de.uol.swp.common.game.dto.GameDTO;
import de.uol.swp.common.game.message.GetMapDataResponse;
import de.uol.swp.common.game.message.StartGameMessage;
import de.uol.swp.common.game.response.ProgramCardDataResponse;
import de.uol.swp.common.lobby.dto.LobbyDTO;
import de.uol.swp.common.user.User;
import de.uol.swp.common.user.response.LoginSuccessfulResponse;
import javafx.scene.Parent;

import java.util.HashMap;
import java.util.Map;

public class GameManagement {

    EventBus eventBus;
//    private Parent lobbyParent;
    private Parent gameParent;
//    private LobbyPresenter lobbyPresenter;
    private GamePresenter currentGamePresenter;
    private int currentLobbyId;
    private int currentGameId;
    private User loggedInUser;
    private final Map<Integer, GameDTO> gameMap = new HashMap<>();
    private final Map<Integer, LobbyDTO> gameIdToLobby = new HashMap<>();
    private GameService gameService;

    private static GameManagement instance;
    public static GameManagement getInstance(){
        return instance;
    }

    /**
     * Constructor to save the lobbyPresenter and lobbyParent in the given attributes *
     *
     * @author Moritz Scheer
     * @since 2023-03-09
     */
    @Inject
    public GameManagement(EventBus eventBus, GameService gameService) {
        this.eventBus = eventBus;
        eventBus.register(this);
        this.gameService = gameService;
        instance = this;
    }

//    public void maybeNeedInLobby(LobbyPresenter lobbyPresenter, Parent lobbyParent) {
//        this.lobbyPresenter = lobbyPresenter;
//        this.lobbyParent = lobbyParent;
//    }

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
        LobbyManagement.getInstance().setGameView(lobbyId, this.gameParent, gameId);
    }

//    /**
//     * Method to save the gamePresenter and gameParent in the given attributes *
//     *
//     * @param gamePresenter the Presenter of the game view
//     * @param gameParent the Parent Object of the game view
//     * @author Moritz Scheer
//     * @since 2023-03-09
//     */
//    public void setGameView(GamePresenter gamePresenter, Parent gameParent) {
//        this.currentGamePresenter = gamePresenter;
//        this.gameParent = gameParent;
//    }

    // -----------------------------------------------------
    // parents
    // -----------------------------------------------------

    /**
     * Getter for the lobbyParent variable
     *
     * @author Moritz Scheer
     * @since 2023-03-09
     */
//    public Parent getLobbyParent() {
//        return lobbyParent;
//    }

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
//    public LobbyPresenter getLobbyPresenter() {
//        return lobbyPresenter;
//    }

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

        gameService.getMapData(msg.getGameID(), msg.getLobby());
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
    public void onGetMapDataMessage(GetMapDataResponse msg){
         // TODO: handle board
//        // TODO: change view to gameView
        //currentGamePresenter.init(msg.getLobbyID(), gameIdToLobby.get(msg.getGameID()) , msg.getBoardImageIds(), msg.getGameID());
//        setGameView(currentGamePresenter, gameParent, msg.getLobbyID(), msg.getGameID());
//        sceneManager.showGameScreen(msg.getLobbyID());
        eventBus.post(new ShowGameViewEvent(msg.getLobby(), msg.getGameID(), msg.getBoardImageIds(), gameMap.get(msg.getGameID())));
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

    public void setGameParent(Parent gameParent) {
        this.gameParent = gameParent;
    }
}
