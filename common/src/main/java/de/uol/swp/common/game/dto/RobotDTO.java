package de.uol.swp.common.game.dto;

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

    private int posX;

    private int poxY;

    public RobotDTO(String imgPath){
        this.imgPath = imgPath;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPoxY() {
        return poxY;
    }

    public void setPoxY(int poxY) {
        this.poxY = poxY;
    }
}
