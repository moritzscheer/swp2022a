package de.uol.swp.client.lobby.Presenter;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import de.uol.swp.client.AbstractPresenter;
import de.uol.swp.client.lobby.LobbyService;
import de.uol.swp.client.lobby.event.ShowCreateLobbyViewEvent;
import de.uol.swp.client.lobby.event.JoinOrCreateCanceledEvent;
import de.uol.swp.client.user.ClientUserService;
import de.uol.swp.common.lobby.dto.LobbyDTO;
import de.uol.swp.common.lobby.message.LobbyCreatedMessage;
import de.uol.swp.common.lobby.response.AllOnlineLobbiesResponse;
import de.uol.swp.common.lobby.response.LobbyJoinedExceptionResponse;
import de.uol.swp.common.user.User;
import de.uol.swp.common.user.UserDTO;
import de.uol.swp.common.user.response.LoginSuccessfulResponse;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.List;

/**
 * Manages the registration window
 *
 * @author Marco Grawunder
 * @see de.uol.swp.client.AbstractPresenter
 * @since 2019-08-29
 *
 */
public class JoinOrCreatePresenter extends AbstractPresenter {

    public static final String FXML = "/fxml/JoinOrCreateView.fxml";
    private static final Logger LOG = LogManager.getLogger(JoinOrCreatePresenter.class);
    @Inject
    private LobbyService lobbyService;
    private ObservableList<String> lobbies;
    private User loggedInUser;
    @FXML
    private ListView<String> lobbiesView;
    @FXML
    private TextField textFieldName;
    @FXML
    private TextField textFieldPassword;

    public JoinOrCreatePresenter() {
    }

    /**
     * Constructor
     *
     * @param eventBus The EventBus set in ClientModule
     * @param userService The injected ClientUserService
     * @see de.uol.swp.client.di.ClientModule
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
     * Handles created Lobbies
     *
     * If an LobbyCreatedMessage object is detected on the EventBus this
     * method is called. It saves the current information on the Lobby in the Client.
     *
     * @param message The LobbyCreatedMessage object detected on the EventBus
     * @since 2022-11-27
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
                if (!u.getOwner().equals(loggedInUser) && !u.getUsers().contains(loggedInUser)) {
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
     * @since 2022-11-27
     */
    @Subscribe
    public void onLobbyJoinedExceptionResponse(LobbyJoinedExceptionResponse message) {
        LOG.error("Lobby join error {}", message);
    }

    /**
     * Method called when the cancel button is pressed
     *
     * This Method is called when the cancel button is pressed. It posts an instance
     * of the RegistrationCanceledEvent to the EventBus the SceneManager is subscribed
     * to.
     *
     * @param event The ActionEvent generated by pressing the register button
     * @see de.uol.swp.client.register.event.RegistrationCanceledEvent
     * @see de.uol.swp.client.SceneManager
     * @since 2019-09-02
     */
    @FXML
    void onCancelButtonPressed(ActionEvent event) {
        eventBus.post(new JoinOrCreateCanceledEvent());
    }

    @FXML
    void onCreateLobbyButtonPressed(ActionEvent actionEvent) {
        eventBus.post(new ShowCreateLobbyViewEvent());
    }

    @FXML
    void onButtonSubmitPressed(ActionEvent actionEvent) {
        lobbyService.joinLobby(textFieldName.getText(), (UserDTO) loggedInUser, textFieldPassword.getText());
    }


}