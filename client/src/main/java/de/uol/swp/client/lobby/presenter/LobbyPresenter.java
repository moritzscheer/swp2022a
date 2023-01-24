package de.uol.swp.client.lobby.presenter;

import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;

import de.uol.swp.client.AbstractPresenter;
import de.uol.swp.client.chat.TextChatChannel;
import de.uol.swp.client.chat.messages.NewTextChatMessageReceived;
import de.uol.swp.client.lobby.LobbyService;
import de.uol.swp.client.tab.TabPresenter;
import de.uol.swp.common.lobby.dto.LobbyDTO;
import de.uol.swp.common.lobby.message.UserJoinedLobbyMessage;
import de.uol.swp.common.lobby.message.UserLeftLobbyMessage;
import de.uol.swp.common.user.User;
import de.uol.swp.common.user.UserDTO;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

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

    private Integer lobbyID;
    private String lobbyName;
    private User owner;
    private ObservableList<String> users;
    private String password;
    private Boolean multiplayer;
    private Integer slots = 1;
    private TextChatChannel textChat;

    @Inject private LobbyService lobbyService;
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
    @FXML private Button startButton;
    @FXML private Button backButton;
    @FXML private TextArea chatOutput;
    @FXML private TextField chatInput;

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
     * @author Moritz Scheer
     * @since 2022-12-13
     */
    public void setInformation(LobbyDTO lobby, UserDTO user) {
        loggedInUser = user;

        lobbyID = lobby.getLobbyID();
        lobbyName = lobby.getName();
        owner = lobby.getOwner();
        password = lobby.getPassword();
        multiplayer = lobby.isMultiplayer();
        slots = lobby.getUsers().size();
        textChat = new TextChatChannel(lobby.getTextChatID(), eventBus);

        // display data in GUI
        textFieldLobbyName.setText(lobbyName);
        textFieldOnlineUsers.setText(String.valueOf(slots));
        textFieldPassword.setText(password);
        textFieldOwner.setText(owner.getUsername());

        // initialize user list
        List<User> list = new ArrayList<>(lobby.getUsers());
        updateUsersList(list);
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

                    slots--;
                    textFieldOnlineUsers.setText(String.valueOf(slots));

                    owner = message.getNewOwner();
                    textFieldOwner.setText(owner.getUsername());
                    System.out.println(slots);
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
     * @see de.uol.swp.common.lobby.message.UserJoinedLobbyMessage @ Moritz Scheer
     * @since 2022-12-13
     */
    public void userJoinedLobby(UserJoinedLobbyMessage message) {
        LOG.debug("New user {}  joined the lobby,", message.getUser().getUsername());
        Platform.runLater(
                () -> {
                    if (users != null
                            && loggedInUser != null
                            && !loggedInUser
                                    .getUsername()
                                    .equals(message.getUser().getUsername())) {
                        users.add(message.getUser().getUsername());
                    }
                    slots++;
                    textFieldOnlineUsers.setText(String.valueOf(slots));
                });
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
        tabPresenter.setInfoLabel(2);
        tabPresenter.updateInfoBox();
        switchButtonDisableEffect();
    }

    /**
     * Method called when the yes button in the infoBox is pressed
     *
     * <p>This Method is called when the yes button is pressed.
     *
     * @author Daniel Merzo
     * @param actionEvent The ActionEvent generated by pressing the cancel button
     * @since 2022-12-15
     */
    @FXML
    private void onYesButtonPressed(ActionEvent actionEvent) {
        lobbyService.leaveLobby(lobbyID, lobbyName, (UserDTO) loggedInUser, multiplayer);
        updateInfoBox();
    }

    /**
     * Method called when the no button in the infoBox is pressed
     *
     * <p>This Method is called when the no button is pressed.
     *
     * @param actionEvent The ActionEvent generated by pressing the cancel button
     * @author Daniel Merzo
     * @since 2022-12-15
     */
    @FXML
    private void onNoButtonPressed(ActionEvent actionEvent) {
        tabPresenter.setInfoLabel(3);
        updateInfoBox();
    }

    @FXML
    private void textChatInputKeyPressed(KeyEvent actionEvent) {
        if (actionEvent.getCode() == KeyCode.ENTER) {
            if (chatInput == null) {
                return;
            }
            textChat.sendTextMessage(chatInput.getText());
            chatInput.setText("");
        }
    }

    @Subscribe
    public void onNewTextChatMessage(NewTextChatMessageReceived message) {
        if (textChat == null) return;
        chatOutput.setText(textChat.getChatString());
        Platform.runLater(
                () -> {
                    chatOutput.setScrollTop(Double.MAX_VALUE);
                });
    }

    /**
     * Auxiliary method for the visibility of the infoBox
     *
     * <p>If this method is called, it is possible to make the elements of a scene visible or
     * invisible.
     *
     * @author Daniel Merzo
     * @since 2022-12-15
     */
    private void updateInfoBox() {
        if (!infoBox.isVisible()) {
            infoBox.setVisible(true);
            yesButton.setVisible(true);
            noButton.setVisible(true);
            infoLabel.setVisible(true);
            startButton.setDisable(true);
            backButton.setDisable(true);
        } else {
            infoBox.setVisible(false);
            yesButton.setVisible(false);
            noButton.setVisible(false);
            infoLabel.setVisible(false);
            startButton.setDisable(false);
            backButton.setDisable(false);
        }
    }

    /**
     * Method called when the start button is pressed
     *
     * <p>This Method is called when the start button is pressed.
     *
     * @param actionEvent The ActionEvent generated by pressing the start button
     * @since 2022-11-30
     */
    @FXML
    private void onStartButtonPressed(ActionEvent actionEvent) {
        // start game
    }
}
