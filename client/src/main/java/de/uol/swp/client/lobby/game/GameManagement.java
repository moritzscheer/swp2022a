package de.uol.swp.client.lobby.game;

import com.google.common.eventbus.Subscribe;
import de.uol.swp.common.game.message.GetMapDataMessage;
import de.uol.swp.common.game.response.ProgramCardDataResponse;
import de.uol.swp.common.user.User;
import de.uol.swp.common.user.response.LoginSuccessfulResponse;

import java.util.HashMap;
import java.util.Map;

public class GameManagement {
    private User loggedInUser;
    private final Map<Integer, Game> gameMap = new HashMap<>(); // TODO: initialize the Game with IDs, maybe GameDTO

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
     * Handles GetMapDataMessage
     *
     * @param msg the GetMapDataMessage object seen on the EventBus
     * @see de.uol.swp.common.game.message.GetMapDataMessage
     * @author Maria Andrade
     * @since 2023-05-06
     */
    @Subscribe
    public void onGetMapDataMessage(GetMapDataMessage msg){
        msg.getBoardImageIds(); // TODO: handle board
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
}
