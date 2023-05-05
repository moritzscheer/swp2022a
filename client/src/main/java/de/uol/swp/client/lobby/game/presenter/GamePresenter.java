package de.uol.swp.client.lobby.game.presenter;

import static javafx.scene.paint.Color.DODGERBLUE;

import de.uol.swp.client.AbstractPresenter;
import de.uol.swp.client.lobby.LobbyManagement;
import de.uol.swp.client.lobby.game.Card;
import de.uol.swp.common.lobby.dto.LobbyDTO;
import de.uol.swp.common.user.User;

import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.File;
import java.io.FileReader;
import java.util.*;

/**
 * Manages the game window
 *
 * @author Moritz Scheer
 * @see de.uol.swp.client.AbstractPresenter
 * @since 2023-03-09
 */
@SuppressWarnings("UnstableApiUsage")
public class GamePresenter extends AbstractPresenter {

    public static final String FXML = "/fxml/GameView.fxml";
    private static final Logger LOG = LogManager.getLogger(GamePresenter.class);

    private Integer lobbyID;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /*
     @FXML private Rectangle card1;
     @FXML private Rectangle card2;
     @FXML private Rectangle card3;
     @FXML private Rectangle card4;
     @FXML private Rectangle card5;
     @FXML private Rectangle card6;
     @FXML private Rectangle card7;
     @FXML private Rectangle card8;
     @FXML private Rectangle card9;

     @FXML private Rectangle slot1;
     @FXML private Rectangle slot2;
     @FXML private Rectangle slot3;
     @FXML private Rectangle slot4;
     @FXML private Rectangle slot5;*/

     @FXML private StackPane paneCard1;
     @FXML private javafx.scene.control.Button data;

     Map<Rectangle, Boolean> cards = new LinkedHashMap<>();
     Map<Rectangle, Boolean> slots = new LinkedHashMap<>();
     Map<Label, Rectangle> valueLabels = new LinkedHashMap<>();

     static ArrayList<Card> cardDeck = new ArrayList<>();
     ArrayList<Card> cardHand = new ArrayList<>();
     ArrayList<Card> submittedCards = new ArrayList<>();

     ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

     @FXML private GridPane mainGrid;
     @FXML private GridPane gameBoard;
     @FXML private Text player2HP;
     @FXML private Text player3HP;
     @FXML private Text player4HP;
     @FXML private Text player5HP;
     @FXML private Text player6HP;
     @FXML private Text player7HP;
     @FXML private Text player8HP;

     @FXML private Text player2Checkpoint;
     @FXML private Text player3Checkpoint;
     @FXML private Text player4Checkpoint;
     @FXML private Text player5Checkpoint;
     @FXML private Text player6Checkpoint;
     @FXML private Text player7Checkpoint;
     @FXML private Text player8Checkpoint;

     @FXML private Text player2RobotLives;
     @FXML private Text player3RobotLives;
     @FXML private Text player4RobotLives;
     @FXML private Text player5RobotLives;
     @FXML private Text player6RobotLives;
     @FXML private Text player7RobotLives;
     @FXML private Text player8RobotLives;

     @FXML private StackPane player2Ready;
     @FXML private StackPane player3Ready;
     @FXML private StackPane player4Ready;
     @FXML private StackPane player5Ready;
     @FXML private StackPane player6Ready;
     @FXML private StackPane player7Ready;
     @FXML private StackPane player8Ready;

     @FXML private Text player2Name;
     @FXML private Text player3Name;
     @FXML private Text player4Name;
     @FXML private Text player5Name;
     @FXML private Text player6Name;
     @FXML private Text player7Name;
     @FXML private Text player8Name;

     @FXML private ImageView player2Card;
     @FXML private ImageView player3Card;
     @FXML private ImageView player4Card;
     @FXML private ImageView player5Card;
     @FXML private ImageView player6Card;
     @FXML private ImageView player7Card;
     @FXML private ImageView player8Card;
     @FXML private ImageView card1;
     @FXML private ImageView card2;
     @FXML private ImageView card3;
     @FXML private ImageView card4;
     @FXML private ImageView card5;
     @FXML private ImageView card6;
     @FXML private ImageView card7;
     @FXML private ImageView card8;
     @FXML private ImageView card9;
     @FXML private ImageView chosenCard1;
     @FXML private ImageView chosenCard2;
     @FXML private ImageView chosenCard3;
     @FXML private ImageView chosenCard4;
     @FXML private ImageView chosenCard5;
     @FXML private ImageView markField;
     private LobbyDTO lobby;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Default Constructor
     *
     * @since 2022-03-12
     */
    public GamePresenter() {}

    /**
     * Method to initialize the game view
     *
     * <p>This method creates a board with the given information and adds all images to the board.
     *
     * @param lobbyID the Integer identifier of the lobby
     * @param lobby LobbyDTO Object containing all the information of the lobby
     * @param board an Integer array containing all the information of the board
     * @author Moritz Scheer, Tommy Dang, Jann Erik Bruns, Maxim Erden
     * @since 2023-03-23
     */
    public void init(Integer lobbyID, LobbyDTO lobby, Integer[][][] board) {
        this.lobbyID = lobbyID;
        this.lobby = lobby;

        /*for (User user: lobby.getUsers()) {
            player2Name.setText(user.getUsername());
            player3Name.setText(user.getUsername());
            player4Name.setText(user.getUsername());
            player5Name.setText(user.getUsername());
            player6Name.setText(user.getUsername());
            player7Name.setText(user.getUsername());
            player8Name.setText(user.getUsername());
        };*/

        File file = new File("C:/Users/Jann/IdeaProjects/swpbaseproject/client/src/main/resources/images/Cards/prgcard001.jpg");
        Image image = new Image(file.toURI().toString());

        setPlayerCard(null, image);
        card1.setPreserveRatio(true);
        card1.setFitHeight(150);
        card1.setFitWidth(50);
        card1.setImage(image);

        card2.setPreserveRatio(true);
        card2.setFitHeight(150);
        card2.setFitWidth(50);
        card2.setImage(image);

        card3.setPreserveRatio(true);
        card3.setFitHeight(150);
        card3.setFitWidth(50);
        card3.setImage(image);

        card4.setPreserveRatio(true);
        card4.setFitHeight(150);
        card4.setFitWidth(50);
        card4.setImage(image);

        card5.setPreserveRatio(true);
        card5.setFitHeight(150);
        card5.setFitWidth(50);
        card5.setImage(image);

        card6.setPreserveRatio(true);
        card6.setFitHeight(150);
        card6.setFitWidth(50);
        card6.setImage(image);

        card7.setPreserveRatio(true);
        card7.setFitHeight(150);
        card7.setFitWidth(50);
        card7.setImage(image);

        card8.setPreserveRatio(true);
        card8.setFitHeight(150);
        card8.setFitWidth(50);
        card8.setImage(image);

        card9.setPreserveRatio(true);
        card9.setFitHeight(150);
        card9.setFitWidth(50);
        card9.setImage(image);

        chosenCard1.setPreserveRatio(true);
        chosenCard1.setFitHeight(150);
        chosenCard1.setFitWidth(50);
        chosenCard1.setImage(image);

        chosenCard2.setPreserveRatio(true);
        chosenCard2.setFitHeight(150);
        chosenCard2.setFitWidth(50);
        chosenCard2.setImage(image);

        chosenCard3.setPreserveRatio(true);
        chosenCard3.setFitHeight(150);
        chosenCard3.setFitWidth(50);
        chosenCard3.setImage(image);

        chosenCard4.setPreserveRatio(true);
        chosenCard4.setFitHeight(150);
        chosenCard4.setFitWidth(50);
        chosenCard4.setImage(image);

        chosenCard5.setPreserveRatio(true);
        chosenCard5.setFitHeight(150);
        chosenCard5.setFitWidth(50);
        chosenCard5.setImage(image);

        markField.setPreserveRatio(true);
        markField.setFitHeight(150);
        markField.setFitWidth(50);
        markField.setImage(image);

        // creates the board
        try {
            JSONObject json =
                    new JSONObject(
                            new JSONTokener(
                                    new FileReader("client/src/main/resources/json/tile.json")));
            JSONArray jsonArray = json.getJSONArray("array");

            for (int i = 0; i < board.length; i++) {
                gameBoard.addColumn(i);
                gameBoard.addRow(i);
            }

            for (int col = 0; col < board.length; col++) {
                for (int row = 0; row < board[col].length; row++) {
                    for (int img = 0; img < board[col][row].length; img++) {
                        String path = searchJSON(jsonArray, board[col][row][img].toString());
                        ImageView imageView = new ImageView(path);
                        imageView.setFitWidth(80);
                        imageView.setFitHeight(80);
                        gameBoard.add(imageView, col, row);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
/*
        cards.put(card1, false);
        cards.put(card2, false);
        cards.put(card3, false);
        cards.put(card4, false);
        cards.put(card5, false);
        cards.put(card6, false);
        cards.put(card7, false);
        cards.put(card8, false);
        cards.put(card9, false);

        slots.put(slot1, false);
        slots.put(slot2, false);
        slots.put(slot3, false);
        slots.put(slot4, false);
        slots.put(slot5, false);
*/

        // owner = true      //sonst erstellt jeder spieler der Lobby ein eigenes carddeck
        //resetCardsAndSlots();
        //setLabels();
    }
    /**
     * Setting all players to not ready
     *
     * @param user
     * @param image
     * @author Jann Erik Bruns
     * @since 2023-05-05
     */
    private void setAllPlayersNotReady() {
        player2Ready.setStyle("-fx-background-color: red");
        player3Ready.setStyle("-fx-background-color: red");
        player4Ready.setStyle("-fx-background-color: red");
        player5Ready.setStyle("-fx-background-color: red");
        player6Ready.setStyle("-fx-background-color: red");
        player7Ready.setStyle("-fx-background-color: red");
        player8Ready.setStyle("-fx-background-color: red");
    }
    /**
     * Setting ready status of the user
     *
     * @param user
     * @param image
     * @author Jann Erik Bruns
     * @since 2023-05-05
     */
    private void setPlayerReadyStatus(User user, boolean ready){
        int i = 0;
        String style;
        if(ready)
            style = "-fx-background-color: green";
        else
            style = "-fx-background-color: red";

        for (User countUser:lobby.getUsers()) {
            if(user.getUsername() == countUser.getUsername())
                break;
            i++;
        };
        switch (i){
            case 0: player2Ready.setStyle(style);
                break;
            case 1: player3Ready.setStyle(style);
                break;
            case 2: player4Ready.setStyle(style);
                break;
            case 3: player5Ready.setStyle(style);
                break;
            case 4: player6Ready.setStyle(style);
                break;
            case 5: player7Ready.setStyle(style);
                break;
            case 6: player8Ready.setStyle(style);
                break;
        }
    }
    /**
     * Setting health points of the user
     *
     * @param user
     * @param image
     * @author Jann Erik Bruns
     * @since 2023-05-05
     */
    private void setPlayerHP(User user, String hp){
        int i = 0;

        for (User countUser:lobby.getUsers()) {
            if(user.getUsername() == countUser.getUsername())
                break;
            i++;
        };
        switch (i){
            case 0: player2HP.setText(hp);
                break;
            case 1: player3HP.setText(hp);
                break;
            case 2: player4HP.setText(hp);
                break;
            case 3: player5HP.setText(hp);
                break;
            case 4: player6HP.setText(hp);
                break;
            case 5: player7HP.setText(hp);
                break;
            case 6: player8HP.setText(hp);
                break;
        }
    }
    /**
     * Setting roboter health pojnts of the user
     *
     * @param user
     * @param image
     * @author Jann Erik Bruns
     * @since 2023-05-05
     */
    private void setRoboterHP(User user, String hp){
        int i = 0;

        for (User countUser:lobby.getUsers()) {
            if(user.getUsername() == countUser.getUsername())
                break;
            i++;
        };
        switch (i){
            case 0: player2RobotLives.setText(hp);
                break;
            case 1: player3RobotLives.setText(hp);
                break;
            case 2: player4RobotLives.setText(hp);
                break;
            case 3: player5RobotLives.setText(hp);
                break;
            case 4: player6RobotLives.setText(hp);
                break;
            case 5: player7RobotLives.setText(hp);
                break;
            case 6: player8RobotLives.setText(hp);
                break;
        }
    }
    /**
     * Setting Checkpoint of the user
     *
     * @param user
     * @param image
     * @author Jann Erik Bruns
     * @since 2023-05-05
     */
    private void setPlayerCheckpoint(User user, String cp){
        int i = 0;

        for (User countUser:lobby.getUsers()) {
            if(user.getUsername() == countUser.getUsername())
                break;
            i++;
        };
        switch (i){
            case 0: player2Checkpoint.setText(cp);
                break;
            case 1: player3Checkpoint.setText(cp);
                break;
            case 2: player4Checkpoint.setText(cp);
                break;
            case 3: player5Checkpoint.setText(cp);
                break;
            case 4: player6Checkpoint.setText(cp);
                break;
            case 5: player7Checkpoint.setText(cp);
                break;
            case 6: player8Checkpoint.setText(cp);
                break;
        }
    }

    /**
     * Setting playercard of the user
     *
     * @param user
     * @param image
     * @author Jann Erik Bruns
     * @since 2023-05-05
     */
    private void setPlayerCard(User user, Image image){
        int i = 0;
        /*
        for (User countUser:lobby.getUsers()) {
            if(user.getUsername() == countUser.getUsername())
                break;
            i++;
        };*/
        switch (i){
            case 0:
                player2Card.setImage(image);
                player2Card.setFitHeight(150);
                player2Card.setFitWidth(100);
                break;
            case 1:
                player3Card.setImage(image);
                player3Card.setFitHeight(150);
                player3Card.setFitWidth(100);
                break;
            case 2:
                player4Card.setImage(image);
                player4Card.setFitHeight(150);
                player4Card.setFitWidth(100);
                break;
            case 3:
                player5Card.setImage(image);
                player5Card.setFitHeight(150);
                player5Card.setFitWidth(100);
                break;
            case 4:
                player6Card.setImage(image);
                player6Card.setFitHeight(150);
                player6Card.setFitWidth(100);
                break;
            case 5:
                player7Card.setImage(image);
                player7Card.setFitHeight(150);
                player7Card.setFitWidth(100);
                break;
            case 6:
                player8Card.setImage(image);
                player8Card.setFitHeight(150);
                player8Card.setFitWidth(100);
                break;
        }
    }


    /**
     * Helper method to search a given value in a JSON array
     *
     * <p>This method goes through all JSON Objects in the JSON Array and looks for id matching to
     * the value from the parameter. Then in returns the path of the image.
     *
     * @param array the JSONArray where the content is saved
     * @param searchValue the String that wants to be searched for
     * @see client/src/main/resources/json/tile.json
     * @author Moritz Scheer
     * @since 2023-03-23
     */

    private String searchJSON(JSONArray array, String searchValue) {
        for (int i = 0; i < array.length(); i++) {
            JSONObject obj = null;
            try {
                obj = array.getJSONObject(i);
                if (obj.getString("id").equals(searchValue.toString())) {
                    return obj.getString("source");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    // -----------------------------------------------------
    // methods for the cards
    // -----------------------------------------------------

    // Clickevent auf deinen Kartenbereich
    public void onCardClicked(MouseEvent click) {
        if (slots.containsValue(false)) {
            for (Map.Entry<javafx.scene.shape.Rectangle, Boolean> cardz : cards.entrySet()) {
                if (cardz.getKey().equals(getCardOrSlot(click.toString()))
                        && cardz.getValue() == true) {
                    for (Map.Entry<javafx.scene.shape.Rectangle, Boolean> slotz :
                            slots.entrySet()) {
                        if (slotz.getValue() == false) {
                            switchTwoCardsOrSlots(getCardOrSlot(click.toString()), slotz.getKey());
                            break;
                        }
                    }
                    break;
                }
            }
        }
    }

    // Clickevent auf deinen Slotbereich
    public void onSlotClicked(MouseEvent click) {
        //cardsGridPane.setVisible(true);

        for (Map.Entry<javafx.scene.shape.Rectangle, Boolean> slotz : slots.entrySet()) {
            if (slotz.getKey().equals(getCardOrSlot(click.toString()))
                    && slotz.getValue() == true) {
                for (Map.Entry<javafx.scene.shape.Rectangle, Boolean> cardz : cards.entrySet()) {
                    if (cardz.getValue() == false) {
                        switchTwoCardsOrSlots(getCardOrSlot(click.toString()), cardz.getKey());
                        break;
                    }
                }
                break;
            }
        }
    }

    // Gibt dir aus einem Target.toString() den passenden Slot aus
    public javafx.scene.shape.Rectangle getCardOrSlot(String click) {
/*
        // checkt obs im Kartenbereich ist
        if (click.contains("card1")) {
            return card1;
        } else if (click.contains("card2")) {
            return card2;
        } else if (click.contains("card3")) {
            return card3;
        } else if (click.contains("card4")) {
            return card4;
        } else if (click.contains("card5")) {
            return card5;
        } else if (click.contains("card6")) {
            return card6;
        } else if (click.contains("card7")) {
            return card7;
        } else if (click.contains("card8")) {
            return card8;
        } else if (click.contains("card9")) {
            return card9;
        }

        // checkt obs im SlotBereich ist
        else if (click.contains("slot1")) {
            return slot1;
        } else if (click.contains("slot2")) {
            return slot2;
        } else if (click.contains("slot3")) {
            return slot3;
        } else if (click.contains("slot4")) {
            return slot4;
        } else if (click.contains("slot5")) {
            return slot5;
        }*/
        return null;
    }
    /*
    // DEAD CODE

        public void cardSwitchSlot(Rectangle card){
            cards.replace(card, false);

            for( Map.Entry<Rectangle, Boolean> value : slots.entrySet()){
                if(value.getValue() == false){
                    slots.replace(value.getKey(), true);
                    changeCard(value.getKey(), card);
                    break;
                }
            }
        }
    */
    //Tauscht den Slot mit der Karte
    public void slotSwitchCard (Rectangle slot){
        for( Map.Entry<Rectangle, Boolean> valueSlot : slots.entrySet()){
            if(valueSlot.getKey().equals(slot) && valueSlot.getValue() == true){
                slots.replace(slot, false);

                for( Map.Entry<Rectangle, Boolean> valueCard : cards.entrySet()){
                    if(valueCard.getValue() == false){
                        cards.replace(valueCard.getKey(), true);
                        //changeCard(valueCard.getKey(), slot);
                        break;
                    }
                }
                break;
            }
        }

    }
    // Tauscht 2 Karten miteinander egal welche
    public void switchTwoCardsOrSlots(
            javafx.scene.shape.Rectangle start, javafx.scene.shape.Rectangle end) {

        System.out.println(start.toString());
        System.out.println(end.toString());
        System.out.println("---------------");

        // change pictures
        javafx.scene.shape.Rectangle copy = new javafx.scene.shape.Rectangle();
        copy.setFill(start.getFill());

        start.setFill(end.getFill());
        end.setFill(copy.getFill());

        // change slotmaps
        boolean startBool = getSwitchTwoCardsOrSlotsBoolean(start);
        boolean endBool = getSwitchTwoCardsOrSlotsBoolean(end);

        if (start.toString().contains("card")) {
            cards.replace(start, endBool);
        } else if (start.toString().contains("slot")) {
            slots.replace(start, endBool);
        }

        if (end.toString().contains("card")) {
            cards.replace(end, startBool);
        } else if (end.toString().contains("slot")) {
            slots.replace(end, startBool);
        }

        // change cardpositions
        int startID = -1;
        int endID = -1;
        for (int i = 0; i < cardHand.size(); i++) {

            if (cardHand.get(i).getPosition().equals(start)) {
                startID = i;
            }
            if (cardHand.get(i).getPosition().equals(end)) {
                endID = i;
            }
        }

        System.out.println(startID);
        System.out.println(endID);
        System.out.println("---------------");

        if (startID == -1 && endID > -1) {
            cardHand.get(endID).setPosition(start);
        } else if (endID == -1 && startID > -1) {
            cardHand.get(startID).setPosition(end);
        } else if (startID > -1 && endID > -1) {
            copy = cardHand.get(startID).getPosition();

            cardHand.get(startID).setPosition(end);
            cardHand.get(endID).setPosition(copy);
        }
    }

    // gibt den Boolean vom Kartenslot raus
    public boolean getSwitchTwoCardsOrSlotsBoolean(javafx.scene.shape.Rectangle cardslot) {
        for (Map.Entry<javafx.scene.shape.Rectangle, Boolean> cardz : cards.entrySet()) {
            if (cardz.getKey().equals(cardslot)) {
                return cardz.getValue();
            }
        }
        for (Map.Entry<javafx.scene.shape.Rectangle, Boolean> slotz : slots.entrySet()) {
            if (slotz.getKey().equals(cardslot)) {
                return slotz.getValue();
            }
        }
        return false;
    }

    /* DEAD CODE
    //Tauscht design
        public void changeCard(Rectangle valueCard, Rectangle slot){

            for(int i = 0; i<cardHand.size(); i++){

                if (cardHand.get(i).getPosition().equals(slot)){

                    cardHand.get(i).setPosition(valueCard);
                    valueCard.setFill(cardHand.get(i).getPicture());
                    slot.setFill(DODGERBLUE);
                    break;
                }
            }
        }
    */

    public ArrayList<Card> newCardDeck() {

        ArrayList<Card> cards = new ArrayList<>();
        for (int i = 10; i <= 840; i = i + 10) {
            cards.add(new Card(i));
        }
        Collections.shuffle(cards);
        return cards;
    }


        public void setLabels(){
            if(valueLabels.size() <= 9){
                for(int i = 1; i< 10; i++){
                    valueLabels.put(new Label(), null);

                }
            }

            ArrayList<String> alreadyDone = new ArrayList<>();

            for(Map.Entry<Label, Rectangle> labMap : valueLabels.entrySet()){
                boolean done = false;

                for(Map.Entry<Rectangle, Boolean> value : cards.entrySet()){
                    if(value.getValue() == true && alreadyDone.contains(value.getKey().toString()) == false){
                        setLabelText(labMap.getKey(), value.getKey());
                        alreadyDone.add(value.getKey().toString());
                        done = true;

                        break;
                    }
                }
                if( done == false) {
                    for (Map.Entry<Rectangle, Boolean> value : slots.entrySet()) {
                        if (value.getValue() == true && alreadyDone.contains(value.getKey().toString()) == false) {

                            setLabelText(labMap.getKey(), value.getKey());
                            alreadyDone.add(value.getKey().toString());
                            done = true;
                            break;
                        }
                    }
                }
            }
        }

        public void setLabelText(Label label, Rectangle box){

            System.out.println(box.toString());

            for( int i = 0; i< cardHand.size(); i++){
                if(cardHand.get(i).getPosition().equals(box)){
                label.setText(String.valueOf(cardHand.get(i).getValue()));

                    label.setTranslateX(box.getX()+32);
                    label.setTranslateY(box.getY()-41);
                    /*label.setAlignment(Pos.CENTER);
                    label.setTextFill(BLACK);
                    handCards.getChildren().add(label);*/

                }
            }
        }

    public void getData(MouseEvent mouseEvent) {
        /*System.out.println("--------------------");
        for(Map.Entry<Rectangle, Boolean> cardz : cards.entrySet()){
            System.out.println(cardz.getKey().toString()+ "  " + cardz.getValue());
        }
        System.out.println("--------------------");
        for(Map.Entry<Rectangle, Boolean> cardz : slots.entrySet()){
            System.out.println(cardz.getKey().toString()+ "  " + cardz.getValue());
        }
        System.out.println("--------------------");
        for (int i = 0; i< cardHand.size(); i++){
            System.out.println(cardHand.get(i).getCardType().toString() + "  " + cardHand.get(i).getPosition().toString());
        }
        */

        //cardsGridPane.setVisible(false);

        resetCardsAndSlots();
    }


    public void resetCardsAndSlots() {
        // if(loggedInUser == owner) {
        cardDeck = newCardDeck();
        System.out.println("KartenDeck größe " + cardDeck.size());
        // }
        cardHand.clear();
        submittedCards.clear();

        for (Map.Entry<javafx.scene.shape.Rectangle, Boolean> cardz : cards.entrySet()) {
            if (cardz.getKey() != null) {
                cards.replace(cardz.getKey(), false);
                cardz.getKey().setFill(DODGERBLUE);
                System.out.print("1");
            }
        }

        for (Map.Entry<javafx.scene.shape.Rectangle, Boolean> slotz : slots.entrySet()) {
            if (slotz.getKey() != null) {
                slots.replace(slotz.getKey(), false);
                slotz.getKey().setFill(DODGERBLUE);
                System.out.print("2");
            }
        }

        for (int i = 0; i < 9; i++) {
            for (Map.Entry<javafx.scene.shape.Rectangle, Boolean> cardSlot : cards.entrySet()) {
                if (cardSlot.getValue() == false && cardSlot.getKey() != null) {
                    cards.replace(cardSlot.getKey(), true);
                    cardSlot.getKey().setFill(cardDeck.get(0).getPicture());
                    cardDeck.get(0).setPosition(cardSlot.getKey());
                    cardHand.add(cardDeck.get(0));
                    cardDeck.remove(0);
                    System.out.print("3");
                    break;
                }
            }
        }
    }


    @FXML
    private void onSubmit(MouseEvent mouseEvent) {

        if (slots.containsValue(false) == false) {
            /*submittedCards.add(getCardBySlot(slot1));
            submittedCards.add(getCardBySlot(slot2));
            submittedCards.add(getCardBySlot(slot3));
            submittedCards.add(getCardBySlot(slot4));
            submittedCards.add(getCardBySlot(slot5));*/

            for (int i = 0; i < submittedCards.size(); i++) {
                System.out.println(submittedCards.get(i).getValue());
            }

            //cardsGridPane.setVisible(false);

            // resetCardsAndSlots();
        }
    }

    public Card getCardBySlot(Rectangle slot) {
        for (int i = 0; i < cardHand.size(); i++) {
            if (cardHand.get(i).getPosition().equals(slot)) {
                return cardHand.get(i);
            }
        }
        return null;
    }

    public void dragDropped(DragEvent event) {
        event.acceptTransferModes(TransferMode.ANY);
        System.out.println(event.toString());

        Dragboard d = event.getDragboard();
        System.out.println(d.getString());

        switchTwoCardsOrSlots(getCardOrSlot(d.getString()), getCardOrSlot(event.toString()));
    }

    public void dragEntered(MouseEvent mouseEvent) {

        if (getCardOrSlot(mouseEvent.toString()).getFill() == DODGERBLUE) {
            return;
        }
        Dragboard dragboard =
                getCardOrSlot(mouseEvent.toString()).startDragAndDrop(TransferMode.ANY);

        ClipboardContent content = new ClipboardContent();

        content.putString(mouseEvent.toString());
        dragboard.setContent(content);
    }

    public void dragOver(DragEvent event) {
        event.acceptTransferModes(TransferMode.ANY);
    }

    /*
    @Author Maxim Erden
     */

    public ArrayList<Card> getSubmittedCards() {
        return this.submittedCards;
    }
}

