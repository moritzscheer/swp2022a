package de.uol.swp.server.gamelogic;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
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

import static de.uol.swp.server.utils.ConvertToDTOUtils.*;
import static java.lang.Math.abs;
import static java.util.concurrent.TimeUnit.SECONDS;

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

    private final ScheduledExecutorService scheduler =
            Executors.newScheduledThreadPool(1);


    /**
     * Constructor
     *
     * @param bus          the EvenBus used throughout the server
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
     * <p>If a StartGameRequest is detected on the EventBus, this method is called.
     * It creates a Game object linked to the lobbyID, which will be used to retrieve information about the game
     *
     * @author Maria Eduarda Costa Leite Andrade
     * @see de.uol.swp.common.game.request.StartGameRequest
     * @see de.uol.swp.common.game.message.StartGameMessage
     * @since 2023-02-28
     */
    public GameDTO createNewGame(int lobbyID) {
        System.out.println("I am creating your game :)");

        System.out.println("New id :)");
        //TODO: fix docking positions
        Position[] checkpointsList = {
                new Position(0, 11),
                new Position(9,3),
                new Position(7,4),
                new Position(1,9)}
                ;
        System.out.println("dockings :)");
        Optional<LobbyDTO> lobby = lobbyManagement.getLobby(lobbyID);
        if(!lobby.isPresent()){
            System.out.println("GameService: lobby not found");
        }

        // create and save Game Object
        games.put(
                lobbyID,
                new Game(lobbyID,
                        checkpointsList,
                        lobby.get().getUsers()
                )
        );
        games.get(lobbyID).startGame();



        // Create DTOs objects
        // TODO: create Player
        List<PlayerDTO> players = new ArrayList<>();
        for(AbstractPlayer player: games.get(lobbyID).getPlayers()) {
            // convert Robot to RobotDTO
            RobotDTO robotDTO = convertRobotToRobotDTO(player.getRobot());

            // create playerDTO
            PlayerDTO playerDTO = new PlayerDTO(robotDTO);

            // check if the player is controlled by a user
            if(player.getClass() == Player.class)
                playerDTO.setUser(((Player) player).getUser());

            // add in the list
            players.add(playerDTO);

            // set currentCards later in the GameDTO Object
        }

        // TODO: create Board, instead of using 4d array
        //BlockDTO blockDTO = new BlockDTO();

        GameDTO gameDTO = new GameDTO(players);

        gamesDTO.put(lobbyID, gameDTO); // save reference to the GameDTO

        System.out.println("New Game :)");
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
            GameDTO game = createNewGame(msg.getLobbyID());
            System.out.println("Sending Message to all in Lobby");
            lobbyService.sendToAllInLobby(
                    msg.getLobbyID(),
                    new StartGameMessage(
                            msg.getLobbyID(), msg.getLobby(), game));
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
    public void onGetProgramCardsRequest(GetProgramCardsRequest msg) {
        LOG.debug("onGetProgramCardsRequest");
        Optional<Game> game = getGame(msg.getLobbyID());
        if(!game.isEmpty()) {
            // only distribute once, then we get all cards for all players
            game.get().distributeProgramCards();

            // get loggedInUser
            UserDTO user = msg.getLoggedInUser();
            ProgramCardDataResponse response = new ProgramCardDataResponse(
                    convertCardsToCardsDTO(
                            game.get().getPlayerByUserDTO(user).getReceivedCards()),
                    msg.getLobbyID());
            response.initWithMessage(msg);
            post(response);
            LOG.debug("Server, cards received by user {}", user.getUsername());
            for(Card card: game.get().getPlayerByUserDTO(user).getReceivedCards()){
                LOG.debug("   id={} priority={}", card.getId(), card.getPriority());
            }

        }
    }

    /**
     * Handles GetMapDataRequest found on the EventBus
     *
     * If a GetMapDataRequest is detected on the EventBus, this method is called. It posts a
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
        if(game.isPresent()){
            Block[][] board = game.get().getBoard();

            if(board == null){
                throw new IllegalStateException("Board is nicht initialisiert");
            }

            BlockDTO[][] boardDTOs= new BlockDTO[board.length][board[0].length];

            for(int row= 0; row< board.length; row++){
                for(int col=0; col < board[0].length; col++){
                    boardDTOs[row][col] = new BlockDTO(board[row][col].getImages());
                }
            }
            GetMapDataResponse getMapDataResponse = new GetMapDataResponse(
                    boardDTOs, msg.getLobby(), game.get().getDockingStartPosition());
            getMapDataResponse.initWithMessage(msg);
            post(getMapDataResponse);
        }
        else {
            //TODO: send ErrorResponse
        }
    }

    /**
     * Handles SubmitCardsRequest found on the EventBus
     *
     * If a SubmitCardsRequest is detected on the EventBus, this method is called. It posts a
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
        if(game.isPresent()){
            // TODO
            LOG.debug("IN SERVER: RECEIVED CARDS from " + request.getloggedInUser().getUsername());
            for(CardDTO card: request.getCardDTOs())
                LOG.debug(card.getID() + " -  " + card.getPriority());

            Boolean allChosen = game.get().register(request.getloggedInUser(), request.getCardDTOs());
            PlayerIsReadyMessage msg = new PlayerIsReadyMessage(request.getloggedInUser(), request.getLobbyID());
            lobbyService.sendToAllInLobby(request.getLobbyID(), msg);

            if(allChosen){
                manageRoundsUpdates(game.get(), request.getLobbyID());
            }
        }
    }


    /**
     * Handles all rounds, call calcGame() for every round and send messages to update the view
     * using help of a scheduler to give some delay to display
     *
     * This method is called after all players are ready
     *
     *  TODO: maybe we could separate this into GameService and GameManagement so this class contains no logic
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

        while (game.getProgramStep() < 5) {
            List<Position> previousPositions = new ArrayList<>();
            LOG.debug("Status of game BEFORE calcGameRound");
            for(AbstractPlayer player: game.getPlayers()){
                Position pos = player.getRobot().getPosition();
                previousPositions.add(pos);
                LOG.debug("     Robot Position {} pos = x {} y {}",
                        ((Player)player).getUser().getUsername(),
                        pos.x, pos.y);
            }
            LOG.debug("Showing chosen cards for round "+game.getProgramStep());
            Map<UserDTO, CardDTO> userDTOCardDTOMap = game.revealProgramCards();
            for(Map.Entry<UserDTO, CardDTO> userCurrentCard: userDTOCardDTOMap.entrySet())
                LOG.debug("Player " + userCurrentCard.getKey().getUsername() +
                        " card " + userCurrentCard.getValue().getID() + " - " + userCurrentCard.getValue().getPriority());

            // show cards and wait 1 second before moving
            scheduler.schedule(
                    new Runnable() {
                        public void run() {
                            lobbyService.sendToAllInLobby(lobbyID,
                                    new ShowAllPlayersCardsMessage(userDTOCardDTOMap, lobbyID));
                        }
                    },
                    secondsToWait, SECONDS);
            secondsToWait++;

            // TODO: FIX calcGame calculates wrong
            game.calcGameRound();

            LOG.debug("Status of game AFTER calcGameRound");
            int i = 0;
            for(AbstractPlayer player: game.getPlayers()) {
                UserDTO currentUser = ((Player) player).getUser();
                Position currentPos = player.getRobot().getPosition();
                Position previousPos = previousPositions.get(i);
                CardinalDirection direction = player.getRobot().getDirection();
                LOG.debug("     Robot Position {} pos = x {} y {}",
                        currentUser.getUsername(),
                        currentPos.x, currentPos.y);

                // ONLY DIRECTION CHANGED
                if (abs(currentPos.x - previousPos.x) + abs(currentPos.y - previousPos.y) == 0) {
                    LOG.debug("ROTATING");
                    scheduler.schedule(
                            new Runnable() {
                                public void run() {
                                    lobbyService.sendToAllInLobby(lobbyID,
                                            new ShowRobotMovingMessage(lobbyID, currentUser, currentPos, direction));
                                }
                            },
                            secondsToWait, SECONDS);
                    secondsToWait = secondsToWait + 2;
                }
                // DISPLAY IN SINGLE STEP
                else if (abs(currentPos.x - previousPos.x) + abs(currentPos.y - previousPos.y) >= 1) {
                    LOG.debug("MOVING FROM x={} y={} to x={} y={}",
                            previousPos.x, previousPos.y, currentPos.x, currentPos.y);
                    scheduler.schedule(
                            new Runnable() {
                                public void run() {
                                    lobbyService.sendToAllInLobby(lobbyID,
                                            new ShowRobotMovingMessage(lobbyID, currentUser, currentPos, direction));
                                }
                            },
                            secondsToWait, SECONDS);
                    secondsToWait = secondsToWait + 2;
                }
            }
            // go to next step
            game.increaseProgramStep();
        }

        game.roundIsOver(); // reset variables
        scheduler.schedule(
                new Runnable(){
                    public void run(){
                        lobbyService.sendToAllInLobby(lobbyID, new RoundIsOverMessage(lobbyID));
                    }
                },
                secondsToWait, SECONDS);

    }

}
