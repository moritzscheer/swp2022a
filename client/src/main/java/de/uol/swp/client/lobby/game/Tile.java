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

    private double imageSize;

    /**
     * Default constructor
     *
     * @author Tommy Dang
     * @since 2023-03-19
     */
    public Tile(double imageSize){
        this.imageSize = imageSize;
    }

    public int rotateTileImage(int direction){
        if(direction == 1){
            return 90;
        }else if(direction == 2){
            return 180;
        }else if(direction == 3) {
            return 270;
        }
        return 0;
    }


    /**
     * Adds the LaserStart on the game board with location, imageSize and rotation
     *
     * @author Tommy Dang
     * @since 2023-03-18
     */
    public ImageView addTileLaserStart(int direction){
        ImageView laserStart = new ImageView(new Image("images/tiles/LaserStart.png", imageSize, imageSize, true, false));
        laserStart.setRotate(rotateTileImage(direction));
        return laserStart;
    }

    /**
     * Adds the LaserStart2 on the game board with location, imageSize and rotation
     *
     * @author Tommy Dang
     * @since 2023-03-19
     */
    public ImageView addTileLaserStart2(int direction){
        ImageView laserStart2 = new ImageView(new Image("images/tiles/LaserStart2.png", imageSize, imageSize, true, false));
        laserStart2.setRotate(rotateTileImage(direction));
        return laserStart2;
    }

    /**
     * Adds the LaserStart3 on the game board with location, imageSize and rotation
     *
     * @author Tommy Dang
     * @since 2023-03-19
     */
    public ImageView addTileLaserStart3(int direction){
        ImageView laserStart3 = new ImageView(new Image("images/tiles/LaserStart3.png", imageSize, imageSize, true, false));
        laserStart3.setRotate(rotateTileImage(direction));
        return laserStart3;
    }

    /**
     * Adds the Laser on the game board with location, imageSize and rotation
     *
     * @author Tommy Dang
     * @since 2023-03-19
     */
    public ImageView addTileLaser(int direction){
        ImageView laser = new ImageView(new Image("images/tiles/laser.png", imageSize, imageSize, true, false));
        laser.setRotate(rotateTileImage(direction));
        return laser;
    }

    /**
     * Adds the Laser2 on the game board with location, imageSize and rotation
     *
     * @author Tommy Dang
     * @since 2023-03-18
     */
    public ImageView addTileLaser2(int direction){
        ImageView laser2 = new ImageView(new Image("images/tiles/laser2.png", imageSize, imageSize, true, false));
        laser2.setRotate(rotateTileImage(direction));
        return laser2;
    }

    /**
     * Adds the Laser3 on the game board with location, imageSize and rotation
     *
     * @author Tommy Dang
     * @since 2023-03-19
     */
    public ImageView addTileLaser3(int direction){
        ImageView laser3 = new ImageView(new Image("images/tiles/laser3.png", imageSize, imageSize, true, false));
        laser3.setRotate(rotateTileImage(direction));
        return laser3;
    }

    /**
     * Adds the LaserHalf on the game board with location, imageSize and rotation
     *
     * @author Tommy Dang
     * @since 2023-03-19
     */
    public ImageView addTileLaserHalf(int direction){
        ImageView laserHalf = new ImageView(new Image("images/tiles/laserHalf.png", imageSize, imageSize, true, false));
        laserHalf.setRotate(rotateTileImage(direction));
        return laserHalf;
    }

    /**
     * Adds the LaserHalf2 on the game board with location, imageSize and rotation
     *
     * @author Tommy Dang
     * @since 2023-03-19
     */
    public ImageView addTileLaserHalf2(int direction){
        ImageView laserHalf2 = new ImageView(new Image("images/tiles/laserHalf2.png", imageSize, imageSize, true, false));
        laserHalf2.setRotate(rotateTileImage(direction));
        return laserHalf2;
    }

    /**
     * Adds the LaserHalf3 on the game board with location, imageSize and rotation
     *
     * @author Tommy Dang
     * @since 2023-03-19
     */
    public ImageView addTileLaserHalf3(int direction){
        ImageView laserHalf3 = new ImageView(new Image("images/tiles/laserHalf3.png", imageSize, imageSize, true, false));
        laserHalf3.setRotate(rotateTileImage(direction));
        return laserHalf3;
    }
}
