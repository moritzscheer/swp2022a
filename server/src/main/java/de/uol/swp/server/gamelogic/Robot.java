package de.uol.swp.server.gamelogic;

import de.uol.swp.common.game.Position;
import de.uol.swp.common.game.enums.CardinalDirection;

import java.io.Serializable;

/**
 * Manages the robot
 *
 * @author Maria Eduarda Costa Leite Andrade
 * @since 06-02-2023
 */
public class Robot implements Serializable {

    private int id;
    private Position currentPosition;
    private int damageToken;
    private CardinalDirection direction;
    private int lastCheckPoint;
    private Position lastBackupCopyPosition;
    private int lifeToken;
    private boolean powerDown;

    private int optionCard;
    private boolean deadForTheRound;
    private boolean deadForever;

    /**
     * Constructor
     *
     * @author Maria Eduarda Costa Leite Andrade
     * @since 2023-06-12
     */
    public Robot(int id, Position currentPosition, CardinalDirection direction) {
        this.id = id;
        this.currentPosition = currentPosition;
        this.damageToken = 0;
        this.direction = direction;
        this.lifeToken = 3;
        this.powerDown = false;
        this.optionCard = 0;
        this.lastCheckPoint = 1;
        this.lastBackupCopyPosition = currentPosition;
        this.deadForTheRound = false;
        this.deadForever = false;
    }

    /**
     * @author Maria Eduarda Costa Leite Andrade
     * @see
     * @since 2023-06-12
     */
    public void move(CardinalDirection directionToMove) {
        currentPosition = Position.translate(currentPosition, directionToMove);
    }

    /**
     * @author Maria Eduarda Costa Leite Andrade
     * @since 2023-06-12
     */
    public void move(Position targetCoords) {}

    /**
     * @author Maria Eduarda Costa Leite Andrade
     * @since 2023-06-12
     */
    public boolean isAlive() {
        return !isDeadForever() && !isDeadForTheRound();
    }

    /**
     * @author Maria Eduarda Costa Leite Andrade
     * @since 2023-06-12
     */
    public Position getPosition() {
        return this.currentPosition;
    }

    /**
     * @author Maria Eduarda Costa Leite Andrade
     * @since 2023-06-12
     */
    public int getID() {
        return id;
    }

    /**
     * @author Maria Eduarda Costa Leite Andrade
     * @since 2023-06-12
     */
    public CardinalDirection setDirection(CardinalDirection dir) {
        this.direction = dir;
        return this.direction;
    }

    /**
     * @author Maria Eduarda Costa Leite Andrade
     * @since 2023-06-12
     */
    public CardinalDirection getDirection() {
        return this.direction;
    }

    /**
     * @author Maria Eduarda Costa Leite Andrade
     * @since 2023-06-12
     */
    public void setAlive(boolean alivee) {
        if (!alivee) {
            // robot is dead
            setLifeToken(getLifeToken() - 1);
            setDamageToken(0);
            // setCurrentPosition(this.lastBackupCopyPosition);
            setDeadForTheRound(true);
        } else {
            if (this.lifeToken > 0) {
                setDeadForTheRound(false);
            } else {
                // robot is dead forever
                setDeadForever();
                setDeadForTheRound(true);
            }
        }
    }

    /**
     * @author Maria Eduarda Costa Leite Andrade
     * @since 2023-06-12
     */
    public void setCurrentPosition(Position currentPosition) {
        this.currentPosition = currentPosition;
    }

    /**
     * @author Maria Eduarda Costa Leite Andrade
     * @since 2023-06-12
     */
    public void setLastCheckPoint(int checkPointNumber) {
        this.lastCheckPoint = checkPointNumber;
    }

    /**
     * @author Maria Eduarda Costa Leite Andrade
     * @since 2023-06-12
     */
    public int getLastCheckPoint() {
        return this.lastCheckPoint;
    }

    /**
     * @author Maria Eduarda Costa Leite Andrade
     * @since 2023-06-12
     */
    public Position getLastBackupCopyPosition() {
        return this.lastBackupCopyPosition;
    }

    /**
     * @author Maria Eduarda Costa Leite Andrade
     * @since 2023-06-12
     */
    public void setLastBackupCopyPosition(Position pos) {
        this.lastBackupCopyPosition = pos;
    }

    /**
     * Robots reentering the race receive 2 Damage tokens (plus any Damage tokens taken while
     * powered down).
     *
     * @author Maria Eduarda Costa Leite Andrade
     * @since 2023-02-26
     */
    public void setDamageByReenteringRace() {
        this.damageToken += 2;
    }

    /**
     * @author Maria Eduarda Costa Leite Andrade
     * @since 2023-02-26
     */
    public int getDamageToken() {
        return this.damageToken;
    }

    /**
     * @author Maria Eduarda Costa Leite Andrade
     * @since 2023-02-26
     */
    public void setDamageToken(int damageToken) {
        this.damageToken = damageToken;
        if (damageToken >= 10) {
            setAlive(false);
        }
    }

    /**
     * Robots discard 1 Damage token in a checkPoint or repair block.
     *
     * @author Maria Eduarda Costa Leite Andrade
     * @since 2023-06-12
     */
    public void fixDamageToken() {
        if (this.damageToken > 0) {
            this.damageToken--;
        }
    }

    public int getOptionCard() {
        return this.optionCard;
    }

    /**
     * Getter for the robot Life Token
     *
     * @author Maria Eduarda Costa Leite Andrade
     * @since 2023-05-14
     */
    public int getLifeToken() {
        return lifeToken;
    }

    /**
     * Setter for the robot Life Token
     *
     * @author Maria Eduarda Costa Leite Andrade
     * @since 2023-05-14
     */
    public void setLifeToken(int lifeToken) {
        if (lifeToken > 0) this.lifeToken = lifeToken;
        else if (lifeToken == 0) {
            setDeadForever();
            this.lifeToken = lifeToken;
        }
    }

    /**
     * Getter for the robot PowerDown
     *
     * @author Maria Eduarda Costa Leite Andrade
     * @since 2023-05-14
     */
    public boolean isPowerDown() {
        return powerDown;
    }

    /**
     * Setter for the robot PowerDown
     *
     * @author Maria Eduarda Costa Leite Andrade
     * @since 2023-05-14
     */
    public void setPowerDown(boolean powerDown) {
        this.powerDown = powerDown;
    }

    /**
     * @author Maria Eduarda Costa Leite Andrade
     * @since 2023-05-14
     */
    public boolean isDeadForTheRound() {
        return deadForTheRound;
    }

    /**
     * @author Maria Eduarda Costa Leite Andrade
     * @since 2023-05-14
     */
    public void setDeadForTheRound(boolean deadForTheRound) {
        this.deadForTheRound = deadForTheRound;
    }

    /**
     * @author Maria Eduarda Costa Leite Andrade
     * @since 2023-05-14
     */
    public boolean isDeadForever() {
        return deadForever;
    }

    /**
     * @author Maria Eduarda Costa Leite Andrade
     * @since 2023-05-14
     */
    public void setDeadForever() {
        this.deadForever = true;
    }
}
