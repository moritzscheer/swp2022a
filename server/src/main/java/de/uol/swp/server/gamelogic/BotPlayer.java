package de.uol.swp.server.gamelogic;

import de.uol.swp.common.game.Position;
import de.uol.swp.common.game.enums.CardinalDirection;
import de.uol.swp.common.user.UserDTO;
import de.uol.swp.server.gamelogic.cards.Card;

/**
 * @author Maria Eduarda Costa Leite Andrade
 * @see de.uol.swp.server.gamelogic.cards.Card
 * @see de.uol.swp.server.gamelogic.Robot
 * @since 2023-03-28
 */
public class BotPlayer extends AbstractPlayer {

    public BotPlayer(Position startPosition, int robotID) {
        super();
        this.userDTO = new UserDTO("Bot"+ robotID, "password", "someEmail" );

        this.robot = new Robot(robotID, startPosition, true, CardinalDirection.East);
    }

    // TODO

}
