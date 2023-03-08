package de.uol.swp.client.game.presenter;

import com.google.inject.Inject;
import de.uol.swp.client.AbstractPresenter;
import de.uol.swp.client.game.Card;
import de.uol.swp.client.lobby.LobbyService;
import de.uol.swp.client.tab.TabPresenter;
import de.uol.swp.common.user.User;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.*;
import javafx.scene.layout.StackPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javafx.scene.shape.Rectangle;
import java.util.*;
import static javafx.scene.paint.Color.*;

/**
 * Manages the Lobby window
 *
 * @author Moritz Scheer
 * @see AbstractPresenter
 * @since 2022-11-15
 */
@SuppressWarnings("UnstableApiUsage")
public class GamePresenter extends AbstractPresenter {

    public static final String FXML = "/fxml/GameView.fxml";
    private static final Logger LOG = LogManager.getLogger(GamePresenter.class);

    private User loggedInUser;

    private Integer lobbyID;
    private String lobbyName;
    private User owner;
    private ObservableList<String> users;
    private String password;
    private Boolean multiplayer;

    @Inject private LobbyService lobbyService;
    @Inject private TabPresenter tabPresenter;

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
    @FXML private Button data;

    Map<Rectangle, Boolean> cards = new LinkedHashMap<>();
    Map<Rectangle, Boolean> slots = new LinkedHashMap<>();
    Map<Label, Rectangle> valueLabels = new LinkedHashMap<>();

    static ArrayList<Card> cardDeck = new ArrayList<>();
    ArrayList<Card> cardHand = new ArrayList<>();
    ArrayList<Card> submittedCards = new ArrayList<>();

    public GamePresenter() {
        /*
            Label a = new Label("500");
            // a.setTranslateX(card2.getTranslateX()+32);
            // a.setTranslateY(card2.getTranslateY()-41);
            a.setAlignment(Pos.CENTER);
            a.setTextFill(BLACK);
            handCards.getChildren().add(a);
        */
    }

    public void init() {
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

   //Clickevent auf deinen Kartenbereich
    public void onCardClicked (MouseEvent click) {
        if (slots.containsValue(false)) {
            for (Map.Entry<Rectangle, Boolean> cardz : cards.entrySet()) {
                if (cardz.getKey().equals(getCardOrSlot(click.toString())) && cardz.getValue() == true) {
                    for (Map.Entry<Rectangle, Boolean> slotz : slots.entrySet()) {
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

        for( Map.Entry<Rectangle, Boolean> slotz : slots.entrySet()){
            if(slotz.getKey().equals(getCardOrSlot(click.toString())) && slotz.getValue() == true){
                for( Map.Entry<Rectangle, Boolean> cardz : cards.entrySet()){
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
public Rectangle getCardOrSlot(String click){

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
    public void switchTwoCardsOrSlots(Rectangle start, Rectangle end){

                    System.out.println(start.toString());
                    System.out.println(end.toString());
                    System.out.println("---------------");

                    //change pictures
                    Rectangle copy = new Rectangle();
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
    public boolean getSwitchTwoCardsOrSlotsBoolean(Rectangle cardslot){
        for( Map.Entry<Rectangle, Boolean> cardz : cards.entrySet()) {
            if(cardz.getKey().equals(cardslot)){
                return cardz.getValue();
            }
        }
        for( Map.Entry<Rectangle, Boolean> slotz : slots.entrySet()) {
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

        resetCardsAndSlots();
    }

    public void resetCardsAndSlots(){
       // if(loggedInUser == owner) {
            cardDeck = newCardDeck();
            System.out.println("KartenDeck größe " + cardDeck.size());
        //}
        cardHand.clear();
        submittedCards.clear();


                for (Map.Entry<Rectangle, Boolean> cardz : cards.entrySet()) {
                    if(cardz.getKey() != null){
                        cards.replace(cardz.getKey(), false);
                        cardz.getKey().setFill(DODGERBLUE);
                        System.out.print("1");
                    }

                }

                for (Map.Entry<Rectangle, Boolean> slotz : slots.entrySet()) {
                    if(slotz.getKey() != null){
                        slots.replace(slotz.getKey(), false);
                        slotz.getKey().setFill(DODGERBLUE);
                        System.out.print("2");
                    }
                }

                for (int i = 0; i < 9; i++) {
                    for (Map.Entry<Rectangle, Boolean> cardSlot : cards.entrySet()) {
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

    public void onSubmit(MouseEvent mouseEvent) {

        if(slots.containsValue(false) == false) {
        submittedCards.add(getCardBySlot(slot1));
        submittedCards.add(getCardBySlot(slot2));
        submittedCards.add(getCardBySlot(slot3));
        submittedCards.add(getCardBySlot(slot4));
        submittedCards.add(getCardBySlot(slot5));

        for (int i = 0; i< submittedCards.size(); i++){
            System.out.println(submittedCards.get(i).getValue());
        }


             resetCardsAndSlots();
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

