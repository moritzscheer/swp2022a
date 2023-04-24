package de.uol.swp.server.gamelogic.cards;

import de.uol.swp.server.gamelogic.Robot;

/**
 * @author Tommy & WKempel
 * @see
 * @since 2023-04-03
 */
public class Card extends CardBehaviour {

    private int id;
    private final CardBehaviour behaviour;
    private int priority;
    private String imgPath;

    /**
     * @author WKempel
     * @see
     * @since 2023-04-03
     */
    public Card(int id, CardBehaviour behaviour, int priority, String imgPath) {
        this.id = id;
        this.behaviour = behaviour;
        this.priority = priority;
        this.imgPath = imgPath;
    }

    /**
     * @author WKempel
     * @see
     * @since 2023-04-03
     */
    public void executeBehaviour(Robot robot) {
        behaviour.execute(robot);
    }

    /**
     * Get direction, from card behaviour
     *
     * @author Maria
     * @since 2023-04-23
     */
    public Direction getDirectionCard() {
        return behaviour.getDirectionCard();
    }

    /**
     * Get how many moves, from card behaviour
     *
     * @author Maria
     * @since 2023-04-23
     */
    public int getMoves() {
        return behaviour.getMoves();
    }

    /**
     * Get uTurn, from card behaviour
     *
     * @author Maria
     * @since 2023-04-23
     */
    public boolean getUTurn() {
        return behaviour.getUTurn();
    }
}
