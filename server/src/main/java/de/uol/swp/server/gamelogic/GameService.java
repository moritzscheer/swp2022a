package de.uol.swp.server.gamelogic;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import de.uol.swp.common.chat.message.TextHistoryMessage;
import de.uol.swp.common.exception.LobbyDoesNotExistException;
import de.uol.swp.common.game.Position;
import de.uol.swp.common.game.dto.BlockDTO;
import de.uol.swp.common.game.dto.CardDTO;
import de.uol.swp.common.game.dto.GameDTO;
import de.uol.swp.common.game.dto.PlayerDTO;
import de.uol.swp.common.game.message.*;
import de.uol.swp.common.game.request.*;
import de.uol.swp.common.game.response.ProgramCardDataResponse;
import de.uol.swp.common.lobby.dto.*;
import de.uol.swp.common.lobby.message.AbstractLobbyMessage;
import de.uol.swp.common.user.UserDTO;
import de.uol.swp.server.AbstractService;
import de.uol.swp.server.gamelogic.cards.Card;
import de.uol.swp.server.gamelogic.moves.GameMovement;
import de.uol.swp.server.lobby.LobbyManagement;
import de.uol.swp.server.lobby.LobbyService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import static de.uol.swp.server.utils.ConvertToDTOUtils.*;
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

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

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
     * <p>If a StartGameRequest is detected on the EventBus, this method is called. It creates a
     * Game object linked to the lobbyID, which will be used to retrieve information about the game
     *
     * @author Maria Eduarda Costa Leite Andrade
     * @see de.uol.swp.common.game.request.StartGameRequest
     * @see de.uol.swp.common.game.message.StartGameMessage
     * @since 2023-02-28
     */
    public GameDTO createNewGame(int lobbyID, String mapName, int numberBots, int checkpointCount) {
        System.out.println("I am creating your game :)");

        System.out.println("New id :)");
        System.out.println("dockings :)");
        Optional<LobbyDTO> lobby = lobbyManagement.getLobby(lobbyID);
        if (!lobby.isPresent()) {
            System.out.println("GameService: lobby not found");
        }

        // create and save Game Object
        games.put(
                lobbyID,
                new Game(lobbyID, lobby.get().getUsers(), mapName, numberBots, checkpointCount));

        // Create DTOs objects
        List<PlayerDTO> players = new ArrayList<>();
        for (AbstractPlayer player : games.get(lobbyID).getPlayers()) {
            // add in the list
            players.add(convertPlayerToPlayerDTO(player));
        }
        BlockDTO[][] boardDTO = convertBoardToBoardDTO(games.get(lobbyID).getBoard());
        GameDTO gameDTO = new GameDTO(players, boardDTO);

        gamesDTO.put(lobbyID, gameDTO); // save reference to the GameDTO

        LOG.debug("Number of players including Bots: " + players.size());
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
     * <p>PS.: GetMapDataRequest/Response was removed, and now the board is sent together with
     * startGameMessage inside the gameDTO object (2023-06-18)
     *
     * @param msg StartGameRequest found on the EventBus
     * @author Moritz Scheer, Maria Eduarda Costa Leite Andrade, WKempel, Jann
     * @see de.uol.swp.common.game.request.StartGameRequest
     * @see de.uol.swp.common.game.message.StartGameMessage
     * @since 2023-02-28
     */
    @Subscribe
    public void onStartGameRequest(StartGameRequest msg) throws LobbyDoesNotExistException {
        Optional<LobbyDTO> tmp = lobbyManagement.getLobby(msg.getLobbyID());
        if (!tmp.isEmpty()) {
            System.out.println("Creating game");
            GameDTO game =
                    createNewGame(
                            msg.getLobbyID(),
                            msg.getLobby().getMapName(),
                            msg.getNumberBots(),
                            msg.getNumberCheckpoints());
            System.out.println("Sending Message to all in Lobby");
            lobbyService.sendToAllInLobby(
                    msg.getLobbyID(), new StartGameMessage(msg.getLobbyID(), msg.getLobby(), game));
        } else {
            // TODO: send ErrorResponse
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
    public void onGetProgramCardsRequest(GetProgramCardsRequest msg)
            throws InterruptedException, LobbyDoesNotExistException {
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
            if (callBot) {
                selectCardBot(game.get(), msg.getLobbyID());
            }
        }
    }

    /**
     * Handles TurnRobotOffRequest found on the EventBus
     *
     * <p>If a TurnRobotOffRequest is detected on the EventBus, this method is called. It posts a
     * RobotTurnedOffMessage to all the users in the lobby, saying that this player is ready
     *
     * @param request SubmitCardsRequest found on the EventBus
     * @author Maria Eduarda Costa Leite Andrade
     * @see de.uol.swp.common.game.request.TurnRobotOffRequest
     * @since 2023-06-13
     */
    @Subscribe
    public void onTurnRobotOffRequest(TurnRobotOffRequest request)
            throws LobbyDoesNotExistException, InterruptedException {
        Optional<Game> game = getGame(request.getLobbyID());
        if (game.isPresent()) {
            boolean allChosen = game.get().setPowerDown(request.getLoggedInUser());
            lobbyService.sendToAllInLobby(
                    request.getLobbyID(),
                    new RobotTurnedOffMessage(request.getLobbyID(), request.getLoggedInUser()));

            if (allChosen) {
                for (AbstractPlayer botPlayer : game.get().getPlayers()) {
                    if (botPlayer instanceof BotPlayer) {
                        PlayerIsReadyMessage msg = new PlayerIsReadyMessage(botPlayer.getUser(), request.getLobbyID());
                        lobbyService.sendToAllInLobby(request.getLobbyID(), msg);
                    }
                }
                manageGameUpdate(game.get(), request.getLobbyID());
            }
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
    public void onSubmitCardsRequest(SubmitCardsRequest request)
            throws InterruptedException, LobbyDoesNotExistException {
        Optional<Game> game = getGame(request.getLobbyID());
        if (game.isPresent()) {
            // TODO
            LOG.debug("IN SERVER: RECEIVED CARDS from " + request.getLoggedInUser().getUsername());
            for (CardDTO card : request.getCardDTOs())
                LOG.debug(card.getID() + " -  " + card.getPriority());

            Boolean allChosen =
                    game.get()
                            .registerCardsFromUser(
                                    request.getLoggedInUser(), request.getCardDTOs());
            PlayerIsReadyMessage msg =
                    new PlayerIsReadyMessage(request.getLoggedInUser(), request.getLobbyID());
            lobbyService.sendToAllInLobby(request.getLobbyID(), msg);

            if (allChosen) {
                manageGameUpdate(game.get(), request.getLobbyID());
            }
        }
    }

    private void selectCardBot(Game game, int lobbyID)
            throws InterruptedException, LobbyDoesNotExistException {
        Boolean allChosen = game.registerCardsFromBot();
        for (AbstractPlayer botPlayer : game.getPlayers()) {
            if (botPlayer instanceof BotPlayer) {
                PlayerIsReadyMessage msg = new PlayerIsReadyMessage(botPlayer.getUser(), lobbyID);
                lobbyService.sendToAllInLobby(lobbyID, msg);
            }
        }

        if (allChosen) {
            LOG.debug("All players have chosen cards");
            Map<UserDTO, CardDTO> userDTOCardDTOMap = game.revealProgramCards();
            for (Map.Entry<UserDTO, CardDTO> userCurrentCard : userDTOCardDTOMap.entrySet())
                LOG.debug(
                        "Player "
                                + userCurrentCard.getKey().getUsername()
                                + " card "
                                + userCurrentCard.getValue().getID()
                                + " - "
                                + userCurrentCard.getValue().getPriority());
            lobbyService.sendToAllInLobby(
                    lobbyID, new ShowAllPlayersCardsMessage(userDTOCardDTOMap, lobbyID));
            manageGameUpdate(game, lobbyID);
        }
    }

    /**
     * Handles all rounds, call calcGame() for every round and send messages to update the view
     * using help of a scheduler to give some delay to display
     * <p>
     * TODO: Remove scheduler and pack everything into a single Method and time the animation on the client Side
     *
     * @param game    reference to which game
     * @param lobbyID lobbyID to which the game belongs
     * @author Finn Oldeboershuis, Maria Andrade
     * @since 2023-06-13
     */
    public void manageGameUpdate(Game game, int lobbyID) {

        int secondsToWait = 1;
        boolean gameOver = false;

        try {
            lobbyService.sendToAllInLobby(
                    lobbyID,
                    new TextHistoryMessage(lobbyID, "======= Round: " + game.getRoundNumber() + " ======= \n"));
        } catch (LobbyDoesNotExistException e) {
            throw new RuntimeException(e);
        }

        for (int i = 0; i < 5; i++) {
            game.calcAllGameRound();
            List<GameMovement> gameMovements = game.getGameMovements();

            String programStep = "" + i;
            scheduler.schedule(() -> {
                try {
                    lobbyService.sendToAllInLobby(lobbyID, new TextHistoryMessage(lobbyID, "==== Program Step: " + programStep + " ==== \n"));
                } catch (LobbyDoesNotExistException e) {
                    throw new RuntimeException(e);
                }
            }, secondsToWait, SECONDS);

            //moveList = filterMoveList(moveList);

            Map<UserDTO, CardDTO> userDTOCardDTOMap = game.revealProgramCards();

            scheduler.schedule(() -> {
                try {
                    lobbyService.sendToAllInLobby(lobbyID,
                            new ShowAllPlayersCardsMessage(userDTOCardDTOMap, lobbyID));
                } catch (LobbyDoesNotExistException e) {
                    throw new RuntimeException(e);
                }
            }, secondsToWait, SECONDS);

            for (GameMovement gameMovement: gameMovements) {
                List<PlayerDTO> moves = gameMovement.getRobotsPositionsInOneMove();

                // just to speed up when there are no moves
                if(gameMovement.isSomeoneMoved() || gameMovement.isCardMove())
                    secondsToWait += 1;

                scheduler.schedule(() -> {
                    try {
                        lobbyService.sendToAllInLobby(lobbyID,
                                new ShowBoardMovingMessage(lobbyID, moves));
                        lobbyService.sendToAllInLobby(lobbyID,
                                new TextHistoryMessage(lobbyID, gameMovement.getMoveMessage()));
                    } catch (LobbyDoesNotExistException e) {
                        throw new RuntimeException(e);
                    }
                }, secondsToWait, SECONDS);
            }
            gameOver = isGameOver(lobbyID, game, secondsToWait+2);
            if (gameOver) {
                break;
            }

            game.increaseProgramStep();
        }
        secondsToWait += 1;
        // if game was over, message was sent already
        if(!gameOver){
            UserDTO winner = game.roundIsOver(); // reset variables
            AbstractLobbyMessage msg;
            if (Objects.equals(winner, null))
                msg = new RoundIsOverMessage(lobbyID, game.getRespawnRobots());
            else
                msg = new GameOverMessage(lobbyID, winner);
            scheduler.schedule(
                    () -> {
                        try {
                            lobbyService.sendToAllInLobby(lobbyID, msg);
                        } catch (LobbyDoesNotExistException e) {
                            throw new RuntimeException(e);
                        }
                    },
                    secondsToWait,
                    SECONDS);
        }

    }

    /**
     * Check if a Player achived the last checkpoint
     *
     * @param game    reference to which game
     * @param lobbyID lobbyID to which the game belongs
     * @author Maria Eduarda Costa Leite Andrade
     * @see de.uol.swp.common.game.request.SubmitCardsRequest
     * @since 2023-06-05
     */
    private boolean isGameOver(int lobbyID, Game game, int secondsToWait) {
        for (AbstractPlayer player : game.getPlayers()) {
            if (player.getRobot().getLastCheckPoint() == game.getLastCheckPoint()) {
                playerWonTheGame(lobbyID, player.getUser(), secondsToWait);
                return true;
            }
        }
        UserDTO survivor = new UserDTO("Nobody", "", "");
        int countSurvivors = 0;
        for (AbstractPlayer player : game.getPlayers()) {
            if (!player.getRobot().isDeadForever()) {
                countSurvivors++;
                survivor = player.getUser();
            }
        }
        if (countSurvivors <= 1) {
            // gameover
            playerWonTheGame(lobbyID, survivor, secondsToWait);
            return true;
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
                        try {
                            lobbyService.sendToAllInLobby(
                                    lobbyID, new GameOverMessage(lobbyID, userWon));
                        } catch (LobbyDoesNotExistException e) {
                            throw new RuntimeException(e);
                        }
                    }
                },
                secondsToWait,
                SECONDS);
    }
}
