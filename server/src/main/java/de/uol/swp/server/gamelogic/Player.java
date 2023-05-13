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
    private User user;

    /**
     * Constructor
     *
     * @author Maria Eduarda Costa Leite Andrade
     * @see de.uol.swp.server.gamelogic.cards.Card
     * @see de.uol.swp.server.gamelogic.Robot
     * @since 2023-03-28
     */
    public Player(User user, Position startPosition) {
        super();
        this.user = user;
        // TODO: we might need to change the robot constructor
        this.robot = new Robot("", startPosition, true, CardinalDirection.East);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
