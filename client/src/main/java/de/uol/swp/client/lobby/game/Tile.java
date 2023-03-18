package de.uol.swp.client.lobby.game;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Manages the all Tiles on the GameBoard
 *
 * @author Tommy Dang
 * @since 2023-03-12
 */
public class Tile {

    private int boardSize;
    private double imageSize;




    public Tile(int boardSize, double imageSize){
        this.boardSize = boardSize;
        this.imageSize = imageSize;
    }

    /**
     * Adds the LaserStart on the game board with location, imageSize and rotation
     *
     * @author Tommy Dang
     * @since 2023-03-18
     */
    public ImageView addTileLaserStart(int direction){
        ImageView laserStart = new ImageView(new Image("images/tiles/LaserStart.png", imageSize, imageSize, true, false));

        if(direction == 0){
            laserStart.setRotate(0);
        }else if(direction == 1){
            laserStart.setRotate(90);
        }else if(direction == 2){
            laserStart.setRotate(180);
        }else if(direction == 3){
            laserStart.setRotate(270);
        }
        return laserStart;
    }

    /**
     * Adds the Laser on the game board with location, imageSize and rotation
     *
     * @author Tommy Dang
     * @since 2023-03-18
     */
    public ImageView addTileLaser(int direction){
        ImageView laser = new ImageView(new Image("images/tiles/laser.png", imageSize, imageSize, true, false));

        if(direction == 0){
            laser.setRotate(0);
        }else if(direction == 1){
            laser.setRotate(90);
        }else if(direction == 2){
            laser.setRotate(180);
        }else if(direction == 3){
            laser.setRotate(270);
        }
        return laser;
    }

}
