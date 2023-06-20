package de.uol.swp.common.game.message;

import de.uol.swp.common.lobby.message.AbstractLobbyMessage;
import de.uol.swp.common.user.UserDTO;

/**
 * Message sent by the server when a robot is dead forever
 *
 * @see AbstractLobbyMessage
 * @author Maria Eduarda Costa Leite Andrade
 * @since 2023-06-09
 */
public class RobotIsFinallyDead extends AbstractLobbyMessage {
    private final int lobbyID;
    private final UserDTO userDied;

    /**
     * Constructor
     *
     * @param lobbyID Integer containing the ID of the lobby
     * @param userDied UserDTO object
     * @author Maria Andrade
     * @since 2023-06-09
     */
    public RobotIsFinallyDead(int lobbyID, UserDTO userDied) {
        this.lobbyID = lobbyID;
        this.userDied = userDied;
    }
    /**
     * Getter for the lobbyID variable
     *
     * @return Integer containing the Lobby ID
     * @author Maria Andrade
     * @since 2023-06-09
     */
    public int getLobbyID() {
        if (lobbyID < 0) {
            throw new IllegalArgumentException("Invalid lobbyID: " + lobbyID);
        }
        return lobbyID;
    }
    /**
     * Getter for the robotDied variable
     *
     * @return RobotDTO containing the robot which has died
     * @author @author Maria Andrade
     * @since 2023-06-09
     */
    public UserDTO getUserDied() {
        return userDied;
    }
}
