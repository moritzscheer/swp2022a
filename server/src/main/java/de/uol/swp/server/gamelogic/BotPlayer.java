package de.uol.swp.server.gamelogic;

import de.uol.swp.common.game.Position;
import de.uol.swp.common.game.enums.CardinalDirection;
import de.uol.swp.common.user.UserDTO;

/**
 * @author Maria Eduarda Costa Leite Andrade
 * @see de.uol.swp.server.gamelogic.cards.Card
 * @see de.uol.swp.server.gamelogic.Robot
 * @since 2023-03-28
 */
public class BotPlayer extends AbstractPlayer {

    /**
     * @author Maria
     * @since 2023-05-06
     */
    public BotPlayer(Position startPosition, int robotID) {
        super();
        int botNumber = robotID + 1;
        this.userDTO = new UserDTO("Bot" + botNumber, "password", "someEmail");

        this.robot = new Robot(robotID, startPosition, CardinalDirection.North);
    }
}
