package de.uol.swp.server.gamelogic;

import de.uol.swp.common.user.User;
import de.uol.swp.server.gamelogic.tiles.enums.CardinalDirection;


/**
 * @author Maria Eduarda Costa Leite Andrade
 * @see de.uol.swp.server.gamelogic.cards.Card
 * @see de.uol.swp.server.gamelogic.Robot
 * @since 2023-03-28
 */
public class Player extends AbstractPlayer {
    protected User user;

    /**
     * Constructor
     *
     * @author Maria Eduarda Costa Leite Andrade
     * @see de.uol.swp.server.gamelogic.cards.Card
     * @see de.uol.swp.server.gamelogic.Robot
     * @since 2023-03-28
     */
    public Player(User user) {
        super();
        this.user = user;
        // TODO: we might need to change the robot constructor
        this.robot = new Robot("", new Position(1,1), true, CardinalDirection.East);
    }

    // TODO

}
