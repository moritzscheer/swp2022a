package de.uol.swp.client.lobby.game.presenter;

import de.uol.swp.client.AbstractPresenter;
import de.uol.swp.client.lobby.game.Card;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
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

import java.io.FileReader;
import java.util.*;

import static javafx.scene.paint.Color.DODGERBLUE;


@SuppressWarnings("UnstableApiUsage")
public class GamePresenter extends AbstractPresenter {

    public static final String FXML = "/fxml/GameView.fxml";
    private static final Logger LOG = LogManager.getLogger(GamePresenter.class);

    private Integer lobbyID;
    private int boardGridSize = 16;


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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
    @FXML private Rectangle slot5;

    @FXML private StackPane paneCard1;
    @FXML private javafx.scene.control.Button data;

    Map<Rectangle, Boolean> cards = new LinkedHashMap<>();
    Map<Rectangle, Boolean> slots = new LinkedHashMap<>();
    Map<Label, Rectangle> valueLabels = new LinkedHashMap<>();

    static ArrayList<Card> cardDeck = new ArrayList<>();
    ArrayList<Card> cardHand = new ArrayList<>();
    ArrayList<Card> submittedCards = new ArrayList<>();

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @FXML private Text player2Ready;
    @FXML private Text player3Ready;
    @FXML private Text player4Ready;
    @FXML private Text player5Ready;
    @FXML private Text player6Ready;
    @FXML private Text player7Ready;
    @FXML private Text player8Ready;
    @FXML private Text player2Name;
    @FXML private Text player3Name;
    @FXML private Text player4Name;
    @FXML private Text player5Name;
    @FXML private Text player6Name;
    @FXML private Text player7Name;
    @FXML private Text player8Name;
    @FXML private GridPane gameBoard;
    @FXML private GridPane cardsGridPane;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    public GamePresenter() {
    }


    private String searchJSON(JSONArray array, String searchValue) {
        for(int i = 0; i < array.length(); i++) {
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



    public void init(Integer lobbyID, Integer[][][] board) {
        this.lobbyID = lobbyID;

        try {
            JSONObject json = new JSONObject(new JSONTokener(new FileReader("client/src/main/resources/json/tile.json")));
            JSONArray jsonArray = json.getJSONArray("array");

            for(int i = 0; i < boardGridSize; i++){
                gameBoard.addColumn(i);
                gameBoard.addRow(i);
            }

            for(int col = 0; col < board.length; col++) {
                for(int row = 0; row < board[col].length; row++) {
                    for (int img = 0; img < board[col][row].length; img++) {
                        String path = searchJSON(jsonArray, board[col][row][img].toString());
                        ImageView imageView = new ImageView(path);
                        imageView.fitWidthProperty().bind(gameBoard.widthProperty().divide(board.length));
                        imageView.fitHeightProperty().bind(gameBoard.heightProperty().divide(board.length));
                        gameBoard.add(imageView, col, row);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        Platform.runLater(() -> {
            setAllPlayersNotReady();
        });

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

        //if owner = true      sonst erstellt jeder spieler der Lobby ein eigenes carddeck
        resetCardsAndSlots();

        //setLabels();
    }

    private void setAllPlayersNotReady(){
        player2Ready.setText("Not ready");
        player2Ready.setFill(Color.RED);
        player3Ready.setText("Not ready");
        player3Ready.setFill(Color.RED);
        player4Ready.setText("Not ready");
        player4Ready.setFill(Color.RED);
        player5Ready.setText("Not ready");
        player5Ready.setFill(Color.RED);
        player6Ready.setText("Not ready");
        player6Ready.setFill(Color.RED);
        player7Ready.setText("Not ready");
        player7Ready.setFill(Color.RED);
        player8Ready.setText("Not ready");
        player8Ready.setFill(Color.RED);
    }

    public void addTiles() {

    }





















    // -----------------------------------------------------
    // methods for the cards
    // -----------------------------------------------------




    //Clickevent auf deinen Kartenbereich
    public void onCardClicked (MouseEvent click) {
        if (slots.containsValue(false)) {
            for (Map.Entry<javafx.scene.shape.Rectangle, Boolean> cardz : cards.entrySet()) {
                if (cardz.getKey().equals(getCardOrSlot(click.toString())) && cardz.getValue() == true) {
                    for (Map.Entry<javafx.scene.shape.Rectangle, Boolean> slotz : slots.entrySet()) {
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

    //Clickevent auf deinen Slotbereich
    public void onSlotClicked (MouseEvent click) {
        cardsGridPane.setVisible(true);

        for( Map.Entry<javafx.scene.shape.Rectangle, Boolean> slotz : slots.entrySet()){
            if(slotz.getKey().equals(getCardOrSlot(click.toString())) && slotz.getValue() == true){
                for( Map.Entry<javafx.scene.shape.Rectangle, Boolean> cardz : cards.entrySet()){
                    if(cardz.getValue() == false){
                        switchTwoCardsOrSlots(getCardOrSlot(click.toString()), cardz.getKey());
                        break;
                    }
                }
                break;
            }
        }
    }

    //Gibt dir aus einem Target.toString() den passenden Slot aus
    public javafx.scene.shape.Rectangle getCardOrSlot(String click){

        //checkt obs im Kartenbereich ist
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

        //checkt obs im SlotBereich ist
        else if (click.contains("slot1")) {
            return slot1;
        } else if (click.contains("slot2")) {
            return slot2;
        } else  if (click.contains("slot3")) {
            return slot3;
        } else  if (click.contains("slot4")) {
            return slot4;
        } else  if (click.contains("slot5")) {
            return slot5;
        }
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

        //Tauscht den Slot mit der Karte
        public void slotSwitchCard (Rectangle slot){
            for( Map.Entry<Rectangle, Boolean> valueSlot : slots.entrySet()){
                if(valueSlot.getKey().equals(slot) && valueSlot.getValue() == true){
                    slots.replace(slot, false);

                    for( Map.Entry<Rectangle, Boolean> valueCard : cards.entrySet()){
                        if(valueCard.getValue() == false){
                            cards.replace(valueCard.getKey(), true);
                            changeCard(valueCard.getKey(), slot);
                            break;
                        }
                    }
                    break;
                }
            }

        }

    */
    // Tauscht 2 Karten miteinander egal welche
    public void switchTwoCardsOrSlots(javafx.scene.shape.Rectangle start, javafx.scene.shape.Rectangle end){

        System.out.println(start.toString());
        System.out.println(end.toString());
        System.out.println("---------------");

        //change pictures
        javafx.scene.shape.Rectangle copy = new javafx.scene.shape.Rectangle();
        copy.setFill(start.getFill());

        start.setFill(end.getFill());
        end.setFill(copy.getFill());



        //change slotmaps
        boolean startBool = getSwitchTwoCardsOrSlotsBoolean(start);
        boolean endBool= getSwitchTwoCardsOrSlotsBoolean(end);

        if (start.toString().contains("card")){
            cards.replace(start, endBool);
        }
        else if (start.toString().contains("slot")) {
            slots.replace(start, endBool);
        }

        if (end.toString().contains("card")){
            cards.replace(end, startBool);
        }
        else if (end.toString().contains("slot")) {
            slots.replace(end, startBool);
        }


        //change cardpositions
        int startID = -1;
        int endID = -1;
        for(int i = 0; i<cardHand.size(); i++){

            if (cardHand.get(i).getPosition().equals(start)){
                startID = i;
            }
            if (cardHand.get(i).getPosition().equals(end)){
                endID = i;
            }
        }

        System.out.println(startID);
        System.out.println(endID);
        System.out.println("---------------");

        if(startID == -1 && endID > -1){
            cardHand.get(endID).setPosition(start);
        }
        else if(endID == -1 && startID > -1){
            cardHand.get(startID).setPosition(end);
        }
        else if (startID > -1 && endID > -1){
            copy = cardHand.get(startID).getPosition();

            cardHand.get(startID).setPosition(end);
            cardHand.get(endID).setPosition(copy);
        }
    }









    // gibt den Boolean vom Kartenslot raus
    public boolean getSwitchTwoCardsOrSlotsBoolean(javafx.scene.shape.Rectangle cardslot){
        for( Map.Entry<javafx.scene.shape.Rectangle, Boolean> cardz : cards.entrySet()) {
            if(cardz.getKey().equals(cardslot)){
                return cardz.getValue();
            }
        }
        for( Map.Entry<javafx.scene.shape.Rectangle, Boolean> slotz : slots.entrySet()) {
            if(slotz.getKey().equals(cardslot)){
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

    public ArrayList<Card> newCardDeck(){

        ArrayList<Card> cards = new ArrayList<>();
        for (int i = 10; i<=840; i = i +10){
            cards.add(new Card(i));
        }
        Collections.shuffle(cards);
        return cards;
    }

    /*
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
                label.setAlignment(Pos.CENTER);
                label.setTextFill(BLACK);
                handCards.getChildren().add(label);

            }
        }
    }
*/

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

        cardsGridPane.setVisible(false);

        resetCardsAndSlots();
    }

    public void resetCardsAndSlots(){
        // if(loggedInUser == owner) {
        cardDeck = newCardDeck();
        System.out.println("KartenDeck größe " + cardDeck.size());
        //}
        cardHand.clear();
        submittedCards.clear();


        for (Map.Entry<javafx.scene.shape.Rectangle, Boolean> cardz : cards.entrySet()) {
            if(cardz.getKey() != null){
                cards.replace(cardz.getKey(), false);
                cardz.getKey().setFill(DODGERBLUE);
                System.out.print("1");
            }

        }

        for (Map.Entry<javafx.scene.shape.Rectangle, Boolean> slotz : slots.entrySet()) {
            if(slotz.getKey() != null){
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

        if(slots.containsValue(false) == false) {
            submittedCards.add(getCardBySlot(slot1));
            submittedCards.add(getCardBySlot(slot2));
            submittedCards.add(getCardBySlot(slot3));
            submittedCards.add(getCardBySlot(slot4));
            submittedCards.add(getCardBySlot(slot5));

            for (int i = 0; i< submittedCards.size(); i++){
                System.out.println(submittedCards.get(i).getValue());
            }

            cardsGridPane.setVisible(false);

            //resetCardsAndSlots();
        }

    }

    public Card getCardBySlot(Rectangle slot){
        for (int i = 0; i<cardHand.size();i++){
            if(cardHand.get(i).getPosition().equals(slot)){
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

        if(getCardOrSlot(mouseEvent.toString()).getFill() == DODGERBLUE) {
            return;
        }
        Dragboard dragboard = getCardOrSlot(mouseEvent.toString()).startDragAndDrop(TransferMode.ANY);


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
    public ArrayList<Card> getSubmittedCards(){
        return this.submittedCards;
    }
}
