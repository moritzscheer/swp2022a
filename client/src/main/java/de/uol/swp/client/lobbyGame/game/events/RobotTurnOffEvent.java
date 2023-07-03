package de.uol.swp.client.lobbyGame.game.events;

import de.uol.swp.common.user.UserDTO;

/**
 * Event used to turn off a player robot
 *
 * @author Maria Andrade
 * @since 2023-06-19
 */
public class RobotTurnOffEvent {

    private final int lobbyID;
    private final UserDTO loggedInUser;

    /**
     * constructor
     *
     * @author Maria Eduarda Costa Leite Andrade
     * @since 2023-06-19
     */
    public RobotTurnOffEvent(int lobbyID, UserDTO loggedInUser) {
        this.lobbyID = lobbyID;
        this.loggedInUser = loggedInUser;
    }

    public int getLobbyID() {
        return lobbyID;
    }

    public UserDTO getLoggedInUser() {
        return loggedInUser;
    }
}
