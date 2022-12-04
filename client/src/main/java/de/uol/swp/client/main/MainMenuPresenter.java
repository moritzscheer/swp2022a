package de.uol.swp.client.main;

import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import de.uol.swp.client.AbstractPresenter;
import de.uol.swp.client.credit.event.ShowCreditViewEvent;
import de.uol.swp.client.main.event.ShowAccountOptionsViewEvent;
import de.uol.swp.client.lobby.LobbyService;
import de.uol.swp.client.rulebook.event.ShowRulebookViewEvent;
import de.uol.swp.client.lobby.event.ShowJoinOrCreateViewEvent;
import de.uol.swp.common.user.User;
import de.uol.swp.common.user.UserDTO;
import de.uol.swp.common.user.message.UserLoggedInMessage;
import de.uol.swp.common.user.message.UserLoggedOutMessage;
import de.uol.swp.common.user.response.AllOnlineUsersResponse;
import de.uol.swp.common.user.response.LoginSuccessfulResponse;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * Manages the main menu
 *
 * @author Marco Grawunder
 * @see de.uol.swp.client.AbstractPresenter
 * @since 2019-08-29
 *
 */
public class MainMenuPresenter extends AbstractPresenter {

    public static final String FXML = "/fxml/MainMenuView.fxml";

    private static final Logger LOG = LogManager.getLogger(MainMenuPresenter.class);

    public Button singleplayerButton;

    private ObservableList<String> users;

    private User loggedInUser;

    private static final ShowAccountOptionsViewEvent  showAccountOptionMessage = new ShowAccountOptionsViewEvent();
    @Inject
    private LobbyService lobbyService;

    @FXML
    private ListView<String> usersView;

    /**
     * Handles successful login
     *
     * If a LoginSuccessfulResponse is posted to the EventBus the loggedInUser
     * of this client is set to the one in the message received and the full
     * list of users currently logged in is requested.
     *
     * @param message the LoginSuccessfulResponse object seen on the EventBus
     * @see de.uol.swp.common.user.response.LoginSuccessfulResponse
     * @since 2019-09-05
     */
    @Subscribe
    public void onLoginSuccessfulResponse(LoginSuccessfulResponse message) {
        this.loggedInUser = message.getUser();
        userService.retrieveAllUsers();
    }

    /**
     * Handles new logged in users
     *
     * If a new UserLoggedInMessage object is posted to the EventBus the name of the newly
     * logged in user is appended to the user list in the main menu.
     * Furthermore if the LOG-Level is set to DEBUG the message "New user {@literal
     * <Username>} logged in." is displayed in the log.
     *
     * @param message the UserLoggedInMessage object seen on the EventBus
     * @see de.uol.swp.common.user.message.UserLoggedInMessage
     * @since 2019-08-29
     */
    @Subscribe
    public void onUserLoggedInMessage(UserLoggedInMessage message) {

        LOG.debug("New user {}  logged in,", message.getUsername());
        Platform.runLater(() -> {
            if (users != null && loggedInUser != null && !loggedInUser.getUsername().equals(message.getUsername()))
                users.add(message.getUsername());
        });
    }

    /**
     * Handles new logged out users
     *
     * If a new UserLoggedOutMessage object is posted to the EventBus the name of the newly
     * logged out user is removed from the user list in the main menu.
     * Furthermore if the LOG-Level is set to DEBUG the message "User {@literal
     * <Username>} logged out." is displayed in the log.
     *
     * @param message the UserLoggedOutMessage object seen on the EventBus
     * @see de.uol.swp.common.user.message.UserLoggedOutMessage
     * @since 2019-08-29
     */
    @Subscribe
    public void onUserLoggedOutMessage(UserLoggedOutMessage message) {
        LOG.debug("User {}  logged out.",  message.getUsername() );
        Platform.runLater(() -> users.remove(message.getUsername()));
    }

    /**
     * Handles new list of users
     *
     * If a new AllOnlineUsersResponse object is posted to the EventBus the names
     * of currently logged in users are put onto the user list in the main menu.
     * Furthermore if the LOG-Level is set to DEBUG the message "Update of user
     * list" with the names of all currently logged in users is displayed in the
     * log.
     *
     * @param allUsersResponse the AllOnlineUsersResponse object seen on the EventBus
     * @see de.uol.swp.common.user.response.AllOnlineUsersResponse
     * @since 2019-08-29
     */
    @Subscribe
    public void onAllOnlineUsersResponse(AllOnlineUsersResponse allUsersResponse) {
        LOG.debug("Update of user list {}", allUsersResponse.getUsers());
        updateUsersList(allUsersResponse.getUsers());
    }

    /**
     * Method called when the Delete User button is pressed
     *
     * If the Delete User button is pressed, this method requests the user service
     * first to logout the user, then to drop the user.
     *
     * @param event The ActionEvent created by pressing the Delete User button
     * @see de.uol.swp.client.lobby.LobbyService
     * @since 2022-11-08
     */
    @FXML
    private void onDropUser(ActionEvent event) {
        userService.dropUser(loggedInUser);
        userService.logout(loggedInUser);
    }

    /**
     * Method called when the Logout button is pressed
     *
     * If the logout button is pressed, this method requests the user service
     * to log this user out.
     *
     * @param event The ActionEvent created by pressing the logout button
     * @see de.uol.swp.client.lobby.LobbyService
     * @since 2022-11-08
     */
    @FXML
    private void onLogout(ActionEvent event) {
        userService.logout(loggedInUser);
    }

    /**
     * Updates the main menus user list according to the list given
     *
     * This method clears the entire user list and then adds the name of each user
     * in the list given to the main menus user list. If there ist no user list
     * this it creates one.
     *
     * @implNote The code inside this Method has to run in the JavaFX-application
     * thread. Therefore it is crucial not to remove the {@code Platform.runLater()}
     * @param userList A list of UserDTO objects including all currently logged in
     *                 users
     * @see de.uol.swp.common.user.UserDTO
     * @since 2019-08-29
     */
    private void updateUsersList(List<UserDTO> userList) {
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

    /**
     * Method called when the multiplayer lobby button is pressed
     *
     * If the multiplayer button is pressed, it posts an ShowJoinOrCreateViewEvent Object to the Eventbus.
     *
     * @param actionEvent The ActionEvent created by pressing the join lobby button
     * @see de.uol.swp.client.lobby.LobbyService
     * @since 2022-11-30
     */
    @FXML
     void onMultiplayer(ActionEvent actionEvent) {
        eventBus.post(new ShowJoinOrCreateViewEvent());
    }

    /**
     * Method called when the singleplayer button is pressed
     *
     * If the singleplayer button is pressed, this method requests the lobby service
     * to create a specified lobby. Therefore it uses as the parameter  name and password the value null.
     *
     * @param event The ActionEvent created by pressing the join lobby button
     * @see de.uol.swp.client.lobby.LobbyService
     * @since 2022-11-30
     */
    @FXML
    void onSingleplayer(ActionEvent event){
        lobbyService.createNewLobby(null, (UserDTO) loggedInUser, false, null);
    }

    /**
     * Method called when the AccountOption button is pressed
     *
     * If the AccountOption button is pressed, this method post on the bus a
     * ShowAccountOptionMessage. This request is received by the SceneManager,
     * which changes the screen to AccountOptionView screen.
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
     * If the credit button is pressed, it changes the scene from main menu to credit.
     *
     * @param event The ActionEvent created by pressing the credit button
     * @see de.uol.swp.client.credit
     * @since 2022-11-29
     */
    @FXML
    void onCreditButtonPressed(ActionEvent event) {
        eventBus.post(new ShowCreditViewEvent());
    }
    /**
     * Method called when the rulebook button is pressed
     *
     * If the rulebook button is pressed, it changes the scene from main menu to rulebook.
     *
     * @param event The ActionEvent created by pressing the rulebook button
     * @see de.uol.swp.client.rulebook
     * @since 2022-11-27
     */
    @FXML
    void onRulebookButtonPressed(ActionEvent event) {
        eventBus.post(new ShowRulebookViewEvent());
    }

}
