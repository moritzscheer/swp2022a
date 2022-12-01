package de.uol.swp.client.lobby.Presenter;

import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import de.uol.swp.client.AbstractPresenter;
import de.uol.swp.client.lobby.LobbyService;
import de.uol.swp.client.lobby.event.CreateLobbyCanceledEvent;
import de.uol.swp.common.lobby.exception.LobbyCreatedExceptionResponse;
import de.uol.swp.common.user.User;
import de.uol.swp.common.user.UserDTO;
import de.uol.swp.common.user.response.LoginSuccessfulResponse;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CreateLobbyPresenter extends AbstractPresenter {
    public static final String FXML = "/fxml/CreateLobbyView.fxml";
    private static final Logger LOG = LogManager.getLogger(CreateLobbyPresenter.class);
    @Inject
    private LobbyService lobbyService;
    private ObservableList<String> users;
    private User loggedInUser;
    @FXML
    private ListView<String> usersView;
    @FXML
    private TextField nameField;
    @FXML
    private TextField passwordField;
    @FXML
    private Label errorMessage;

    @FXML
    void onCancelButtonPressed(ActionEvent event) {
        eventBus.post(new CreateLobbyCanceledEvent());
    }

    /**
     * Handles successful login
     *
     * If a LoginSuccessfulResponse is posted to the EventBus the loggedInUser
     * of this client is set to the one in the message received.
     *
     * @param message the LoginSuccessfulResponse object seen on the EventBus
     * @see de.uol.swp.common.user.response.LoginSuccessfulResponse
     * @since 2019-09-05
     */
    @Subscribe
    public void onLoginSuccessfulResponse(LoginSuccessfulResponse message) {
        this.loggedInUser = message.getUser();
    }

    /**
     * Handles lobby create exceptions
     *
     * If an LobbyCreatedExceptionResponse object is detected on the EventBus this
     * method is called. If the loglevel is set to Error or higher "Lobby create error " and the
     * error message are written to the log.
     *
     * @param message The RegistrationSuccessfulResponse object detected on the EventBus
     * @see de.uol.swp.client.SceneManager
     * @since 2019-09-02
     */
    @Subscribe
    public void onLobbyCreatedExceptionMessage(LobbyCreatedExceptionResponse message) {
        errorMessage.setVisible(true);
        LOG.error("Lobby create error {}", message);
    }

    @FXML
    public void onCreateLobbyPressed(ActionEvent actionEvent) {
        lobbyService.createNewLobby(nameField.getText(), (UserDTO) loggedInUser, true, passwordField.getText());
    }
}
