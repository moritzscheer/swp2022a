package de.uol.swp.common.game.request;

import de.uol.swp.common.lobby.request.AbstractLobbyRequest;
import de.uol.swp.common.user.UserDTO;

/**
 * Request sent to the server when the user requests to turn off the robot
 *
 * @see de.uol.swp.common.message.AbstractRequestMessage
 * @author Maria Eduarda Costa Leite Andrade
 * @since 2023-06-13
 */
public class TurnRobotOffRequest extends AbstractLobbyRequest {
    private final int lobbyID;
    private final UserDTO loggedInUser;
    /**
     * Constructor
     *
     * @param lobbyID integer of the lobby
     * @param loggedInUser UserDTO of the user who is logged in
     * @author Waldemar Kempel, Maria Andrade
     * @since 2023-06-13
     */
    public TurnRobotOffRequest(Integer lobbyID, UserDTO loggedInUser) {
        this.lobbyID = lobbyID;
        this.loggedInUser = loggedInUser;
    }
    /**
     * Getter for the lobby id
     *
     * @return lobby id as integer
     * @author Maria Andrade
     * @since 2023-06-13
     */
    public int getLobbyID() {
        return lobbyID;
    }
    /**
     * Getter for the logged in user
     *
     * @return UserDTO of the user who is logged in
     * @author Maria Andrade
     * @since 2023-06-13
     */
    public UserDTO getLoggedInUser() {
        return loggedInUser;
    }
}
