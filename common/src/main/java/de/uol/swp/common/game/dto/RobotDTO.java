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

    public RobotDTO(int robotID, Position position, CardinalDirection direction) {
        this.robotID = robotID;
        this.position = position;
        this.direction = direction;
    }

    public int getRobotID() {
        return robotID;
    }

    public void setRobotID(int robotID) {
        this.robotID = robotID;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public CardinalDirection getDirection() {
        return direction;
    }

    public void setDirection(CardinalDirection direction) {
        this.direction = direction;
    }

    public int getLifeToken() {
        return lifeToken;
    }

    public void setLifeToken(int lifeToken) {
        if (lifeToken < 0) {
            throw new IllegalArgumentException("Life token cannot be negative");
        }
        this.lifeToken = lifeToken;
    }

    public int getDamageToken() {
        return damageToken;
    }

    public void setDamageToken(int damageToken) {
        if (damageToken < 0) {
            throw new IllegalArgumentException("Damage token cannot be negative");
        }
        this.damageToken = damageToken;
    }

    public int getLastCheckpoint() {
        return lastCheckpoint;
    }

    public void setLastCheckpoint(int lastCheckpoint) {
        this.lastCheckpoint = lastCheckpoint;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public boolean isPowerDown() {
        return powerDown;
    }

    public void setPowerDown(boolean powerDown) {
        this.powerDown = powerDown;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RobotDTO robotDTO = (RobotDTO) o;
        return robotID == robotDTO.robotID && lifeToken == robotDTO.lifeToken && damageToken == robotDTO.damageToken && lastCheckpoint == robotDTO.lastCheckpoint && alive == robotDTO.alive && position.equals(robotDTO.position) && direction == robotDTO.direction;
    }

    @Override
    public int hashCode() {
        return Objects.hash(robotID, position, direction, lifeToken, damageToken, lastCheckpoint, alive);
    }
}
