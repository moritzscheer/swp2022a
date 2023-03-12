package de.uol.swp.client.lobby.game;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;



/**
 * Manages the all Tiles on the GameBoard
 *
 * @author Tommy Dang
 * @since 2023-03-12
 */
public class Tile{

    private ImageView fieldImage = new ImageView(new Image("images/tiles/field.jpg"));

    /**
     * Creates a StackPane with the fieldImage as Base für the Tile
     *
     * @author Tommy Dang
     * @since 2023-03-12
     */

    // Andere Lösung für StackPane wäre, dass man in der GameView das Spielbrett schon mit StackPane ausfüllt (manuell), dann auf die GridPane-Zelle zugreift, dann dessen
    // Children und per add(fieldImage) das Image hinzufügt. Dadurch muss man keine neues StackPane erstellen und neue Elemente sind einfacher hinzuzufügen
    public StackPane fieldTile(){
        StackPane stack = new StackPane();
        stack.getChildren().add(fieldImage);

        return stack;
    }

    /**
     * Adds a LaserImage to the StackPane on the GameBoard
     *
     * @author Tommy Dang
     * @since 2023-03-12
     */
    public StackPane laserTile(){

        return null;
    }

    /**
     * Rotates the Image in the StackPane
     *
     * @author Tommy Dang
     * @since 2023-03-12
     */
//    public void rotate(CardinalDirection direction){
//
//    }

}
