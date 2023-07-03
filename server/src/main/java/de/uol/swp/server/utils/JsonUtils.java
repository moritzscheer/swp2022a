package de.uol.swp.server.utils;

import de.uol.swp.server.gamelogic.cards.*;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.File;
import java.io.FileReader;

/**
 * This is a utility class
 *
 * <p>The purpose of this class is to contain functions to help handling the JSON files
 *
 * @author Maria Andrade
 * @since 2023-05-13
 */
public final class JsonUtils {
    /**
     * Helper method to search a card in a JSON array
     *
     * <p>This method goes through all JSON Objects in the JSON Array and looks for id matching to
     * the value from the parameter. Then in returns the path of the image.
     *
     * @param cardId the cardID that wants to be searched for
     * @author Maria Andrade
     * @since 2023-05-06
     */
    public static Card searchCardInJSON(int cardId) {
        JSONObject json;
        JSONArray jsonArray;

        String jsonFilePath = "server/src/main/resources/json/cards.json";
        //TODO: change File Path if executed from tests
        if(new File(".").getAbsolutePath().endsWith("server\\.")){
            jsonFilePath = "src/main/resources/json/cards.json";
        }


        try {
            try{
                json =
                        new JSONObject(
                                new JSONTokener(
                                        new FileReader(jsonFilePath)));
            }catch (Exception ex){
                json =
                        new JSONObject(
                                new JSONTokener(
                                        new FileReader("src/main/resources/json/cards.json")));
            }
            jsonArray = json.getJSONArray("cards");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = null;
                try {
                    obj = jsonArray.getJSONObject(i);
                    if (obj.getInt("card-id") == cardId) {
                        Card card;
                        String cardType = obj.getString("type-id");
                        int priority = obj.getInt("priority");
                        card = new Card(cardId, cardType, priority);
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
     * <p>This method goes through all JSON Objects in the JSON Array and looks for id matching to
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


    /**
     * Helper method to search for a card Type
     *
     *
     * @param cardId the cardID that wants to be searched for
     * @author Maria Andrade
     * @since 2023-06-20
     */
    public static String searchCardType(int cardId) {
        String type = "";
        if (cardId > 0 & cardId <= 18) {
            type = "TurnLeft";
        } else if (cardId > 18 & cardId <= 36) {
            type = "TurnRight";
        } else if (cardId > 36 & cardId <= 42) {
            type = "U-Turn";
        } else if (cardId > 42 & cardId <= 48) {
            type = "Back-Up";
        } else if (cardId > 48 & cardId <= 66) {
            type = "Move1";
        } else if (cardId > 66 & cardId <= 78) {
            type = "Move2";
        } else if (cardId > 78 & cardId <= 84) {
            type = "Move3";
        }
        return type;
    }
}
