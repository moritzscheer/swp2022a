package de.uol.swp.client.lobby.presenter;

import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import de.uol.swp.client.AbstractPresenter;
import de.uol.swp.client.lobby.LobbyService;
import de.uol.swp.client.lobby.event.ShowLobbyViewEvent;
import de.uol.swp.common.game.Map;
import de.uol.swp.common.lobby.dto.LobbyDTO;
import de.uol.swp.common.lobby.message.MapChangedMessage;
import de.uol.swp.common.lobby.message.UserJoinedLobbyMessage;
import de.uol.swp.common.lobby.message.UserLeftLobbyMessage;
import de.uol.swp.common.lobby.request.MapChangeRequest;
import de.uol.swp.common.lobby.response.LobbyCreatedSuccessfulResponse;
import de.uol.swp.common.lobby.response.LobbyJoinedSuccessfulResponse;
import de.uol.swp.common.user.User;
import de.uol.swp.common.user.UserDTO;
import de.uol.swp.common.user.response.AllUsersInLobbyResponse;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
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
import java.util.ArrayList;
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

    private User loggedInUser;

    private Integer lobbyID;
    private String lobbyName;
    private User owner;
    private ObservableList<String> users;
    private String password;
    private Boolean isMultiplayer;
    private Integer slots = 1;

    @Inject
    private LobbyService lobbyService;

    @FXML
    private Label labelPlayer;
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
    private Label labelMapName;
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

    // -----------------------------------------------------
    // Responses
    // -----------------------------------------------------

    /**
     * Handles created Lobbies
     *
     * If an LobbyCreatedResponse object is detected on the EventBus this
     * method is called. It saves the current information on the Lobby in the Client.
     *
     * @param message The LobbyCreatedResponse object detected on the EventBus
     * @author Moritz Scheer
     * @since 2022-11-17
     */
    @Subscribe
    public void onLobbyCreatedSuccessfulResponse(LobbyCreatedSuccessfulResponse message) {
        LOG.info("Lobby " + message.getName() + " created successful");

        //safe information in the Client
        loggedInUser = message.getUser();
        updateInformation(message.getLobby());

        this.mapList.setItems(FXCollections.observableList(Map.getMapList()));
        ChangeListener<? super Number> cl = (obsV, oldV, newV) -> {
            int mapIndex = mapList.getItems().get((Integer) newV).getIndex();
            Map m = new Map(mapIndex);

            updateMapDisplay(m);

            if(this.isMultiplayer)
            {
                User u = this.loggedInUser;
                UserDTO dto = new UserDTO(u.getUsername(), u.getPassword(), u.getEMail());
                eventBus.post(new MapChangeRequest(this.lobbyName, dto, m));
            }
        };
        this.mapList.getSelectionModel().selectedIndexProperty().addListener(cl);

        this.textFieldMapName.setText("None");

        eventBus.post(new ShowLobbyViewEvent());
    }

    /**
     * Handles joined Lobbies
     *
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
        System.out.println(message.getLobby().getOwner().getUsername());
        updateInformation(message.getLobby());

        mapList.setMouseTransparent(true);
        mapList.setFocusTraversable(false);
        this.mapList.setItems(FXCollections.observableList(Map.getMapList()));

        updateMapDisplay(message.getMap());

        eventBus.post(new ShowLobbyViewEvent());
    }

    /**
     * helper methods for ResponseMessages
     *
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
        isMultiplayer = message.isMultiplayer();
        slots = message.getUsers().size();

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
     *
     * If a new UserJoinedLobbyMessage object is posted to the EventBus the name of the newly
     * joined user is appended to the user list in the lobby.
     * Furthermore if the LOG-Level is set to DEBUG the message "New user {@literal
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
    private void updateUsersList(List<? extends User> userList) {
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
     *
     * This Method is called when the cancel button is pressed.
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
     * Updates the displayed map in the lobby when a MapChangedMessage is received
     *
     * @param mapChangedMessage The MapChangedMessage object
     * @see de.uol.swp.common.lobby.message.MapChangedMessage
     * @since 2022-12-31
     */
    @Subscribe
    public void onMapChangedMessage(MapChangedMessage mapChangedMessage)
    {
        Map m = new Map(this.mapList.getSelectionModel().getSelectedIndex());
        if(!mapChangedMessage.getMap().equals(m))
        {
            updateMapDisplay(mapChangedMessage.getMap());
        }
    }

    /**
     * Updates the displayed map in the lobby according to the parameter
     *
     * @param m The Map object
     * @see de.uol.swp.common.game.Map
     * @since 2022-12-31
     */
    private void updateMapDisplay(Map m)
    {
        Platform.runLater(() -> {
            textFieldMapName.setText(m.getName());
            mapThumb.setImage(new Image(m.getImageResource().toString()));
        });
    }
}
