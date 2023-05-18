package de.uol.swp.common.game.dto;

import de.uol.swp.common.game.Position;
import de.uol.swp.common.game.enums.CardinalDirection;

import java.io.Serializable;

/**
 * Object to transfer the information of a Robot
 *
 * This object is used to communicate the current state of a robot between the server and
 * clients. It contains information about the
 *
 * @author Maria Eduarda Costa Leite Andrade
 * @since 2023-05-13
 */
public class RobotDTO implements Serializable {
    private String imgPath; // todo: change to id

    private Position position;

    private CardinalDirection direction;

    private int lifeToken;

    private int damageToken;

    private int lastCheckpoint;

    public RobotDTO(String imgPath, Position position, CardinalDirection direction){
        this.imgPath = imgPath;
        this.position = position;
        this.direction = direction;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
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
        this.lifeToken = lifeToken;
    }

    public int getDamageToken() {
        return damageToken;
    }

    public void setDamageToken(int damageToken) {
        this.damageToken = damageToken;
    }

    public int getLastCheckpoint() {
        return lastCheckpoint;
    }

    public void setLastCheckpoint(int lastCheckpoint) {
        this.lastCheckpoint = lastCheckpoint;
    }
}
