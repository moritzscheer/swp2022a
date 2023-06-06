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
    private boolean backupCopy;
    private int lastCheckPoint;
    private Position lastCheckPointPosition;
    private int lifeToken;
    private boolean powerDown;

    private boolean a;

    private boolean b;

    private int optionCard;
    private int lockedRegisters;

    /**
     * Constructor
     *
     * @author Maria Eduarda Costa Leite Andrade
     * @since 06-02-2023
     */
    public Robot(int id, Position currentPosition, boolean alive, CardinalDirection direction) {
        this.id = id;
        this.currentPosition = currentPosition;
        this.alive = alive;
        this.damageToken = 0;
        this.direction = direction;
        this.lifeToken = 3;
        this.powerDown = false;
        this.optionCard = 0;
        this.lastCheckPoint = 1;
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
    public void move(CardinalDirection directionToMove) {
        currentPosition = Position.translate(currentPosition, directionToMove);
    }

    public void move(Position targetCoords) {
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

    public boolean isAlive() {
        return this.alive;
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

    public void setAlive(boolean alive) {
        this.alive = alive;
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

    public boolean getBackupCopy() {
        return this.backupCopy;
    }

    public void setBackupCopy(boolean backupCopy) {
        this.backupCopy = backupCopy;
    }

    public Position getLastCheckPointPosition() {
        return this.lastCheckPointPosition;
    }

    public void setLastCheckPointPosition(Position pos) {
        this.lastCheckPointPosition = pos;
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
    }

    /**
     * Robots on a crossed wrench/hammer space discard 1 Damage token AND draw one Option card.
     *
     * @author Maria Eduarda Costa Leite Andrade
     * @since 2023-02-26
     */
    public void drawOptionCard() {
        this.optionCard += 1;
    }

    public int getOptionCard() {
        return this.optionCard;
    }

    /**
     * Getter for the robot Image Path
     *
     * @author Maria Eduarda Costa Leite Andrade
     * @since 2023-05-13
     */
    public String getImgPath() {
        return imgPath;
    }

    /**
     * Setter for the robot Image Path
     *
     * @author Maria Eduarda Costa Leite Andrade
     * @since 2023-05-13
     */
    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
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
        this.lifeToken = lifeToken;
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
}
