package de.uol.swp.client.lobby.game;

import com.google.common.eventbus.EventBus;
import com.google.inject.Inject;
import de.uol.swp.common.game.request.GetProgramCardsRequest;

/**
 * Classes that manages game
 *
 * @author Maria
 * @since 2023-05-06
 */
@SuppressWarnings("UnstableApiUsage")
public class GameService {
    private final EventBus eventBus;

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
        GetProgramCardsRequest getProgramCardsRequest =
                new GetProgramCardsRequest(gameID);
        eventBus.post(getProgramCardsRequest);
        System.out.println("Getting Cards");
    }
}
