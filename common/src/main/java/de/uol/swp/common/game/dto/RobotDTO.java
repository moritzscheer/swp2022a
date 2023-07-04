package de.uol.swp.common.game.dto;

import de.uol.swp.common.game.Position;
import de.uol.swp.common.game.enums.CardinalDirection;

import java.io.Serializable;
import java.util.Objects;

/**
 * Object to transfer the information of a Robot
 *
 * <p>This object is used to communicate the current state of a robot between the server and
 * clients. It contains information about the
 *
 * @author Maria Eduarda Costa Leite Andrade
 * @since 2023-05-13
 */
public class RobotDTO implements Serializable {
    private int robotID;

    private Position position;

    private CardinalDirection direction;

    private int lifeToken;

    private int damageToken;

    private int lastCheckpoint;

    private boolean alive = true;

    private boolean powerDown = false;

    private boolean deadForever = false;

    public RobotDTO(int robotID, Position position, CardinalDirection direction) {
        this.robotID = robotID;
        this.position = position;
        this.direction = direction;
    }
    /**
     * Getter for the robotID
     *
     * @return returns the robotID as integer
     * @author Maria Eduarda Costa Leite Andrade
     * @since 2023-05-19
     */
    public int getRobotID() {
        return robotID;
    }
    /**
     * Setter for the robotID
     *
     * @param robotID integer
     * @author Maria Eduarda Costa Leite Andrade
     * @since 2023-05-19
     */
    public void setRobotID(int robotID) {
        this.robotID = robotID;
    }
    /**
     * Getter for the position
     *
     * @return returns the position of the robot
     * @author Maria Eduarda Costa Leite Andrade
     * @since 2023-05-13
     */
    public Position getPosition() {
        return position;
    }
    /**
     * Setter for the position
     *
     * @param position Position
     * @author Maria Eduarda Costa Leite Andrade
     * @since 2023-05-13
     */
    public void setPosition(Position position) {
        this.position = position;
    }
    /**
     * Getter for the direction of the robot
     *
     * @return returns the direction as CardinalDirection
     * @author Maria Eduarda Costa Leite Andrade
     * @since 2023-05-13
     */
    public CardinalDirection getDirection() {
        return direction;
    }
    /**
     * Setter for the direction of the robot
     *
     * @param direction CardinalDirection
     * @author Maria Eduarda Costa Leite Andrade
     * @since 2023-05-13
     */
    public void setDirection(CardinalDirection direction) {
        this.direction = direction;
    }
    /**
     * Getter for the life tokens
     *
     * @return integer value which represents the life tokens
     * @author Maria Eduarda Costa Leite Andrade
     * @since 2023-05-13
     */
    public int getLifeToken() {
        return lifeToken;
    }
    /**
     * Setter for the life token, throws IllegalArgumentException if lifeToken is negative
     *
     * @param lifeToken life token as integer
     * @author Maria Eduarda Costa Leite Andrade, Waldemar Kempel
     * @since 2023-05-13
     */
    public void setLifeToken(int lifeToken) {
        if (lifeToken < 0) {
            throw new IllegalArgumentException("Life token cannot be negative");
        }
        this.lifeToken = lifeToken;
    }
    /**
     * Getter for the damage token
     *
     * @return the damage token as integer
     * @author Maria Eduarda Costa Leite Andrade
     * @since 2023-05-13
     */
    public int getDamageToken() {
        return damageToken;
    }
    /**
     * Setter for damage token, throws IllegalArgumentException if damageToken is negative
     *
     * @param damageToken integer
     * @author Maria Eduarda Costa Leite Andrade, Waldemar Kempel
     * @since 2023-05-13
     */
    public void setDamageToken(int damageToken) {
        if (damageToken < 0) {
            throw new IllegalArgumentException("Damage token cannot be negative");
        }
        this.damageToken = damageToken;
    }
    /**
     * Getter for last checkpoint of the robot
     *
     * @return the last checkpoint as integer
     * @author Maria Eduarda Costa Leite Andrade
     * @since 2023-05-13
     */
    public int getLastCheckpoint() {
        return lastCheckpoint;
    }
    /**
     * Setter for the last checkpoint
     *
     * @param lastCheckpoint integer
     * @author Maria Eduarda Costa Leite Andrade
     * @since 2023-05-13
     */
    public void setLastCheckpoint(int lastCheckpoint) {
        this.lastCheckpoint = lastCheckpoint;
    }
    /**
     * Getter for the robot life status
     *
     * @return the life state as boolean (true = alive, false = dead)
     * @author Maria Eduarda Costa Leite Andrade
     * @since 2023-05-31
     */
    public boolean isAlive() {
        return alive;
    }
    /**
     * Setter for the robot life status
     *
     * @param alive boolean
     * @author Maria Eduarda Costa Leite Andrade
     * @since 2023-05-31
     */
    public void setAlive(boolean alive) {
        this.alive = alive;
    }
    /**
     * Getter for the robot's power status
     *
     * @return the power status as boolean (true = powered down, false = powered on)
     * @author Maria Eduarda Costa Leite Andrade
     * @since 2023-06-13
     */
    public boolean isPowerDown() {
        return powerDown;
    }
    /**
     * Setter for the robot's power status
     *
     * @param powerDown boolean
     * @author Maria Eduarda Costa Leite Andrade
     * @since 2023-05-13
     */
    public void setPowerDown(boolean powerDown) {
        this.powerDown = powerDown;
    }
    /**
     * Override of the equals method to compare the objects attributes
     *
     * @param o Object
     * @author Finn Oldeboershuis, Ole Zimmermann
     * @since 2023-06-19
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RobotDTO robotDTO = (RobotDTO) o;
        return robotID == robotDTO.robotID
                && lifeToken == robotDTO.lifeToken
                && damageToken == robotDTO.damageToken
                && lastCheckpoint == robotDTO.lastCheckpoint
                && alive == robotDTO.alive
                && position.equals(robotDTO.position)
                && direction == robotDTO.direction;
    }
    /**
     * Override of the hashCode method
     *
     * @return the Objects hashCode
     * @author Maria Eduarda Costa Leite Andrade
     * @since 2023-05-13
     */
    @Override
    public int hashCode() {
        return Objects.hash(
                robotID, position, direction, lifeToken, damageToken, lastCheckpoint, alive);
    }
    /**
     * Getter for the dead forever status of the robot
     *
     * @return boolean true = is dead forever, false = is not dead forever
     * @author Maria Eduarda Costa Leite Andrade
     * @since 2023-06-22
     */
    public boolean isDeadForever() {
        return deadForever;
    }
    /**
     * Setter for the dead forever status of the robot
     *
     * @param deadForever boolean
     * @author Maria Eduarda Costa Leite Andrade
     * @since 2023-06-22
     */
    public void setDeadForever(boolean deadForever) {
        this.deadForever = deadForever;
    }
}
