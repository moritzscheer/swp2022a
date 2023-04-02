package de.uol.swp.server.gamelogic.cards;

/**
 * @author
 * @see
 * @since
 */
public class Turn extends CardBehaviour {

    private Direction direction;

    /**
     * @author
     * @see
     * @since
     */
    @Override
    public Direction turn() {
        direction = Direction.Left;
        return direction;
    }
}
