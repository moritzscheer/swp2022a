package de.uol.swp.client.utils;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

/** This is a utility class
 *
 * The purpose of this class is to contain functions
 * to help handling the JSON files
 *
 * @author Maria Andrade
 * @since 2023-05-14
 */
public final class JsonUtils {
    private final JSONObject jsonTile;
    private final JSONArray jsonTileArray;

    public JsonUtils() throws FileNotFoundException {
        jsonTile =
                new JSONObject(
                        new JSONTokener(
                                new FileReader("client/src/main/resources/json/tile.json")));
        jsonTileArray = jsonTile.getJSONArray("array");
    }

    /**
     * Helper method to search a tile in a JSON array
     *
     * This method goes through all JSON Objects in the JSON Array and looks for id matching to
     * the value from the parameter. Then in returns the path of the image.
     *
     * @param tileId the tileID that wants to be searched for
     * @author Moritz Scheer
     * @since 2023-03-23
     * @author Maria Andrade
     * @since 2023-05-14
     */
    public File searchInTileJSON(String tileId){
        String path;
        for (int i = 0; i < this.jsonTileArray.length(); i++) {
            JSONObject obj = null;
            try {
                obj = this.jsonTileArray.getJSONObject(i);
                if (obj.getString("id").equals(tileId.toString())) {
                    path = obj.getString("source");
                    path = "client/src/main/resources/" + path;
                    File file = new File(path);
                    if(!file.exists()){
                        System.out.println( "TileID:" + tileId + ", could not be resolved to a path");
                    }
                    return file;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
