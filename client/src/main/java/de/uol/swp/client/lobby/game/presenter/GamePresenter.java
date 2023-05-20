package de.uol.swp.client.lobby.game.presenter;

import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import de.uol.swp.client.AbstractPresenter;
import de.uol.swp.client.chat.TextChatChannel;
import de.uol.swp.client.chat.messages.NewTextChatMessageReceived;
import de.uol.swp.client.utils.JsonUtils;
import de.uol.swp.common.game.Position;
import de.uol.swp.common.game.dto.BlockDTO;
import de.uol.swp.common.game.dto.CardDTO;
import de.uol.swp.common.game.dto.GameDTO;
import de.uol.swp.common.game.dto.PlayerDTO;
import de.uol.swp.common.game.message.GetMapDataResponse;
import de.uol.swp.common.user.User;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import de.uol.swp.common.user.UserDTO;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.StackPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javafx.scene.shape.Rectangle;

import java.io.FileNotFoundException;
import java.util.*;

import static javafx.scene.paint.Color.*;

import de.uol.swp.client.AbstractPresenter;
import de.uol.swp.client.lobby.game.Card;
import de.uol.swp.common.lobby.dto.LobbyDTO;
import de.uol.swp.common.user.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
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

import static javafx.scene.paint.Color.DODGERBLUE;

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
    private JsonUtils jsonUtils;
    private Integer lobbyID;
    private User loggedInUser;
    @FXML
    private Button readyButton;
    @FXML
    private GridPane mainGrid;
    @FXML
    private GridPane gameBoard;

    @FXML
    private GridPane gameBoardWrapper;


    @FXML
    private Text player2HP;
    @FXML
    private Text player3HP;
    @FXML
    private Text player4HP;
    @FXML
    private Text player5HP;
    @FXML
    private Text player6HP;
    @FXML
    private Text player7HP;
    @FXML
    private Text player8HP;

    @FXML
    private Text player2Checkpoint;
    @FXML
    private Text player3Checkpoint;
    @FXML
    private Text player4Checkpoint;
    @FXML
    private Text player5Checkpoint;
    @FXML
    private Text player6Checkpoint;
    @FXML
    private Text player7Checkpoint;
    @FXML
    private Text player8Checkpoint;

    @FXML
    private Text player2RobotLives;
    @FXML
    private Text player3RobotLives;
    @FXML
    private Text player4RobotLives;
    @FXML
    private Text player5RobotLives;
    @FXML
    private Text player6RobotLives;
    @FXML
    private Text player7RobotLives;
    @FXML
    private Text player8RobotLives;

    @FXML
    private StackPane player2Ready;
    @FXML
    private StackPane player3Ready;
    @FXML
    private StackPane player4Ready;
    @FXML
    private StackPane player5Ready;
    @FXML
    private StackPane player6Ready;
    @FXML
    private StackPane player7Ready;
    @FXML
    private StackPane player8Ready;

    @FXML
    private Text player2Name;
    @FXML
    private Text player3Name;
    @FXML
    private Text player4Name;
    @FXML
    private Text player5Name;
    @FXML
    private Text player6Name;
    @FXML
    private Text player7Name;
    @FXML
    private Text player8Name;

    @FXML
    private ImageView player2Card;
    @FXML
    private ImageView player3Card;
    @FXML
    private ImageView player4Card;
    @FXML
    private ImageView player5Card;
    @FXML
    private ImageView player6Card;
    @FXML
    private ImageView player7Card;
    @FXML
    private ImageView player8Card;
    @FXML
    private Rectangle card1;
    @FXML
    private Rectangle card2;
    @FXML
    private Rectangle card3;
    @FXML
    private Rectangle card4;
    @FXML
    private Rectangle card5;
    @FXML
    private Rectangle card6;
    @FXML
    private Rectangle card7;
    @FXML
    private Rectangle card8;
    @FXML
    private Rectangle card9;
    @FXML
    private Rectangle chosenCard1;
    @FXML
    private Rectangle chosenCard2;
    @FXML
    private Rectangle chosenCard3;
    @FXML
    private Rectangle chosenCard4;
    @FXML
    private Rectangle chosenCard5;
    @FXML
    private ImageView markField;
    @FXML
    private GridPane player2Grid;
    @FXML
    private GridPane player3Grid;
    @FXML
    private GridPane player4Grid;
    @FXML
    private GridPane player5Grid;
    @FXML
    private GridPane player6Grid;
    @FXML
    private GridPane player7Grid;
    @FXML
    private GridPane player8Grid;
    @FXML private TextArea chatOutput;
    @FXML private TextField chatInput;
    Map<Rectangle, Boolean> cards = new LinkedHashMap<>();
    Map<Rectangle, Boolean> slots = new LinkedHashMap<>();
    ArrayList<Card> cardHand = new ArrayList<>();
    static ArrayList<Card> cardDeck = new ArrayList<>();
    ArrayList<Card> submittedCards = new ArrayList<>();
    private LobbyDTO lobby;
    private ArrayList<User> users = new ArrayList<User>();

    private List<PlayerDTO> playersDTO;
    private int playerCount;
    private boolean playerReady = false;
    private ArrayList<StackPane> playerReadyStackPanes;
    private ArrayList<Text> playerHpTexts;
    private ArrayList<Text> playerCpTexts;
    private ArrayList<Text> playerRlTexts;
    private ArrayList<ImageView> playerCards;
    private BlockDTO[][] board;
    private TextChatChannel textChat;
    @FXML
    private Button robotOffButton;
    private int x = 2;
    private int y = 2;

    /**
     * Default Constructor
     *
     * @since 2022-03-12
     */
    public GamePresenter() throws FileNotFoundException {
        this.jsonUtils = new JsonUtils();
    }

    /**
     * Method to initialize the game view
     *
     * <p>This method creates a board with the given information and adds all images to the board.
     *
     * @param lobbyID the Integer identifier of the lobby
     * @param lobby   LobbyDTO Object containing all the information of the lobby
     * @author Moritz Scheer, Tommy Dang, Jann Erik Bruns, Maxim Erden
     * @since 2023-03-23
     */
    public void init(int lobbyID, LobbyDTO lobby, GameDTO game, UserDTO loggedInUser) {
        this.lobbyID = lobbyID;
        this.lobby = lobby;
        this.textChat = new TextChatChannel(lobby.getTextChatID(),eventBus);
        this.playersDTO = game.getPlayers();

        //TODO: ADD LOGGEDINUSER
        this.loggedInUser = loggedInUser;

        LOG.debug("LoggedInUser", this.loggedInUser);


        readyButton.setText("Not Ready");
        robotOffButton.setText("Turn Robot OFF");

        ArrayList<GridPane> playerGrids = new ArrayList<GridPane>();
        playerGrids.add(player2Grid);
        playerGrids.add(player3Grid);
        playerGrids.add(player4Grid);
        playerGrids.add(player5Grid);
        playerGrids.add(player6Grid);
        playerGrids.add(player7Grid);
        playerGrids.add(player8Grid);

        ArrayList<Text> playerNames = new ArrayList<Text>();
        playerNames.add(player2Name);
        playerNames.add(player3Name);
        playerNames.add(player4Name);
        playerNames.add(player5Name);
        playerNames.add(player6Name);
        playerNames.add(player7Name);
        playerNames.add(player8Name);

        playerReadyStackPanes = new ArrayList<StackPane>();
        playerReadyStackPanes.add(player2Ready);
        playerReadyStackPanes.add(player3Ready);
        playerReadyStackPanes.add(player4Ready);
        playerReadyStackPanes.add(player5Ready);
        playerReadyStackPanes.add(player6Ready);
        playerReadyStackPanes.add(player7Ready);
        playerReadyStackPanes.add(player8Ready);

        // damage tokens -> refers to how many cards a player receives
        playerHpTexts = new ArrayList<Text>();
        playerHpTexts.add(player2HP);
        playerHpTexts.add(player3HP);
        playerHpTexts.add(player4HP);
        playerHpTexts.add(player5HP);
        playerHpTexts.add(player6HP);
        playerHpTexts.add(player7HP);
        playerHpTexts.add(player8HP);

        // last checkpoint
        playerCpTexts = new ArrayList<Text>();
        playerCpTexts.add(player2Checkpoint);
        playerCpTexts.add(player3Checkpoint);
        playerCpTexts.add(player4Checkpoint);
        playerCpTexts.add(player5Checkpoint);
        playerCpTexts.add(player6Checkpoint);
        playerCpTexts.add(player7Checkpoint);
        playerCpTexts.add(player8Checkpoint);

        // life tokens
        playerRlTexts = new ArrayList<Text>();
        playerRlTexts.add(player2RobotLives);
        playerRlTexts.add(player3RobotLives);
        playerRlTexts.add(player4RobotLives);
        playerRlTexts.add(player5RobotLives);
        playerRlTexts.add(player6RobotLives);
        playerRlTexts.add(player7RobotLives);
        playerRlTexts.add(player8RobotLives);

        // create users list, minus the loggedInUser
        LOG.debug("Loading players");
        loadPlayers(playerGrids, playerNames);

        // TODO: load cards

        cards.put(card1, false);
        cards.put(card2, false);
        cards.put(card3, false);
        cards.put(card4, false);
        cards.put(card5, false);
        cards.put(card6, false);
        cards.put(card7, false);
        cards.put(card8, false);
        cards.put(card9, false);

        slots.put(chosenCard1, false);
        slots.put(chosenCard2, false);
        slots.put(chosenCard3, false);
        slots.put(chosenCard4, false);
        slots.put(chosenCard5, false);

//        markField.setPreserveRatio(true);
//        markField.setFitHeight(50);
//        markField.setFitWidth(50);
//        markField.setImage(image);

        // creates the board
        //reloadMap(null);

        resetCardsAndSlots();
    }


    /**
     * Handles the player list
     * Simplify it from init
     *
     * @author Maria Andrade
     * @since 2023-05-06
     */
    private void loadPlayers(ArrayList<GridPane> playerGrids, ArrayList<Text> playerNames) {
        int count = 0;
        for (PlayerDTO playerDTO : this.playersDTO) {
            if (!Objects.equals(loggedInUser.getUsername(), playerDTO.getUser().getUsername())) {
                playerGrids.get(count).setVisible(true);
                playerNames.get(count).setText(playerDTO.getUser().getUsername());
                playerCpTexts.get(count).setText(
                        String.valueOf(playerDTO.getRobotDTO().getLastCheckpoint()));
                playerHpTexts.get(count).setText(
                        String.valueOf(playerDTO.getRobotDTO().getDamageToken()));
                playerRlTexts.get(count).setText(
                        String.valueOf(playerDTO.getRobotDTO().getLifeToken()));
                count++; // only counts when it is not the current user, to avoid empty grid
            }
        }
    }

    /**
     * Handles GetMapDataMessage
     *
     * @param msg the GetMapDataMessage object seen on the EventBus
     * @author Maria Andrade
     * @see GetMapDataResponse
     * @since 2023-05-06
     */
    public void reloadMap(GetMapDataResponse msg) {
        Platform.runLater(
                () -> {
                    this.board = msg.getBoardImageIds();
                    try {
                        for (int i = 0; i < board.length; i++) {
                            //gameBoard.addColumn(i);
                            ColumnConstraints gameBoardColum = new ColumnConstraints();
                            gameBoardColum.setHalignment(HPos.CENTER);
                            gameBoard.getColumnConstraints().add(gameBoardColum);
                        }

                        for (int i = 0; i < board[0].length; i++) {
                            //gameBoard.addRow(i);
                            RowConstraints gameBoardRow = new RowConstraints();
                            gameBoardRow.setValignment(VPos.CENTER);
                            gameBoard.getRowConstraints().add(gameBoardRow);
                        }

                        for (int row = 0; row < board.length; row++) {
                            for (int col = 0; col < board[row].length; col++) {
                                int[] images = board[row][col].getBlockImages();
                                for (int img = 0; img < images.length; img++) {
                                    ImageView imageView = jsonUtils.searchInTileJSON(String.valueOf(images[img]));
                                    imageView.setRotate(board[row][col].getBlockImagesDirection()[img].ordinal() * 90); // Rotate the image
                                    imageView.fitWidthProperty().bind(gameBoardWrapper.heightProperty().divide(board.length + 1));
                                    imageView.fitHeightProperty().bind(gameBoardWrapper.heightProperty().divide(board[0].length + 1));
                                    gameBoard.add(imageView, row + 1, col + 1);
                                }
                            }
                        }

                        Position startPosition = msg.getCheckPoint1Position();
                        LOG.debug("startPosition {} {}", startPosition.x, startPosition.y);

                        // show this player robot, since they all start in checkpoint 1
                        for(PlayerDTO playerDTO: this.playersDTO){
                            if(Objects.equals(playerDTO.getUser(), this.loggedInUser)){
                                ImageView imageView = jsonUtils.getRobotImage(
                                        playerDTO.getRobotDTO().getRobotID());
                                imageView.fitWidthProperty().bind(gameBoardWrapper.heightProperty().divide(board.length + 1).subtract(10));
                                imageView.fitHeightProperty().bind(gameBoardWrapper.heightProperty().divide(board[0].length + 1).subtract(10));
                                gameBoard.add(imageView, startPosition.x +1, startPosition.y +1);
                                break;
                            }
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    @FXML
    public void onCardClicked(MouseEvent click) {
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

    @FXML
    //Clickevent auf deinen Slotbereich
    public void onSlotClicked(MouseEvent click) {

        for (Map.Entry<Rectangle, Boolean> slotz : slots.entrySet()) {
            if (slotz.getKey().equals(getCardOrSlot(click.toString())) && slotz.getValue() == true) {
                for (Map.Entry<Rectangle, Boolean> cardz : cards.entrySet()) {
                    if (cardz.getValue() == false) {
                        switchTwoCardsOrSlots(getCardOrSlot(click.toString()), cardz.getKey());
                        break;
                    }
                }
                break;
            }
        }
    }

    //Gibt dir aus einem Target.toString() den passenden Slot aus
    public Rectangle getCardOrSlot(String click) {

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
        else if (click.contains("chosenCard1")) {
            return chosenCard1;
        } else if (click.contains("chosenCard2")) {
            return chosenCard2;
        } else if (click.contains("chosenCard3")) {
            return chosenCard3;
        } else if (click.contains("chosenCard4")) {
            return chosenCard4;
        } else if (click.contains("chosenCard5")) {
            return chosenCard5;
        }
        return null;
    }

    // Tauscht 2 Karten miteinander egal welche
    public void switchTwoCardsOrSlots(Rectangle start, Rectangle end) {

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
        boolean endBool = getSwitchTwoCardsOrSlotsBoolean(end);

        if (start.toString().contains("card")) {
            cards.replace(start, endBool);
        } else if (start.toString().contains("chosenCard")) {
            slots.replace(start, endBool);
        }

        if (end.toString().contains("card")) {
            cards.replace(end, startBool);
        } else if (end.toString().contains("chosenCard")) {
            slots.replace(end, startBool);
        }


        //change cardpositions
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
    public boolean getSwitchTwoCardsOrSlotsBoolean(Rectangle cardslot) {
        for (Map.Entry<Rectangle, Boolean> cardz : cards.entrySet()) {
            if (cardz.getKey().equals(cardslot)) {
                return cardz.getValue();
            }
        }
        for (Map.Entry<Rectangle, Boolean> slotz : slots.entrySet()) {
            if (slotz.getKey().equals(cardslot)) {
                return slotz.getValue();
            }
        }
        return false;
    }

    public ArrayList<Card> newCardDeck() {

        ArrayList<Card> cards = new ArrayList<>();
        for (int i = 10; i <= 840; i = i + 10) {
            cards.add(new Card(i));
        }
        Collections.shuffle(cards);
        return cards;
    }

    /**
     * Reset slots
     *
     * @author Moritz
     * @since 2023-05-06
     */
    public void resetCardsAndSlots() {
        cardHand.clear();
        submittedCards.clear();

        for (Map.Entry<Rectangle, Boolean> cardz : cards.entrySet()) {
            if (cardz.getKey() != null) {
                cards.replace(cardz.getKey(), false);
                cardz.getKey().setFill(DODGERBLUE);
            }

        }

        for (Map.Entry<Rectangle, Boolean> slotz : slots.entrySet()) {
            if (slotz.getKey() != null) {
                slots.replace(slotz.getKey(), false);
                slotz.getKey().setFill(DODGERBLUE);
            }
        }
    }

    /** Implement cards based on response with given ids to each player
     *
     * @author Maria Andrade
     * @since 2023-05-18
     */
    public void setReceivedCards(List<CardDTO> receivedCards){
        for (CardDTO receivedCard: receivedCards) {
            for (Map.Entry<Rectangle, Boolean> cardSlot : cards.entrySet()) {
                if (!cardSlot.getValue() && cardSlot.getKey() != null) {
                    cards.replace(cardSlot.getKey(), true);
                    cardSlot.getKey().setFill(
                            jsonUtils.getCardImageById(receivedCard.getID())
                    );
                    // TODO: implement cardHand?
                    // cardHand.add(cardDeck.get(0));
                    break;
                }
            }
        }
    }

    @FXML
    public void onSubmit(MouseEvent mouseEvent) {

        if (slots.containsValue(false) == false) {
            submittedCards.add(getCardBySlot(chosenCard1));
            submittedCards.add(getCardBySlot(chosenCard2));
            submittedCards.add(getCardBySlot(chosenCard3));
            submittedCards.add(getCardBySlot(chosenCard4));
            submittedCards.add(getCardBySlot(chosenCard5));

            for (int i = 0; i < submittedCards.size(); i++) {
                System.out.println(submittedCards.get(i).getValue());
            }


            resetCardsAndSlots();
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

    @FXML
    public void dragDropped(DragEvent event) {
        event.acceptTransferModes(TransferMode.ANY);
        System.out.println(event.toString());

        Dragboard d = event.getDragboard();
        System.out.println(d.getString());


        switchTwoCardsOrSlots(getCardOrSlot(d.getString()), getCardOrSlot(event.toString()));

    }

    @FXML
    public void dragEntered(MouseEvent mouseEvent) {

        if (getCardOrSlot(mouseEvent.toString()).getFill() == DODGERBLUE) {
            return;
        }
        Dragboard dragboard = getCardOrSlot(mouseEvent.toString()).startDragAndDrop(TransferMode.ANY);


        ClipboardContent content = new ClipboardContent();

        content.putString(mouseEvent.toString());
        dragboard.setContent(content);

    }

    @FXML
    public void dragOver(DragEvent event) {
        event.acceptTransferModes(TransferMode.ANY);
    }

    /*
    @Author Maxim Erden
     */
    public ArrayList<Card> getSubmittedCards() {
        return this.submittedCards;
    }

    /**
     * Setting all players to not ready
     *
     * @author Jann Erik Bruns
     * @since 2023-05-05
     */
    private void setAllPlayersNotReady() {//to implement onNextRoundMessage
        for (int i = 0; i < playerCount; i++) {
            playerReadyStackPanes.get(i).setStyle("-fx-background-color: red");
        }
    }

    /**
     * Setting ready status of the user
     *
     * @author Jann Erik Bruns
     * @since 2023-05-05
     */
    private void setPlayerReadyStatus() { //To implement onPlayerReadyChangedMessage
        User user = users.get(0);
        boolean ready = false;
        String style;
        if (ready)
            style = "-fx-background-color: green";
        else
            style = "-fx-background-color: red";

        for (int i = 0; i < playerCount; i++) {
            if (users.get(i).getUsername() == user.getUsername()) {
                playerReadyStackPanes.get(i).setStyle(style);
                break;
            }
        }
    }

    /**
     * Setting health points of the user
     *
     * @author Jann Erik Bruns
     * @since 2023-05-05
     */
    private void setPlayerHP() {  //To implement onPlayerHPChangedMessage
        User user = users.get(0);
        for (int i = 0; i < playerCount; i++) {
            if (users.get(i).getUsername() == user.getUsername()) {
                playerHpTexts.get(i).setText("1");//to implement
                break;
            }
        }
    }

    /**
     * Setting roboter health pojnts of the user
     *
     * @author Jann Erik Bruns
     * @since 2023-05-05
     */
    private void setRoboterHP() {//To implement onPlayerHPChangedMessage
        User user = users.get(0);
        for (int i = 0; i < playerCount; i++) {
            if (users.get(i).getUsername() == user.getUsername()) {
                //TODO: Robot HP
                playerRlTexts.get(i).setText("1");//to implement
                break;
            }
        }
    }

    @FXML
    private void onReadyButtonPressed(ActionEvent actionEvent) {

        if (!playerReady) {
            readyButton.setStyle("-fx-background-color: green;-fx-text-fill: #C0C0C0;-fx-background-radius: 5;");
            readyButton.setText("Ready");
            playerReady = true;
        } else {
            readyButton.setStyle("-fx-background-color: red;-fx-text-fill: #C0C0C0;-fx-background-radius: 5;");
            readyButton.setText("Not Ready");
            playerReady = false;
        }
    }
    /**
     * Setting Checkpoint of the user
     *
     * @author Jann Erik Bruns
     * @since 2023-05-05
     */
    private void setPlayerCheckpoint() {//To implement onPlayerHPChangedMessage
        User user = users.get(0);
        for (int i = 0; i < playerCount; i++) {
            if (users.get(i).getUsername() == user.getUsername()) {
                //TODO Checkpoint
                playerCpTexts.get(i).setText("1");//to implement
                break;
            }
        }
    }

    /**
     * Setting playercard of the user
     *
     * @author Jann Erik Bruns
     * @since 2023-05-05
     */
    private void setPlayerCard() {//To implement onPlayerHPChangedMessage
        User user = users.get(0);
        for (int i = 0; i < playerCount; i++) {
            if (users.get(i).getUsername() == user.getUsername()) {
                //TODO: set Player Card
                playerCards.get(i).setImage(new Image(""));//to implement
                playerCards.get(i).setFitHeight(150);
                playerCards.get(i).setFitWidth(100);
                break;
            }
        }
    }



    @FXML
    private void onRobotOffButtonPressed(ActionEvent actionEvent) {

    }

    @FXML
    private void textChatInputKeyPressed(KeyEvent actionEvent) {
        if (actionEvent.getCode() == KeyCode.ENTER) {
            if (chatInput.getLength() != 0 && !chatInput.getText().isBlank()) {
                textChat.sendTextMessage(chatInput.getText());
                chatInput.setText("");
            }
        }
    }

    @Subscribe
    public void onNewTextChatMessage(NewTextChatMessageReceived message) {
        if (textChat == null) return;
        chatOutput.setText(textChat.getChatString());
        chatOutput.appendText("");
        chatOutput.setWrapText(true);
        Platform.runLater(
                () -> {
                    chatOutput.setScrollTop(Double.MAX_VALUE);
                });
    }

}

