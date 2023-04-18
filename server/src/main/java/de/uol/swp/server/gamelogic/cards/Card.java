package de.uol.swp.server.gamelogic.cards;

import de.uol.swp.server.gamelogic.Robot;

/**
 * @author Tommy & WKempel
 * @see
 * @since 2023-04-03
 */
public class Card extends CardBehaviour {

    private int id;
    private CardBehaviour behaviour;
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
}
