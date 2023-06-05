package de.uol.swp.server.utils;

import de.uol.swp.server.gamelogic.cards.Card;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.FileReader;


/** This is a utility class
 *
 * The purpose of this class is to contain functions
 * to help handling the JSON files
 *
 * @author Maria Andrade
 * @since 2023-05-13
 */
public final class JsonUtils {
    /**
     * Helper method to search a card in a JSON array
     *
     * This method goes through all JSON Objects in the JSON Array and looks for id matching to
     * the value from the parameter. Then in returns the path of the image.
     *
     * @param cardId the cardID that wants to be searched for
     * @author Maria Andrade
     * @since 2023-05-06
     */
    public static Card searchCardInJSON(int cardId) {
        JSONObject json;
        JSONArray jsonArray;

        try {
            json =
                    new JSONObject(
                            new JSONTokener(
                                    new FileReader("server/src/main/resources/json/cards.json")));
            jsonArray = json.getJSONArray("cards");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = null;
                try {
                    obj = jsonArray.getJSONObject(i);
                    if (obj.getInt("card-id") == cardId) {
                        Card card;
                        String  cardType = obj.getString("type-id");
                        int priority = obj.getInt("priority");
                        String imgPath = obj.getString("source");
                        card = new Card(cardId, cardType, priority, imgPath);
                        return card;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Helper method to search for a card Type in a JSON array
     *
     * This method goes through all JSON Objects in the JSON Array and looks for id matching to
     * the value from the parameter. Then in returns the path of the image.
     *
     * @param cardId the cardID that wants to be searched for
     * @author Maria Andrade
     * @since 2023-06-02
     */
    public static String searchCardTypeInJSON(int cardId) {
        JSONObject json;
        JSONArray jsonArray;

        try {
            json =
                    new JSONObject(
                            new JSONTokener(
                                    new FileReader("server/src/main/resources/json/cards.json")));
            jsonArray = json.getJSONArray("cards");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = null;
                try {
                    obj = jsonArray.getJSONObject(i);
                    if (obj.getInt("card-id") == cardId) {

                        String typeName = obj.getString("name");
                        return typeName;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
