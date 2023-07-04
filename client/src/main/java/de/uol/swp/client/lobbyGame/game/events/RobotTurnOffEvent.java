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

    /**
     * Getter Method for Lobby ID
     *
     * @author Maria Andrade
     * @return the lobbyID
     * @since 2023-06-19
     */
    public int getLobbyID() {
        return lobbyID;
    }

    /**
     * Getter Method for the current logged-in user
     *
     * @author Maria Andrade
     * @return the current logged-in user
     * @since 2023-06-19
     */
    public UserDTO getLoggedInUser() {
        return loggedInUser;
    }
}
