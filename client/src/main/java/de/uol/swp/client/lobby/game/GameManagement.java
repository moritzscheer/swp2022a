package de.uol.swp.client.lobby.game;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import de.uol.swp.client.lobby.game.events.ShowGameViewEvent;
import de.uol.swp.client.lobby.game.presenter.GamePresenter;
import de.uol.swp.common.game.dto.GameDTO;
import de.uol.swp.common.game.message.GetMapDataResponse;
import de.uol.swp.common.game.message.StartGameMessage;
import de.uol.swp.common.game.response.ProgramCardDataResponse;
import de.uol.swp.common.lobby.dto.LobbyDTO;
import de.uol.swp.common.user.User;
import de.uol.swp.common.user.response.LoginSuccessfulResponse;

import java.util.HashMap;
import java.util.Map;

public class GameManagement {

    EventBus eventBus;
    // give access of GamePresenter/LobbyPresenter to GameManagement
    // still does not work as expected
    // TODO: when works as expected, remove subscribe from GamePresenter
    private final Map<Integer, LobbyGameTuple> lobbyGameMap = new HashMap<>();
    private GamePresenter currentGamePresenter;
    private int currentLobbyId;
    private int currentGameId;
    private GameDTO currentGame;
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
        this.currentGame = msg.getGame();
        this.currentGameId = msg.getGameID();
        this.currentLobbyId = msg.getLobbyID();

        /**this post is to create the gamePresenter and save the reference of it
         * inside the LobbyGame class
         * LobbyManagement and GameManagement both need access to same reference
         * For this reason, both instantiate a hashmap lobbyGameMap
         */
        eventBus.post(new ShowGameViewEvent(msg.getLobby(), msg.getGameID()));

        // create request to get the cards
        gameService.getMapData(msg.getGameID(), msg.getLobby());
    }

    /**
     * Handles the reference to LobbyGame class
     *
     * Handles references to lobbyPresenter and gamePresenter
     *
     * @see LobbyGameTuple
     * @see de.uol.swp.client.lobby.LobbyManagement
     * @author Maria Andrade
     * @since 2023-05-14
     */
    public void setupLobbyGame(LobbyGameTuple lobbyGameTupleReference, LobbyDTO lobby) {
        lobbyGameMap.put(lobby.getLobbyID(), lobbyGameTupleReference); // same as in Lobby Management
        // initialize presenter here
        lobbyGameMap.get(lobby.getLobbyID()).getGamePresenter().init(lobby.getLobbyID(), lobby, this.currentGame);
    }

    /**
     * Handles GetMapDataMessage
     *
     * @param msg the GetMapDataMessage object seen on the EventBus
     * @see GetMapDataResponse
     * @author Maria Andrade
     * @since 2023-05-06
     */
    public void onGetMapDataResponse(GetMapDataResponse msg){
        //TODO: fix the reference to getGamePresenter
        // the reference seems correct, but during runtime it says it´s null
        // there might be more than one thread running this code
        GamePresenter a = lobbyGameMap.get(msg.getLobbyID()).getGamePresenter();
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

    /**
     * Setter for the currentGamePresenter variable
     *
     * @author Moritz Scheer & Maxim Erden
     * @since 2023-02-28
     */
    public void setNextGamePresenter(GamePresenter currentGamePresenter) {
        this.currentGamePresenter = currentGamePresenter;
    }

}
