package de.uol.swp.client.lobby.Presenter;

import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import de.uol.swp.client.AbstractPresenter;
import de.uol.swp.client.lobby.LobbyService;
import de.uol.swp.client.main.MainMenuPresenter;
import de.uol.swp.common.lobby.response.LobbyCreatedResponse;
import de.uol.swp.common.user.User;
import de.uol.swp.common.user.UserDTO;
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
    public void onLobbyCreatedResponse(LobbyCreatedResponse message) {
        this.isMultiplayer = message.isMultiplayer();
        this.loggedInUser = message.getUser();
        this.owner = message.getUser();
        this.lobbyName = message.getName();
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
        if(loggedInUser.getUsername().equals(owner.getUsername())) {
            lobbyService.dropLobby(lobbyName, new UserDTO(loggedInUser.getUsername(), loggedInUser.getPassword(), loggedInUser.getEMail()), isMultiplayer);
        } else {
            lobbyService.leaveLobby(lobbyName, new UserDTO(loggedInUser.getUsername(), loggedInUser.getPassword(), loggedInUser.getEMail()));
        }
    }

    @FXML
    private void onButtonGameSettingsPressed(ActionEvent event) {
        //eventBus.post(createLobbyCanceledEvent);
    }

    @FXML
    private void onButtonStartPressed(ActionEvent event) {
        //eventBus.post(startGameEvent);

    }
}
