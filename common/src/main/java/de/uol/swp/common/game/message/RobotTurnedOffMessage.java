package de.uol.swp.common.game.message;

import de.uol.swp.common.lobby.message.AbstractLobbyMessage;
import de.uol.swp.common.user.UserDTO;

/**
 * Message sent by the server when a robot is turned off for the round
 *
 * @see AbstractLobbyMessage
 * @author Maria Eduarda Costa Leite Andrade
 * @since 2023-06-13
 */
public class RobotTurnedOffMessage extends AbstractLobbyMessage {
    private final int lobbyID;
    private final UserDTO turnedOffUser;

    public RobotTurnedOffMessage(int lobbyID, UserDTO turnedOffUser) {
        this.lobbyID = lobbyID;
        this.turnedOffUser = turnedOffUser;
    }

    public int getLobbyID() {
        return lobbyID;
    }

    public UserDTO getTurnedOffUser() {
        return turnedOffUser;
    }
}
