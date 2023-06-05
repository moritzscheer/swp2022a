package de.uol.swp.common.game.message;

import de.uol.swp.common.game.Position;
import de.uol.swp.common.game.dto.PlayerDTO;
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

    private final PlayerDTO playerDTO;


    public ShowRobotMovingMessage(int lobbyID,
                                  PlayerDTO playerDTO) {
        this.lobbyID = lobbyID;
        System.out.println("In ShowRobotMovingMessage, user: "+playerDTO.getUser().getUsername());
        this.playerDTO = playerDTO;
    }

    public int getLobbyID() {
        return lobbyID;
    }

    public PlayerDTO getPlayerDTO() {
        return playerDTO;
    }
}
