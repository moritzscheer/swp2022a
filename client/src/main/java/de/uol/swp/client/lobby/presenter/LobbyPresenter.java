package de.uol.swp.client.lobby.presenter;

import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import de.uol.swp.client.AbstractPresenter;
import de.uol.swp.client.chat.TextChatChannel;
import de.uol.swp.client.chat.messages.NewTextChatMessageRecieved;
import de.uol.swp.client.lobby.LobbyService;
import de.uol.swp.client.lobby.event.ShowJoinOrCreateViewEvent;
import de.uol.swp.client.lobby.event.ShowLobbyViewEvent;
import de.uol.swp.client.main.event.ShowMainMenuViewEvent;
import de.uol.swp.common.lobby.dto.LobbyDTO;
import de.uol.swp.common.lobby.exception.LobbyLeaveExceptionResponse;
import de.uol.swp.common.lobby.message.UserJoinedLobbyMessage;
import de.uol.swp.common.lobby.message.UserLeftLobbyMessage;
import de.uol.swp.common.lobby.response.LobbyCreatedSuccessfulResponse;
import de.uol.swp.common.lobby.response.LobbyJoinedSuccessfulResponse;
import de.uol.swp.common.lobby.response.LobbyDroppedResponse;
import de.uol.swp.common.lobby.response.LobbyLeaveUserResponse;
import de.uol.swp.common.user.User;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import de.uol.swp.common.user.UserDTO;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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

    @Inject
    private LobbyService lobbyService;

    @FXML
    private ListView<String> usersView;
    @FXML
    private Label textFieldPassword;
    @FXML
    private Label textFieldLobbyName;
    @FXML
    private Label textFieldOnlineUsers;
    @FXML
    private Label textFieldOwner;
    @FXML
    private Label labelMap;
    @FXML
    private Label labelMapName;
    @FXML
    private AnchorPane infoBox;
    @FXML
    private Button yesButton;
    @FXML
    private Button noButton;
    @FXML
    private Label infoLabel;
    @FXML
    private Button startButton;
    @FXML
    private Button backButton;
    @FXML
    private TextArea chatOutput;
    @FXML
    private TextField chatInput;


    /**
     * Default Constructor
     *
     * @since 2022-11-15
     */
    public LobbyPresenter() {
        // needed for javafx
    }

    // -----------------------------------------------------
    // Responses
    // -----------------------------------------------------

    /**
     * Handles created Lobbies
     * <p>
     * If an LobbyCreatedResponse object is detected on the EventBus this
     * method is called. It saves the current information on the Lobby in the Client.
     *
     * @param message The LobbyCreatedResponse object detected on the EventBus
     * @author Moritz Scheer
     * @since 2022-11-17
     */
    @Subscribe
    public void onLobbyCreatedSuccessfulResponse(LobbyCreatedSuccessfulResponse message) {
        LOG.info("Lobby " + message.getName() + " successfully created");

        //safe information in the Client
        loggedInUser = message.getUser();
        updateInformation(message.getLobby());

        eventBus.post(new ShowLobbyViewEvent(loggedInUser));
    }

    /**
     * Handles joined Lobbies
     * <p>
     * If an LobbyJoinedSuccessfulResponse object is detected on the EventBus this
     * method is called. It saves the current information on the Lobby in the Client.
     *
     * @param message The LobbyJoinedSuccessfulResponse object detected on the EventBus
     * @author Moritz Scheer
     * @since 2022-12-13
     */
    @Subscribe
    public void onLobbyJoinedSuccessfulResponse(LobbyJoinedSuccessfulResponse message) {
        LOG.info("Lobby " + message.getName() + " successfully joined");

        //safe information in the Client
        loggedInUser = message.getUser();
        updateInformation(message.getLobby());

        eventBus.post(new ShowLobbyViewEvent(loggedInUser));
    }

    /**
     * Handles when a lobby is dropped
     * <p>
     * If a LobbyDroppedResponse is posted to the EventBus the
     * lobby specific variable are set to null and
     * the ShowJoinOrCreateViewEvent is triggered.
     *
     * @param message the LobbyDroppedResponse object seen on the EventBus
     * @author Daniel Merzo
     * @see de.uol.swp.common.lobby.response.LobbyDroppedResponse
     * @since 2022-12-15
     */
    @Subscribe
    void onLobbyDroppedResponse(LobbyDroppedResponse message) {
        LOG.info("Lobby " + message.getName() + " successfully deleted");
        if (multiplayer) {
            eventBus.post(new ShowJoinOrCreateViewEvent());
        } else {
            eventBus.post(new ShowMainMenuViewEvent(message.getUser()));
        }
        deleteLobbyData();
        lobbyService.retrieveAllLobbies();
    }

    /**
     * Handles when a user leave the lobby
     * <p>
     * If a LobbyLeaveUserResponse is posted to the EventBus the
     * user's specific variable to the lobby are set to null and
     * the ShowJoinOrCreateViewEvent is triggered.
     *
     * @param message the LobbyLeaveUserResponse object seen on the EventBus
     * @author Daniel Merzo
     * @see de.uol.swp.common.lobby.response.LobbyLeaveUserResponse
     * @since 2022-12-15
     */
    @Subscribe
    void onLobbyLeaveUserResponse(LobbyLeaveUserResponse message) {
        LOG.info("Lobby " + message.getName() + " successfully left");
        eventBus.post(new ShowJoinOrCreateViewEvent());
        deleteLobbyData();
        lobbyService.retrieveAllLobbies();
    }

    /**
     * Handles when the lobby was not found
     * <p>
     * If a LobbyLeaveExceptionResponse is posted to the EventBus the
     * user's specific variable to the lobby are set to null and
     * the ShowJoinOrCreateViewEvent is triggered.
     *
     * @param message the LobbyLeaveUserResponse object seen on the EventBus
     * @author Daniel Merzo
     * @see de.uol.swp.common.lobby.response.LobbyLeaveUserResponse
     * @since 2022-12-15
     */
    @Subscribe
    void onLobbyLeaveExceptionResponse(LobbyLeaveExceptionResponse message) {
        LOG.error(message.toString());
        eventBus.post(new ShowJoinOrCreateViewEvent());
        deleteLobbyData();
        lobbyService.retrieveAllLobbies();
    }

    /**
     * Delete the lobby date
     *
     * @author Daniel Merzo
     * @since 2022-12-18
     */
    private void deleteLobbyData() {
        lobbyID = null;
        owner = null;
        loggedInUser = null;
        lobbyName = null;
        multiplayer = null;
    }

    /**
     * helper methods for ResponseMessages
     * <p>
     * It saves the current information of the lobby in the presenter.
     *
     * @param message The LobbyJoinedSuccessfulResponse object detected on the EventBus
     * @author Moritz Scheer
     * @since 2022-12-13
     */
    private void updateInformation(LobbyDTO message) {
        lobbyID = message.getLobbyID();
        lobbyName = message.getName();
        owner = message.getOwner();
        password = message.getPassword();
        multiplayer = message.isMultiplayer();
        slots = message.getUsers().size();
        textChat = new TextChatChannel(message.getTextChatID(), eventBus);

        //display data in GUI
        textFieldLobbyName.setText(lobbyName);
        textFieldOnlineUsers.setText(String.valueOf(slots));
        textFieldPassword.setText(password);
        textFieldOwner.setText(owner.getUsername());

        //initialize user list
        List<User> list = new ArrayList<>(message.getUsers());
        updateUsersList(list);
    }

    // -----------------------------------------------------
    // Messages
    // -----------------------------------------------------

    /**
     * Handles joined users
     * <p>
     * If a new UserJoinedLobbyMessage object is posted to the EventBus the name of the newly
     * joined user is appended to the user list in the lobby.
     * Furthermore, if the LOG-Level is set to DEBUG the message "New user {@literal
     * <Username>} joined the lobby." is displayed in the log.
     *
     * @param message the UserJoinedLobbyMessage object seen on the EventBus
     * @see de.uol.swp.common.lobby.message.UserJoinedLobbyMessage
     * @since 2022-12-13
     */
    @Subscribe
    public void onUserJoinedLobbyMessage(UserJoinedLobbyMessage message) {
        LOG.debug("New user {}  joined the lobby,", message.getUser().getUsername());
        Platform.runLater(() -> {
            if (users != null && loggedInUser != null && !loggedInUser.getUsername().equals(message.getUser().getUsername()))
                users.add(message.getUser().getUsername());

            slots++;
            textFieldOnlineUsers.setText(String.valueOf(slots));
        });
    }

    /**
     * Handles when a user left the Lobby
     * <p>
     * If a UserLeftLobbyMessage is posted to the EventBus this method is called.
     *
     * @param message the UserLeftLobbyMessage object seen on the EventBus
     * @author Daniel Merzo
     * @see de.uol.swp.common.lobby.message.UserLeftLobbyMessage
     * @since 2022-12-15
     */
    @Subscribe
    private void onUserLeftLobbyMessage(UserLeftLobbyMessage message) {
        LOG.debug("user {}  left the lobby,", message.getUser().getUsername());
        Platform.runLater(() -> {
            users.remove(message.getUser().getUsername());

            slots--;
            textFieldOnlineUsers.setText(String.valueOf(slots));

            owner = message.getNewOwner();
            textFieldOwner.setText(owner.getUsername());
        });
    }

    /**
     * Updates the main menus user list according to the list given
     * <p>
     * This method clears the entire user list and then adds the name of each user
     * in the list given to the main menus user list. If there ist no user list
     * this it creates one.
     *
     * @param userList A list of UserDTO objects including all currently logged-in
     *                 users
     * @implNote The code inside this Method has to run in the JavaFX-application
     * thread. Therefore, it is crucial not to remove the {@code Platform.runLater()}
     * @see de.uol.swp.common.user.UserDTO
     * @since 2019-08-29
     */
    private void updateUsersList(List<User> userList) {
        // Attention: This must be done on the FX Thread!
        Platform.runLater(() -> {
            if (users == null) {
                users = FXCollections.observableArrayList();
                usersView.setItems(users);
            }
            users.clear();
            userList.forEach(u -> users.add(u.getUsername()));
        });
    }

    // -----------------------------------------------------
    // ActionEvents
    // -----------------------------------------------------

    /**
     * Method called when the cancel button is pressed
     * <p>
     * This Method is called when the cancel button is pressed.
     *
     * @param actionEvent The ActionEvent generated by pressing the back button
     * @author Moritz Scheer and Maria
     * @since 2022-12-08
     */
    @FXML
    private void onBackButtonPressed(ActionEvent actionEvent) {
        updateInfoBox();
    }

    /**
     * Method called when the yes button in the infoBox is pressed
     * <p>
     * This Method is called when the yes button is pressed.
     *
     * @param actionEvent The ActionEvent generated by pressing the cancel button
     * @author Daniel Merzo
     * @since 2022-12-15
     */
    @FXML
    private void onYesButtonPressed(ActionEvent actionEvent) {
        lobbyService.leaveLobby(lobbyName, (UserDTO) loggedInUser, lobbyID, multiplayer);
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
    private void onNewTextChatMessage(NewTextChatMessageRecieved message) {
        chatOutput.setText(textChat.getChatString());
    }

    /**
     * Method called when the no button in the infoBox is pressed
     * <p>
     * This Method is called when the no button is pressed.
     *
     * @param actionEvent The ActionEvent generated by pressing the cancel button
     * @author Daniel Merzo
     * @since 2022-12-15
     */
    @FXML
    private void onNoButtonPressed(ActionEvent actionEvent) {
        updateInfoBox();
    }

    /**
     * Auxiliary method for the visibility of the infoBox
     * <p>
     * If this method is called, it is possible to make the elements of a scene visible or invisible.
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
     * <p>
     * This Method is called when the start button is pressed.
     *
     * @param actionEvent The ActionEvent generated by pressing the start button
     * @since 2022-11-30
     */
    @FXML
    private void onStartButtonPressed(ActionEvent actionEvent) {
        // start game
    }


}
