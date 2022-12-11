package de.uol.swp.client.lobby.presenter;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import de.uol.swp.client.AbstractPresenter;
import de.uol.swp.client.lobby.LobbyService;
import de.uol.swp.client.lobby.event.ShowCreateLobbyViewEvent;
import de.uol.swp.client.lobby.event.JoinOrCreateCanceledEvent;
import de.uol.swp.client.user.ClientUserService;
import de.uol.swp.common.lobby.dto.LobbyDTO;
import de.uol.swp.common.lobby.exception.LobbyJoinedExceptionResponse;
import de.uol.swp.common.lobby.message.LobbyCreatedMessage;
import de.uol.swp.common.lobby.response.AllOnlineLobbiesResponse;
import de.uol.swp.common.user.User;
import de.uol.swp.common.user.UserDTO;
import de.uol.swp.common.user.response.LoginSuccessfulResponse;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.List;
import javafx.scene.layout.AnchorPane;

/**
 * Manages the joinOrCreate window
 *
 * @author Maxim Erden
 * @see de.uol.swp.client.AbstractPresenter
 * @since 2022-11-23
 *
 */
public class JoinOrCreatePresenter extends AbstractPresenter {

    public static final String FXML = "/fxml/JoinOrCreateView.fxml";
    private static final Logger LOG = LogManager.getLogger(JoinOrCreatePresenter.class);

    private User loggedInUser;

    private ObservableList<String> lobbies;


    @FXML
    private Label LabelPasswordView;

    @FXML
    private Label errorMessage;

    @Inject
    private LobbyService lobbyService;
    @FXML
    private ListView<String> lobbiesView;
    @FXML
    private TextField textFieldName;
    @FXML
    private TextField textFieldPassword;


    @FXML
    private AnchorPane AnchorPanePassword;

    @FXML
    private AnchorPane AnchorPaneBottomView;

    /**
     * Default Constructor
     * @since 2022-11-15
     */
    public JoinOrCreatePresenter() {
        // needed for javafx
    }

    /**
     * Constructor
     *
     * @param eventBus The EventBus set in ClientModule
     * @param userService The injected ClientUserService
     * @see de.uol.swp.client.di.ClientModule
     * @author Maxim Erden
     * @since 2019-09-18
     */
    @Inject
    public JoinOrCreatePresenter(EventBus eventBus, ClientUserService userService) {
        setEventBus(eventBus);
    }

    /**
     * Handles successful login
     *
     * If a LoginSuccessfulResponse is posted to the EventBus the loggedInUser
     * of this client is set to the one in the message received.
     *
     * @param message the LoginSuccessfulResponse object seen on the EventBus
     * @see de.uol.swp.common.user.response.LoginSuccessfulResponse
     * @author Moritz Scheer
     * @since 2022-11-27
     */
    @Subscribe
    public void onLoginSuccessfulResponse(LoginSuccessfulResponse message) {
        this.loggedInUser = message.getUser();
    }

    // -----------------------------------------------------
    // Methods for the LobbyList
    // -----------------------------------------------------

    /**
     * Handles created multiplayer lobbies
     *
     * If a LobbyCreatedMessage is posted to the EventBus this method is called.
     *
     * @param message the LobbyCreatedMessage object seen on the EventBus
     * @see de.uol.swp.common.lobby.message.LobbyCreatedMessage
     * @author Moritz Scheer & Maxim Erden
     * @since 2022-11-30
     */
    @Subscribe
    public void onLobbyCreatedMessage(LobbyCreatedMessage message) {
        LOG.info("User " + message.getUser().getUsername() + " created the lobby " + message.getName());
        System.out.println(!loggedInUser.getUsername().equals(message.getUser().getUsername()));
        Platform.runLater(() -> {
            if(lobbies != null && loggedInUser != null && !loggedInUser.getUsername().equals(message.getUser().getUsername()))
                lobbies.add(message.getName());
        });
    }

    /**
     * Handles new list of lobbies
     *
     * If a new AllOnlineLobbiesResponse object is posted to the EventBus the names
     * of currently open lobbies are put onto the lobby list in the joinOrCreate.
     * Furthermore, if the LOG-Level is set to DEBUG the message "Update of lobby
     * list" with the names of all currently open lobbies is displayed in the
     * log.
     *
     * @param allLobbiesResponse the AllOnlineLobbiesResponse object seen on the EventBus
     * @see de.uol.swp.common.lobby.response.AllOnlineLobbiesResponse
     * @author Moritz Scheer & Maxim Erden
     * @since 2022-11-30
     */
    @Subscribe
    public void onAllOnlineLobbiesResponse(AllOnlineLobbiesResponse allLobbiesResponse) {
        LOG.debug("Update of lobby list {}", allLobbiesResponse.getLobbies());
        updateLobbyList(allLobbiesResponse.getLobbies());
    }

    /**
     * Updates the joinOrCreate lobby list according to the list given
     *
     * This method clears the entire lobby list and then adds the name of each lobby, which contains not the loggedInUser
     * in the list given to the joinOrCreate lobby list. If there is no lobby list, then it creates one.
     *
     * @implNote The code inside this Method has to run in the JavaFX-application
     * thread. Therefore, it is crucial not to remove the {@code Platform.runLater()}
     * @param lobbyList A list of LobbyDTO objects including all currently open lobbies
     * @see de.uol.swp.common.lobby.dto.LobbyDTO
     * @author Moritz Scheer & Maxim Erden
     * @since 2022-11-30
     */
    private void updateLobbyList(List<LobbyDTO> lobbyList) {
        // Attention: This must be done on the FX Thread!
        Platform.runLater(() -> {
            if (lobbies == null) {
                lobbies = FXCollections.observableArrayList();
                lobbiesView.setItems(lobbies);
            }
            lobbies.clear();
            lobbyList.forEach(u -> {
                if (!u.getUsers().contains(loggedInUser)) {
                    lobbies.add(u.getName());
                }
            });
        });
    }

    // -----------------------------------------------------
    // Lobby Join Methods
    // -----------------------------------------------------

    /**
     * Handles lobby join exceptions
     *
     * If an LobbyJoinedExceptionResponse object is detected on the EventBus this
     * method is called. If the loglevel is set to Error or higher "Lobby join error " and the
     * error message are written to the log.
     *
     * @param message The LobbyJoinedExceptionResponse object detected on the EventBus
     * @author Moritz Scheer
     * @since 2022-11-27
     */
    @Subscribe
    public void onLobbyJoinedExceptionResponse(LobbyJoinedExceptionResponse message) {
        errorMessage.setVisible(true);
        LOG.error("Lobby join error {}", message);
    }

    // -----------------------------------------------------
    // Button Methods
    // -----------------------------------------------------

    /**
     * Method called when the cancel button is pressed
     *
     * This Method is called when the cancel button is pressed. It posts an instance
     * of the JoinOrCreateCanceledEvent to the EventBus the SceneManager is subscribed
     * to.
     *
     * @param actionEvent The ActionEvent generated by pressing the cancel button
     * @see de.uol.swp.client.lobby.event.JoinOrCreateCanceledEvent
     * @see de.uol.swp.client.SceneManager
     * @author Maxim Erden
     * @since 2022-11-30
     */
    @FXML
    void onCancelButtonPressed(ActionEvent actionEvent) {
        eventBus.post(new JoinOrCreateCanceledEvent(loggedInUser));
    }

    /**
     * Method called when the create lobby button is pressed
     *
     * This Method is called when the create lobby button is pressed. It posts an instance
     * of the ShowCreateLobbyViewEvent to the EventBus the SceneManager is subscribed
     * to.
     *
     * @param actionEvent The ActionEvent generated by pressing the create lobby button
     * @see de.uol.swp.client.lobby.event.ShowCreateLobbyViewEvent
     * @see de.uol.swp.client.SceneManager
     * @author Maxim Erden
     * @since 2022-11-30
     */
    @FXML
    void onCreateLobbyButtonPressed(ActionEvent actionEvent) {
        eventBus.post(new ShowCreateLobbyViewEvent());
    }

    /**
     * Method to test the GUI
     *
     * @param actionEvent The ActionEvent generated by pressing the create lobby button
     * @author Maxim Erden
     * @since 2022-11-30
     */



    @FXML
    void onButtonJoinLobbyInJoinorCreateViewPressed(ActionEvent actionEvent) {
        if(lobbiesView.getSelectionModel().getSelectedItem() == null){
            return;
        }
        updatePasswordView();
    }

    public void handleMouseClick(MouseEvent click) {

        if (click.getClickCount() == 2) {
            updatePasswordView();
        }
    }

    public void updatePasswordView(){
        if(!AnchorPanePassword.isVisible()) {
            LabelPasswordView.setText("Join Lobby "+ lobbiesView.getSelectionModel().getSelectedItem());
            lobbiesView.setMouseTransparent(true);
            lobbiesView.setFocusTraversable(false);
            AnchorPanePassword.setVisible(true);
            AnchorPaneBottomView.setVisible(false);
        }
        else{
            lobbiesView.setMouseTransparent(false);
            lobbiesView.setFocusTraversable(true);
            AnchorPanePassword.setVisible(false);
            AnchorPaneBottomView.setVisible(true);
            textFieldPassword.clear();
            errorMessage.setVisible(false);

        }
    }

    public void onButtonJoinLobbyButtonPressed(ActionEvent actionEvent) {
        lobbyService.joinLobby(lobbiesView.getSelectionModel().getSelectedItem(), (UserDTO) loggedInUser, textFieldPassword.getText());
        textFieldPassword.clear();
        errorMessage.setVisible(true);
    }

    public void onButtonPasswordViewCancelButtonPressed(ActionEvent actionEvent) {
        updatePasswordView();
    }
}