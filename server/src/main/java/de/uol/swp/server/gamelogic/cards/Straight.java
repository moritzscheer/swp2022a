package de.uol.swp.server.gamelogic.cards;

/**
 * @author
 * @see
 * @since
 */
public class Straight extends CardBehaviour {

    private int moves;

    /**
     * @author
     * @see
     * @since
     */
    @Override
    public int move() {
        return moves + 1;
    }
}
