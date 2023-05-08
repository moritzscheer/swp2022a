package de.uol.swp.server.gamelogic;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import de.uol.swp.common.game.request.GetProgramCardsRequest;
import de.uol.swp.server.AbstractService;
import de.uol.swp.server.lobby.LobbyManagement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Handles the game requests send by the users
 *
 * @author Maria
 * @since 2023-05-06
 */
public class GameService extends AbstractService {

    private static final Logger LOG = LogManager.getLogger(GameService.class);
    private final LobbyManagement lobbyManagement;


    private final Map<Integer, Game> games = new HashMap<>();

    /**
     * Constructor
     *
     * @param bus the EvenBus used throughout the server
     * @since 2019-10-08
     */
    @Inject
    public GameService(EventBus bus, LobbyManagement lobbyManagement) {
        super(bus);
        this.lobbyManagement = lobbyManagement;
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
    public Integer createNewGame(Integer lobbyID) {
        System.out.println("I am creating your game :)");
        Integer gameID = 1;
        while (games.containsKey(lobbyID)) {
            gameID++;
        }
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
        games.put(
                gameID,
                new Game(lobbyID,
                        MapBuilder.getMap("maps/tempMap.map"),
                        dockings,
                        lobbyManagement.getLobby(lobbyID).get().getUsers()
                )
        );
        System.out.println("New Game :)");
        return gameID;
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
        Optional<Game> game = getGame(msg.getGameID());
        if(!game.isEmpty()) {
            game.get().distributeProgramCards();
            // TODO: here it is called the function that give cards to all players,
            // then one must send a response to each player individually with their cards

        }
    }

}
