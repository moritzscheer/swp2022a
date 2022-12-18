package de.uol.swp.client.lobby.presenter;

import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import de.uol.swp.client.AbstractPresenter;
import de.uol.swp.client.lobby.LobbyService;
import de.uol.swp.client.lobby.event.ShowJoinOrCreateViewEvent;
import de.uol.swp.client.lobby.event.ShowLobbyViewEvent;
import de.uol.swp.common.lobby.message.UserLeftLobbyMessage;
import de.uol.swp.common.lobby.response.LobbyCreatedSuccessfulResponse;
import de.uol.swp.common.lobby.response.LobbyDroppedResponse;
import de.uol.swp.common.lobby.response.LobbyLeaveUserResponse;
import de.uol.swp.common.user.User;
import de.uol.swp.common.user.UserDTO;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
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

    private Boolean multiplayer;

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
    private AnchorPane infoBox;
    @FXML
    private Button yesButton;
    @FXML
    private Button noButton;
    @FXML
    private Label infoLabel;


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
        this.multiplayer = message.isMultiplayer();
        this.loggedInUser = message.getUser();
        this.owner = message.getUser();
        this.lobbyName = message.getName();
        this.lobbyID = message.getLobbyID();
        eventBus.post(new ShowLobbyViewEvent());
    }

    /**
     * Auxiliary method for the visibility of the infoBox
     *
     * If this method is called, it is possible to make the elements of a scene visible or invisible.
     *
     * @author Daniel Merzo
     * @since 2022-12-15
     */
    private void updateInfoBox(){
        if(!infoBox.isVisible()){
            infoBox.setVisible(true);
            yesButton.setVisible(true);
            noButton.setVisible(true);
            infoLabel.setVisible(true);
        }else {
            infoBox.setVisible(false);
            yesButton.setVisible(false);
            noButton.setVisible(false);
            infoLabel.setVisible(false);
        }
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
      updateInfoBox();
    }

    /**
     * Method called when the yes button in the infoBox is pressed
     *
     * This Method is called when the yes button is pressed.
     *
     * @author Daniel Merzo
     * @param actionEvent The ActionEvent generated by pressing the cancel button
     * @since 2022-12-15
     */
    @FXML
    private void onYesButtonPressed(ActionEvent actionEvent){
        lobbyService.leaveLobby(lobbyName,(UserDTO) loggedInUser, lobbyID, multiplayer);
        updateInfoBox();
    }

    /**
     * Method called when the no button in the infoBox is pressed
     *
     * This Method is called when the no button is pressed.
     *
     * @param actionEvent The ActionEvent generated by pressing the cancel button
     * @author Daniel Merzo
     * @since 2022-12-15
     */
    @FXML
    private void onNoButoonPressed(ActionEvent actionEvent){
        updateInfoBox();
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

    /**
     * Handles when a user left the Lobby
     *
     * If a UserLeftLobbyMessage is posted to the EventBus this method is called.
     *
     * @param message the UserLeftLobbyMessage object seen on the EventBus
     * @see de.uol.swp.common.lobby.message.UserLeftLobbyMessage
     * @author Daniel Merzo
     * @since 2022-12-15
     */
    @FXML
    private void onUserLeftLobbyMessage(UserLeftLobbyMessage message){

    }

    /**
     * Handles when a lobby is dropped
     *
     * If a LobbyDroppedResponse is posted to the EventBus the
     * lobby specific variable are set to null and
     * the ShowJoinOrCreateViewEvent is triggered.
     *
     * @param response the LobbyDroppedResponse object seen on the EventBus
     * @see de.uol.swp.common.lobby.response.LobbyDroppedResponse
     * @author Daniel Merzo
     * @since 2022-12-15
     */
    @Subscribe
    void onLobbyDroppedResponse(LobbyDroppedResponse response){
        eventBus.post(new ShowJoinOrCreateViewEvent());
        lobbyID = null;
        owner = null;
        loggedInUser = null;
        lobbyName = null;
        multiplayer = null;
        System.out.println("test1");
    }

    /**
     * Handles when a user leave the lobby
     *
     * If a LobbyLeaveUserResponse is posted to the EventBus the
     * user's specific variable to the lobby are set to null and
     * the ShowJoinOrCreateViewEvent is triggered.
     *
     * @param response the LobbyLeaveUserResponse object seen on the EventBus
     * @see de.uol.swp.common.lobby.response.LobbyLeaveUserResponse
     * @author Daniel Merzo
     * @since 2022-12-15
     */
    @Subscribe
    void onLobbyLeaveUserResponse(LobbyLeaveUserResponse response){
        eventBus.post(new ShowJoinOrCreateViewEvent());
        lobbyID = null;
        owner = null;
        loggedInUser = null;
        lobbyName = null;
        multiplayer = null;
    }
}
