package de.uol.swp.client.lobby.presenter;

import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import de.uol.swp.client.AbstractPresenter;
import de.uol.swp.client.lobby.LobbyService;
import de.uol.swp.client.lobby.event.ShowLobbyViewEvent;
import de.uol.swp.client.main.event.ShowMainMenuViewEvent;
import de.uol.swp.common.lobby.dto.LobbyDTO;
import de.uol.swp.common.lobby.response.LobbyCreatedSuccessfulResponse;
import de.uol.swp.common.user.User;
import de.uol.swp.common.user.request.ReturnToMainMenuRequest;
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
    public LobbyPresenter(LobbyDTO lobby) {
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
        eventBus.post(new ShowLobbyViewEvent(message.getLobbyID(), message.getName(), message.isMultiplayer()));
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
}
