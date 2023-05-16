package de.uol.swp.server.gamelogic;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import de.uol.swp.common.game.Position;
import de.uol.swp.common.game.dto.BlockDTO;
import de.uol.swp.common.game.dto.GameDTO;
import de.uol.swp.common.game.dto.PlayerDTO;
import de.uol.swp.common.game.dto.RobotDTO;
import de.uol.swp.common.game.enums.CardinalDirection;
import de.uol.swp.common.game.message.GetMapDataResponse;
import de.uol.swp.common.game.message.StartGameMessage;
import de.uol.swp.common.game.request.GetMapDataRequest;
import de.uol.swp.common.game.request.GetProgramCardsRequest;
import de.uol.swp.common.game.request.StartGameRequest;
import de.uol.swp.common.lobby.dto.LobbyDTO;
import de.uol.swp.server.AbstractService;
import de.uol.swp.server.lobby.LobbyManagement;
import de.uol.swp.server.lobby.LobbyService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

import static de.uol.swp.server.utils.ConvertToDTOUtils.convertRobotToRobotDTO;

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
        Position[] dockings = {
                new Position(1, 2),
                new Position(1, 3),
                new Position(1, 4),
                new Position(1, 5),
                new Position(1, 6),
                new Position(2, 3),
                new Position(2, 2),
                new Position(2, 4)
        };
        System.out.println("dockings :)");
        Optional<LobbyDTO> lobby = lobbyManagement.getLobby(lobbyID);
        if(!lobby.isPresent()){
            System.out.println("GameService: lobby not found");
        }

        // create and save Game Object
        games.put(
                lobbyID,
                new Game(lobbyID,
                        dockings,
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
//        Optional<Game> game = getGame();
//        if(!game.isEmpty()) {
//            game.get().distributeProgramCards();
//            // TODO: here it is called the function that give cards to all players,
//            // then one must send a response to each player individually with their cards
//
//        }
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
            BlockDTO[][] boardDTOs= new BlockDTO[board.length][board[0].length];

            for(int row= 0; row< board.length; row++){
                for(int col=0; col < board[0].length; col++){
                    boardDTOs[row][col] = new BlockDTO(board[row][col].getImages());
                }
            }
            GetMapDataResponse getMapDataResponse = new GetMapDataResponse(
                    boardDTOs, msg.getLobby());
            getMapDataResponse.initWithMessage(msg);
            post(getMapDataResponse);
        }
        else {
            //TODO: send ErrorResponse
        }
    }

}
