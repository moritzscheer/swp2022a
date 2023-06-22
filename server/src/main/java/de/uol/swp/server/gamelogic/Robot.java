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
    private String imgPath;
    private Position currentPosition;
    private boolean alive;
    private int damageToken;
    private CardinalDirection direction;
    private int lastCheckPoint;
    private Position lastBackupCopyPosition;
    private int lifeToken;
    private boolean powerDown;

    private boolean a;

    private boolean b;

    private int optionCard;
    private int lockedRegisters;
    private boolean deadForTheRound;
    private boolean deadForever;

    /**
     * Constructor
     *
     * @author Maria Eduarda Costa Leite Andrade
     * @since 06-02-2023
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
    public void move(CardinalDirection directionToMove) {
        currentPosition = Position.translate(currentPosition, directionToMove);
    }

    public boolean isAlive() {
        return !isDeadForever() && !isDeadForTheRound();
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

    public void setAlive(boolean alivee) {
        if (!alivee) {
            // robot is dead
            setLifeToken(getLifeToken()-1);
            //setCurrentPosition(this.lastBackupCopyPosition);
            setDeadForTheRound(true);
        } else {
            if (this.lifeToken > 0) {
                setDeadForTheRound(false);
            }
            else {
                // robot is dead forever
                setDeadForever();
                setDeadForTheRound(true);
            }

        }
    }

    public void setCurrentPosition(Position currentPosition) {
        this.currentPosition = currentPosition;
    }

    public void setLastCheckPoint(int checkPointNumber) {
        this.lastCheckPoint = checkPointNumber;
    }

    public int getLastCheckPoint() {
        return this.lastCheckPoint;
    }

    public Position getLastBackupCopyPosition() {
        return this.lastBackupCopyPosition;
    }

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

    public int getDamageToken() {
        return this.damageToken;
    }

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
        if(lifeToken > 0)
            this.lifeToken = lifeToken;
        else if (lifeToken == 0) {
            setDeadForever();
            this.lifeToken = lifeToken;
        }
        return;
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

    public boolean isDeadForTheRound() {
        return deadForTheRound;
    }

    public void setDeadForTheRound(boolean deadForTheRound) {
        this.deadForTheRound = deadForTheRound;
    }

    public boolean isDeadForever() {
        return deadForever;
    }

    public void setDeadForever() {
        this.deadForever = true;
    }
}
