package de.uol.swp.client.lobby.presenter;

import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import de.uol.swp.client.AbstractPresenter;
import de.uol.swp.client.lobby.LobbyService;
import de.uol.swp.client.lobby.event.ShowLobbyViewEvent;
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

import java.util.Set;

/**
 * Manages the Lobby window
 *
 * @author Moritz Scheer
 * @see de.uol.swp.client.AbstractPresenter
 * @since 2022-11-15
 *
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

    @Inject
    private LobbyService lobbyService;
    @FXML
    private Label labelPlayer;
    @FXML
    private ListView<String> usersView;
    @FXML
    private Label labelMap;
    @FXML
    private Label labelMapName;
    @FXML
    private Button buttonBack;

    /**
     * Default Constructor
     * @since 2022-11-15
     */
    public LobbyPresenter() {
        // needed for javafx
    }

    /**
     * Handles created Lobbies
     *
     * If an LobbyCreatedResponse object is detected on the EventBus this
     * method is called. It saves the current information on the Lobby in the Client.
     *
     * @param message The LobbyCreatedResponse object detected on the EventBus
     * @since 2022-11-17
     */
    @Subscribe
    public void onLobbyCreatedSuccessfulResponse(LobbyCreatedSuccessfulResponse message) {
        LOG.info("Lobby " + message.getName() + " created successfully");
        this.loggedInUser = message.getUser();
        this.lobbyID = message.getLobby().getLobbyID();
        this.lobbyName = message.getName();
        this.owner = loggedInUser;
        this.password = message.getLobby().getPassword();
        this.isMultiplayer = message.getLobby().isMultiplayer();
        updateUsersList(message.getLobby().getUsers());
        eventBus.post(new ShowLobbyViewEvent());
    }

    /**
     * Handles joined user
     *
     * If an LobbyCreatedResponse object is detected on the EventBus this
     * method is called. It saves the current information on the Lobby in the Client.
     *
     * @param message The LobbyCreatedResponse object detected on the EventBus
     * @since 2022-11-17
     */
    @Subscribe
    public void onLobbyJoinedSuccessfulResponse(LobbyJoinedSuccessfulResponse message) {
        LOG.info("You successfully joined the Lobby " + message.getName());
        this.loggedInUser = message.getUser();
        this.lobbyID = message.getLobby().getLobbyID();
        this.lobbyName = message.getName();
        this.owner = message.getLobby().getOwner();
        this.password = message.getLobby().getPassword();
        this.isMultiplayer = message.getLobby().isMultiplayer();
        updateUsersList(message.getLobby().getUsers());
        eventBus.post(new ShowLobbyViewEvent());
    }

    /**
     * Handles joined user
     *
     * If an LobbyCreatedResponse object is detected on the EventBus this
     * method is called. It saves the current information on the Lobby in the Client.
     *
     * @param message The LobbyCreatedResponse object detected on the EventBus
     * @since 2022-11-17
     */
    @Subscribe
    public void onUserJoinedLobbyMessage(UserJoinedLobbyMessage message) {
        LOG.info("User " + message.getUser().getUsername() + " joined successfully");
        Platform.runLater(() -> {
            if (users != null && loggedInUser != null)
                users.add(message.getUser().getUsername());
        });
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
    private void updateUsersList(Set<User> userList) {
        for(User user : userList) {
            System.out.println(user.getUsername());
        }
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
     * Method called when the cancel button is pressed
     *
     * This Method is called when the cancel button is pressed.
     *
     * @param actionEvent The ActionEvent generated by pressing the cancel button
     * @since 2022-11-30
     */
    @FXML
    private void onButtonBackPressed(ActionEvent actionEvent) {
        // leave user
    }

    /**
     * Method called when the start button is pressed
     *
     * This Method is called when the start button is pressed.
     *
     * @param actionEvent The ActionEvent generated by pressing the start button
     * @since 2022-11-30
     */
    @FXML
    private void onButtonStartPressed(ActionEvent actionEvent) {
        // start game
    }
}