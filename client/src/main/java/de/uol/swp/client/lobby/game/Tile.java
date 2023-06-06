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
    public Tile(double imageSize) {
        this.imageSize = imageSize;
    }

    public int rotateTileImage(int direction) {
        return 90 * direction;
    }

    /**
     * Adds the LaserStart on the game board with location, imageSize and rotation
     *
     * @author Tommy Dang
     * @since 2023-03-18
     */
    public ImageView addTileLaserStart(int direction) {
        ImageView laserStart =
                new ImageView(
                        new Image(
                                "images/tiles/laser/LaserStart.png", imageSize, imageSize, true, false));
        laserStart.setRotate(rotateTileImage(direction));
        return laserStart;
    }

    /**
     * Adds the LaserStart2 on the game board with location, imageSize and rotation
     *
     * @author Tommy Dang
     * @since 2023-03-19
     */
    public ImageView addTileLaserStart2(int direction) {
        ImageView laserStart2 =
                new ImageView(
                        new Image(
                                "images/tiles/laser/LaserStart2.png", imageSize, imageSize, true, false));
        laserStart2.setRotate(rotateTileImage(direction));
        return laserStart2;
    }

    /**
     * Adds the LaserStart3 on the game board with location, imageSize and rotation
     *
     * @author Tommy Dang
     * @since 2023-03-19
     */
    public ImageView addTileLaserStart3(int direction) {
        ImageView laserStart3 =
                new ImageView(
                        new Image(
                                "images/tiles/laser/LaserStart3.png", imageSize, imageSize, true, false));
        laserStart3.setRotate(rotateTileImage(direction));
        return laserStart3;
    }

    /**
     * Adds the Laser on the game board with location, imageSize and rotation
     *
     * @author Tommy Dang
     * @since 2023-03-19
     */
    public ImageView addTileLaser(int direction) {
        ImageView laser =
                new ImageView(
                        new Image("images/tiles/laser/laser.png", imageSize, imageSize, true, false));
        laser.setRotate(rotateTileImage(direction));
        return laser;
    }

    /**
     * Adds the Laser2 on the game board with location, imageSize and rotation
     *
     * @author Tommy Dang
     * @since 2023-03-18
     */
    public ImageView addTileLaser2(int direction) {
        ImageView laser2 =
                new ImageView(
                        new Image("images/tiles/laser/laser2.png", imageSize, imageSize, true, false));
        laser2.setRotate(rotateTileImage(direction));
        return laser2;
    }

    /**
     * Adds the Laser3 on the game board with location, imageSize and rotation
     *
     * @author Tommy Dang
     * @since 2023-03-19
     */
    public ImageView addTileLaser3(int direction) {
        ImageView laser3 =
                new ImageView(
                        new Image("images/tiles/laser/laser3.png", imageSize, imageSize, true, false));
        laser3.setRotate(rotateTileImage(direction));
        return laser3;
    }

    /**
     * Adds the LaserHalf on the game board with location, imageSize and rotation
     *
     * @author Tommy Dang
     * @since 2023-03-19
     */
    public ImageView addTileLaserHalf(int direction) {
        ImageView laserHalf =
                new ImageView(
                        new Image("images/tiles/laser/laserHalf.png", imageSize, imageSize, true, false));
        laserHalf.setRotate(rotateTileImage(direction));
        return laserHalf;
    }

    /**
     * Adds the LaserHalf2 on the game board with location, imageSize and rotation
     *
     * @author Tommy Dang
     * @since 2023-03-19
     */
    public ImageView addTileLaserHalf2(int direction) {
        ImageView laserHalf2 =
                new ImageView(
                        new Image(
                                "images/tiles/laser/laserHalf2.png", imageSize, imageSize, true, false));
        laserHalf2.setRotate(rotateTileImage(direction));
        return laserHalf2;
    }

    /**
     * Adds the LaserHalf3 on the game board with location, imageSize and rotation
     *
     * @author Tommy Dang
     * @since 2023-03-19
     */
    public ImageView addTileLaserHalf3(int direction) {
        ImageView laserHalf3 =
                new ImageView(
                        new Image(
                                "images/tiles/laser/laserHalf3.png", imageSize, imageSize, true, false));
        laserHalf3.setRotate(rotateTileImage(direction));
        return laserHalf3;
    }

    /**
     * Returns an ImageView object with the corresponding pusher
     *
     * @author Mathis Eilers
     * @since 2023-03-24
     */
    public ImageView addTilePusher1(int direction) {
        ImageView pusher =
                new ImageView(
                        new Image("images/tiles/pusher/Pusher_1.png", imageSize, imageSize, true, false));
        pusher.setRotate(rotateTileImage(direction));
        return pusher;
    }

    /**
     * Returns an ImageView object with the corresponding pusher
     *
     * @author Mathis Eilers
     * @since 2023-03-24
     */
    public ImageView addTilePusher135(int direction) {
        ImageView pusher =
                new ImageView(
                        new Image(
                                "images/tiles/pusher/Pusher_1_3_5.png",
                                imageSize,
                                imageSize,
                                true,
                                false));
        pusher.setRotate(rotateTileImage(direction));
        return pusher;
    }

    /**
     * Returns an ImageView object with the corresponding pusher
     *
     * @author Mathis Eilers
     * @since 2023-03-24
     */
    public ImageView addTilePusher2(int direction) {
        ImageView pusher =
                new ImageView(
                        new Image("images/tiles/pusher/Pusher_2.png", imageSize, imageSize, true, false));
        pusher.setRotate(rotateTileImage(direction));
        return pusher;
    }

    /**
     * Returns an ImageView object with the corresponding pusher
     *
     * @author Mathis Eilers
     * @since 2023-03-24
     */
    public ImageView addTilePusher24(int direction) {
        ImageView pusher =
                new ImageView(
                        new Image(
                                "images/tiles/pusher/Pusher_2_4.png", imageSize, imageSize, true, false));
        pusher.setRotate(rotateTileImage(direction));
        return pusher;
    }

    /**
     * Returns an ImageView object with the corresponding pusher
     *
     * @author Mathis Eilers
     * @since 2023-03-24
     */
    public ImageView addTilePusher3(int direction) {
        ImageView pusher =
                new ImageView(
                        new Image("images/tiles/pusher/Pusher_3.png", imageSize, imageSize, true, false));
        pusher.setRotate(rotateTileImage(direction));
        return pusher;
    }

    /**
     * Returns an ImageView object with the corresponding gear
     *
     * @author Mathis Eilers
     * @since 2023-04-05
     */
    public ImageView addGearCW() {
        // CW: Clockwise
        return new ImageView(
                new Image("images/tiles/gear/GearCW.png", imageSize, imageSize, true, false));
    }

    /**
     * Returns an ImageView object with the corresponding gear
     *
     * @author Mathis Eilers
     * @since 2023-04-05
     */
    public ImageView addGearCCW() {
        // CCW: Counterclockwise
        return new ImageView(
                new Image("images/tiles/gear/GearCCW.png", imageSize, imageSize, true, false));
    }
}
