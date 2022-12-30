package de.uol.swp.client.lobby.presenter;

import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;

import de.uol.swp.client.AbstractPresenter;
import de.uol.swp.client.lobby.LobbyService;
import de.uol.swp.client.lobby.event.ShowLobbyViewEvent;
import de.uol.swp.common.lobby.dto.LobbyDTO;
import de.uol.swp.common.lobby.message.UserJoinedLobbyMessage;
import de.uol.swp.common.lobby.response.LobbyCreatedSuccessfulResponse;
import de.uol.swp.common.lobby.response.LobbyJoinedSuccessfulResponse;
import de.uol.swp.common.user.User;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

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
    private Boolean isMultiplayer;
    private Integer slots = 1;

    @Inject private LobbyService lobbyService;

    @FXML private Label labelPlayer;
    @FXML private ListView<String> usersView;
    @FXML private Label textFieldPassword;
    @FXML private Label textFieldLobbyName;
    @FXML private Label textFieldOnlineUsers;
    @FXML private Label textFieldOwner;
    @FXML private Label labelMap;
    @FXML private Label labelMapName;
    @FXML private Button buttonBack;

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
     *
     * <p>If an LobbyCreatedResponse object is detected on the EventBus this method is called. It
     * saves the current information on the Lobby in the Client.
     *
     * @param message The LobbyCreatedResponse object detected on the EventBus
     * @author Moritz Scheer
     * @since 2022-11-17
     */
    @Subscribe
    public void onLobbyCreatedSuccessfulResponse(LobbyCreatedSuccessfulResponse message) {
        LOG.info("Lobby " + message.getName() + " created successful");

        // safe information in the Client
        loggedInUser = message.getUser();
        updateInformation(message.getLobby());

        eventBus.post(new ShowLobbyViewEvent());
    }

    /**
     * Handles joined Lobbies
     *
     * <p>If an LobbyJoinedSuccessfulResponse object is detected on the EventBus this method is
     * called. It saves the current information on the Lobby in the Client.
     *
     * @param message The LobbyJoinedSuccessfulResponse object detected on the EventBus
     * @author Moritz Scheer
     * @since 2022-12-13
     */
    @Subscribe
    public void onLobbyJoinedSuccessfulResponse(LobbyJoinedSuccessfulResponse message) {
        LOG.info("Lobby " + message.getName() + " successfully joined");

        // safe information in the Client
        loggedInUser = message.getUser();
        System.out.println(message.getLobby().getOwner().getUsername());
        updateInformation(message.getLobby());

        eventBus.post(new ShowLobbyViewEvent());
    }

    /**
     * helper methods for ResponseMessages
     *
     * <p>It saves the current information of the lobby in the presenter.
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
        isMultiplayer = message.isMultiplayer();
        slots = message.getUsers().size();

        // display data in GUI
        textFieldLobbyName.setText(lobbyName);
        textFieldOnlineUsers.setText(String.valueOf(slots));
        textFieldPassword.setText(password);
        textFieldOwner.setText(owner.getUsername());

        // initialize user list
        List<User> list = new ArrayList<>(message.getUsers());
        updateUsersList(list);
    }

    // -----------------------------------------------------
    // Messages
    // -----------------------------------------------------

    /**
     * Handles joined users
     *
     * <p>If a new UserJoinedLobbyMessage object is posted to the EventBus the name of the newly
     * joined user is appended to the user list in the lobby. Furthermore if the LOG-Level is set to
     * DEBUG the message "New user {@literal <Username>} joined the lobby." is displayed in the log.
     *
     * @param message the UserJoinedLobbyMessage object seen on the EventBus
     * @see de.uol.swp.common.lobby.message.UserJoinedLobbyMessage
     * @since 2022-12-13
     */
    @Subscribe
    public void onUserJoinedLobbyMessage(UserJoinedLobbyMessage message) {
        LOG.debug("New user {}  joined the lobby,", message.getUser().getUsername());
        Platform.runLater(
                () -> {
                    if (users != null
                            && loggedInUser != null
                            && !loggedInUser.getUsername().equals(message.getUser().getUsername()))
                        users.add(message.getUser().getUsername());
                    slots++;
                    textFieldOnlineUsers.setText(String.valueOf(slots));
                });
    }

    /**
     * Updates the main menus user list according to the list given
     *
     * <p>This method clears the entire user list and then adds the name of each user in the list
     * given to the main menus user list. If there ist no user list this it creates one.
     *
     * @implNote The code inside this Method has to run in the JavaFX-application thread. Therefore
     *     it is crucial not to remove the {@code Platform.runLater()}
     * @param userList A list of UserDTO objects including all currently logged in users
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

    // -----------------------------------------------------
    // ActionEvents
    // -----------------------------------------------------

    /**
     * Method called when the cancel button is pressed
     *
     * <p>This Method is called when the cancel button is pressed.
     *
     * @param actionEvent The ActionEvent generated by pressing the back button
     * @since 2022-12-08
     * @author Moritz Scheer and Maria
     */
    @FXML
    private void onBackButtonPressed(ActionEvent actionEvent) {
        // leave user
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
