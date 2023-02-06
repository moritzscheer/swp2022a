package de.uol.swp.server.gamelogic;

import de.uol.swp.server.gamelogic.cards.Card;
import de.uol.swp.server.gamelogic.tiles.enums.CardinalDirection;

/**
 * Manages the robot
 *
 * @author Maria Eduarda Costa Leite Andrade
 * @since 06-02-2023
 */
public class Robot {

    private int id;
    private String imgPath;
    private Position currentPosition;
    private boolean alive;
    private int damageToken;
    private CardinalDirection direction;
    private boolean backupCopy;
    private int lastCheckPoint;
    private Position lastCheckPointPosition;
    private Position lastRepairSite;
    private int lifePoints;
    private boolean powerDown;

    // TODO: In my opinion this has to be moved to a new class named "Player" ~Finn
    private Card[] cards; // max 9 cards
    private int[] registerCardIDs; // max 5 cards
    private int optionCard;
    private int lockedRegisters;

    /**
     * Constructor
     *
     * @author Maria Eduarda Costa Leite Andrade
     * @since 06-02-2023
     */
    public Robot(
            String imgPath,
            Position currentPosition,
            boolean alive,
            int damageToken,
            CardinalDirection direction,
            boolean backupCopy,
            int lastCheckPoint,
            Position lastCheckPointPosition,
            Position lastRepairSite,
            int lifePoints,
            boolean powerDown) {
        this.imgPath = imgPath;
        this.currentPosition = currentPosition;
        this.alive = alive;
        this.damageToken = damageToken;
        this.direction = direction;
        this.backupCopy = backupCopy;
        this.lastCheckPoint = lastCheckPoint;
        this.lastCheckPointPosition = lastCheckPointPosition;
        this.lastRepairSite = lastRepairSite;
        this.lifePoints = lifePoints;
        this.powerDown = powerDown;
    }

    /**
     * @author
     * @see
     * @since
     */
    public boolean registerReady() {
        // TODO
        return false;
    }

    /**
     * @author
     * @see
     * @since
     */
    public void fireLaser() {
        // TODO
    }

    /**
     * @author
     * @see
     * @since
     */
    public Card[] revealCard(int programStep) {
        // TODO
        return this.cards;
    }

    /**
     * @author
     * @see
     * @since
     */
    public void move(Card[] cards[]) {
        // TODO
    }

    /**
     * @author
     * @see
     * @since
     */
    public void setPowerDown() {
        // TODO
    }

    /**
     * @author
     * @see
     * @since
     */
    public int lockRegister() {
        // TODO
        return 0;
    }

    public Position getPosition() {
        return this.currentPosition;
    }

    public int getID() {
        return id;
    }

    public CardinalDirection setDirection(CardinalDirection dir) {
        this.direction = dir;
        return this.direction;
    }

    public CardinalDirection getDirection() {
        return this.direction;
    }
}
