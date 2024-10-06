package de.uol.swp.server.gamelogic.cards;

import de.uol.swp.server.gamelogic.Robot;

/**
 * @author Tommy & WKempel
 * @since 2023-04-03
 */
public class Card {

    private final int id;
    private CardBehaviour behaviour;
    private int priority;
    private String behaviourType;

    /**
     * Constructor
     *
     * @author WKempel
     * @see de.uol.swp.server.gamelogic.cards.CardBehaviour
     * @since 2023-04-03
     */
    public Card(int id, CardBehaviour behaviour, int priority) {
        this.id = id;
        this.behaviour = behaviour;
        this.priority = priority;
    }

    /**
     * Constructor for when Player is powered down
     *
     * @author Maria
     * @since 2023-06-19
     */
    public Card(int id) {
        this.id = id;
    }

    /**
     * Constructor for Card with behaviourType which identify with the ID of the card the behaviour
     *
     * @author Maria
     * @since 2023-05-06
     */
    public Card(int id, String behaviourType, int priority) throws Exception {
        this.id = id;
        this.priority = priority;
        this.behaviourType = behaviourType;

        switch (behaviourType) {
            case "1":
                this.behaviour = new Turn(Direction.Left);
                break;
            case "3":
                this.behaviour = new Turn(Direction.Right);
                break;
            case "4":
                this.behaviour = new UTurn();
                break;
            case "8":
                this.behaviour = new Straight(-1);
                break;
            case "6":
                this.behaviour = new Straight(1);
                break;
            case "7":
                this.behaviour = new Straight(2);
                break;
            case "5":
                this.behaviour = new Straight(3);
                break;
            default:
                throw new Exception("Behaviour not found.");
        }
    }

    /**
     * The method execute the behaviour of the card for the robot
     *
     * @author WKempel
     * @see de.uol.swp.server.gamelogic.Robot
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

    /**
     * Get card id
     *
     * @author Maria
     * @since 2023-05-18
     */
    public int getId() {
        return id;
    }

    /**
     * Get behaviour type
     *
     * @author Maria
     * @since 2023-06-23
     */
    public String getBehaviourType() {
        return behaviourType;
    }
}
