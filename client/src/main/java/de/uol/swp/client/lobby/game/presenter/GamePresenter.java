package de.uol.swp.client.lobby.game.presenter;

import static javafx.scene.paint.Color.*;
import static javafx.scene.paint.Color.LIGHTGREY;

import com.google.common.eventbus.Subscribe;

import de.uol.swp.client.AbstractPresenter;
import de.uol.swp.client.chat.TextChatChannel;
import de.uol.swp.client.chat.messages.NewTextChatMessageReceived;
import de.uol.swp.client.lobby.game.Card;
import de.uol.swp.client.lobby.game.events.SubmitCardsEvent;
import de.uol.swp.client.utils.JsonUtils;
import de.uol.swp.common.game.Position;
import de.uol.swp.common.game.dto.BlockDTO;
import de.uol.swp.common.game.dto.CardDTO;
import de.uol.swp.common.game.dto.GameDTO;
import de.uol.swp.common.game.dto.PlayerDTO;
import de.uol.swp.common.game.enums.CardinalDirection;
import de.uol.swp.common.game.message.GetMapDataResponse;
import de.uol.swp.common.lobby.dto.LobbyDTO;
import de.uol.swp.common.user.User;
import de.uol.swp.common.user.UserDTO;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileNotFoundException;
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
    private JsonUtils jsonUtils;
    private Integer lobbyID;
    private User loggedInUser;
    @FXML private Button readyButton;
    @FXML private GridPane mainGrid;
    @FXML private GridPane rightGrid;
    @FXML private GridPane gameBoard;
    @FXML private GridPane gameBoardWrapper;
    @FXML private GridPane handCardGridPane;
    @FXML private GridPane selectedCardGridPane;
    @FXML private Text player1HP;
    @FXML private Text player2HP;
    @FXML private Text player3HP;
    @FXML private Text player4HP;
    @FXML private Text player5HP;
    @FXML private Text player6HP;
    @FXML private Text player7HP;
    @FXML private Text player8HP;

    @FXML private Text player1Checkpoint;
    @FXML private Text player2Checkpoint;
    @FXML private Text player3Checkpoint;
    @FXML private Text player4Checkpoint;
    @FXML private Text player5Checkpoint;
    @FXML private Text player6Checkpoint;
    @FXML private Text player7Checkpoint;
    @FXML private Text player8Checkpoint;

    @FXML private Text player1RobotLives;
    @FXML private Text player2RobotLives;
    @FXML private Text player3RobotLives;
    @FXML private Text player4RobotLives;
    @FXML private Text player5RobotLives;
    @FXML private Text player6RobotLives;
    @FXML private Text player7RobotLives;
    @FXML private Text player8RobotLives;
    @FXML private Text winnerIs;

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
    @FXML private Rectangle card1;
    @FXML private Rectangle card2;
    @FXML private Rectangle card3;
    @FXML private Rectangle card4;
    @FXML private Rectangle card5;
    @FXML private Rectangle card6;
    @FXML private Rectangle card7;
    @FXML private Rectangle card8;
    @FXML private Rectangle card9;
    @FXML private Rectangle chosenCard1;
    @FXML private Rectangle chosenCard2;
    @FXML private Rectangle chosenCard3;
    @FXML private Rectangle chosenCard4;
    @FXML private Rectangle chosenCard5;

    @FXML private Text text_card1;

    @FXML private Text text_card2;

    @FXML private Text text_card3;

    @FXML private Text text_card4;

    @FXML private Text text_card5;

    @FXML private Text text_card6;

    @FXML private Text text_card7;

    @FXML private Text text_card8;

    @FXML private Text text_card9;

    @FXML private Text text_chosenCard1;

    @FXML private Text text_chosenCard2;

    @FXML private Text text_chosenCard3;

    @FXML private Text text_chosenCard4;

    @FXML private Text text_chosenCard5;

    @FXML private ImageView markField;
    @FXML private GridPane player2Grid;
    @FXML private GridPane player3Grid;
    @FXML private GridPane player4Grid;
    @FXML private GridPane player5Grid;
    @FXML private GridPane player6Grid;
    @FXML private GridPane player7Grid;
    @FXML private GridPane player8Grid;
    @FXML private TextArea chatOutput;
    @FXML private TextField chatInput;
    @FXML private TextArea historyOutput;
    Map<Rectangle, CardDTO> cardsMap = new LinkedHashMap<>();
    Map<Rectangle, CardDTO> chosenCardsMap = new LinkedHashMap<>();

    Map<Rectangle, Text> cardValues = new LinkedHashMap<>();
    ArrayList<Card> cardHand = new ArrayList<>();
    ArrayList<Card> submittedCards = new ArrayList<>();
    private LobbyDTO lobby;
    private ArrayList<User> users = new ArrayList<User>();

    private Map<UserDTO, PlayerDTO> userDTOPlayerDTOMap = new HashMap<>();
    private Map<UserDTO, ImageView> userRobotImageViewReference = new HashMap<>();
    private int playerCount;
    private boolean playerReady = false;
    private Map<UserDTO, Integer> userToPositionInStackPanes = new HashMap<>();
    private ArrayList<StackPane> playerReadyStackPanes;
    private ArrayList<Text> playerHpTexts;
    private ArrayList<Text> playerCpTexts;
    private ArrayList<Text> playerRlTexts;
    private ArrayList<ImageView> playerCards;
    private BlockDTO[][] board;
    private TextChatChannel textChat;
    private TextChatChannel textHistory;

    @FXML private Button robotOffButton;
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
     * @param lobby LobbyDTO Object containing all the information of the lobby
     * @author Moritz Scheer, Tommy Dang, Jann Erik Bruns, Maxim Erden
     * @since 2023-03-23
     */
    public void init(int lobbyID, LobbyDTO lobby, GameDTO game, UserDTO loggedInUser) {
        this.lobbyID = lobbyID;
        this.lobby = lobby;
        this.textChat = new TextChatChannel(lobby.getTextChatID(), eventBus);

        for (PlayerDTO playerDTO : game.getPlayers()) {
            this.userDTOPlayerDTOMap.put(playerDTO.getUser(), playerDTO);
        }
        this.playerCount = this.userDTOPlayerDTOMap.size();

        // TODO: ADD LOGGEDINUSER
        this.loggedInUser = loggedInUser;

        LOG.debug("LoggedInUser", this.loggedInUser);

        readyButton.setText("Submit Cards");
        readyButton.setDisable(true);
        readyButton.setStyle(
                "-fx-background-color: green;-fx-text-fill: #C0C0C0;-fx-background-radius: 5;");
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
        playerHpTexts.add(player1HP);
        playerHpTexts.add(player2HP);
        playerHpTexts.add(player3HP);
        playerHpTexts.add(player4HP);
        playerHpTexts.add(player5HP);
        playerHpTexts.add(player6HP);
        playerHpTexts.add(player7HP);
        playerHpTexts.add(player8HP);

        // last checkpoint
        playerCpTexts = new ArrayList<Text>();
        playerCpTexts.add(player1Checkpoint);
        playerCpTexts.add(player2Checkpoint);
        playerCpTexts.add(player3Checkpoint);
        playerCpTexts.add(player4Checkpoint);
        playerCpTexts.add(player5Checkpoint);
        playerCpTexts.add(player6Checkpoint);
        playerCpTexts.add(player7Checkpoint);
        playerCpTexts.add(player8Checkpoint);

        // life tokens
        playerRlTexts = new ArrayList<Text>();
        playerRlTexts.add(player1RobotLives);
        playerRlTexts.add(player2RobotLives);
        playerRlTexts.add(player3RobotLives);
        playerRlTexts.add(player4RobotLives);
        playerRlTexts.add(player5RobotLives);
        playerRlTexts.add(player6RobotLives);
        playerRlTexts.add(player7RobotLives);
        playerRlTexts.add(player8RobotLives);

        playerCards = new ArrayList<ImageView>();
        playerCards.add(player2Card);
        playerCards.add(player3Card);
        playerCards.add(player4Card);
        playerCards.add(player5Card);
        playerCards.add(player6Card);
        playerCards.add(player7Card);
        playerCards.add(player8Card);

        // create users list, minus the loggedInUser
        LOG.debug("Loading players");
        loadPlayers(playerGrids, playerNames);

        // TODO: load cards

        cardsMap.put(card1, null);
        cardsMap.put(card2, null);
        cardsMap.put(card3, null);
        cardsMap.put(card4, null);
        cardsMap.put(card5, null);
        cardsMap.put(card6, null);
        cardsMap.put(card7, null);
        cardsMap.put(card8, null);
        cardsMap.put(card9, null);

        chosenCardsMap.put(chosenCard1, null);
        chosenCardsMap.put(chosenCard2, null);
        chosenCardsMap.put(chosenCard3, null);
        chosenCardsMap.put(chosenCard4, null);
        chosenCardsMap.put(chosenCard5, null);

        cardValues.put(card1, text_card1);
        cardValues.put(card2, text_card2);
        cardValues.put(card3, text_card3);
        cardValues.put(card4, text_card4);
        cardValues.put(card5, text_card5);
        cardValues.put(card6, text_card6);
        cardValues.put(card7, text_card7);
        cardValues.put(card8, text_card8);
        cardValues.put(card9, text_card9);
        cardValues.put(chosenCard1, text_chosenCard1);
        cardValues.put(chosenCard2, text_chosenCard2);
        cardValues.put(chosenCard3, text_chosenCard3);
        cardValues.put(chosenCard4, text_chosenCard4);
        cardValues.put(chosenCard5, text_chosenCard5);

        resetCardsAndSlots();
    }

    /**
     * Handles the player list Simplify it from init
     *
     * @author Maria Andrade
     * @since 2023-05-06
     */
    private void loadPlayers(ArrayList<GridPane> playerGrids, ArrayList<Text> playerNames) {
        int count = 0;
        for (Map.Entry<UserDTO, PlayerDTO> player : this.userDTOPlayerDTOMap.entrySet()) {
            PlayerDTO playerDTO = player.getValue();
            if (!Objects.equals(loggedInUser.getUsername(), playerDTO.getUser().getUsername())) {
                playerGrids.get(count).setVisible(true);
                playerNames.get(count).setText(playerDTO.getUser().getUsername());
                playerCpTexts
                        .get(count)
                        .setText(String.valueOf(playerDTO.getRobotDTO().getLastCheckpoint()));
                playerHpTexts
                        .get(count)
                        .setText(String.valueOf(playerDTO.getRobotDTO().getDamageToken()));
                playerRlTexts
                        .get(count)
                        .setText(String.valueOf(playerDTO.getRobotDTO().getLifeToken()));
                userToPositionInStackPanes.put(playerDTO.getUser(), count);
                count++; // only counts when it is not the current user, to avoid empty grid
            }
        }
    }

    /**
     * Handles GetMapDataMessage
     *
     * @param msg the GetMapDataMessage object seen on the EventBus
     * @author Maria Andrade and Tommy Dang
     * @see GetMapDataResponse
     * @since 2023-05-06
     */
    public void reloadMap(GetMapDataResponse msg) {
        Platform.runLater(
                () -> {
                    this.board = msg.getBoardImageIds();
                    try {
                        for (int i = 0; i < board.length; i++) {
                            // gameBoard.addColumn(i);
                            ColumnConstraints gameBoardColum = new ColumnConstraints();
                            gameBoardColum.setHalignment(HPos.CENTER);
                            gameBoard.getColumnConstraints().add(gameBoardColum);
                        }

                        for (int i = 0; i < board[0].length; i++) {
                            // gameBoard.addRow(i);
                            RowConstraints gameBoardRow = new RowConstraints();
                            gameBoardRow.setValignment(VPos.CENTER);
                            gameBoard.getRowConstraints().add(gameBoardRow);
                        }

                        for (int row = 0; row < board.length; row++) {
                            for (int col = 0; col < board[row].length; col++) {
                                int[] images = board[row][col].getBlockImages();
                                for (int img = 0; img < images.length; img++) {
                                    ImageView imageView =
                                            jsonUtils.searchInTileJSON(String.valueOf(images[img]));
                                    imageView.setRotate(
                                            board[row][col].getBlockImagesDirection()[img].ordinal()
                                                    * 90); // Rotate the image
                                    imageView
                                            .fitWidthProperty()
                                            .bind(
                                                    gameBoardWrapper
                                                            .heightProperty()
                                                            .divide(board.length + 1));
                                    imageView
                                            .fitHeightProperty()
                                            .bind(
                                                    gameBoardWrapper
                                                            .heightProperty()
                                                            .divide(board[0].length + 1));
                                    gameBoard.add(imageView, row + 1, col + 1);
                                }
                            }
                        }

                        Position startPosition = msg.getCheckPoint1Position();
                        LOG.debug("startPosition {} {}", startPosition.x, startPosition.y);

                        // update robot position in board
                        for (Map.Entry<UserDTO, PlayerDTO> player :
                                this.userDTOPlayerDTOMap.entrySet()) {
                            // show this player robot, since they all start in checkpoint 1
                            int robotID = player.getValue().getRobotDTO().getRobotID();
                            ImageView imageView = jsonUtils.getRobotImage(robotID);
                            imageView.setRotate(
                                    (player.getValue().getRobotDTO().getDirection().ordinal())
                                            * 90);
                            imageView
                                    .fitWidthProperty()
                                    .bind(
                                            gameBoardWrapper
                                                    .heightProperty()
                                                    .divide(board.length + 1)
                                                    .subtract(10));
                            imageView
                                    .fitHeightProperty()
                                    .bind(
                                            gameBoardWrapper
                                                    .heightProperty()
                                                    .divide(board[0].length + 1)
                                                    .subtract(10));

                            gameBoard.add(imageView, startPosition.x + 1, startPosition.y + 1);

                            this.userRobotImageViewReference.put(player.getKey(), imageView);
                        }

                        /**
                         * Helps to resize the rectangles of the cards and makes it more automatic
                         *
                         * @author Tommy Dang
                         * @since 2023-05-23
                         */
                        double widthOfRightGrid = 5.5; // 5.5 gut
                        double heightOfHandCardGridPane = 2.2; // 2.2 gut
                        double heightOfSelectedCardGridPane = 1.2; // 1.2 gut

                        for (Map.Entry<Rectangle, CardDTO> handCards : cardsMap.entrySet()) {
                            handCards
                                    .getKey()
                                    .widthProperty()
                                    .bind(rightGrid.widthProperty().divide(widthOfRightGrid));
                            handCards
                                    .getKey()
                                    .heightProperty()
                                    .bind(
                                            handCardGridPane
                                                    .heightProperty()
                                                    .divide(heightOfHandCardGridPane));
                        }
                        for (Map.Entry<Rectangle, CardDTO> chosenCards :
                                chosenCardsMap.entrySet()) {
                            chosenCards
                                    .getKey()
                                    .widthProperty()
                                    .bind(rightGrid.widthProperty().divide(widthOfRightGrid));
                            chosenCards
                                    .getKey()
                                    .heightProperty()
                                    .bind(
                                            selectedCardGridPane
                                                    .heightProperty()
                                                    .divide(heightOfSelectedCardGridPane));
                        }

                        /**
                         * Helps to align the Card priority text in the cards
                         *
                         * <p>In the programming cards is a white box, where the value of the
                         * priority is. This helps to correctly align the text into the card Needs
                         * to separate handcards and selected cards because of their different
                         * sizes.
                         *
                         * @author Tommy Dang
                         * @since 2023-05-23
                         */
                        for (Map.Entry<Rectangle, Text> handCardsText : cardValues.entrySet()) {
                            handCardsText
                                    .getValue()
                                    .translateYProperty()
                                    .bind(
                                            selectedCardGridPane
                                                    .heightProperty()
                                                    .divide(8.1)
                                                    .subtract(3.5)); // 8.1 / 3.5
                        }

                        text_chosenCard1
                                .translateYProperty()
                                .bind(handCardGridPane.heightProperty().divide(11.4).subtract(6));
                        text_chosenCard2
                                .translateYProperty()
                                .bind(handCardGridPane.heightProperty().divide(11.4).subtract(6));
                        text_chosenCard3
                                .translateYProperty()
                                .bind(handCardGridPane.heightProperty().divide(11.4).subtract(6));
                        text_chosenCard4
                                .translateYProperty()
                                .bind(handCardGridPane.heightProperty().divide(11.4).subtract(6));
                        text_chosenCard5
                                .translateYProperty()
                                .bind(handCardGridPane.heightProperty().divide(11.4).subtract(6));

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    @FXML
    public void onCardClicked(MouseEvent click) {
        LOG.debug("CARD CLICKED");
        if (chosenCardsMap.containsValue(null)) {
            setNotReadyWhileAllCardsWereNotChosen();

            // simplify
            for (Map.Entry<Rectangle, CardDTO> slotz : chosenCardsMap.entrySet()) {
                if (slotz.getValue() == null) {
                    // click.getSource() is a Rectangle
                    switchTwoCardsOrSlots((Rectangle) click.getSource(), slotz.getKey());
                    break;
                }
            }
            if (!chosenCardsMap.containsValue(null)) readyButton.setDisable(false);

        } else {
            LOG.debug("NO MORE SLOTS AVAILABLE");
        }
    }

    @FXML
    // Clickevent auf deinen Slotbereich
    public void onSlotClicked(MouseEvent click) {
        LOG.debug("SLOT CLICKED");
        if (chosenCardsMap.get((Rectangle) click.getSource()) != null) {
            setNotReadyWhileAllCardsWereNotChosen();
            for (Map.Entry<Rectangle, CardDTO> cardz : cardsMap.entrySet()) {
                if (cardz.getValue() == null) {
                    switchTwoCardsOrSlots((Rectangle) click.getSource(), cardz.getKey());
                    break;
                }
            }
        }
    }

    // TODO: this method can be simplified
    // Gibt dir aus einem Target.toString() den passenden Slot aus
    public Rectangle getCardOrSlot(String click) {

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

        // change Values
        String copyStart = cardValues.get(start).getText();
        cardValues.get(start).setText(cardValues.get(end).getText());
        cardValues.get(end).setText(copyStart);

        // change pictures
        Rectangle copy = new Rectangle();
        copy.setFill(start.getFill());

        start.setFill(end.getFill());
        end.setFill(copy.getFill());

        if (start.toString().contains("card") && end.toString().contains("chosenCard")) {
            CardDTO from = cardsMap.get(start);
            CardDTO to = chosenCardsMap.get(end);

            cardsMap.replace(start, to);
            chosenCardsMap.replace(end, from);

        } else if (start.toString().contains("chosenCard") && end.toString().contains("card")) {
            CardDTO from = chosenCardsMap.get(start);
            CardDTO to = cardsMap.get(end);

            chosenCardsMap.replace(start, to);
            cardsMap.replace(end, from);

        } else if (start.toString().contains("card") && end.toString().contains("card")) {
            CardDTO from = cardsMap.get(start);
            CardDTO to = cardsMap.get(end);

            cardsMap.replace(start, to);
            cardsMap.replace(end, from);
        } else if (start.toString().contains("chosenCard")
                && end.toString().contains("chosenCard")) {
            CardDTO from = chosenCardsMap.get(start);
            CardDTO to = chosenCardsMap.get(end);

            chosenCardsMap.replace(start, to);
            chosenCardsMap.replace(end, from);
        } else {
            // some weird case
            LOG.debug("IS THIS CORRECT????");
        }
    }

    /**
     * Reset chosenCardsSlots
     *
     * @author Moritz
     * @since 2023-05-06
     */
    public void resetCardsAndSlots() {
        // submittedCards.clear();

        for (Map.Entry<Rectangle, CardDTO> slotz : chosenCardsMap.entrySet()) {
            if (slotz.getKey() != null) {
                chosenCardsMap.replace(slotz.getKey(), null);
                slotz.getKey().setFill(LIGHTGREY);
            }
        }

        for (Map.Entry<Rectangle, Text> cardText : cardValues.entrySet()) {
            cardText.getValue().setText("");
        }
    }

    /**
     * Implement cards based on response with given ids to each player
     *
     * @author Maria Andrade
     * @since 2023-05-18
     */
    public void setReceivedCards(List<CardDTO> receivedCards) {

        for (Map.Entry<Rectangle, CardDTO> chosenCard : chosenCardsMap.entrySet()) {
            chosenCard.getKey().setDisable(false);
        }
        for (Map.Entry<Rectangle, CardDTO> handCard : cardsMap.entrySet()) {
            handCard.getKey().setDisable(false);
        }
        for (CardDTO receivedCard : receivedCards) {
            for (Map.Entry<Rectangle, CardDTO> cardSlot : cardsMap.entrySet()) {
                if (cardSlot.getValue() == null) {
                    cardSlot.getKey().setFill(jsonUtils.getCardImageById(receivedCard.getID()));
                    cardValues
                            .get(cardSlot.getKey())
                            .setText(String.valueOf(receivedCard.getPriority()));
                    cardsMap.replace(cardSlot.getKey(), receivedCard);
                    break;
                }
            }
        }
    }

    @FXML
    public void onSubmit(MouseEvent mouseEvent) {
        //        if (slots.containsValue(false) == false) {
        //            submittedCards.add(getCardBySlot(chosenCard1));
        //            submittedCards.add(getCardBySlot(chosenCard2));
        //            submittedCards.add(getCardBySlot(chosenCard3));
        //            submittedCards.add(getCardBySlot(chosenCard4));
        //            submittedCards.add(getCardBySlot(chosenCard5));
        //
        //            for (int i = 0; i < submittedCards.size(); i++) {
        //                System.out.println(submittedCards.get(i).getValue());
        //            }
        //
        //
        //            resetCardsAndSlots();
        //        }

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
        setNotReadyWhileAllCardsWereNotChosen();
        switchTwoCardsOrSlots(getCardOrSlot(d.getString()), getCardOrSlot(event.toString()));

        if (!chosenCardsMap.containsValue(null)) {
            readyButton.setDisable(false);
        }
    }

    @FXML
    public void dragEntered(MouseEvent mouseEvent) {

        if (getCardOrSlot(mouseEvent.toString()).getFill() == LIGHTGREY) {
            return;
        }
        Dragboard dragboard =
                getCardOrSlot(mouseEvent.toString()).startDragAndDrop(TransferMode.ANY);
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
    private void setAllPlayersNotReady() { // to implement onNextRoundMessage
        for (int i = 0; i < playerCount; i++) {
            playerReadyStackPanes
                    .get(i)
                    .setStyle("-fx-background-color: red;-fx-background-radius: 5");
        }
    }

    /**
     * Setting ready status of the user
     *
     * @author Jann Erik Bruns
     * @since 2023-05-05
     */
    public void setPlayerReadyStatus(
            UserDTO playerIsReady) { // To implement onPlayerReadyChangedMessage
        if (Objects.equals(playerIsReady, loggedInUser)) {
            readyButton.setDisable(true);
        } else {
            // TODO: for now keep it only to set ready
            int position = userToPositionInStackPanes.get(playerIsReady);
            playerReadyStackPanes
                    .get(position)
                    .setStyle("-fx-background-color: green;-fx-background-radius: 5");
        }

        //        String style;
        //        if (ready)
        //            style = "-fx-background-color: green";
        //        else
        //            style = "-fx-background-color: red";
        //
        //        for (int i = 0; i < playerCount; i++) {
        //            if (users.get(i).getUsername() == user.getUsername()) {
        //                playerReadyStackPanes.get(i).setStyle(style);
        //                break;
        //            }
        //        }
    }

    /**
     * Setting health points of the user
     *
     * @author Jann Erik Bruns, Maria
     * @since 2023-05-05
     */
    private void setPlayerHP(PlayerDTO playerDTO) { // To implement onPlayerHPChangedMessage
        LOG.debug("in setPlayerHP " + playerDTO.getUser().getUsername() + "   ");
        if (Objects.equals(playerDTO.getUser(), loggedInUser)) {
            playerHpTexts.get(0).setText(String.valueOf(playerDTO.getRobotDTO().getDamageToken()));
            return;
        }
        int position = userToPositionInStackPanes.get(playerDTO.getUser());
        playerHpTexts
                .get(position)
                .setText(String.valueOf(playerDTO.getRobotDTO().getDamageToken()));
    }

    /**
     * Setting roboter health points of the user
     *
     * @author Jann Erik Bruns, Maria
     * @since 2023-05-05
     */
    private void setRoboterHP(PlayerDTO playerDTO) { // To implement onPlayerHPChangedMessage
        if (Objects.equals(playerDTO.getUser(), loggedInUser)) {
            playerRlTexts.get(0).setText(String.valueOf(playerDTO.getRobotDTO().getLifeToken()));
            return;
        }
        int position = userToPositionInStackPanes.get(playerDTO.getUser());
        playerRlTexts.get(position).setText(String.valueOf(playerDTO.getRobotDTO().getLifeToken()));
    }

    /**
     * Setting Checkpoint of the user
     *
     * @author Jann Erik Bruns, Maria
     * @since 2023-05-05
     */
    private void setPlayerCheckpoint(PlayerDTO playerDTO) { // To implement onPlayerHPChangedMessage
        if (Objects.equals(playerDTO.getUser(), loggedInUser)) {
            playerCpTexts
                    .get(0)
                    .setText(String.valueOf(playerDTO.getRobotDTO().getLastCheckpoint()));
            return;
        }
        int position = userToPositionInStackPanes.get(playerDTO.getUser());
        playerCpTexts
                .get(position)
                .setText(String.valueOf(playerDTO.getRobotDTO().getLastCheckpoint()));
    }

    @FXML
    private void onReadyButtonPressed(ActionEvent actionEvent) {
        // TODO: mabye change READY  to SUBMIT
        if (!playerReady) {
            LOG.debug("Submitting chosen cards");
            readyButton.setStyle(
                    "-fx-background-color: gray;-fx-text-fill: #C0C0C0;-fx-background-radius: 5;");
            readyButton.setText("Submitted");
            playerReady = true;

            // submit cards when ready is clicked
            List<CardDTO> chosenCards = new ArrayList<>(chosenCardsMap.values());
            eventBus.post(
                    new SubmitCardsEvent(this.lobbyID, (UserDTO) this.loggedInUser, chosenCards));
        } else {
            readyButton.setStyle(
                    "-fx-background-color: green;-fx-text-fill: #C0C0C0;-fx-background-radius: 5;");
            readyButton.setText("Submit Cards");
            playerReady = false;
        }
    }

    /**
     * Prevent Player from sending requests while all cards were not yet chosen
     *
     * @author Maria Eduarda
     * @since 2023-05-18
     */
    private void setNotReadyWhileAllCardsWereNotChosen() {
        readyButton.setStyle(
                "-fx-background-color: green;-fx-text-fill: #C0C0C0;-fx-background-radius: 5;");
        readyButton.setText("Submit Cards");
        readyButton.setDisable(true);
        playerReady = false;
    }

    /**
     * Block choosenCards and availableCards
     *
     * @author Maria Andrade
     * @since 2023-05-23
     */
    public void blockPlayerCardsAfterSubmit(UserDTO playerReady) {
        if (Objects.equals(playerReady, this.loggedInUser)) {
            // remove available cards

            for (Map.Entry<Rectangle, CardDTO> card : cardsMap.entrySet()) {
                if (card.getKey() != null) {
                    cardsMap.replace(card.getKey(), null);
                    card.getKey().setFill(RED);

                    // remove text
                    cardValues.get(card.getKey()).setText("");
                }
                card.getKey().setDisable(true);
            }
            // block chosen cards
            for (Map.Entry<Rectangle, CardDTO> card : chosenCardsMap.entrySet()) {
                card.getKey().setDisable(true);
            }
        }
    }

    /**
     * Setting playercard of the user
     *
     * @author Jann Erik Bruns
     * @since 2023-05-05
     */
    public void setPlayerCard(
            Map<UserDTO, CardDTO> userDTOCardDTOMap) { // To implement onPlayerHPChangedMessage
        for (Map.Entry<UserDTO, CardDTO> userCurrentCard : userDTOCardDTOMap.entrySet()) {
            if (Objects.equals(userCurrentCard.getKey(), this.loggedInUser)) {
                LOG.debug(
                        "Current User is logged In, should skip "
                                + userCurrentCard.getKey().getUsername());
                continue;
            }

            int position = userToPositionInStackPanes.get(userCurrentCard.getKey());
            playerCards
                    .get(position)
                    .setImage(jsonUtils.getCardImage(userCurrentCard.getValue().getID()));
            playerCards.get(position).setFitHeight(150);
            playerCards.get(position).setFitWidth(100);
        }

        //        User user = users.get(0);
        //        for (int i = 0; i < playerCount; i++) {
        //            if (users.get(i).getUsername() == user.getUsername()) {
        //                //TODO: set Player Card
        //                playerCards.get(i).setImage(new Image(""));//to implement
        //                playerCards.get(i).setFitHeight(150);
        //                playerCards.get(i).setFitWidth(100);
        //                break;
        //            }
        //        }
    }

    @FXML
    private void onRobotOffButtonPressed(ActionEvent actionEvent) {}

    /**
     * Update robot states every time server sends a message
     *
     * @author Maria Andrade
     * @see de.uol.swp.common.game.message.ShowRobotMovingMessage
     * @since 2023-05-20
     */
    public void updateRobotState(PlayerDTO playerDTO) {

        LOG.debug("in updateRobotState");
        LOG.debug("user {}", playerDTO.getUser().getUsername());
        LOG.debug("robotID {}", playerDTO.getRobotDTO().getRobotID());
        LOG.debug("gameBoard {}", gameBoard);
        LOG.debug(
                "newPosition x = {} y = {}",
                playerDTO.getRobotDTO().getPosition().x,
                playerDTO.getRobotDTO().getPosition().y);
        LOG.debug("newDirection {}", playerDTO.getRobotDTO().getDirection());
        int robotID = playerDTO.getRobotDTO().getRobotID();
        UserDTO userToUpdate = playerDTO.getUser();
        Position newPos = playerDTO.getRobotDTO().getPosition();
        CardinalDirection newDir = playerDTO.getRobotDTO().getDirection();

        // set new info after this robot suffered from lasers and might have died
        setPlayerHP(playerDTO);
        setRoboterHP(playerDTO);
        setPlayerCheckpoint(playerDTO);

        // only create new image if robot is alive
        if (playerDTO.getRobotDTO().isAlive())
            Platform.runLater(
                    () -> {
                        // show this player robot, since they all start in checkpoint 1
                        Position prevPosition =
                                this.userDTOPlayerDTOMap
                                        .get(userToUpdate)
                                        .getRobotDTO()
                                        .getPosition();
                        LOG.debug(
                                "old Position to delete x = {} y = {}",
                                prevPosition.x,
                                prevPosition.y);
                        removeNodeByRowColumnIndex(
                                prevPosition.x + 1,
                                prevPosition.y + 1,
                                this.userRobotImageViewReference.get(userToUpdate));

                        ImageView imageView = jsonUtils.getRobotImage(robotID);
                        imageView.setRotate((newDir.ordinal()) * 90); // Rotate the image
                        imageView
                                .fitWidthProperty()
                                .bind(
                                        gameBoardWrapper
                                                .heightProperty()
                                                .divide(board.length + 1)
                                                .subtract(10));
                        imageView
                                .fitHeightProperty()
                                .bind(
                                        gameBoardWrapper
                                                .heightProperty()
                                                .divide(board[0].length + 1)
                                                .subtract(10));
                        gameBoard.add(imageView, newPos.x + 1, newPos.y + 1);

                        // Update new position
                        this.userDTOPlayerDTOMap
                                .get(userToUpdate)
                                .getRobotDTO()
                                .setPosition(newPos);
                        this.userDTOPlayerDTOMap
                                .get(userToUpdate)
                                .getRobotDTO()
                                .setDirection(newDir);
                        this.userRobotImageViewReference.replace(userToUpdate, imageView);
                    });
        else {
            // try to remove last position where robot was
            Platform.runLater(
                    () -> {
                        this.userRobotImageViewReference.get(playerDTO.getUser());
                        Position prevPosition =
                                this.userDTOPlayerDTOMap
                                        .get(playerDTO.getUser())
                                        .getRobotDTO()
                                        .getPosition();
                        LOG.debug(
                                "old Position to delete x = {} y = {}",
                                prevPosition.x,
                                prevPosition.y);
                        if (!Objects.equals(
                                this.userRobotImageViewReference.get(playerDTO.getUser()), null))
                            removeNodeByRowColumnIndex(
                                    prevPosition.x + 1,
                                    prevPosition.y + 1,
                                    this.userRobotImageViewReference.get(playerDTO.getUser()));
                        this.userRobotImageViewReference.replace(playerDTO.getUser(), null);
                    });
        }
    }

    public void animateBoardElements(List<PlayerDTO> playerDTOList) {
        // TODO ANIMATION
        // all info is in PlayerDTO, current Positions and current Directions as well the UserDTO
        // TODO: remove this temporary code

        LOG.debug("in animateBoardElements");

        for (PlayerDTO playerDTO : playerDTOList) {
            if (playerDTO.getRobotDTO().isAlive()) {
                LOG.debug("user {}", playerDTO.getUser().getUsername());
                LOG.debug(
                        "robotID {}",
                        this.userDTOPlayerDTOMap
                                .get(playerDTO.getUser())
                                .getRobotDTO()
                                .getRobotID());
                LOG.debug("gameBoard {}", gameBoard);
                Position newPos = playerDTO.getRobotDTO().getPosition();
                CardinalDirection newDir = playerDTO.getRobotDTO().getDirection();
                LOG.debug("newPosition x = {} y = {}", newPos.x, newPos.y);
                LOG.debug("newDirection {}", newDir);
                int robotID =
                        this.userDTOPlayerDTOMap
                                .get(playerDTO.getUser())
                                .getRobotDTO()
                                .getRobotID();
                Position prevPosition =
                        this.userDTOPlayerDTOMap
                                .get(playerDTO.getUser())
                                .getRobotDTO()
                                .getPosition();
                ImageView imageView = jsonUtils.getRobotImage(robotID);
                UserDTO userToUpdate = playerDTO.getUser();

                Platform.runLater(
                        () -> {
                            LOG.debug(
                                    "old Position to delete x = {} y = {}",
                                    prevPosition.x,
                                    prevPosition.y);
                            removeNodeByRowColumnIndex(
                                    prevPosition.x + 1,
                                    prevPosition.y + 1,
                                    this.userRobotImageViewReference.get(playerDTO.getUser()));
                            // TODO: we might have to fix all robots images facing north
                            // +3 is just a workaround
                            imageView.setRotate((newDir.ordinal()) * 90); // Rotate the image
                            imageView
                                    .fitWidthProperty()
                                    .bind(
                                            gameBoardWrapper
                                                    .heightProperty()
                                                    .divide(board.length + 1)
                                                    .subtract(10));
                            imageView
                                    .fitHeightProperty()
                                    .bind(
                                            gameBoardWrapper
                                                    .heightProperty()
                                                    .divide(board[0].length + 1)
                                                    .subtract(10));
                            gameBoard.add(imageView, newPos.x + 1, newPos.y + 1);

                            // Update new position
                            this.userDTOPlayerDTOMap
                                    .get(userToUpdate)
                                    .getRobotDTO()
                                    .setPosition(newPos);
                            this.userDTOPlayerDTOMap
                                    .get(userToUpdate)
                                    .getRobotDTO()
                                    .setDirection(newDir);
                            this.userRobotImageViewReference.replace(userToUpdate, imageView);
                        });
            } else {
                // TODO: there is one step between not dead and dead misisng to be shown
                // try to remove last position where robot was
                Platform.runLater(
                        () -> {
                            this.userRobotImageViewReference.get(playerDTO.getUser());
                            Position prevPosition =
                                    this.userDTOPlayerDTOMap
                                            .get(playerDTO.getUser())
                                            .getRobotDTO()
                                            .getPosition();
                            LOG.debug(
                                    "old Position to delete x = {} y = {}",
                                    prevPosition.x,
                                    prevPosition.y);
                            if (!Objects.equals(
                                    this.userRobotImageViewReference.get(playerDTO.getUser()),
                                    null))
                                removeNodeByRowColumnIndex(
                                        prevPosition.x + 1,
                                        prevPosition.y + 1,
                                        this.userRobotImageViewReference.get(playerDTO.getUser()));
                            this.userRobotImageViewReference.replace(playerDTO.getUser(), null);
                        });
            }

            // update
            setPlayerHP(playerDTO);
            setRoboterHP(playerDTO);
            setPlayerCheckpoint(playerDTO);
        }
    }

    /**
     * Remove last ImageView from the board when robot moves
     *
     * @author Maria Andrade
     * @see de.uol.swp.common.game.message.ShowRobotMovingMessage
     * @since 2023-05-20
     */
    public void removeNodeByRowColumnIndex(final int row, final int column, ImageView toRemove) {
        ObservableList<Node> childrens = gameBoard.getChildren();
        if (Objects.equals(toRemove, null)) {
            LOG.debug("REMOVING NODE: but it is NULL");
            return;
        }
        LOG.debug("REMOVING NODE: row {} col {} img {}", row, column, toRemove.toString());
        gameBoard.getChildren().remove(toRemove);
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

    public void updateHistoryMessage(String message) {
        historyOutput.appendText(message);
        historyOutput.setWrapText(true);
        Platform.runLater(
                () -> {
                    historyOutput.setScrollTop(Double.MAX_VALUE);
                });
    }
}
