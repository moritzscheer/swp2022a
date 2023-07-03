package de.uol.swp.server.gamelogic;

import static java.util.concurrent.TimeUnit.SECONDS;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;

import de.uol.swp.common.exception.LobbyDoesNotExistException;
import de.uol.swp.common.game.dto.CardDTO;
import de.uol.swp.common.game.dto.GameDTO;
import de.uol.swp.common.game.message.*;
import de.uol.swp.common.game.request.*;
import de.uol.swp.common.game.response.ProgramCardDataResponse;
import de.uol.swp.common.lobby.dto.*;
import de.uol.swp.common.lobby.message.AbstractLobbyMessage;
import de.uol.swp.common.user.User;
import de.uol.swp.common.user.UserDTO;
import de.uol.swp.server.AbstractService;
import de.uol.swp.server.lobby.LobbyManagement;
import de.uol.swp.server.lobby.LobbyService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.javatuples.Pair;

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
    private final GameManagement gameManagement;

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    /**
     * Constructor
     *
     * @param bus the EvenBus used throughout the server
     * @param gameManagement
     * @since 2023-05-06
     */
    @Inject
    public GameService(
            EventBus bus,
            GameManagement gameManagement,
            LobbyManagement lobbyManagement,
            LobbyService lobbyService) {
        super(bus);
        this.gameManagement = gameManagement;
        this.lobbyManagement = lobbyManagement;
        this.lobbyService = lobbyService;
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
        Optional<LobbyDTO> lobby = lobbyManagement.getLobby(msg.getLobbyID());
        if (lobby.isPresent()) {
            Set<User> users = lobby.get().getUsers();
            GameDTO game =
                    gameManagement.createNewGame(
                            msg.getLobbyID(),
                            msg.getLobby().getMapName(),
                            msg.getNumberBots(),
                            msg.getNumberCheckpoints(),
                            users);
            lobbyService.sendToAllInLobby(
                    msg.getLobbyID(), new StartGameMessage(msg.getLobbyID(), msg.getLobby(), game));
        } else {
            throw new LobbyDoesNotExistException(
                    "Lobby Not Found! ID: " + msg.getLobbyID(), msg.getLobbyID());
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
     * @see de.uol.swp.common.game.request.GetProgramCardsRequest
     * @see de.uol.swp.common.game.response.ProgramCardDataResponse
     * @since 2023-02-28
     */
    @Subscribe
    public void onGetProgramCardsRequest(GetProgramCardsRequest msg)
            throws InterruptedException, LobbyDoesNotExistException {
        LOG.debug("onGetProgramCardsRequest");
        boolean callBot = gameManagement.getProgramCards(msg.getLobbyID());
        // get loggedInUser
        UserDTO user = msg.getLoggedInUser();
        ProgramCardDataResponse response =
                new ProgramCardDataResponse(
                        gameManagement.getPlayerReceivedCards(msg.getLobbyID(), user),
                        9 - gameManagement.getPlayerDamageTokens(msg.getLobbyID(), user),
                        msg.getLobbyID());
        response.initWithMessage(msg);
        post(response);
        gameManagement.printReceivedCards(msg.getLobbyID(), msg.getLoggedInUser());

        if (callBot) {
            selectCardBot(msg.getLobbyID());
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
        Optional<Game> game = gameManagement.getGame(request.getLobbyID());
        if (game.isPresent()) {
            boolean allChosen = game.get().setPowerDown(request.getLoggedInUser());
            lobbyService.sendToAllInLobby(
                    request.getLobbyID(),
                    new RobotTurnedOffMessage(request.getLobbyID(), request.getLoggedInUser()));

            if (allChosen) {
                manageGameUpdate(request.getLobbyID());
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
        Optional<Game> game = gameManagement.getGame(request.getLobbyID());
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
                manageGameUpdate(request.getLobbyID());
            }
        }
    }

    /**
     * Select the cards from the bots and send a message to all players that the bots are ready
     *
     * @param lobbyID lobbyID to which the game belongs
     * @author Waldemar Kempel, Maria Andrade
     * @since 2023-06-13
     */
    private void selectCardBot(int lobbyID)
            throws InterruptedException, LobbyDoesNotExistException {
        Pair<Boolean, List<AbstractLobbyMessage>> pair = gameManagement.selectCardBot(lobbyID);
        boolean allChosen = pair.getValue0();
        List<AbstractLobbyMessage> msgs = pair.getValue1();
        for (AbstractLobbyMessage msg : msgs) {
            lobbyService.sendToAllInLobby(lobbyID, msg);
        }
        if (allChosen) manageGameUpdate(lobbyID);
    }

    /**
     * Handles all rounds, call calcGame() for every round and send messages to update the view
     * using help of a scheduler to give some delay to display
     *
     * @param lobbyID lobbyID to which the game belongs
     * @author Finn Oldeboershuis, Maria Andrade
     * @since 2023-06-13
     */
    public void manageGameUpdate(int lobbyID) {
        List<Pair<Integer, AbstractLobbyMessage>> pairs = gameManagement.manageGameUpdate(lobbyID);

        for (Pair<Integer, AbstractLobbyMessage> pair : pairs) {
            scheduler.schedule(
                    () -> {
                        try {
                            lobbyService.sendToAllInLobby(lobbyID, pair.getValue1());
                        } catch (LobbyDoesNotExistException e) {
                            throw new RuntimeException(e);
                        }
                    },
                    pair.getValue0(),
                    SECONDS);
        }
    }
}
