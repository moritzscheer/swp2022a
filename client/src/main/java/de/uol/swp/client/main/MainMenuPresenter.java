package de.uol.swp.client.main;

import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;

import de.uol.swp.client.AbstractPresenter;
import de.uol.swp.client.auth.events.ShowLoginViewEvent;
import de.uol.swp.client.chat.TextChatChannel;
import de.uol.swp.client.chat.messages.NewTextChatMessageReceived;
import de.uol.swp.client.credit.event.ShowCreditViewEvent;
import de.uol.swp.client.lobbyGame.lobby.LobbyService;
import de.uol.swp.client.lobbyGame.lobby.event.CreateNewLobbyEvent;
import de.uol.swp.client.lobbyGame.lobby.event.UpdateLobbiesListEvent;
import de.uol.swp.client.main.event.ShowAccountOptionsViewEvent;
import de.uol.swp.client.preLobby.events.ShowJoinOrCreateViewEvent;
import de.uol.swp.client.rulebook.event.ShowRulebookViewEvent;
import de.uol.swp.client.setting.event.ShowSettingViewEvent;
import de.uol.swp.client.tab.TabPresenter;
import de.uol.swp.common.user.User;
import de.uol.swp.common.user.UserDTO;
import de.uol.swp.common.user.message.UserLoggedInMessage;
import de.uol.swp.common.user.message.UserLoggedOutMessage;
import de.uol.swp.common.user.response.LoginSuccessfulResponse;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Manages the main menu
 *
 * @author Marco Grawunder
 * @see de.uol.swp.client.AbstractPresenter
 * @since 2019-08-29
 */
public class MainMenuPresenter extends AbstractPresenter {

    public static final String FXML = "/fxml/MainMenuView.fxml";

    private static final Logger LOG = LogManager.getLogger(MainMenuPresenter.class);

    private ObservableList<String> users;

    private User loggedInUser;

    private TextChatChannel textChat;

    private static final ShowAccountOptionsViewEvent showAccountOptionMessage =
            new ShowAccountOptionsViewEvent();

    @Inject private TabPresenter tabPresenter;

    @FXML private ListView<String> usersView;

    @FXML private TextArea textChatOutput;

    @FXML private TextField textChatInput;

    /**
     * Default Constructor
     *
     * @since 2023-05-19
     */
    public MainMenuPresenter() {
        // needed for javafx
    }

    /**
     * Handles successful login
     *
     * <p>If a LoginSuccessfulResponse is posted to the EventBus the loggedInUser of this client is
     * set to the one in the message received and the full list of users currently logged in is
     * requested.
     *
     * @param message the LoginSuccessfulResponse object seen on the EventBus
     * @see de.uol.swp.common.user.response.LoginSuccessfulResponse
     * @author Marco Grawunder
     * @since 2019-09-05
     */
    @Subscribe
    public void onLoginSuccessfulResponse(LoginSuccessfulResponse message) {
        this.loggedInUser = message.getUser();
        textChat = new TextChatChannel(message.getChatID(), eventBus);
        userService.retrieveAllUsers();
    }

    /**
     * Handles new logged-in users
     *
     * <p>If a new UserLoggedInMessage object is posted to the EventBus the name of the newly
     * logged-in user is appended to the user list in the main menu. Furthermore, if the LOG-Level
     * is set to DEBUG the message "New user {@literal <Username>} logged in." is displayed in the
     * log.
     *
     * @param message the UserLoggedInMessage object seen on the EventBus
     * @see de.uol.swp.common.user.message.UserLoggedInMessage
     * @author Marco Grawunder
     * @since 2019-08-29
     */
    @Subscribe
    public void onUserLoggedInMessage(UserLoggedInMessage message) {

        LOG.debug("New user {}  logged in,", message.getUsername());
        Platform.runLater(
                () -> {
                    if (users != null
                            && loggedInUser != null
                            && !loggedInUser.getUsername().equals(message.getUsername()))
                        users.add(message.getUsername());
                });
    }

    /**
     * Handles new logged-out users
     *
     * <p>If a new UserLoggedOutMessage object is posted to the EventBus the name of the newly
     * logged-out user is removed from the user list in the main menu. Furthermore, if the LOG-Level
     * is set to DEBUG the message "User {@literal <Username>} logged out." is displayed in the log.
     *
     * @param message the UserLoggedOutMessage object seen on the EventBus
     * @see de.uol.swp.common.user.message.UserLoggedOutMessage
     * @author Marco Grawunder
     * @since 2019-08-29
     */
    @Subscribe
    public void onUserLoggedOutMessage(UserLoggedOutMessage message) {
        if (this.users != null) {
            Platform.runLater(() -> users.remove(message.getUsername()));
        }
    }

    /**
     * Method called when the Delete User button is pressed
     *
     * <p>If the Delete User button is pressed, this method requests the user service first to log
     * out the user, then to drop the user.
     *
     * @param event The ActionEvent created by pressing the Delete User button
     * @see LobbyService
     * @author Marco Grawunder
     * @since 2022-11-08
     */
    @FXML
    private void onDropUser(ActionEvent event) {
        userService.dropUser(loggedInUser);
        userService.logout(loggedInUser);
    }

    /**
     * Method called when the multiplayer lobby button is pressed
     *
     * <p>If the multiplayer button is pressed, it posts an ShowJoinOrCreateViewEvent Object to the
     * Eventbus.
     *
     * @param actionEvent The ActionEvent created by pressing the join lobby button
     * @see LobbyService
     * @author Marco Grawunder, Maria Anrade, Moritz Scheer
     * @since 2022-11-30
     */
    @FXML
    void onMultiplayerButtonPressed(ActionEvent actionEvent) {
        eventBus.post(new UpdateLobbiesListEvent());
        eventBus.post(new ShowJoinOrCreateViewEvent());
    }

    /**
     * Method called when the singleplayer button is pressed
     *
     * <p>If the singleplayer button is pressed, this method requests the lobby service to create a
     * specified lobby. Therefore, it uses as the parameter name and password the value null.
     *
     * @param event The ActionEvent created by pressing the join lobby button
     * @see LobbyService
     * @author Marco Grawunder, Maria Anrade, Moritz Scheer
     * @since 2022-11-30
     */
    @FXML
    void onSingleplayerButtonPressed(ActionEvent event) {
        eventBus.post(new CreateNewLobbyEvent("Singleplayer", (UserDTO) loggedInUser, false, null));
    }

    /**
     * Method called when the AccountOption button is pressed
     *
     * <p>If the AccountOption button is pressed, this method post on the bus a
     * ShowAccountOptionMessage. This request is received by the SceneManager, which changes the
     * screen to AccountOptionView screen.
     *
     * @param event The ActionEvent created by pressing the AccountOption button
     * @see de.uol.swp.client.main.event.ShowAccountOptionsViewEvent
     * @see de.uol.swp.client.SceneManager
     * @since 2022-11-30
     * @author Waldemar Kempel and Maria Eduarda Costa Leite Andrade
     */
    @FXML
    void onAccountOptionButtonPressed(ActionEvent event) {
        eventBus.post(showAccountOptionMessage);
    }

    /**
     * Method called when the credit button is pressed
     *
     * <p>If the credit button is pressed, it changes the scene from main menu to credit.
     *
     * @param event The ActionEvent created by pressing the credit button
     * @see de.uol.swp.client.credit
     * @author Tommy Dang
     * @since 2022-11-29
     */
    @FXML
    void onCreditButtonPressed(ActionEvent event) {
        eventBus.post(new ShowCreditViewEvent());
    }

    /**
     * Method called when the rulebook button is pressed
     *
     * <p>If the rulebook button is pressed, it changes the scene from main menu to rulebook.
     *
     * @param event The ActionEvent created by pressing the rulebook button
     * @see de.uol.swp.client.rulebook
     * @author Tommy Dang
     * @since 2022-11-27
     */
    @FXML
    void onRulebookButtonPressed(ActionEvent event) {
        eventBus.post(new ShowRulebookViewEvent());
    }

    /**
     * Method called when the setting button is pressed
     *
     * <p>If the setting button is pressed, it changes the scene from main menu to setting.
     *
     * @param event The ActionEvent created by pressing the setting button
     * @see de.uol.swp.client.setting
     * @author Tommy Dang
     * @since 2022-11-27
     */
    @FXML
    void onSettingButtonPressed(ActionEvent event) {
        eventBus.post(new ShowSettingViewEvent());
    }

    /**
     * Method called when the Logout button is pressed
     *
     * <p>If the logout button is pressed, this method requests the user service to log this user
     * out.
     *
     * @param event The ActionEvent created by pressing the logout button
     * @see LobbyService
     * @author Tommy Dang, Moritz Scheer
     * @since 2022-11-08
     */
    @FXML
    private void onLogoutButtonPressed(ActionEvent event) {
        tabPresenter.setInfoLabel(1);
        tabPresenter.updateInfoBox();
        userService.logout(loggedInUser);
        eventBus.post(new ShowLoginViewEvent());
    }

    /**
     * Method called when the exit button is pressed
     *
     * <p>This Method is called when the exit button is pressed. It posts an instance of the
     * CloseClientEvent to the EventBus the SceneManager is subscribed to.
     *
     * @param event The ActionEvent generated by pressing the exit button
     * @see de.uol.swp.client.CloseClientEvent
     * @see de.uol.swp.client.SceneManager
     * @author Tommy Dang, Moritz Scheer
     * @since 2023-01-04
     */
    @FXML
    private void onExitButtonPressed(ActionEvent event) {
        tabPresenter.setInfoLabel(2);
        tabPresenter.updateInfoBox();
        userService.logout(loggedInUser);
    }

    /**
     * Method is called, when chat input key "Enter" is pressed
     *
     * @author Finn Oldeboershuis, Tommy Dang
     * @param actionEvent
     * @since 2023-01-10
     */
    @FXML
    private void textChatInputKeyPressed(KeyEvent actionEvent) {
        if (actionEvent.getCode() == KeyCode.ENTER) {
            if (textChatInput.getLength() != 0 && !textChatInput.getText().isBlank()) {
                textChat.sendTextMessage(textChatInput.getText());
                textChatInput.setText("");
            }
        }
    }

    /**
     * Method is subscribed to NewTextChatMessageReceived, when NewTextChatMessageReceived is posted
     * on bus
     *
     * @author Finn Oldeboershuis, Tommy Dang
     * @see de.uol.swp.client.chat.messages.NewTextChatMessageReceived
     * @param message
     * @since 2023-01-10
     */
    @Subscribe
    public void onNewTextChatMessage(NewTextChatMessageReceived message) {
        if (textChat == null) return;
        textChatOutput.setText(textChat.getChatString());
        textChatOutput.appendText("");
        textChatOutput.setWrapText(true);
        Platform.runLater(
                () -> {
                    textChatOutput.setScrollTop(Double.MAX_VALUE);
                });
    }
}
