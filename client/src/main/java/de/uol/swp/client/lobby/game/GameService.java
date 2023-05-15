package de.uol.swp.client.lobby.game;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import de.uol.swp.client.lobby.LobbyGameManagement;
import de.uol.swp.client.lobby.LobbyManagement;
import de.uol.swp.client.lobby.game.events.RequestMapDataEvent;
import de.uol.swp.client.lobby.game.presenter.GamePresenter;
import de.uol.swp.common.game.message.GetMapDataResponse;
import de.uol.swp.common.game.message.StartGameMessage;
import de.uol.swp.common.game.request.GetMapDataRequest;
import de.uol.swp.common.game.request.GetProgramCardsRequest;
import de.uol.swp.common.game.request.StartGameRequest;
import de.uol.swp.common.lobby.dto.LobbyDTO;
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
     * @param eventBus        The EventBus set in ClientModule
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
     * @param lobby To identify the lobby with a unique key
     * @see StartGameRequest
     * @author Moritz Scheer
     * @since 2023-03-09
     */
    public void startGame(LobbyDTO lobby) {
        LOG.debug("Starting Game");
        StartGameRequest startGameRequest = new StartGameRequest(lobby);
        eventBus.post(startGameRequest);
    }

    @Subscribe
    public void onRequestMapDataEvent(RequestMapDataEvent event) {
        LOG.debug("Getting Map");
        eventBus.post(new GetMapDataRequest(event.getLobbyDTO()));
    }

    /** Get cards 9-5 Cards for each player
     *
     * One single request, but several responses one to each player
     * One single request, so all players get the cards at the same time
     *
     * @param gameID
     * @author Maria Andrade
     * @since 2023-05-06
     */
    public void getProgramCardsForPlayers(Integer gameID) {
        LOG.debug("Getting Cards");
        GetProgramCardsRequest getProgramCardsRequest =
                new GetProgramCardsRequest(gameID);
        eventBus.post(getProgramCardsRequest);
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
     * @see de.uol.swp.common.game.message.StartGameMessage
     * @author Moritz Scheer & Maxim Erden & Maria Eduarda
     * @since 2023-02-28
     */
    @Subscribe
    public void onStartGameMessage(StartGameMessage msg){
        LOG.debug("onStartGameMessage");
        LobbyManagement.getInstance().startGame(msg);
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
    public void onGetMapDataResponse(GetMapDataResponse msg){
        LOG.debug("onGetMapDataResponse");
        LobbyManagement.getInstance().reloadMapData(msg);
    }
}
