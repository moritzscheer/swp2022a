package de.uol.swp.client.lobbyGame.game;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import de.uol.swp.client.lobbyGame.LobbyGameManagement;
import de.uol.swp.client.lobbyGame.game.events.RequestDistributeCardsEvent;
import de.uol.swp.client.lobbyGame.game.events.RequestStartGameEvent;
import de.uol.swp.client.lobbyGame.game.events.RobotTurnOffEvent;
import de.uol.swp.client.lobbyGame.game.events.SubmitCardsEvent;
import de.uol.swp.common.chat.message.TextHistoryMessage;
import de.uol.swp.common.game.dto.CardDTO;
import de.uol.swp.common.game.message.*;
import de.uol.swp.common.game.request.*;
import de.uol.swp.common.game.response.ProgramCardDataResponse;

import de.uol.swp.common.game.response.StartGameResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Classes that manages game
 *
 * @author Maria
 * @since 2023-05-06
 */
@SuppressWarnings("UnstableApiUsage")
@Singleton
public class GameService {
    private final EventBus eventBus;
    private static final Logger LOG = LogManager.getLogger(GameService.class);

    /**
     * Constructor
     *
     * @param eventBus The EventBus set in ClientModule
     * @see de.uol.swp.client.di.ClientModule
     * @since 2023-05-06
     */
    @Inject
    public GameService(EventBus eventBus) {
        this.eventBus = eventBus;
        this.eventBus.register(this);
    }

    ///////////////
    // Requests
    ///////////////

    /**
     * Posts a request to start the game on the EventBus
     *
     * @param event To identify the lobby with a unique key
     * @see StartGameRequest
     * @author Moritz Scheer
     * @since 2023-03-09
     */
    @Subscribe
    public void onRequestStartGameEvent(RequestStartGameEvent event) {
        LOG.debug("Starting Game");
        StartGameRequest startGameRequest =
                new StartGameRequest(
                        event.getLobbyDTO(), event.getNumberBots(), event.getNumberCheckpoints());
        eventBus.post(startGameRequest);
    }

    /**
     * Get cards 9-5 Cards for each player
     *
     * @param event RequestDistributeCardsEvent
     * @author Maria Andrade
     * @since 2023-05-06
     */
    @Subscribe
    public void onRequestDistributeCardsEvent(RequestDistributeCardsEvent event) {
        LOG.debug("Requesting to distribute cards");
        eventBus.post(
                new GetProgramCardsRequest(event.getLobby().getLobbyID(), event.getLoggedInUser()));
    }

    /**
     * Send chosen cards of player
     *
     * @param event RequestDistributeCardsEvent
     * @author Maria Andrade
     * @since 2023-05-06
     */
    @Subscribe
    public void onSubmitCardsEvent(SubmitCardsEvent event) {
        LOG.debug("Submitting cards from" + event.getLoggedInUser().getUsername());
        eventBus.post(
                new SubmitCardsRequest(
                        event.getLobbyID(), event.getLoggedInUser(), event.getCardDTOS()));
    }

    /**
     * Turn robot off
     *
     * @param event RobotTurnOffEvent
     * @author Maria Andrade
     * @since 2023-06-19
     */
    @Subscribe
    public void onRobotTurnOffEvent(RobotTurnOffEvent event) {
        LOG.debug("Turn Robot off from" + event.getLoggedInUser().getUsername());
        eventBus.post(
                new TurnRobotOffRequest(
                        event.getLobbyID(), event.getLoggedInUser()));
    }

    /////////////////////
    // Responses/Messages
    /////////////////////

    /**
     * Handles StartGameMessage detected on the EventBus
     *
     * <p>If a StartGameMessage is detected on the EventBus, this method gets called.
     *
     * @param msg The StartGameMessage detected on the EventBus
     * @see StartGameResponse
     * @author Moritz Scheer & Maxim Erden & Maria Eduarda
     * @since 2023-02-28
     */
    @Subscribe
    public void onStartGameMessage(StartGameResponse msg) {
        LOG.debug("onStartGameMessage");
        LobbyGameManagement.getInstance().startGame(msg);
    }

    @Subscribe
    public void onProgramCardDataResponse(ProgramCardDataResponse msg) {
        LOG.debug("onProgramCardDataResponse");
        LOG.debug("Showing received cards");
        for (CardDTO cardDTO : msg.getAssignedProgramCards()) {
            LOG.debug("   id={} priority={}", cardDTO.getID(), cardDTO.getPriority());
        }
        LobbyGameManagement.getInstance().showCardsToUser(msg);
    }

    @Subscribe
    public void onRobotTurnedOffMessage(RobotTurnedOffMessage msg) {
        LOG.debug("Player {} is turned off for the round", msg.getTurnedOffUser().getUsername());
        LobbyGameManagement.getInstance().sendMessageTurnedOffRobot(msg);
    }

    @Subscribe
    public void onPlayerIsReadyMessage(PlayerIsReadyMessage msg) {
        LOG.debug("Player {} is ready", msg.getPlayerIsReady().getUsername());
        LobbyGameManagement.getInstance().sendMessagePlayerIsReady(msg);
    }

    @Subscribe
    public void onShowAllPlayersCardsMessage(ShowAllPlayersCardsMessage msg) {
        LOG.debug("All players have chosen cards");
        LobbyGameManagement.getInstance().sendMessageAllPlayersAreReady(msg);
    }

    @Subscribe
    public void onShowRobotMovingMessage(ShowRobotMovingMessage msg) {
        LOG.debug("Updating view, robot moving - " + msg.getPlayerDTO().getUser().getUsername());
        LobbyGameManagement.getInstance().sendMessageRobotIsMoving(msg);
}

    @Subscribe
    public void onHistoryMessage(TextHistoryMessage msg) {
        LOG.debug("Updating history - " + msg.getMessage());
        LobbyGameManagement.getInstance().updateHistory(msg);
    }

    @Subscribe
    public void onShowBoardMovingMessage(ShowBoardMovingMessage msg) {
        LOG.debug("Updating view, board moving ");
        LobbyGameManagement.getInstance().sendMessageBoardIsMoving(msg);
    }

    @Subscribe
    public void onRoundIsOverMessage(RoundIsOverMessage msg) {
        LOG.debug("Restarting rounds");
        LobbyGameManagement.getInstance().restartRounds(msg);
    }

    /**
     * Handles RobotIsFinallyDead detected on the EventBus
     *
     * <p>If a RobotIsFinallyDead is detected on the EventBus, this method gets called.
     *
     * @param msg The RobotIsFinallyDead detected on the EventBus
     * @see de.uol.swp.common.game.message.RobotIsFinallyDead
     * @author Maria Eduarda
     * @since 2023-06-09
     */
    @Subscribe
    public void onRobotIsFinallyDead(RobotIsFinallyDead msg) {
        LOG.debug(msg.getUserDied() + " died forever!!!");
        LobbyGameManagement.getInstance().setRobotDied(msg);
    }

    /**
     * Handles GameOverMessage detected on the EventBus
     *
     * <p>If a GameOverMessage is detected on the EventBus, this method gets called.
     *
     * @param msg The GameOverMessage detected on the EventBus
     * @see de.uol.swp.common.game.message.GameOverMessage
     * @author Daniel Merzo & Maria Eduarda
     * @since 2023-05-24
     */
    @Subscribe
    public void onGameOverMessage(GameOverMessage msg) {
        LOG.debug(msg.getUserWon() + "won the game");
        LobbyGameManagement.getInstance().gameOver(msg);
    }
}
