package de.uol.swp.client.lobby.Presenter;

import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import de.uol.swp.client.AbstractPresenter;
import de.uol.swp.client.lobby.LobbyService;
import de.uol.swp.client.lobby.event.ShowLobbyViewEvent;
import de.uol.swp.common.lobby.message.UserJoinedLobbyMessage;
import de.uol.swp.common.lobby.response.LobbyCreatedSuccessfulResponse;
import de.uol.swp.common.lobby.response.LobbyJoinedSuccessfulResponse;
import de.uol.swp.common.user.User;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
    public void onLobbyCreatedResponse(LobbyCreatedSuccessfulResponse message) {
        this.isMultiplayer = message.isMultiplayer();
        this.loggedInUser = message.getUser();
        this.owner = message.getUser();
        this.lobbyName = message.getName();
        this.lobbyID = message.getLobbyID();
        LOG.info("Lobby " + message.getName() + " created successful");
        eventBus.post(new ShowLobbyViewEvent());
    }

    /**
     * Handles User joined Lobbies Request
     *
     * If an LobbyCreatedResponse object is detected on the EventBus this
     * method is called. It saves the current information on the Lobby in the Client.
     *
     * @param message The LobbyCreatedResponse object detected on the EventBus
     * @since 2022-11-17
     */
    @Subscribe
    public void onLobbyJoinedSuccessfulResponse(LobbyJoinedSuccessfulResponse message) {
        this.isMultiplayer = message.isMultiplayer();
        this.loggedInUser = message.getUser();
        this.lobbyName = message.getName();
        this.lobbyID = message.getLobbyID();
        LOG.info("Lobby " + message.getName() + " successfully joined");
        eventBus.post(new ShowLobbyViewEvent());
    }

    /**
     * Handles user joined lobbies messages
     *
     * If an UserJoinedLobbyMessage object is detected on the EventBus this
     * method is called. It saves the current information on the Lobby in the Client.
     *
     * @param message The LobbyCreatedResponse object detected on the EventBus
     * @since 2022-11-27
     */
    @Subscribe
    public void onUserJoinedLobbyMessage(UserJoinedLobbyMessage message) {
        LOG.info("User " + message.getUser().getUsername() + " joined the Lobby " + message.getName());
    }


    /**
     * Method called when the back button is pressed
     *
     * If the join lobby button is pressed, this method requests the lobby service
     * to drop a specified lobby, if the loggedInUser is the owner. Else the method requests the
     * lobbyservice to leave a specified.
     *
     * @param event The ActionEvent created by pressing the back button
     * @see de.uol.swp.client.lobby.LobbyService
     * @since 2022-11-18
     */
    @FXML
    private void onButtonBackPressed(ActionEvent event) {
        // leave user
    }

    @FXML
    private void onButtonStartPressed(ActionEvent event) {
        //eventBus.post(startGameEvent);
    }
}
