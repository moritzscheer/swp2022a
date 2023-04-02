package de.uol.swp.server.gamelogic.cards;

/**
 * @author Tommy Dang
 * @since 2023-04-02
 */
abstract class CardBehaviour {

    /**
     * @author Tommy Dang
     * @since 2023-04-02
     */
    public int move() {
        return 0;
    }

    /**
     * @author Tommy Dang
     * @since 2023-04-02
     */
    public Direction turn() {
        return null;
    }

    /**
     * @author Tommy Dang
     * @since 2023-04-02
     */
    public boolean uTurn() {
        return false;
    }
}
