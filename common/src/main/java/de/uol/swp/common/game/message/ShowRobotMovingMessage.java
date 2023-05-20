package de.uol.swp.common.game.message;

import de.uol.swp.common.game.Position;
import de.uol.swp.common.game.enums.CardinalDirection;
import de.uol.swp.common.lobby.message.AbstractLobbyMessage;
import de.uol.swp.common.user.UserDTO;


/**
 * Message sent by the server when there is an update of the robot position
 *
 * @see AbstractLobbyMessage
 * @author Maria Eduarda Costa Leite Andrade
 * @since 2022-05-20
 */
public class ShowRobotMovingMessage extends AbstractLobbyMessage {
    private final int lobbyID;
    private final UserDTO userDTO;
    private final Position newRobotPosition;
    private final CardinalDirection newDirection;


    public ShowRobotMovingMessage(int lobbyID, UserDTO userDTO, Position newRobotPosition, CardinalDirection newDirection) {
        this.lobbyID = lobbyID;
        System.out.println("In ShowRobotMovingMessage, user: "+userDTO.getUsername());
        this.userDTO = userDTO;
        this.newRobotPosition = newRobotPosition;
        this.newDirection = newDirection;
    }

    public int getLobbyID() {
        return lobbyID;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public Position getNewRobotPosition() {
        return newRobotPosition;
    }

    public CardinalDirection getNewDirection() {
        return newDirection;
    }

}
