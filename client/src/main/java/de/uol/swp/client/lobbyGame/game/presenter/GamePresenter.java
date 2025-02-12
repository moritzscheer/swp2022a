package de.uol.swp.client.lobbyGame.game.presenter;

import static javafx.scene.paint.Color.*;
import static javafx.scene.paint.Color.LIGHTGREY;

import com.google.common.eventbus.Subscribe;

import de.uol.swp.client.AbstractPresenter;
import de.uol.swp.client.chat.TextChatChannel;
import de.uol.swp.client.chat.messages.NewTextChatMessageReceived;
import de.uol.swp.client.lobbyGame.game.Card;
import de.uol.swp.client.lobbyGame.game.events.RequestDistributeCardsEvent;
import de.uol.swp.client.lobbyGame.game.events.RobotTurnOffEvent;
import de.uol.swp.client.lobbyGame.game.events.SubmitCardsEvent;
import de.uol.swp.client.utils.JsonUtils;
import de.uol.swp.common.game.Position;
import de.uol.swp.common.game.dto.*;
import de.uol.swp.common.game.enums.CardinalDirection;
import de.uol.swp.common.lobby.dto.LobbyDTO;
import de.uol.swp.common.user.User;
import de.uol.swp.common.user.UserDTO;

import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
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
import javafx.util.Duration;

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
    @FXML private HBox player1Robot;
    @FXML private VBox player2Robot;
    @FXML private VBox player3Robot;
    @FXML private VBox player4Robot;
    @FXML private VBox player5Robot;
    @FXML private VBox player6Robot;
    @FXML private VBox player7Robot;
    @FXML private VBox player8Robot;

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
    @FXML private Text textPlayer2Card;
    @FXML private Text textPlayer3Card;
    @FXML private Text textPlayer4Card;
    @FXML private Text textPlayer5Card;
    @FXML private Text textPlayer6Card;
    @FXML private Text textPlayer7Card;
    @FXML private Text textPlayer8Card;

    @FXML private ImageView markField;
    @FXML private GridPane player2Grid;
    @FXML private GridPane player3Grid;
    @FXML private GridPane player4Grid;
    @FXML private GridPane player5Grid;
    @FXML private GridPane player6Grid;
    @FXML private GridPane player7Grid;
    @FXML private GridPane player8Grid;
    @FXML private GridPane player2CardWrapper;
    @FXML private GridPane player3CardWrapper;
    @FXML private GridPane player4CardWrapper;
    @FXML private GridPane player5CardWrapper;
    @FXML private GridPane player6CardWrapper;
    @FXML private GridPane player7CardWrapper;
    @FXML private GridPane player8CardWrapper;
    @FXML private GridPane player2CardImageHolder;
    @FXML private GridPane player3CardImageHolder;
    @FXML private GridPane player4CardImageHolder;
    @FXML private GridPane player5CardImageHolder;
    @FXML private GridPane player6CardImageHolder;
    @FXML private GridPane player7CardImageHolder;
    @FXML private GridPane player8CardImageHolder;
    @FXML private TextArea chatOutput;
    @FXML private TextField chatInput;
    @FXML private GridPane ButtonWrapper;
    @FXML private GridPane playerGrid1Wrapper;
    @FXML private GridPane playerGrid2Wrapper;
    @FXML private GridPane playerGrid3Wrapper;
    @FXML private GridPane playerGrid4Wrapper;
    @FXML private GridPane playerGrid5Wrapper;
    @FXML private GridPane playerGrid6Wrapper;
    @FXML private GridPane playerGrid7Wrapper;
    @FXML private GridPane playerGrid8Wrapper;
    @FXML private TextArea historyOutput;
    @FXML private ImageView blockedCardIcon1;
    @FXML private ImageView blockedCardIcon2;
    @FXML private ImageView blockedCardIcon3;
    @FXML private ImageView blockedCardIcon4;
    @FXML private ImageView blockedCardIcon5;

    Map<Rectangle, CardDTO> cardsMap = new LinkedHashMap<>();
    Map<Rectangle, CardDTO> chosenCardsMap = new LinkedHashMap<>();

    Map<Rectangle, Text> cardValues = new LinkedHashMap<>();
    private ArrayList<Text> textPlayerXCard = new ArrayList<>();
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
    private ArrayList<GridPane> playerCardImageHolders;
    private ArrayList<GridPane> playerCardWrappers;
    private ArrayList<VBox> playerRobot;
    private ArrayList<GridPane> playerGridWrapper;
    private ArrayList<GridPane> playerGrids;
    private ArrayList<ImageView> blockedCardIcons;
    private ArrayList<Text> playerNames;
    private BlockDTO[][] board;
    private TextChatChannel textChat;
    private TextChatChannel textHistory;
    private List<UserDTO> deadForeverUsers = new ArrayList<>();
    private UserDTO userWonTheGame;

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
    public void init(
            int lobbyID,
            LobbyDTO lobby,
            GameDTO game,
            UserDTO loggedInUser,
            TextChatChannel lobbyChat) {
        this.lobbyID = lobbyID;
        this.lobby = lobby;
        this.textChat = lobbyChat;
        chatOutput.setText(textChat.getChatString());

        for (PlayerDTO playerDTO : game.getPlayers()) {
            this.userDTOPlayerDTOMap.put(playerDTO.getUser(), playerDTO);
        }
        this.playerCount = this.userDTOPlayerDTOMap.size();
        this.loggedInUser = loggedInUser;

        LOG.debug("LoggedInUser", this.loggedInUser);

        readyButton.setText("Submit Cards");
        readyButton.setDisable(true);
        readyButton.setStyle(
                "-fx-background-color: green;-fx-text-fill: #C0C0C0;-fx-background-radius: 5;");
        robotOffButton.setText("Turn Robot OFF");
        robotOffButton.setDisable(true);

        playerGrids = new ArrayList<GridPane>();
        playerGrids.add(player2Grid);
        playerGrids.add(player3Grid);
        playerGrids.add(player4Grid);
        playerGrids.add(player5Grid);
        playerGrids.add(player6Grid);
        playerGrids.add(player7Grid);
        playerGrids.add(player8Grid);

        playerNames = new ArrayList<Text>();
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

        playerCards = new ArrayList<ImageView>();
        playerCards.add(player2Card);
        playerCards.add(player3Card);
        playerCards.add(player4Card);
        playerCards.add(player5Card);
        playerCards.add(player6Card);
        playerCards.add(player7Card);
        playerCards.add(player8Card);

        playerCardWrappers = new ArrayList<GridPane>();
        playerCardWrappers.add(player2CardWrapper);
        playerCardWrappers.add(player3CardWrapper);
        playerCardWrappers.add(player4CardWrapper);
        playerCardWrappers.add(player5CardWrapper);
        playerCardWrappers.add(player6CardWrapper);
        playerCardWrappers.add(player7CardWrapper);
        playerCardWrappers.add(player8CardWrapper);

        playerCardImageHolders = new ArrayList<GridPane>();
        playerCardImageHolders.add(player2CardImageHolder);
        playerCardImageHolders.add(player3CardImageHolder);
        playerCardImageHolders.add(player4CardImageHolder);
        playerCardImageHolders.add(player5CardImageHolder);
        playerCardImageHolders.add(player6CardImageHolder);
        playerCardImageHolders.add(player7CardImageHolder);
        playerCardImageHolders.add(player8CardImageHolder);

        playerRobot = new ArrayList<VBox>();
        playerRobot.add(player2Robot);
        playerRobot.add(player3Robot);
        playerRobot.add(player4Robot);
        playerRobot.add(player5Robot);
        playerRobot.add(player6Robot);
        playerRobot.add(player7Robot);
        playerRobot.add(player8Robot);

        playerGridWrapper = new ArrayList<GridPane>();
        playerGridWrapper.add(playerGrid2Wrapper);
        playerGridWrapper.add(playerGrid3Wrapper);
        playerGridWrapper.add(playerGrid4Wrapper);
        playerGridWrapper.add(playerGrid5Wrapper);
        playerGridWrapper.add(playerGrid6Wrapper);
        playerGridWrapper.add(playerGrid7Wrapper);
        playerGridWrapper.add(playerGrid8Wrapper);

        blockedCardIcons = new ArrayList<ImageView>();
        blockedCardIcons.add(blockedCardIcon1);
        blockedCardIcons.add(blockedCardIcon2);
        blockedCardIcons.add(blockedCardIcon3);
        blockedCardIcons.add(blockedCardIcon4);
        blockedCardIcons.add(blockedCardIcon5);

        // create users list, minus the loggedInUser
        LOG.debug("Loading players");
        loadPlayers();

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

        textPlayerXCard.add(textPlayer2Card);
        textPlayerXCard.add(textPlayer3Card);
        textPlayerXCard.add(textPlayer4Card);
        textPlayerXCard.add(textPlayer5Card);
        textPlayerXCard.add(textPlayer6Card);
        textPlayerXCard.add(textPlayer7Card);
        textPlayerXCard.add(textPlayer8Card);

        resetCardsAndSlots();
        resizeCardsRectangles();

        // load board
        this.board = game.getBoardDTO();
        reloadMap();
        initializeRobotsOnBoard();
    }

    /**
     * Handles the player list and add images next to username Simplify it from init
     *
     * @author Maria Andrade and Tommy Dang
     * @since 2023-06-06
     */
    private void loadPlayers() {
        int count = 0;
        int robotImageID;

        for (Map.Entry<UserDTO, PlayerDTO> player : this.userDTOPlayerDTOMap.entrySet()) {
            PlayerDTO playerDTO = player.getValue();
            robotImageID = playerDTO.getRobotDTO().getRobotID();

            // robot image
            ImageView imageView =
                    jsonUtils.getRobotImage(
                            robotImageID); // this is only to be displayed in the list

            // list
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
                playerRobot.get(count).getChildren().add(imageView);
                playerRobot.get(count).setVisible(true);
                imageView
                        .fitHeightProperty()
                        .bind(playerGridWrapper.get(count).heightProperty().multiply(0.8));
                imageView
                        .fitWidthProperty()
                        .bind(playerGridWrapper.get(count).heightProperty().multiply(0.8));

                count++; // only counts when it is not the current user, to avoid empty grid
            } else {
                // for player is loggedInUser
                player1Robot.getChildren().add(imageView);
                imageView.fitHeightProperty().bind(playerGrid1Wrapper.heightProperty().subtract(4));
                imageView.fitWidthProperty().bind(playerGrid1Wrapper.heightProperty().subtract(4));
            }
        }
    }

    /**
     * reload map based on blockDTO matrix from gameDTO
     *
     * @author Maria Andrade and Tommy Dang
     * @exception Exception
     * @since 2023-05-06
     */
    public void reloadMap() {
        Platform.runLater(
                () -> {
                    try {
                        for (int i = 0; i < board.length; i++) {
                            ColumnConstraints gameBoardColum = new ColumnConstraints();
                            gameBoardColum.setHalignment(HPos.CENTER);
                            gameBoard.getColumnConstraints().add(gameBoardColum);
                        }

                        for (int i = 0; i < board[0].length; i++) {
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
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    /**
     * method the put the robot images on the gameboard
     *
     * @author Maria Anrade, Ole Zimmermann
     * @see de.uol.swp.common.game.dto
     * @see de.uol.swp.common.user
     * @see de.uol.swp.common.game.dto.PlayerDTO
     * @since 2023-06-10
     */
    public void initializeRobotsOnBoard() {
        Platform.runLater(
                () -> {
                    Position startPosition;

                    // update robot position in board
                    for (Map.Entry<UserDTO, PlayerDTO> player :
                            this.userDTOPlayerDTOMap.entrySet()) {
                        startPosition = player.getValue().getRobotDTO().getPosition();
                        LOG.debug("startPosition {} {}", startPosition.x, startPosition.y);

                        // show this player robot, since they all start in checkpoint 1
                        int robotID = player.getValue().getRobotDTO().getRobotID();
                        ImageView imageView = jsonUtils.getRobotImage(robotID);
                        imageView.setRotate(
                                (player.getValue().getRobotDTO().getDirection().ordinal()) * 90);
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
                });
    }

    /**
     * Helps to resize the rectangles of the cards and makes it more automatic
     *
     * @author Tommy Dang
     * @since 2023-05-23
     */
    public void resizeCardsRectangles() {
        double widthOfRightGrid = 5.4; // 5.5 gut
        double heightOfHandCardGridPane = 2.1; // 2.2 gut
        double heightOfSelectedCardGridPane = 1.1; // 1.2 gut

        for (Map.Entry<Rectangle, CardDTO> handCards : cardsMap.entrySet()) {
            handCards
                    .getKey()
                    .widthProperty()
                    .bind(rightGrid.widthProperty().divide(widthOfRightGrid));
            handCards
                    .getKey()
                    .heightProperty()
                    .bind(handCardGridPane.heightProperty().divide(heightOfHandCardGridPane));
        }
        for (Map.Entry<Rectangle, CardDTO> chosenCards : chosenCardsMap.entrySet()) {
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
    }

    /**
     * method when card is clicked
     *
     * @author Maria Anrade, Moritz Scheer
     * @see de.uol.swp.common.game.dto
     * @since 2023-05-19
     */
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

    /**
     * method when on slot is clicked
     *
     * @author Maria Anrade, Moritz Scheer
     * @see de.uol.swp.common.game.dto
     * @since 2023-05-19
     */
    @FXML
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

    /**
     * returns the card or slot
     *
     * @author Maxim Erden
     * @since 2023-03-23
     */
    public Rectangle getCardOrSlot(String click) {

        // checks if its one of the 9 selection cards
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

        // checks if card is in chosen card section
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

    /**
     * method to switch two position of the cards
     *
     * @author Maria Andrade
     * @since 2023-03-23
     */
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
            LOG.debug("No cards are found in the position");
        }
    }

    /**
     * Reset chosenCardsSlots
     *
     * @author Moritz and Tommy Dang
     * @since 2023-06-07
     */
    public void resetCardsAndSlots() {
        int counterForIcon = 0;
        for (Map.Entry<Rectangle, CardDTO> slotz : chosenCardsMap.entrySet()) {
            if (slotz.getKey() != null) {
                chosenCardsMap.replace(slotz.getKey(), null);
                slotz.getKey().setFill(LIGHTGREY);
                slotz.getKey()
                        .setStyle(
                                "-fx-stroke: black; -fx-stroke-width: 1; -fx-arc-height: 7;"
                                        + " -fx-arc-width: 7;");
                blockedCardIcons.get(counterForIcon).setVisible(false);
                counterForIcon++;
            }
        }

        for (Map.Entry<Rectangle, Text> cardText : cardValues.entrySet()) {
            cardText.getValue().setText("");
        }

        // clean the list of players
        for (int i = 0; i < playerCards.size(); i++) {
            playerCards.get(i).setImage(null);
            textPlayerXCard.get(i).setText("");
            playerCardImageHolders.get(i).setVisible(false);
        }
    }

    /**
     * Implement cards based on response with given ids to each player
     *
     * <p>block cards when damageTokens > 4
     *
     * @author Maria Andrade and Tommy Dang
     * @since 2023-06-12
     */
    public void setReceivedCards(List<CardDTO> receivedCards, int freeCards) {
        Collections.sort(receivedCards, Comparator.comparingInt(CardDTO::getID));
        int tmp = freeCards;
        int imageCounter = 0;
        Image blockedCardIcon = new Image("images/cards/blockedCardIcon.PNG");
        for (Map.Entry<Rectangle, CardDTO> chosenCard : chosenCardsMap.entrySet()) {
            boolean blocked = tmp <= 0; // this will block the last cards
            chosenCard.getKey().setDisable(blocked);
            if (blocked) {
                chosenCard
                        .getKey()
                        .setStyle(
                                "-fx-stroke: red; -fx-stroke-width: 1; -fx-arc-height: 7;"
                                        + " -fx-arc-width: 7;");
                this.blockedCardIcons.get(imageCounter).setImage(blockedCardIcon);
                this.blockedCardIcons.get(imageCounter).setFitWidth(20);
                this.blockedCardIcons.get(imageCounter).setFitHeight(20);
                this.blockedCardIcons.get(imageCounter).setVisible(blocked);
            }
            tmp--;
            imageCounter++;
        }
        for (Map.Entry<Rectangle, CardDTO> handCard : cardsMap.entrySet()) {
            handCard.getKey().setDisable(false);
        }
        int countCards = 0;
        for (CardDTO receivedCard : receivedCards) {
            for (Map.Entry<Rectangle, CardDTO> cardSlot : cardsMap.entrySet()) {
                if (countCards >= freeCards) {
                    break;
                }
                if (cardSlot.getValue() == null) {
                    cardSlot.getKey().setFill(jsonUtils.getCardImageById(receivedCard.getID()));
                    cardValues
                            .get(cardSlot.getKey())
                            .setText(String.valueOf(receivedCard.getPriority()));
                    cardsMap.replace(cardSlot.getKey(), receivedCard);
                    break;
                }
            }
            countCards++;
        }
        // block red ones
        for (Map.Entry<Rectangle, CardDTO> cardSlot : cardsMap.entrySet()) {
            if (Objects.equals(cardSlot.getKey().getFill(), RED)) {
                cardSlot.getKey().setDisable(true);
            }
        }
        LOG.debug("countCards " + countCards);
        LOG.debug("freeCards " + freeCards);
        int i = 0;
        if (freeCards < 5) {
            for (Map.Entry<Rectangle, CardDTO> cardLocked : chosenCardsMap.entrySet()) {
                if (freeCards < i + 1) {
                    cardLocked
                            .getKey()
                            .setFill(jsonUtils.getCardImageById(receivedCards.get(i).getID()));
                    cardValues
                            .get(cardLocked.getKey())
                            .setText(String.valueOf(receivedCards.get(i).getPriority()));
                    chosenCardsMap.replace(cardLocked.getKey(), receivedCards.get(i));
                }
                i++;
            }
        }
        if (freeCards == 0) {
            // submit chosen
            // submit cards when ready is clicked
            List<CardDTO> chosenCards = new ArrayList<>(chosenCardsMap.values());
            eventBus.post(
                    new SubmitCardsEvent(this.lobbyID, (UserDTO) this.loggedInUser, chosenCards));
        }
    }

    /**
     * returns the card in slot
     *
     * @author Maxim Erden
     * @since 2023-03-23
     */
    public Card getCardBySlot(Rectangle slot) {
        for (int i = 0; i < cardHand.size(); i++) {
            if (cardHand.get(i).getPosition().equals(slot)) {
                return cardHand.get(i);
            }
        }
        return null;
    }

    /**
     * method when card is drag and then dropped on the slots of received or selected cards
     *
     * @author Maxim Erden, Tommy Dang
     * @since 2023-03-23
     */
    @FXML
    public void dragDropped(DragEvent event) {
        event.acceptTransferModes(TransferMode.ANY);
        Dragboard d = event.getDragboard();
        setNotReadyWhileAllCardsWereNotChosen();
        switchTwoCardsOrSlots(getCardOrSlot(d.getString()), getCardOrSlot(event.toString()));

        if (!chosenCardsMap.containsValue(null)) {
            readyButton.setDisable(false);
        }

        for (Map.Entry<Rectangle, CardDTO> card : cardsMap.entrySet()) {
            if (card.getKey().getFill() == RED) {
                card.getKey().setDisable(true);
            }
        }
    }

    /**
     * method when card is drag and then dropped on the slots of received or selected cards
     *
     * @author Maxim Erden, Tommy Dang
     * @since 2023-03-23
     */
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

        for (Map.Entry<Rectangle, CardDTO> card : cardsMap.entrySet()) {
            if (card.getKey().getFill() == RED) {
                card.getKey().setDisable(true);
            }
        }
    }

    /**
     * method when card is dragged over
     *
     * @author Maxim Erden
     * @since 2023-03-23
     */
    @FXML
    public void dragOver(DragEvent event) {
        event.acceptTransferModes(TransferMode.ANY);
    }

    /**
     * Getter method for submitted cards
     *
     * @author Maxim Erden
     * @since 2023-03-23
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
    public void setAllPlayersNotReady() {
        for (int i = 0; i < playerReadyStackPanes.size(); i++) {
            playerReadyStackPanes
                    .get(i)
                    .setStyle(
                            "-fx-background-color: red;-fx-background-radius: 5;-fx-border-radius:"
                                    + " 5;-fx-border-color: black;");
        }
    }

    /**
     * Setting ready status of the user
     *
     * @author Jann Erik Bruns
     * @since 2023-05-05
     */
    public void setPlayerReadyStatus(UserDTO playerIsReady) {
        if (Objects.equals(playerIsReady, loggedInUser)) {
            readyButton.setDisable(true);
        } else {
            int position = userToPositionInStackPanes.get(playerIsReady);
            playerReadyStackPanes
                    .get(position)
                    .setStyle(
                            "-fx-background-color: green;-fx-background-radius:"
                                    + " 5;-fx-border-radius: 5;-fx-border-color: black;");
        }
    }

    /**
     * Setting health points of the user
     *
     * @author Jann Erik Bruns, Maria
     * @since 2023-05-05
     */
    private void setPlayerHP(PlayerDTO playerDTO) {
        LOG.debug("in setPlayerHP " + playerDTO.getUser().getUsername() + "   ");
        if (Objects.equals(playerDTO.getUser(), loggedInUser)) {
            player1HP.setText(String.valueOf(playerDTO.getRobotDTO().getDamageToken()));
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
    private void setRoboterHP(PlayerDTO playerDTO) {
        if (Objects.equals(playerDTO.getUser(), loggedInUser)) {
            player1RobotLives.setText(String.valueOf(playerDTO.getRobotDTO().getLifeToken()));
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
    private void setPlayerCheckpoint(PlayerDTO playerDTO) {
        if (Objects.equals(playerDTO.getUser(), loggedInUser)) {
            player1Checkpoint.setText(String.valueOf(playerDTO.getRobotDTO().getLastCheckpoint()));
            return;
        }
        int position = userToPositionInStackPanes.get(playerDTO.getUser());
        playerCpTexts
                .get(position)
                .setText(String.valueOf(playerDTO.getRobotDTO().getLastCheckpoint()));
    }

    /**
     * method when ready button is pressed
     *
     * @author Maria Anrade, Tommy Dang
     * @since 2023-03-23
     */
    @FXML
    private void onReadyButtonPressed(ActionEvent actionEvent) {
        if (Objects.equals(readyButton.getText(), "Get Cards")) {
            // create request to get the cards
            eventBus.post(new RequestDistributeCardsEvent(this.lobby, (UserDTO) this.loggedInUser));
            readyButton.setText("Submit Cards");
            readyButton.setDisable(true);
            robotOffButton.setDisable(true);
        } else {
            if (!playerReady) {
                LOG.debug("Submitting chosen cards");
                readyButton.setStyle(
                        "-fx-background-color: gray;-fx-text-fill: #C0C0C0;-fx-background-radius:"
                                + " 5;");
                readyButton.setText("Submitted");
                playerReady = true;

                // submit cards when ready is clicked
                List<CardDTO> chosenCards = new ArrayList<>(chosenCardsMap.values());
                eventBus.post(
                        new SubmitCardsEvent(
                                this.lobbyID, (UserDTO) this.loggedInUser, chosenCards));
            } else {
                readyButton.setStyle(
                        "-fx-background-color: green;-fx-text-fill: #C0C0C0;-fx-background-radius:"
                                + " 5;");
                readyButton.setText("Submit Cards");
                playerReady = false;
            }
        }
    }

    /**
     * Turning the robot off for the next round
     *
     * @author Maria Andrade
     * @since 2023-06-13
     */
    @FXML
    private void onRobotOffButtonPressed(ActionEvent actionEvent) {
        eventBus.post(new RobotTurnOffEvent(this.lobbyID, (UserDTO) this.loggedInUser));
        robotOffButton.setDisable(true);
        readyButton.setDisable(true);
    }

    /**
     * Turns the background color stackpanel of the turnedOffRobot user grey
     *
     * @author Maria Andrade
     * @since 2023-06-13
     */
    public void showRobotTurnedOff(UserDTO turnedOffRobot) {
        // show robot as turned off in the list
        // for all players except the loggedInUser
        if (!Objects.equals(loggedInUser, turnedOffRobot)) {
            playerReadyStackPanes
                    .get(userToPositionInStackPanes.get(turnedOffRobot))
                    .setStyle("-fx-background-color: #adadad");
        }
    }

    /**
     * Enable Robot Button for the gameview
     *
     * @author Tommy Dang
     * @since 2023-06-13
     */
    public void enableRobotButton() {
        Platform.runLater(
                () -> {
                    readyButton.setText("Get Cards");
                    readyButton.setStyle(
                            "-fx-background-color: green;-fx-text-fill:"
                                    + " #C0C0C0;-fx-background-radius: 5;");
                    readyButton.setDisable(false);
                    robotOffButton.setDisable(false);
                });
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
     * @author Maria Andrade and Tommy Dang
     * @since 2023-06-07
     */
    public void blockPlayerCardsAfterSubmit(UserDTO playerReady) {
        if (Objects.equals(playerReady, this.loggedInUser)) {
            // remove available cards
            for (Map.Entry<Rectangle, CardDTO> card : cardsMap.entrySet()) {
                if (card.getKey() != null) {
                    cardsMap.replace(card.getKey(), null);
                    card.getKey().setFill(RED);
                    card.getKey()
                            .setStyle(
                                    "-fx-arc-width: 10; -fx-arc-height: 10; -fx-stroke: black;"
                                            + " -fx-stroke-width: 1;");

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
     * @author Jann Erik Bruns and Maria and Tommy Dang
     * @since 2023-06-12
     */
    public void setPlayerCard(Map<UserDTO, CardDTO> userDTOCardDTOMap) {
        for (Map.Entry<UserDTO, CardDTO> userCurrentCard : userDTOCardDTOMap.entrySet()) {
            if (this.userDTOPlayerDTOMap
                    .get(userCurrentCard.getKey())
                    .getRobotDTO()
                    .isPowerDown()) {
                LOG.debug(
                        "Current User robot is turned off, should skip "
                                + userCurrentCard.getKey().getUsername());
                continue;
            }

            if (Objects.equals(userCurrentCard.getKey(), this.loggedInUser)) {
                LOG.debug(
                        "Current User is logged In, should skip "
                                + userCurrentCard.getKey().getUsername());
                continue;
            }

            /**
             * Setting all player cards of the opponent of the user
             *
             * @author Maria and Tommy Dang
             * @since 2023-06-12
             */
            int position = userToPositionInStackPanes.get(userCurrentCard.getKey());
            playerCards
                    .get(position)
                    .setImage(jsonUtils.getCardImage(userCurrentCard.getValue().getID()));
            textPlayerXCard
                    .get(position)
                    .setText(String.valueOf(userCurrentCard.getValue().getPriority()));
            textPlayerXCard.get(position).setStyle("-fx-font-weight: bold");

            /**
             * Makes the card images responsiv to the size of the window and gives a border to the
             * images
             *
             * @author Tommy Dang
             * @since 2023-06-07
             */
            playerCards
                    .get(position)
                    .fitHeightProperty()
                    .bind(playerCardWrappers.get(position).heightProperty().subtract(5));
            playerCards
                    .get(position)
                    .fitWidthProperty()
                    .bind(playerCardWrappers.get(position).widthProperty().divide(2));
            playerCardImageHolders.get(position).setVisible(true);
            playerCardImageHolders
                    .get(position)
                    .setStyle(
                            "-fx-border-color: blue; -fx-border-radius: 5; -fx-border-insets: -1;"
                                    + " -fx-border-width: 2");
            playerCardImageHolders
                    .get(position)
                    .prefHeightProperty()
                    .bind(playerCardWrappers.get(position).heightProperty().subtract(5));
            playerCardImageHolders
                    .get(position)
                    .prefWidthProperty()
                    .bind(playerCardWrappers.get(position).widthProperty().divide(2));
        }
    }

    /**
     * animate multiple board elements
     *
     * @author Jann Erik Bruns
     * @see de.uol.swp.common.game.message.ShowBoardMovingMessage @Exeption Exception
     * @since 2023-06-13
     */
    public void animateBoardElements(List<PlayerDTO> playerDTOList) {
        Platform.runLater(
                () -> {
                    try {

                        ArrayList<RotateTransition> rotateAnimations = new ArrayList<>();
                        ArrayList<TranslateTransition> moveAnimations = new ArrayList<>();

                        for (PlayerDTO playerDTO : playerDTOList) {
                            if (deadForeverUsers.contains(playerDTO.getUser())
                                || playerDTO.getRobotDTO().getPosition().x < 0
                                || playerDTO.getRobotDTO().getPosition().y < 0
                                || playerDTO.getRobotDTO().getPosition().x >= 12
                                || playerDTO.getRobotDTO().getPosition().y >= 12
                            ) continue;
                            UserDTO userToUpdate = playerDTO.getUser();
                            Position newPos = playerDTO.getRobotDTO().getPosition();
                            Position prevPos =
                                    this.userDTOPlayerDTOMap
                                            .get(userToUpdate)
                                            .getRobotDTO()
                                            .getPosition();
                            int newDir = playerDTO.getRobotDTO().getDirection().ordinal();
                            int prevDir =
                                    this.userDTOPlayerDTOMap
                                            .get(userToUpdate)
                                            .getRobotDTO()
                                            .getDirection()
                                            .ordinal();
                            int robotID =
                                    this.userDTOPlayerDTOMap
                                            .get(userToUpdate)
                                            .getRobotDTO()
                                            .getRobotID();
                            ImageView imageView = jsonUtils.getRobotImage(robotID);

                            Node node = this.userRobotImageViewReference.get(userToUpdate);

                            if (newDir != prevDir) {
                                RotateTransition rotateTransition =
                                        new RotateTransition(Duration.millis(500), node);
                                int degrees = (prevDir - newDir) * -90;
                                if (degrees > 180) degrees = (degrees - 360);
                                else if (degrees < -180) degrees = (degrees + 360);
                                rotateTransition.setByAngle(degrees);
                                rotateTransition.setNode(node);
                                rotateAnimations.add(rotateTransition);
                            } else if (prevPos.x != newPos.x || newPos.y != prevPos.y) {

                                TranslateTransition translateTransition =
                                        new TranslateTransition(Duration.millis(500), node);
                                imageView.setVisible(false);

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
                                gameBoard.layout();

                                double toX = imageView.getLayoutX();
                                double toY = imageView.getLayoutY();

                                double fromX = node.getLayoutX();
                                double fromY = node.getLayoutY();

                                double moveX = 0;
                                double moveY = 0;

                                moveX = fromX - toX;
                                moveY = fromY - toY;

                                if (fromY <= 0 && toY >= 0) moveY = toY + fromY * -1;
                                else if (fromY >= 0 && toY <= 0) moveY = toY - fromY * -1;
                                else if (fromY <= 0 && toY <= 0) moveY = toY + fromY * -1;
                                else if (fromY >= 0 && toY >= 0) moveY = toY - fromY;

                                if (fromX <= 0 && toX >= 0) moveX = toX + fromX * -1;
                                else if (fromX >= 0 && toX <= 0) moveX = toX - fromX * -1;
                                else if (fromX <= 0 && toX <= 0) moveX = toX + fromX * -1;
                                else if (fromX >= 0 && toX >= 0) moveX = toX - fromX;

                                // gameBoard.getChildren().remove(node);
                                // imageView.setVisible(true);

                                translateTransition.setToX(moveX);
                                translateTransition.setToY(moveY);
                                moveAnimations.add(translateTransition);
                            }
                        }
                        int i = 0;
                        int z = 0;
                        for (RotateTransition rotateTransition : rotateAnimations) {
                            i++;
                            if (rotateAnimations.size() == i)
                                rotateTransition.setOnFinished(
                                        e -> updateBoardElements(playerDTOList));
                            rotateTransition.play();
                        }
                        for (TranslateTransition translateTransition : moveAnimations) {
                            z++;
                            if (moveAnimations.size() == z && i == 0)
                                translateTransition.setOnFinished(
                                        e -> updateBoardElements(playerDTOList));
                            translateTransition.play();
                        }
                        if (i == 0 && z == 0) updateBoardElements(playerDTOList);
                    } catch (Exception ex) {
                        LOG.debug("Error while playing animations: " + ex.getMessage());
                    }
                });
    }

    /**
     * update direction of robot images, in case many robots are in checkpoint over each other
     *
     * @author Maria Anrade
     * @since 2023-07-07
     */
    public void updateRobotDirection(PlayerDTO playerDTO) {
        if (Objects.equals(playerDTO.getUser(), loggedInUser)) {
            CardinalDirection newDir = playerDTO.getRobotDTO().getDirection();
            ImageView imageView = (ImageView) player1Robot.getChildren().get(0);
            imageView.setRotate((newDir.ordinal()) * 90);
        } else {
            int pos = userToPositionInStackPanes.get(playerDTO.getUser());
            ImageView imageView = (ImageView) playerRobot.get(pos).getChildren().get(0);
            CardinalDirection newDir = playerDTO.getRobotDTO().getDirection();
            imageView.setRotate((newDir.ordinal()) * 90);
        }
    }

    /**
     * method to update all board elements
     *
     * @author Maria Anrade, Jans Bruns
     * @since 2023-06-13
     */
    public void updateBoardElements(List<PlayerDTO> playerDTOList) {
        LOG.debug("in updateBoardElements");
        for (PlayerDTO playerDTO : playerDTOList) {
            // update
            setPlayerHP(playerDTO);
            setRoboterHP(playerDTO);
            setPlayerCheckpoint(playerDTO);
            updateRobotDirection(playerDTO);

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
                removeNodeByRowColumnIndex(
                        prevPosition.x + 1,
                        prevPosition.y + 1,
                        this.userRobotImageViewReference.get(playerDTO.getUser()));
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
                this.userRobotImageViewReference.replace(userToUpdate, imageView);
                gameBoard.add(imageView, newPos.x + 1, newPos.y + 1);
            } else if (this.userDTOPlayerDTOMap.get(playerDTO.getUser()).getRobotDTO().isAlive()
                    && !playerDTO.getRobotDTO().isAlive()) {
                Position prevPosition =
                        this.userDTOPlayerDTOMap
                                .get(playerDTO.getUser())
                                .getRobotDTO()
                                .getPosition();
                LOG.debug(
                        "old Position to delete x = {} y = {}",
                        prevPosition.x,
                        prevPosition.y);
                // try to remove last position where robot was
                if (!Objects.equals(
                        this.userRobotImageViewReference.get(playerDTO.getUser()), null)){
                    removeNodeByRowColumnIndex(
                            prevPosition.x + 1,
                            prevPosition.y + 1,
                            this.userRobotImageViewReference.get(playerDTO.getUser()));
                    this.userRobotImageViewReference.replace(playerDTO.getUser(), null);
                }
            } else {
                // it was and stays dead
                if (!Objects.equals(
                        this.userRobotImageViewReference.get(playerDTO.getUser()),
                        null))
                    gameBoard
                            .getChildren()
                            .remove(
                                    this.userRobotImageViewReference.get(
                                            playerDTO.getUser()));
                this.userRobotImageViewReference.replace(playerDTO.getUser(), null);

            }

            // update playerDTO with new info in hashmap
            this.userDTOPlayerDTOMap.replace(playerDTO.getUser(), playerDTO);
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

    /**
     * Method when chat input key is pressed
     *
     * @author Finn Oldeboershuis
     * @since 2023-05-15
     */
    @FXML
    private void textChatInputKeyPressed(KeyEvent actionEvent) {
        if (actionEvent.getCode() == KeyCode.ENTER) {
            if (chatInput.getLength() != 0 && !chatInput.getText().isBlank()) {
                textChat.sendTextMessage(chatInput.getText());
                chatInput.setText("");
            }
        }
    }

    /**
     * Method to update chat history when new text arrives
     *
     * @author Finn Oldeboershuis
     * @since 2023-05-15
     */
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

    /**
     * Update the History in the game
     *
     * @author Waldemar Kempel
     * @param message
     * @since 2023-06-02
     */
    public void updateHistoryMessage(String message) {
        historyOutput.appendText(message);
        historyOutput.setWrapText(true);
        Platform.runLater(
                () -> {
                    historyOutput.setScrollTop(Double.MAX_VALUE);
                });
    }

    /**
     * Disables the player's interface and suspends programming card selection
     *
     * @author Maria Andrade, Tommy Dang
     * @param userDied
     * @since 2023-09-09
     */
    public void setRobotDied(UserDTO userDied) {
        deadForeverUsers.add(userDied);
        if (Objects.equals(loggedInUser.getUsername(), userDied.getUsername())) {
            selectedCardGridPane.setDisable(true);
            selectedCardGridPane.setVisible(false);

            handCardGridPane.setDisable(true);
            handCardGridPane.setVisible(false);

            playerGrid1Wrapper.setDisable(true);
            playerGrid1Wrapper.setVisible(false);

            readyButton.setDisable(true);
            readyButton.setVisible(false);

            robotOffButton.setDisable(true);
            robotOffButton.setVisible(false);
        } else {
            int position = this.userToPositionInStackPanes.get(userDied);
            playerGrids.get(position).setDisable(true);
            playerGrids.get(position).setVisible(false);
        }
    }

    /**
     * Respawn robots after round is over
     *
     * @author Maria Andrade
     * @param respawnRobots
     * @since 2023-06-22
     */
    public void respawnDeadRobots(List<PlayerDTO> respawnRobots) {
        Platform.runLater(
                () -> {
                    Position startPosition;

                    // update robot position in board
                    for (PlayerDTO player : respawnRobots) {
                        if (player.getRobotDTO().isDeadForever()) continue;
                        startPosition = player.getRobotDTO().getPosition();
                        LOG.debug("startPosition {} {}", startPosition.x, startPosition.y);

                        // show this player robot, since they all start in checkpoint 1
                        int robotID = player.getRobotDTO().getRobotID();
                        ImageView imageView = jsonUtils.getRobotImage(robotID);
                        imageView.setRotate((player.getRobotDTO().getDirection().ordinal()) * 90);
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
                        this.userRobotImageViewReference.replace(player.getUser(), imageView);
                    }
                });
    }

    /**
     * Block everything when game is over
     *
     * @author Maria Andrade
     * @since 2023-07-04
     */
    public void gameOver() {
        selectedCardGridPane.setDisable(true);
        handCardGridPane.setDisable(true);
        readyButton.setDisable(true);
        robotOffButton.setDisable(true);
    }

    /**
     * Setter
     *
     * @author Maria
     * @since 2023-07-04
     */
    public void setUserWon(UserDTO userWonTheGame) {
        this.userWonTheGame = userWonTheGame;
    }

    /**
     * Getter
     *
     * @author Maria Andrade
     * @since 2023-07-04
     */
    public ImageView getUserWonImage(UserDTO userWonTheGame) {
        int robotID = userDTOPlayerDTOMap.get(userWonTheGame).getRobotDTO().getRobotID();
        ImageView imageView = jsonUtils.getRobotImage(robotID);
        imageView
                .fitWidthProperty()
                .bind(gameBoardWrapper.heightProperty().divide(board.length + 1).subtract(10));
        imageView
                .fitHeightProperty()
                .bind(gameBoardWrapper.heightProperty().divide(board[0].length + 1).subtract(10));
        return imageView;
    }
}
