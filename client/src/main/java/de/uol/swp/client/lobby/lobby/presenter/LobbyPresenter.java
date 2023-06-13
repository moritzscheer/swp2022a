package de.uol.swp.client.lobby.lobby.presenter;

import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;

import de.uol.swp.client.AbstractPresenter;
import de.uol.swp.client.chat.TextChatChannel;
import de.uol.swp.client.chat.messages.NewTextChatMessageReceived;
import de.uol.swp.client.lobby.game.events.RequestStartGameEvent;
import de.uol.swp.client.lobby.lobby.event.SetPlayerReadyEvent;
import de.uol.swp.client.tab.TabPresenter;
import de.uol.swp.common.game.Map;
import de.uol.swp.common.lobby.dto.LobbyDTO;
import de.uol.swp.common.lobby.message.PlayerReadyInLobbyMessage;
import de.uol.swp.common.lobby.message.UserJoinedLobbyMessage;
import de.uol.swp.common.lobby.message.UserLeftLobbyMessage;
import de.uol.swp.common.lobby.request.MapChangeRequest;
import de.uol.swp.common.user.User;
import de.uol.swp.common.user.UserDTO;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Manages the Lobby window
 *
 * @author Moritz Scheer
 * @see de.uol.swp.client.AbstractPresenter
 * @since 2022-11-15
 */
@SuppressWarnings("UnstableApiUsage")
public class LobbyPresenter extends AbstractPresenter {

    public static final String FXML = "/fxml/LobbyView.fxml";
    private static final Logger LOG = LogManager.getLogger(LobbyPresenter.class);

    private User loggedInUser;

    private LobbyDTO lobbyDTO;
    private int lobbyID;
    private String lobbyName;

    private String mapName;
    private User owner;
    private ObservableList<String> users;
    private ObservableList<User> usersNotReady;
    private String password;
    private Boolean multiplayer;
    private Integer slots = 1;
    private TextChatChannel textChat;

    @Inject private TabPresenter tabPresenter;

    @FXML private ListView<String> usersView;
    @FXML private Label textFieldPassword;
    @FXML private Label textFieldLobbyName;
    @FXML private Label textFieldOnlineUsers;
    @FXML private Label textFieldOwner;
    @FXML private Label labelMap;
    @FXML private Label labelMapName;
    @FXML private AnchorPane infoBox;
    @FXML private Button yesButton;
    @FXML private Button noButton;
    @FXML private Label infoLabel;
    @FXML private Button readyButton;
    @FXML private Button startButton;
    @FXML private Button backButton;
    @FXML private TextArea chatOutput;
    @FXML private TextField chatInput;
    @FXML private ListView<Map> mapList;
    @FXML private Label textFieldMapName;
    @FXML private GridPane mapThumbWrapper;
    @FXML private ImageView mapThumb;

    @FXML
    private Spinner<Integer> numberBots;
    @FXML
    private Spinner<Integer> spinnerCheckpoints;

    private SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,7,0);
    private SpinnerValueFactory<Integer> valueFactoryCP = new SpinnerValueFactory.IntegerSpinnerValueFactory(2,6,0);



    /**
     * Default Constructor
     *
     * @since 2022-11-15
     */
    public LobbyPresenter() {}

    // -----------------------------------------------------
    // public methods and helper methods
    // -----------------------------------------------------

    /**
     * method to safe the information
     *
     * <p>It saves the current information of the lobby in the presenter.
     *
     * @param lobby The lobby file containing all the information of the lobby
     * @param user The lobby file containing all the information of the user
     * @author Moritz Scheer and Tommy Dang and Maxim Erden and Mathis Eilers
     * @since 2023-06-12
     */
    public void setInformation(LobbyDTO lobby, UserDTO user) {
        loggedInUser = user;
        lobbyDTO = lobby;
        lobbyID = lobby.getLobbyID();
        lobbyName = lobby.getName();
        owner = lobby.getOwner();
        password = lobby.getPassword();
        multiplayer = lobby.isMultiplayer();
        slots = lobby.getUsers().size();
        textChat = new TextChatChannel(lobby.getTextChatID(), eventBus);

        boolean first = false;
        if(first == false){
            Map m = new Map(0);
            updateMapDisplay(m);
            User u = this.loggedInUser;
            UserDTO dto = new UserDTO(u.getUsername(), u.getPassword(), u.getEMail());
            eventBus.post(new MapChangeRequest(this.lobbyID, dto, m));
            lobby.setMapName("MapOne");
            first = true;
        }

        if (!owner.equals(loggedInUser)) {
            mapList.setMouseTransparent(true);
            mapList.setFocusTraversable(false);
        } else {
            ChangeListener<? super Number> cl =
                    (obsV, oldV, newV) -> {
                        int mapIndex = mapList.getItems().get((Integer) newV).getIndex();
                        Map m = new Map(mapIndex);

                        updateMapDisplay(m);System.out.println(m.getName());

                        if (this.multiplayer) {
                            User u = this.loggedInUser;
                            UserDTO dto =
                                    new UserDTO(u.getUsername(), u.getPassword(), u.getEMail());
                            eventBus.post(new MapChangeRequest(this.lobbyID, dto, m));
                        }
        switch(m.getName()) {
                    case "Map 1":
                        mapName = "MapOne";
                        break;
                    case "Map 2":
                        mapName = "MapTwo";
                        break;
                    case "Map 3":
                        mapName = "MapThree";
                        break;
                    case "TEST_LaserMap":
                        mapName = "TestLaserMap";
                        break;
                    case "TEST_PusherMap":
                        mapName = "TestPusherMap";
                        break;
                    case "TEST_ConveyorMap":
                        mapName = "TestConveyorMap";
                        break;
                    case "TEST_WallMap":
                        mapName = "TestWallMap";
                        break;
                    default:
                }
                lobby.setMapName(mapName);

            };
            this.mapList.getSelectionModel().selectedIndexProperty().addListener(cl);
        }

        this.mapList.setItems(FXCollections.observableList(Arrays.asList(Map.getMapList())));
        this.mapList.getSelectionModel().selectFirst();
        textFieldMapName.setText("None");

        // display data in GUI
        textFieldLobbyName.setText(lobbyName);
        textFieldOnlineUsers.setText(String.valueOf(slots));
        textFieldPassword.setText(password);
        textFieldOwner.setText(owner.getUsername());
        if(Objects.equals(user.getUsername(), owner.getUsername())) {
            numberBots.setValueFactory(valueFactory);
            spinnerCheckpoints.setValueFactory(valueFactoryCP);
        }else {
            numberBots.setDisable(true);
            spinnerCheckpoints.setDisable(true);
        }
        if (!loggedInUser.equals(owner)) {
            startButton.setManaged(false);
            startButton.setVisible(false);
        } else if (lobby.getUsers().size() == 1) {
            readyButton.setVisible(false);
        }

        // initialize user list
        List<User> list1 = new ArrayList<>(lobby.getUsers());
        updateUsersList(list1);

        // initialize user not ready list
        List<User> list2 = new ArrayList<>(lobby.getUsers());
        updateUsersNotReadyList(list2);

        eventBus.register(this);
    }

    /**
     * Updates the main menus user list according to the list given
     *
     * <p>This method clears the entire user list and then adds the name of each user in the list
     * given to the main menus user list. If there ist no user list this it creates one.
     *
     * @implNote The code inside this Method has to run in the JavaFX-application thread. Therefore,
     *     it is crucial not to remove the {@code Platform.runLater()}
     * @param userList A list of UserDTO objects including all currently logged-in users
     * @see de.uol.swp.common.user.UserDTO
     * @since 2019-08-29
     */
    private void updateUsersList(List<User> userList) {
        // Attention: This must be done on the FX Thread!
        Platform.runLater(
                () -> {
                    if (users == null) {
                        users = FXCollections.observableArrayList();
                        usersView.setItems(users);
                    }
                    users.clear();
                    userList.forEach(u -> users.add(u.getUsername()));
                });
    }

    /**
     * Updates the list of players that are not ready on the client side
     *
     * <p>This method clears the entire userNotReady list and then adds each user in the list given
     * to it. If there ist no userNotReady list this it creates one.
     *
     * @implNote The code inside this Method has to run in the JavaFX-application thread. Therefore,
     *     it is crucial not to remove the {@code Platform.runLater()}
     * @param userList A list of UserDTO objects including all users that are not ready
     * @see de.uol.swp.common.user.UserDTO
     * @authr Moritz Scheer
     * @since 2023-05-28
     */
    private void updateUsersNotReadyList(List<User> userList) {
        // Attention: This must be done on the FX Thread!
        Platform.runLater(
                () -> {
                    if (usersNotReady == null) {
                        usersNotReady = FXCollections.observableArrayList();
                    }
                    usersNotReady.clear();
                    userList.forEach(u -> usersNotReady.add(u));
                });
    }

    /**
     * method to switch the disability effect of the back and start button
     *
     * <p>This method sets the start and back button to disabled if the buttons are enabled and if
     * the buttons are enabled, the buttons are disabled.
     *
     * @author Moritz Scheer
     * @since 2022-12-28
     */
    public void switchButtonDisableEffect() {
        if (startButton.isDisabled() && backButton.isDisabled()) {
            backButton.setDisable(false);
            startButton.setDisable(false);
        } else {
            backButton.setDisable(true);
            startButton.setDisable(true);
        }
    }

    /**
     * Handles when a user left the Lobby
     *
     * <p>If a UserLeftLobbyMessage is posted to the EventBus this method is called.
     *
     * @param message the UserLeftLobbyMessage object seen on the EventBus
     * @see de.uol.swp.common.lobby.message.UserLeftLobbyMessage
     * @author Daniel Merzo
     * @since 2022-12-15
     */
    public void userLeftLobby(UserLeftLobbyMessage message) {
        LOG.debug("user {}  left the lobby,", message.getUser().getUsername());
        Platform.runLater(
                () -> {
                    users.remove(message.getUser().getUsername());
                    usersNotReady.remove(message.getUser());

                    slots--;
                    textFieldOnlineUsers.setText(String.valueOf(slots));

                    owner = message.getNewOwner();
                    textFieldOwner.setText(owner.getUsername());

                    if (loggedInUser.equals(owner)) {
                        startButton.setManaged(true);
                        startButton.setVisible(true);
                    }
                    if (users.size() == 1) {
                        startButton.setDisable(false);
                        readyButton.setVisible(false);
                    }
                });
    }

    /**
     * Handles joined users
     *
     * <p>If a new UserJoinedLobbyMessage object is posted to the EventBus the name of the newly
     * joined user is appended to the user list in the lobby. Furthermore, if the LOG-Level is set
     * to DEBUG the message "New user {@literal <Username>} joined the lobby." is displayed in the
     * log.
     *
     * @param message the UserJoinedLobbyMessage object seen on the EventBus
     * @see de.uol.swp.common.lobby.message.UserJoinedLobbyMessage
     * @author Moritz Scheer
     * @since 2022-12-13
     */
    public void userJoinedLobby(UserJoinedLobbyMessage message) {
        LOG.debug("New user {} joined the lobby,", message.getUser().getUsername());
        Platform.runLater(
                () -> {
                    if (users != null
                            && loggedInUser != null
                            && !loggedInUser
                                    .getUsername()
                                    .equals(message.getUser().getUsername())) {
                        users.add(message.getUser().getUsername());
                        usersNotReady.add(message.getUser());
                    }
                    slots++;
                    textFieldOnlineUsers.setText(String.valueOf(slots));
                    if (users.size() >= 1) {
                        readyButton.setVisible(true);
                        startButton.setDisable(true);
                    }
                });
    }

    /**
     * Handles changes if a player pressed the ready or not ready button
     *
     * <p>If a new PlayerReadyInLobbyMessage object is posted to the EventBus
     *
     * @param message the PlayerReadyInLobbyMessage object seen on the EventBus
     * @see de.uol.swp.common.lobby.message.PlayerReadyInLobbyMessage
     * @author Moritz Scheer
     * @since 2023-05-28
     */
    public void updatePlayerReadyStatus(PlayerReadyInLobbyMessage message) {
        if (message.isReady()) {
            usersNotReady.remove(message.getUser());
            if (loggedInUser.equals(message.getUser())) {
                LOG.debug("you are now ready");
            } else {
                LOG.debug("user {} is now ready", message.getUser().getUsername());
            }
        } else {
            usersNotReady.add(message.getUser());
            startButton.setDisable(true);
            if (loggedInUser.equals(message.getUser())) {
                LOG.debug("you are now not ready");
            } else {
                LOG.debug("user {} is now not ready", message.getUser().getUsername());
            }
        }
        if (usersNotReady.size() == 0 && loggedInUser.equals(owner)) {
            startButton.setDisable(false);
        }
    }

    // -----------------------------------------------------
    // ActionEvents
    // -----------------------------------------------------

    /**
     * Method called when the cancel button is pressed
     *
     * <p>This Method is called when the cancel button is pressed.
     *
     * @param actionEvent The ActionEvent generated by pressing the back button
     * @author Moritz Scheer and Maria
     * @since 2022-12-08
     */
    @FXML
    private void onBackButtonPressed(ActionEvent actionEvent) {
        if (tabPresenter.infoLabel2IsVisible() || tabPresenter.infoLabel1IsVisible()) {
            tabPresenter.updateInfoBox();
        }
        tabPresenter.setInfoLabel(3);
        tabPresenter.updateInfoBox();
        switchButtonDisableEffect();
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

    /**
     * Method called when the ready button is pressed
     *
     * <p>This Method is called when the ready button is pressed.
     *
     * @param actionEvent The ActionEvent generated by pressing the ready button
     * @author Moritz Scheer
     * @since 2023-05-28
     */
    @FXML
    private void onReadyButtonPressed(ActionEvent actionEvent) {
        if (readyButton.getText().equals("Ready?")) {
            Platform.runLater(
                    () -> {
                        readyButton.setText("Ready!");
                        readyButton.setStyle("-fx-background-color: GREEN");
                    });
            eventBus.post(new SetPlayerReadyEvent(lobbyID, loggedInUser, true));
        } else {
            Platform.runLater(
                    () -> {
                        readyButton.setText("Ready?");
                        readyButton.setStyle("-fx-background-color: RED");
                    });
            eventBus.post(new SetPlayerReadyEvent(lobbyID, loggedInUser, false));
        }
    }

    /**
     * Method called when the start button is pressed
     *
     * <p>This Method is called when the start button is pressed and take / send the number of bots.
     *
     * @param actionEvent The ActionEvent generated by pressing the start button
     * @since 2022-11-30
     */
    @FXML
    private void onStartButtonPressed(ActionEvent actionEvent) throws Exception {
        if(numberBots.valueProperty().getValue() + users.size() >8) {
            textChat.sendTextMessage("There are too many players or bots in the lobby!");
            throw new Exception("There are too many players or bots in the lobby!");
        }else {
            if (loggedInUser == owner) {
                eventBus.post(new RequestStartGameEvent((Integer) numberBots.getValue(), spinnerCheckpoints.getValue(), lobbyDTO));
            }
        }

    }

    public ObservableList<String> getUsers() {
        return users;
    }

    /**
     * Updates the displayed map in the lobby according to the parameter
     *
     * @param m The Map object
     * @see de.uol.swp.common.game.Map
     * @author Mathis Eilers
     * @since 2023-05-12
     */
    public void updateMapDisplay(Map m) {
        if (m == null) return;

        Platform.runLater(
                () -> {
                    textFieldMapName.setText(m.getName());
                    mapThumb.setImage(new Image(m.getImageResource().toString()));
                    mapThumb.fitWidthProperty().bind(mapThumbWrapper.widthProperty().subtract(10));
                    mapThumb.fitHeightProperty().bind(mapThumbWrapper.widthProperty().subtract(10));
                });
    }
}
