package de.uol.swp.common.game.dto;

import de.uol.swp.common.game.Position;

/**
 * Object to transfer the information of a Robot
 *
 * This object is used to communicate the current state of a robot between the server and
 * clients. It contains information about the
 *
 * @author Maria Eduarda Costa Leite Andrade
 * @since 2023-05-13
 */
public class RobotDTO {
    private String imgPath;

    private Position position;

    public RobotDTO(String imgPath, Position position){
        this.imgPath = imgPath;
        this.position = position;
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
}
