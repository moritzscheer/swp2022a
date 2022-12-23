package de.uol.swp.client.lobby.presenter;

import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import de.uol.swp.client.AbstractPresenter;
import de.uol.swp.client.lobby.LobbyService;
import de.uol.swp.client.lobby.event.ShowLobbyViewEvent;
import de.uol.swp.common.game.Map;
import de.uol.swp.common.lobby.message.UserJoinedLobbyMessage;
import de.uol.swp.common.lobby.message.UserLeftLobbyMessage;
import de.uol.swp.common.lobby.response.LobbyCreatedSuccessfulResponse;
import de.uol.swp.common.user.User;
import de.uol.swp.common.user.UserDTO;
import de.uol.swp.common.user.request.ReturnToMainMenuRequest;
import de.uol.swp.common.user.response.AllUsersInLobbyResponse;
import de.uol.swp.common.user.response.LobbyJoinSuccessfulResponse;
import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

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

    private ObservableList<String> users;

    private Integer lobbyID;
    private User owner;

    private User loggedInUser;

    private String lobbyName;

    private Boolean isMultiplayer;

    @Inject
    private LobbyService lobbyService;

    @FXML
    private Label labelPlayer;

    @FXML
    private ListView<String> usersView;

    @FXML
    private Button buttonBack;

    @FXML
    private ListView<Map> mapList;

    @FXML
    private Label textFieldMapName;

    @FXML
    private ImageView mapThumb;

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
        LOG.info("Lobby " + message.getName() + " created successful");
        this.isMultiplayer = message.isMultiplayer();
        this.loggedInUser = message.getUser();
        this.owner = message.getUser();
        this.lobbyName = message.getName();
        this.lobbyID = message.getLobbyID();

        this.mapList.setItems(FXCollections.observableList(Map.getMapList()));
        ChangeListener<? super Number> cl = (obsV, oldV, newV) -> {
            int mapIndex = mapList.getItems().get((Integer) newV).getIndex();
            textFieldMapName.setText(new Map(mapIndex).getName());
            mapThumb.setImage(new Image(new Map(mapIndex).getImageResource().toString()));
        };
        this.mapList.getSelectionModel().selectedIndexProperty().addListener(cl);

        this.textFieldMapName.setText("None");

        eventBus.post(new ShowLobbyViewEvent());
    }

    /**
     * Method called when the back button is pressed
     *
     * This Method is called when the back button is pressed. Goes back to MainMenu
     *
     * @param actionEvent The ActionEvent generated by pressing the back button
     * @since 2022-12-08
     * @author Moritz and Maria
     */
    @FXML
    private void onBackButtonPressed(ActionEvent actionEvent) {
        eventBus.post(new ReturnToMainMenuRequest(loggedInUser));
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
    private void onStartButtonPressed(ActionEvent actionEvent) {
        // start game
    }

    /**
     * Handles successful lobby join
     *
     * If a LoginSuccessfulResponse is posted to the EventBus the loggedInUser
     * of this client is set to the one in the message received and the full
     * list of users currently logged in is requested.
     *
     * @param message the LoginSuccessfulResponse object seen on the EventBus
     * @see de.uol.swp.common.user.response.LobbyJoinSuccessfulResponse
     * @since 2022-11-20
     */
    @Subscribe
    public void onLobbyJoinSuccessfulResponse(LobbyJoinSuccessfulResponse message) {
        this.loggedInUser = message.getUser();
        lobbyService.retrieveAllUsersInLobby(lobbyName);
    }

    /**
     * Handles users who have newly joined the lobby
     *
     * If a new UserJoinedLobbyMessage object is posted to the EventBus the name of the newly
     * joined user is appended to the user list in the lobby screen.
     * Furthermore if the LOG-Level is set to DEBUG the message "New user {@literal
     * <Username>} joined the lobby." is displayed in the log.
     *
     * @param message the UserJoinedLobbyMessage object seen on the EventBus
     * @see de.uol.swp.common.lobby.message.UserJoinedLobbyMessage
     * @since 2022-11-20
     */
    @Subscribe
    public void onUserJoinedLobbyMessage(UserJoinedLobbyMessage message) {
        if (message.getName().equals(lobbyName)) {
            LOG.debug("New user {}  joined the lobby,", message.getUser().getUsername());
            Platform.runLater(() -> {
                if (users != null && loggedInUser != null && !loggedInUser.getUsername().equals(message.getUser().getUsername()))
                    users.add(message.getUser().getUsername());
            });
        }
    }

    /**
     * Handles users who left the lobby
     *
     * If a new UserLeftLobbyMessage object is posted to the EventBus the name of the newly
     * left user is removed from the user list in the lobby screen.
     * Furthermore if the LOG-Level is set to DEBUG the message "User {@literal
     * <Username>} left the lobby." is displayed in the log.
     *
     * @param message the UserLeftLobbyMessage object seen on the EventBus
     * @see de.uol.swp.common.lobby.message.UserLeftLobbyMessage
     * @since 2022-11-20
     */
    @Subscribe
    public void onUserLeftLobbyMessage(UserLeftLobbyMessage message) {
        if (message.getName().equals(lobbyName)) {
            LOG.debug("User {}  left the lobby.", message.getUser().getUsername());
            Platform.runLater(() -> users.remove(message.getUser().getUsername()));
        }
    }

    /**
     * Handles new list of users
     *
     * If a new AllUsersInLobbyResponse object is posted to the EventBus the names
     * of users currently in the lobby are put onto the user list in the lobby screen.
     * Furthermore if the LOG-Level is set to DEBUG the message "Update of user
     * list" with the names of all users who are currently in the lobby is displayed in the
     * log.
     *
     * @param allUsersResponse the AllUsersInLobbyResponse object seen on the EventBus
     * @see de.uol.swp.common.user.response.AllUsersInLobbyResponse
     * @since 2022-11-20
     */
    @Subscribe
    public void onAllUsersInLobbyResponse(AllUsersInLobbyResponse allUsersResponse) {
        if (allUsersResponse.getLobbyName().equals(lobbyName)) {
            LOG.debug("Update of user list {}", allUsersResponse.getUsers());
            updateUsersList(allUsersResponse.getUsers());
        }
    }

    /**
     * Updates the lobby user list according to the list given
     *
     * This method clears the entire user list and then adds the name of each user
     * in the list given to the lobby screen user list. If there ist no user list
     * this it creates one.
     *
     * @implNote The code inside this Method has to run in the JavaFX-application
     * thread. Therefore it is crucial not to remove the {@code Platform.runLater()}
     * @param userList A list of UserDTO objects including all users who are currently
     *                 in the lobby
     * @see de.uol.swp.common.user.UserDTO
     * @since 2022-11-20
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
}

