package de.uol.swp.server.gamelogic.cards;

import de.uol.swp.server.gamelogic.Robot;

/**
 * @author Tommy & WKempel
 * @see
 * @since 2023-04-03
 */
public class Card {

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
     * @author Maria
     * @see
     * @since 2023-05-06
     */
    public Card(int id, String behaviourType, int priority, String imgPath) throws Exception {
        this.id = id;
        this.priority = priority;
        this.imgPath = imgPath;

        switch (behaviourType){
            case "1":
                this.behaviour = new Turn(Direction.Left);
            case "3":
                this.behaviour = new Turn(Direction.Right);
            case "4":
                this.behaviour = new UTurn();
            case "8":
                this.behaviour = new Straight(-1);
            case "6":
                this.behaviour = new Straight(1);
            case "7":
                this.behaviour = new Straight(2);
            case "5":
                this.behaviour = new Straight(3);
            default:
                throw new Exception("Behaviour not found.");
        }

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

    /**
     * Get card priority
     *
     * @author Finn Oldeboershuis
     * @since 2023-04-24
     */
    public int getPriority() {
        return priority;
    }
}
