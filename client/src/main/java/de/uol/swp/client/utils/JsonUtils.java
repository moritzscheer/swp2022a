package de.uol.swp.client.utils;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * This is a utility class
 *
 * <p>The purpose of this class is to contain functions to help handling the JSON files
 *
 * @author Maria Andrade
 * @since 2023-05-14
 */
public final class JsonUtils {
    private final JSONObject jsonTile;
    private final JSONArray jsonTileArray;

    private final JSONObject jsonCard;
    private final JSONArray jsonCardArray;

    public JsonUtils() throws FileNotFoundException {
        jsonTile =
                new JSONObject(
                        new JSONTokener(
                                new FileReader("client/src/main/resources/json/tile.json")));
        jsonTileArray = jsonTile.getJSONArray("array");

        jsonCard =
                new JSONObject(
                        new JSONTokener(
                                new FileReader("client/src/main/resources/json/cards.json")));
        jsonCardArray = jsonCard.getJSONArray("cards");
    }

    /**
     * Helper method to search a tile in a JSON array
     *
     * <p>This method goes through all JSON Objects in the JSON Array and looks for id matching to
     * the value from the parameter. Then in returns the path of the image.
     *
     * @param tileId the tileID that wants to be searched for
     * @author Moritz Scheer
     * @since 2023-03-23
     * @author Maria Andrade
     * @since 2023-05-14
     */
    public ImageView searchInTileJSON(String tileId) {
        String path;
        for (int i = 0; i < this.jsonTileArray.length(); i++) {
            JSONObject obj = null;
            try {
                obj = this.jsonTileArray.getJSONObject(i);
                if (obj.getString("id").equals(tileId.toString())) {
                    path = obj.getString("source");
                    ImageView imageView = new ImageView(new Image(path));
                    return imageView;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * Helper method to search a card in a JSON array
     *
     * <p>This method goes through all JSON Objects in the JSON Array and looks for id matching to
     * the value from the parameter. Then in returns the Image of the card.
     *
     * @param cardID the cardID that wants to be searched for
     * @author Maria Andrade
     * @since 2023-05-18
     */
    public ImagePattern getCardImageById(int cardID) {
        String path;
        for (int i = 0; i < this.jsonCardArray.length(); i++) {
            JSONObject obj = null;
            try {
                obj = this.jsonCardArray.getJSONObject(i);
                if (obj.get("card-id").equals(cardID)) {
                    path = obj.getString("source");
                    ImagePattern picture = new ImagePattern(new Image(path));
                    return picture;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * Helper method to create the Image for the robot
     *
     * @param robotID the robotID to get the path
     * @author Maria Andrade
     * @since 2023-05-19
     */
    public ImageView getRobotImage(int robotID) {
        // robot id starts in 0
        String path = String.format("images/player/Player0%d.png", robotID + 1);

        ImageView imageView = new ImageView(new Image(path));
        return imageView;
    }

    /**
     * Helper method to create the ImageView for the card
     *
     * @param cardID the cardID to get the path
     * @author Maria Andrade
     * @since 2023-05-20
     */
    public Image getCardImage(int cardID) {
        String path;
        for (int i = 0; i < this.jsonCardArray.length(); i++) {
            JSONObject obj = null;
            try {
                obj = this.jsonCardArray.getJSONObject(i);
                if (obj.get("card-id").equals(cardID)) {
                    path = obj.getString("source");
                    return new Image(path);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
