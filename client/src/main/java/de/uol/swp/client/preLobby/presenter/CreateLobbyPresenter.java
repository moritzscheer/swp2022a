package de.uol.swp.client.preLobby.presenter;

import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;

import de.uol.swp.client.AbstractPresenter;
import de.uol.swp.client.lobby.LobbyService;
import de.uol.swp.client.preLobby.events.CreateLobbyCanceledEvent;
import de.uol.swp.common.lobby.exception.LobbyCreatedExceptionResponse;
import de.uol.swp.common.lobby.response.LobbyCreatedSuccessfulResponse;
import de.uol.swp.common.user.User;
import de.uol.swp.common.user.UserDTO;
import de.uol.swp.common.user.response.LoginSuccessfulResponse;

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

    private User loggedInUser;

    @Inject private LobbyService lobbyService;

    @FXML private ListView<String> usersView;
    @FXML private TextField nameField;
    @FXML private TextField passwordField;
    @FXML private Label errorMessage1; // no name typed in
    @FXML private Label errorMessage2; // Name already exists!

    /**
     * Default Constructor
     *
     * @author Maxim Erden
     * @since 2022-11-15
     */
    public CreateLobbyPresenter() {
        // needed for javafx
    }

    /**
     * Handles successful login
     *
     * <p>If a LoginSuccessfulResponse is posted to the EventBus the loggedInUser of this client is
     * set to the one in the message received.
     *
     * @param message the LoginSuccessfulResponse object seen on the EventBus
     * @see de.uol.swp.common.user.response.LoginSuccessfulResponse
     * @author Maxim Erden
     * @since 2022-11-23
     */
    @Subscribe
    public void onLoginSuccessfulResponse(LoginSuccessfulResponse message) {
        this.loggedInUser = message.getUser();
    }

    /**
     * Handles lobby create exceptions
     *
     * <p>If an LobbyCreatedExceptionResponse object is detected on the EventBus this method is
     * called. If the loglevel is set to Error or higher "Lobby create error " and the error message
     * are written to the log.
     *
     * @param message The LobbyCreatedExceptionResponse object detected on the EventBus
     * @see de.uol.swp.client.SceneManager
     * @author Moritz Scheer
     * @since 2022-11-30
     */
    @Subscribe
    public void onLobbyCreatedExceptionMessage(LobbyCreatedExceptionResponse message) {
        errorMessage1.setVisible(false);
        errorMessage2.setVisible(true);
        LOG.error("Lobby create error {}", message);
    }

    /**
     * Method called when the lobby is created successfully
     *
     * @param message The LobbyCreatedSuccessfulResponse object detected on the EventBus
     * @see de.uol.swp.common.lobby.response.LobbyCreatedSuccessfulResponse
     * @author Ole Zimmermann
     * @since 2023-01-25
     */
    @Subscribe
    public void onLobbyCreatedSuccessfulResponse(LobbyCreatedSuccessfulResponse message) {
        backToDefault();
        nameField.clear();
        passwordField.clear();
    }

    /**
     * Method called when the cancel button is pressed
     *
     * <p>This Method is called when the cancel button is pressed. It posts an instance of the
     * CreateLobbyCanceledEvent to the EventBus the SceneManager is subscribed to.
     *
     * @param actionEvent The ActionEvent generated by pressing the create lobby button
     * @see CreateLobbyCanceledEvent
     * @see de.uol.swp.client.SceneManager
     * @author Maxim Erden
     * @since 2022-11-30
     */
    @FXML
    void onCancelButtonPressed(ActionEvent actionEvent) {
        backToDefault();
        nameField.clear();
        passwordField.clear();
        eventBus.post(new CreateLobbyCanceledEvent());
    }

    /**
     * Method called when the create lobby button is pressed
     *
     * <p>This Method called the createNewLobby method on the lobbyService. It transfers the typed
     * in information in the TextField nameField and passwordField to the lobbyService
     *
     * @param actionEvent The ActionEvent generated by pressing the create lobby button
     * @see LobbyService
     * @author Maxim Erden
     * @since 2022-11-30
     */
    @FXML
    public void onCreateLobbyPressed(ActionEvent actionEvent) {
        if (!nameField.getText().isBlank()) {
            lobbyService.createNewLobby(
                    nameField.getText(), (UserDTO) loggedInUser, true, passwordField.getText());
            backToDefault();
        } else {
            errorMessage2.setVisible(false);
            errorMessage1.setVisible(true);
        }
    }

    /**
     * helper method to set the label and textField Nodes back to default
     *
     * <p>This Method sets the label and textField Nodes back to default
     *
     * @author Moritz Scheer, Ole Zimmermann
     * @since 2022-12-27
     */
    private void backToDefault() {
        errorMessage1.setVisible(false);
        errorMessage2.setVisible(false);
    }
}