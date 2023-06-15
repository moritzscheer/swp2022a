package de.uol.swp.server.gamelogic;

import static de.uol.swp.server.utils.ConvertToDTOUtils.*;
import static de.uol.swp.server.utils.JsonUtils.searchCardTypeInJSON;

import static java.lang.Math.abs;
import static java.util.concurrent.TimeUnit.SECONDS;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;

import de.uol.swp.common.chat.message.TextHistoryMessage;
import de.uol.swp.common.game.Position;
import de.uol.swp.common.game.dto.*;
import de.uol.swp.common.game.enums.CardinalDirection;
import de.uol.swp.common.game.message.*;
import de.uol.swp.common.game.request.GetMapDataRequest;
import de.uol.swp.common.game.request.GetProgramCardsRequest;
import de.uol.swp.common.game.request.StartGameRequest;
import de.uol.swp.common.game.request.SubmitCardsRequest;
import de.uol.swp.common.game.response.ProgramCardDataResponse;
import de.uol.swp.common.lobby.dto.LobbyDTO;
import de.uol.swp.common.lobby.message.AbstractLobbyMessage;
import de.uol.swp.common.user.UserDTO;
import de.uol.swp.server.AbstractService;
import de.uol.swp.server.gamelogic.cards.Card;
import de.uol.swp.server.lobby.LobbyManagement;
import de.uol.swp.server.lobby.LobbyService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Handles the game requests send by the users
 *
 * @author Maria
 * @since 2023-05-06
 */
public class GameService extends AbstractService {

    private static final Logger LOG = LogManager.getLogger(GameService.class);
    private final LobbyManagement lobbyManagement;
    private final LobbyService lobbyService;

    private final Map<Integer, Game> games = new HashMap<>();
    private final Map<Integer, GameDTO> gamesDTO = new HashMap<>();

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    /**
     * Constructor
     *
     * @param bus the EvenBus used throughout the server
     * @param lobbyService
     * @since 2019-10-08
     */
    @Inject
    public GameService(EventBus bus, LobbyManagement lobbyManagement, LobbyService lobbyService) {
        super(bus);
        this.lobbyManagement = lobbyManagement;
        this.lobbyService = lobbyService;
    }

    /**
     * Handles the StartGameRequest
     *
     * <p>If a StartGameRequest is detected on the EventBus, this method is called. It creates a
     * Game object linked to the lobbyID, which will be used to retrieve information about the game
     *
     * @author Maria Eduarda Costa Leite Andrade
     * @see de.uol.swp.common.game.request.StartGameRequest
     * @see de.uol.swp.common.game.message.StartGameMessage
     * @since 2023-02-28
     */
    public GameDTO createNewGame(int lobbyID, String mapName, int numberBots) {
        System.out.println("I am creating your game :)");

        System.out.println("New id :)");
        // TODO: fix docking positions
        Position[] checkpointsList = {
            new Position(11, 3), new Position(9, 3), new Position(7, 4), new Position(1, 9)
        };
        System.out.println("dockings :)");
        Optional<LobbyDTO> lobby = lobbyManagement.getLobby(lobbyID);
        if (!lobby.isPresent()) {
            System.out.println("GameService: lobby not found");
        }

        // create and save Game Object
        games.put(
                lobbyID,
                new Game(lobbyID,
                        checkpointsList,
                        lobby.get().getUsers(),
                        mapName,
                        numberBots
                )
        );
        games.get(lobbyID).startGame();

        // Create DTOs objects
        // TODO: create Player
        List<PlayerDTO> players = new ArrayList<>();
        for(AbstractPlayer player: games.get(lobbyID).getPlayers()) {
            // add in the list
            players.add(
                    convertPlayerToPlayerDTO(player)
            );
        }

        // TODO: create Board, instead of using 4d array
        // BlockDTO blockDTO = new BlockDTO();

        GameDTO gameDTO = new GameDTO(players);

        gamesDTO.put(lobbyID, gameDTO); // save reference to the GameDTO

        LOG.debug("Number of players including Bots: "+ players.size() );
        return gameDTO;
    }

    /**
     * Searches for the game with the requested gameID
     *
     * @param gameID Integer containing the gameID of the game to search for
     * @return either empty Optional or Optional containing the game
     * @author Maria Andrade
     * @since 2023-05-06
     */
    public Optional<Game> getGame(Integer gameID) {
        for (Map.Entry<Integer, Game> entry : games.entrySet()) {
            if (entry.getKey().equals(gameID)) {
                Game game = games.get(entry.getKey());
                return Optional.of(game);
            }
        }
        return Optional.empty();
    }

    /**
     * Searches for the gameDTO with the requested gameID
     *
     * @param gameID Integer containing the gameID of the game to search for
     * @return either empty Optional or Optional containing the gameDTO
     * @author Maria Andrade
     * @since 2023-05-13
     */
    public Optional<GameDTO> getGameDTO(Integer gameID) {
        for (Map.Entry<Integer, GameDTO> entry : gamesDTO.entrySet()) {
            if (entry.getKey().equals(gameID)) {
                GameDTO game = gamesDTO.get(entry.getKey());
                return Optional.of(game);
            }
        }
        return Optional.empty();
    }

    /**
     * Handles StartGameRequest found on the EventBus
     *
     * <p>If a StartGameRequest is detected on the EventBus, this method is called. It posts a
     * StartGameMessage to all the users in the lobby, containing the
     *
     * @param msg StartGameRequest found on the EventBus
     * @author Moritz Scheer, Maria Eduarda Costa Leite Andrade, WKempel
     * @see de.uol.swp.common.game.request.StartGameRequest
     * @see de.uol.swp.common.game.message.StartGameMessage
     * @since 2023-02-28
     */
    @Subscribe
    public void onStartGameRequest(StartGameRequest msg) {
        Optional<LobbyDTO> tmp = lobbyManagement.getLobby(msg.getLobbyID());
        if (!tmp.isEmpty()) {
            System.out.println("Creating game");
            GameDTO game = createNewGame(msg.getLobbyID(), msg.getLobby().getMapName(), msg.getNumberBots());
            System.out.println("Sending Message to all in Lobby");
            lobbyService.sendToAllInLobby(
                    msg.getLobbyID(),
                    new StartGameMessage(
                            msg.getLobbyID(), msg.getLobby() , game));
            tmp.get().resetCounterRequest();
        }
    }

    /**
     * Handles GetProgramCardsRequest found on the EventBus
     *
     * <p>If a GetProgramCardsRequest is detected on the EventBus, this method is called. It posts a
     * GetProgramCardsRequest to all the users in the lobby, containing the
     *
     * @param msg StartGameRequest found on the EventBus
     * @author Moritz Scheer, Maria Eduarda Costa Leite Andrade, WKempel
     * @see de.uol.swp.common.game.request.StartGameRequest
     * @see de.uol.swp.common.game.message.StartGameMessage
     * @since 2023-02-28
     */
    @Subscribe
    public void onGetProgramCardsRequest(GetProgramCardsRequest msg) throws InterruptedException {
        LOG.debug("onGetProgramCardsRequest");
        Optional<Game> game = getGame(msg.getLobbyID());
        boolean callBot = false;
        if (!game.isEmpty()) {
            // only distribute once, then we get all cards for all players

            callBot = game.get().distributeProgramCards();

            // get loggedInUser
            UserDTO user = msg.getLoggedInUser();
            ProgramCardDataResponse response =
                    new ProgramCardDataResponse(
                            convertCardsToCardsDTO(
                                    game.get().getPlayerByUserDTO(user).getReceivedCards()),
                            9 - game.get().getPlayerByUserDTO(user).getRobot().getDamageToken(),
                            msg.getLobbyID());
            response.initWithMessage(msg);
            post(response);
            LOG.debug("Server, cards received by user {}", user.getUsername());
            for (Card card : game.get().getPlayerByUserDTO(user).getReceivedCards()) {
                LOG.debug("   id={} priority={}", card.getId(), card.getPriority());
            }
            // todo verify we need to check if user is a bot
            if(callBot) {
                selectCardBot(game.get(), msg.getLobbyID());
            }
        }
    }

    /**
     * Handles GetMapDataRequest found on the EventBus
     *
     * <p>If a GetMapDataRequest is detected on the EventBus, this method is called. It posts a
     * GetMapDataMessage to all the users in the lobby, containing the game board
     *
     * @param msg GetMapDataRequest found on the EventBus
     * @author Maria Eduarda Costa Leite Andrade
     * @see de.uol.swp.common.game.request.GetMapDataRequest
     * @see GetMapDataResponse
     * @since 2023-02-28
     */
    @Subscribe
    public void onGetMapDataRequest(GetMapDataRequest msg) {

        System.out.println("Get Map Data server");
        Optional<Game> game = getGame(msg.getLobby().getLobbyID());
        if (game.isPresent()) {
            Block[][] board = game.get().getBoard();

            if (board == null) {
                throw new IllegalStateException("Board is nicht initialisiert");
            }

            BlockDTO[][] boardDTOs = new BlockDTO[board.length][board[0].length];

            for (int row = 0; row < board.length; row++) {
                for (int col = 0; col < board[0].length; col++) {
                    boardDTOs[row][col] = new BlockDTO(board[row][col].getImages());
                }
            }
            GetMapDataResponse getMapDataResponse =
                    new GetMapDataResponse(
                            boardDTOs, msg.getLobby(), game.get().getDockingStartPosition());
            getMapDataResponse.initWithMessage(msg);
            post(getMapDataResponse);
        } else {
            // TODO: send ErrorResponse
        }
    }

    /**
     * Handles SubmitCardsRequest found on the EventBus
     *
     * <p>If a SubmitCardsRequest is detected on the EventBus, this method is called. It posts a
     * PlayerIsReadyMessage to all the users in the lobby, saying that this player is ready
     *
     * @param request SubmitCardsRequest found on the EventBus
     * @author Maria Eduarda Costa Leite Andrade
     * @see de.uol.swp.common.game.request.SubmitCardsRequest
     * @since 2023-05-18
     */
    @Subscribe
    public void onSubmitCardsRequest(SubmitCardsRequest request) throws InterruptedException {
        Optional<Game> game = getGame(request.getLobbyID());
        if (game.isPresent()) {
            // TODO
            LOG.debug("IN SERVER: RECEIVED CARDS from " + request.getLoggedInUser().getUsername());
            for (CardDTO card : request.getCardDTOs())
                LOG.debug(card.getID() + " -  " + card.getPriority());

            Boolean allChosen =
                    game.get().registerCardsFromUser(request.getLoggedInUser(), request.getCardDTOs());
            PlayerIsReadyMessage msg =
                    new PlayerIsReadyMessage(request.getLoggedInUser(), request.getLobbyID());
            lobbyService.sendToAllInLobby(request.getLobbyID(), msg);

            if (allChosen) {
                manageRoundsUpdates(game.get(), request.getLobbyID());
            }
        }
    }

    private void selectCardBot(Game game,int lobbyID) throws InterruptedException {
        Boolean allChosen = game.registerCardsFromBot();
        for(AbstractPlayer botPlayer : game.getPlayers()) {
            if(botPlayer instanceof BotPlayer) {
                PlayerIsReadyMessage msg = new PlayerIsReadyMessage(botPlayer.getUser(), lobbyID);
                lobbyService.sendToAllInLobby(lobbyID, msg);
            }
        }


        if(allChosen){
            LOG.debug("All players have chosen cards");
            Map<UserDTO, CardDTO> userDTOCardDTOMap = game.revealProgramCards();
            for(Map.Entry<UserDTO, CardDTO> userCurrentCard: userDTOCardDTOMap.entrySet())
                LOG.debug("Player " + userCurrentCard.getKey().getUsername() +
                        " card " + userCurrentCard.getValue().getID() + " - " + userCurrentCard.getValue().getPriority());
            lobbyService.sendToAllInLobby(lobbyID,
                    new ShowAllPlayersCardsMessage(userDTOCardDTOMap, lobbyID));
            manageRoundsUpdates(game, lobbyID);
        }
    }


    /**
     * Handles all rounds, call calcGame() for every round and send messages to update the view
     * using help of a scheduler to give some delay to display
     *
     * <p>This method is called after all players are ready
     *
     * <p>TODO: maybe we could separate this into GameService and GameManagement so this class
     * contains no logic
     *
     * @param game reference to which game
     * @param lobbyID lobbyID to which the game belongs
     * @author Maria Eduarda Costa Leite Andrade
     * @see de.uol.swp.common.game.request.SubmitCardsRequest
     * @since 2023-05-24
     */
    public void manageRoundsUpdates(Game game, int lobbyID) throws InterruptedException {
        // TODO
        int secondsToWait = 1;

        lobbyService.sendToAllInLobby(
                lobbyID,
                new TextHistoryMessage(
                        lobbyID, "======= Round " + game.getRoundNumber() + " ======= \n"));

        while (game.getProgramStep() < 5) {
            Map<UserDTO, CardDTO> userDTOCardDTOMap = game.revealProgramCards();
            List<Position> previousPositions = logInformationPosition(game);
            List<CardinalDirection> previousDirections = logInformationDirection(game);
            List<CardDTO> cardDTOS = logInformationCards(game);

            // show cards and wait 1 second before moving
            scheduler.schedule(
                    new Runnable() {
                        public void run() {
                            lobbyService.sendToAllInLobby(
                                    lobbyID,
                                    new ShowAllPlayersCardsMessage(userDTOCardDTOMap, lobbyID));
                        }
                    },
                    secondsToWait,
                    SECONDS);
            secondsToWait++;

            // TODO: FIX calcGame calculates wrong
            game.calcGameRoundCards();
            // game.calcGameRound();
            secondsToWait =
                    moveCards(
                            lobbyID,
                            secondsToWait,
                            game,
                            previousPositions,
                            previousDirections,
                            cardDTOS);
            // get new previous positions
            if (isGameOver(lobbyID, game, secondsToWait)) {
                break;
            }

            previousPositions = logInformationPosition(game);

            game.calcGameRoundBoard();
            // show board animation
            sendBoardMoveMessage(
                    lobbyID, convertPlayerListToPlayerDTOList(game.getPlayers()), secondsToWait);
            if (isGameOver(lobbyID, game, secondsToWait)) {
                break;
            }

            // go to next step
            game.increaseProgramStep();
        }

        UserDTO winner = game.roundIsOver(); // reset variables
        AbstractLobbyMessage msg;
        if(Objects.equals(winner, null))
            msg = new RoundIsOverMessage(lobbyID);
        else
            msg = new GameOverMessage(lobbyID, winner);
        scheduler.schedule(
                new Runnable() {
                    public void run() {
                        lobbyService.sendToAllInLobby(lobbyID, msg);
                    }
                },
                secondsToWait,
                SECONDS);
    }

    public List<Position> logInformationPosition(Game game) {
        List<Position> previousPositions = new ArrayList<>();
        LOG.debug("Status of game BEFORE calcGameRound");
        for (AbstractPlayer player : game.getPlayers()) {
            Position pos = player.getRobot().getPosition();
            previousPositions.add(pos);
            LOG.debug(
                    "     Robot Position {} pos = x {} y {}",
                     player.getUser().getUsername(),
                    pos.x,
                    pos.y);
        }
        LOG.debug("Showing chosen cards for round " + game.getProgramStep());
        Map<UserDTO, CardDTO> userDTOCardDTOMap = game.revealProgramCards();
        for (Map.Entry<UserDTO, CardDTO> userCurrentCard : userDTOCardDTOMap.entrySet())
            LOG.debug(
                    "Player "
                            + userCurrentCard.getKey().getUsername()
                            + " card "
                            + userCurrentCard.getValue().getID()
                            + " - "
                            + userCurrentCard.getValue().getPriority());

        return previousPositions;
    }

    public List<CardinalDirection> logInformationDirection(Game game) {
        List<CardinalDirection> previousDirections = new ArrayList<>();
        LOG.debug("Status of game BEFORE calcGameRound");
        for (AbstractPlayer player : game.getPlayers()) {
            CardinalDirection dir = player.getRobot().getDirection();
            previousDirections.add(dir);
        }
        return previousDirections;
    }

    public List<CardDTO> logInformationCards(Game game) {
        List<CardDTO> cards = new ArrayList<>();
        LOG.debug("Showing chosen cards for round " + game.getProgramStep());
        Map<UserDTO, CardDTO> userDTOCardDTOMap = game.revealProgramCards();
        for (Map.Entry<UserDTO, CardDTO> userCurrentCard : userDTOCardDTOMap.entrySet()) {
            cards.add(userCurrentCard.getValue());
        }

        return cards;
    }

    public int moveCards(
            int lobbyID,
            int secondsToWait,
            Game game,
            List<Position> previousPositions,
            List<CardinalDirection> previousDirections,
            List<CardDTO> cardDTOS) {
        LOG.debug("Status of game AFTER calcGameRoundCards");
        int i = 0;
        // Move each player at a time
        for (AbstractPlayer player : game.getPlayers()) {
            if (player.getRobot().isDeadForTheRound()) continue; // if robot is dead, do nothing
            UserDTO currentUser = player.getUser();
            Position currentPos = player.getRobot().getPosition();
            Position previousPos = previousPositions.get(i);
            CardinalDirection previousDirection = previousDirections.get(i);
            CardinalDirection direction = player.getRobot().getDirection();
            CardDTO cardDTO = cardDTOS.get(i);
            LOG.debug(
                    "     Robot Position {} pos = x {} y {}",
                    currentUser.getUsername(),
                    currentPos.x,
                    currentPos.y);

            // ONLY DIRECTION CHANGED
            if (abs(currentPos.x - previousPos.x) + abs(currentPos.y - previousPos.y) == 0) {
                LOG.debug("ROTATING");
            }
            // DISPLAY IN SINGLE STEP
            else if (abs(currentPos.x - previousPos.x) + abs(currentPos.y - previousPos.y) >= 1) {
                LOG.debug(
                        "MOVING FROM x={} y={} to x={} y={}",
                        previousPos.x,
                        previousPos.y,
                        currentPos.x,
                        currentPos.y);
            }

            //        [Type] [Username] [oldPosition] [oldDirection] [CardName/BoardElement] to
            // [newPosition] [newDirection]
            //        ======Round 1=======
            //        [Card] Test1 (x1,y1){East} Move3 to (x2,y2){East}

            TextHistoryMessage msg =
                    new TextHistoryMessage(
                            lobbyID,
                            "[Card] "
                                    + player.getUser().getUsername() // User
                                    + " ("
                                    + previousPos.x
                                    + ", "
                                    + previousPos.y
                                    + ")" // old x and y position
                                    + "{"
                                    + previousDirection
                                    + "} " // old direction
                                    + searchCardTypeInJSON(cardDTO.getID())
                                    + // CardType
                                    " to" //
                                    + " ("
                                    + currentPos.x
                                    + ", "
                                    + currentPos.y
                                    + ")" // new x and y position
                                    + "{"
                                    + direction
                                    + "} \n" // new direction
                            );

            sendCardMoveMessage(lobbyID, convertPlayerToPlayerDTO(player), secondsToWait);
            sendAbstractLobbyMessage(lobbyID, secondsToWait, msg);

            if (!player.getRobot().isAlive()){
                // if robot is dead, send message
                sendAbstractLobbyMessage(lobbyID, secondsToWait,
                        new TextHistoryMessage(lobbyID,
                                player.getUser().getUsername() + " is dead!\n"));
                player.getRobot().setDeadForTheRound(true);
                if(player.getRobot().getLifeToken() <= 0){
                    player.getRobot().setDeadForever();
                    sendAbstractLobbyMessage(
                            lobbyID, secondsToWait, new RobotIsFinallyDead(
                                    lobbyID, player.getUser()
                            )
                    );
                }
            }

            secondsToWait = secondsToWait + 2;
        }
        return secondsToWait;
    }

    public void sendCardMoveMessage(
            int lobbyID, PlayerDTO playerDTO, int secondsToWait) {
        scheduler.schedule(
                new Runnable() {
                    public void run() {
                        lobbyService.sendToAllInLobby(
                                lobbyID, new ShowRobotMovingMessage(lobbyID, playerDTO));
                    }
                },
                secondsToWait,
                SECONDS);
    }

    public void sendBoardMoveMessage(int lobbyID, List<PlayerDTO> players, int secondsToWait) {
        scheduler.schedule(
                new Runnable() {
                    public void run() {
                        lobbyService.sendToAllInLobby(
                                lobbyID, new ShowBoardMovingMessage(lobbyID, players));
                    }
                },
                secondsToWait,
                SECONDS);
    }

    public void sendAbstractLobbyMessage(
            int lobbyID, int secondsToWait, AbstractLobbyMessage msg) {
        scheduler.schedule(
                new Runnable() {
                    public void run() {
                        lobbyService.sendToAllInLobby(lobbyID, msg);
                    }
                },
                secondsToWait,
                SECONDS);
    }

    /**
     * Check if a Player achived the last checkpoint
     *
     * @param game reference to which game
     * @param lobbyID lobbyID to which the game belongs
     * @author Maria Eduarda Costa Leite Andrade
     * @see de.uol.swp.common.game.request.SubmitCardsRequest
     * @since 2023-06-05
     */
    private boolean isGameOver(int lobbyID, Game game, int secondsToWait) {
        for (AbstractPlayer player : game.getPlayers()) {
            if (player.getRobot().getLastCheckPoint() == game.getLastCheckPoint()) {
                // TODO: fix when AbstractPlayer has User
                // playerWonTheGame(lobbyID, player.getUser());
                playerWonTheGame(lobbyID, ((Player) player).getUser(), secondsToWait);
                return true;
            }
        }
        return false;
    }

    /**
     * Send Message when a Player achieved the last checkpoint
     *
     * @param userWon user that won the game
     * @param lobbyID lobbyID to which the game belongs
     * @author Maria Eduarda Costa Leite Andrade
     * @see de.uol.swp.common.game.message.GameOverMessage
     * @since 2023-06-05
     */
    private void playerWonTheGame(int lobbyID, UserDTO userWon, int secondsToWait) {
        scheduler.schedule(
                new Runnable() {
                    public void run() {
                        lobbyService.sendToAllInLobby(
                                lobbyID, new GameOverMessage(lobbyID, userWon));
                    }
                },
                secondsToWait,
                SECONDS);
    }
}
