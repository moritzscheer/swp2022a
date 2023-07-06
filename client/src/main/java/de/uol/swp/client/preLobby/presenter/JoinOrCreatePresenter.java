package de.uol.swp.client.preLobby.presenter;

import com.google.common.eventbus.Subscribe;

import de.uol.swp.client.AbstractPresenter;
import de.uol.swp.client.lobbyGame.LobbyGameManagement;
import de.uol.swp.client.lobbyGame.lobby.event.UserJoinLobbyEvent;
import de.uol.swp.client.preLobby.events.JoinOrCreateCanceledEvent;
import de.uol.swp.client.preLobby.events.ShowCreateLobbyViewEvent;
import de.uol.swp.common.game.message.StartGameMessage;
import de.uol.swp.common.lobby.dto.LobbyDTO;
import de.uol.swp.common.lobby.exception.LobbyJoinedExceptionResponse;
import de.uol.swp.common.lobby.message.UserCreatedLobbyMessage;
import de.uol.swp.common.lobby.message.UserDroppedLobbyMessage;
import de.uol.swp.common.lobby.response.AllOnlineLobbiesResponse;
import de.uol.swp.common.lobby.response.LobbyJoinedSuccessfulResponse;
import de.uol.swp.common.lobby.response.LobbyLeftSuccessfulResponse;
import de.uol.swp.common.user.User;
import de.uol.swp.common.user.UserDTO;
import de.uol.swp.common.user.response.LoginSuccessfulResponse;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * Manages the joinOrCreate window
 *
 * @author Maxim Erden, Tommy Dang, Maria Eduarda, Moritz Scheer
 * @see de.uol.swp.client.AbstractPresenter
 * @since 2022-11-23
 */
public class JoinOrCreatePresenter extends AbstractPresenter {

    public static final String FXML = "/fxml/JoinOrCreateView.fxml";
    private static final Logger LOG = LogManager.getLogger(JoinOrCreatePresenter.class);

    private User loggedInUser;

    private ObservableList<LobbyDTO> lobbiesList;

    @FXML private GridPane tableViewWrapper;
    @FXML private TableView<LobbyDTO> lobbiesView;
    @FXML private TableColumn<LobbyDTO, Integer> column1;
    @FXML private TableColumn<LobbyDTO, String> column2;
    @FXML private TableColumn<LobbyDTO, String> column3;
    @FXML private TableColumn<LobbyDTO, String> column4;

    @FXML private Label errorMessage1; // incorrect password
    @FXML private Label errorMessage2; // lobby full
    @FXML private Label passwordLabel;
    @FXML private Label headerPasswordView;
    @FXML private Button passwordViewCancelButton;
    @FXML private Button passwordViewJoinButton;
    @FXML private TextField textFieldPassword;
    @FXML private AnchorPane PasswordView;

    /**
     * Default Constructor
     *
     * @since 2022-11-15
     */
    public JoinOrCreatePresenter() {
        // needed for javafx
    }

    // -----------------------------------------------------
    // Responses
    // -----------------------------------------------------

    /**
     * Handles successful login
     *
     * <p>If a LoginSuccessfulResponse is posted to the EventBus the loggedInUser of this client is
     * set to the one in the message received.
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

    /**
     * Handles new list of lobbies
     *
     * <p>If a new AllOnlineLobbiesResponse object is posted to the EventBus the names of currently
     * open lobbies are put onto the lobby list in the joinOrCreate. Furthermore, if the LOG-Level
     * is set to DEBUG the message "Update of lobby list" with the names of all currently open
     * lobbies is displayed in the log.
     *
     * @param allLobbiesResponse the AllOnlineLobbiesResponse object seen on the EventBus
     * @see de.uol.swp.common.lobby.response.AllOnlineLobbiesResponse
     * @author Moritz Scheer & Maxim Erden
     * @since 2022-11-30
     */
    @Subscribe
    public void onAllOnlineLobbiesResponse(AllOnlineLobbiesResponse allLobbiesResponse) {
        LOG.info("Update of lobby list {}", allLobbiesResponse.getLobbies());
        updateLobbyList(allLobbiesResponse.getLobbies());
    }

    /**
     * Handles lobby join exceptions
     *
     * <p>If an LobbyJoinedExceptionResponse object is detected on the EventBus this method is
     * called. If the loglevel is set to Error or higher "Lobby join error " and the error message
     * are written to the log.
     *
     * @param message The LobbyJoinedExceptionResponse object detected on the EventBus
     * @author Moritz Scheer
     * @since 2022-11-27
     */
    @Subscribe
    public void onLobbyJoinedExceptionResponse(LobbyJoinedExceptionResponse message) {
        LOG.error("Lobby join error {}", message);
        updatePasswordView();
        if (message.toString().contains("already full")) {
            errorMessage2.setVisible(true);
        } else {
            errorMessage1.setVisible(true);
        }
    }

    /**
     * Handles successfully joined lobbies
     *
     * <p>If an LobbyJoinedSuccessfulResponse object is detected on the EventBus this method is
     * called.
     *
     * @param message The LobbyJoinedSuccessfulResponse object detected on the EventBus
     * @author Moritz Scheer
     * @since 2022-12-28
     */
    @Subscribe
    public void onLobbyJoinedSuccessfulResponse(LobbyJoinedSuccessfulResponse message) {
        Platform.runLater(
                () -> {
                    if (lobbiesList != null && loggedInUser != null) {
                        lobbiesList.removeIf(
                                u -> u.getLobbyID().equals(message.getLobby().getLobbyID()));
                    }
                    LobbyGameManagement.getInstance()
                            .updateGameMap(message.getLobby().getLobbyID(), message.getMap());
                });
    }

    /**
     * Handles successfully left lobbies
     *
     * <p>If an LobbyLeaveUserResponse object is detected on the EventBus this method is called.
     *
     * @param message The LobbyLeaveUserResponse object detected on the EventBus
     * @author Moritz Scheer
     * @since 2022-12-28
     */
    @Subscribe
    public void onLobbyLeaveUserResponse(LobbyLeftSuccessfulResponse message) {
        System.out.println(message.getLobby().isLobbyStarted());
        Platform.runLater(
                () -> {
                    if (lobbiesList != null
                            && loggedInUser != null
                            && !message.getLobby().isLobbyStarted()) {
                        lobbiesList.add(message.getLobby());
                    }
                });
    }

    // -----------------------------------------------------
    // Messages
    // -----------------------------------------------------

    /**
     * Handles created multiplayer lobbies
     *
     * <p>If a LobbyCreatedMessage is posted to the EventBus this method is called.
     *
     * @param message the LobbyCreatedMessage object seen on the EventBus
     * @see de.uol.swp.common.lobby.message.UserCreatedLobbyMessage
     * @author Moritz Scheer & Maxim Erden
     * @since 2022-11-30
     */
    @Subscribe
    public void onUserCreatedLobbyMessage(UserCreatedLobbyMessage message) {
        Platform.runLater(
                () -> {
                    if (lobbiesList != null
                            && loggedInUser != null
                            && !message.getLobby().getUsers().contains(loggedInUser)) {
                        lobbiesList.add(message.getLobby());
                    }
                });
    }

    /**
     * Handles when a Lobby dropped
     *
     * <p>If a LobbyDroppedMessage is posted to the EventBus this method is called.
     *
     * @param message the LobbyDroppedMessage object seen on the EventBus
     * @see de.uol.swp.common.lobby.message.UserDroppedLobbyMessage
     * @author Daniel Merzo
     * @since 2022-12-15
     */
    @Subscribe
    private void onLobbyDroppedMessage(UserDroppedLobbyMessage message) {
        Platform.runLater(
                () -> {
                    if (lobbiesList != null
                            && loggedInUser != null
                            && !message.getUser().equals(loggedInUser)) {
                        lobbiesList.removeIf(
                                u -> u.getLobbyID().equals(message.getLobby().getLobbyID()));
                    }
                });
    }

    /**
     * Updates the joinOrCreate lobby list according to the list given
     *
     * <p>This method clears the entire lobby list and then adds the name of each lobby, which
     * contains not the loggedInUser in the list given to the joinOrCreate lobby list. If there is
     * no lobby list, then it creates one.
     *
     * @implNote The code inside this Method has to run in the JavaFX-application thread. Therefore,
     *     it is crucial not to remove the {@code Platform.runLater()}
     * @param list A list of LobbyDTO objects including all currently open lobbies
     * @see de.uol.swp.common.lobby.dto.LobbyDTO
     * @author Moritz Scheer & Maxim Erden & Tommy Dang
     * @since 2022-11-30
     */
    private void updateLobbyList(List<LobbyDTO> list) {
        // Attention: This must be done on the FX Thread!

        /**
         * Updates columnWidth to the screensize
         *
         * @author Tommy Dang
         * @since 2023-06-07
         */
        column1.prefWidthProperty().bind(lobbiesView.widthProperty().multiply(0.05));
        column2.prefWidthProperty().bind(lobbiesView.widthProperty().multiply(0.35));
        column3.prefWidthProperty().bind(lobbiesView.widthProperty().multiply(0.35));
        column4.prefWidthProperty().bind(lobbiesView.widthProperty().multiply(0.10));

        Platform.runLater(
                () -> {
                    if (lobbiesList == null) {
                        lobbiesList = FXCollections.observableArrayList();
                        lobbiesView.setItems(lobbiesList);
                    }
                    lobbiesList.clear();

                    column1.setCellValueFactory(new PropertyValueFactory<>("lobbyID"));
                    column2.setCellValueFactory(
                            cellData -> new SimpleStringProperty(cellData.getValue().getName()));
                    column3.setCellValueFactory(
                            cellData ->
                                    new SimpleStringProperty(
                                            cellData.getValue().getOwner().getUsername()));
                    column4.setCellValueFactory(
                            cellData ->
                                    new SimpleStringProperty(
                                            cellData.getValue().getPassword().length() > 0
                                                    ? "*****"
                                                    : ""));
                    list.forEach(
                            u -> {
                                if (!u.getUsers().contains(loggedInUser)) {
                                    lobbiesList.add(u);
                                }
                            });
                });
    }

    /**
     * Deletes the lobby in the lobby list
     *
     * <p>If a Game has been started, the lobby will be deleted from the list of open lobbies
     *
     * @author Moritz Scheer
     * @param message the StartGameMessage from the server
     * @see de.uol.swp.common.game.message.StartGameMessage
     * @since 2023-07-05
     */
    @Subscribe
    public void onStartGameMessage(StartGameMessage message) {
        Platform.runLater(
                () -> {
                    if (lobbiesList != null && loggedInUser != null) {
                        lobbiesList.removeIf(u -> u.getLobbyID().equals(message.getLobbyID()));
                    }
                });
    }

    // -----------------------------------------------------
    // ActionEvents
    // -----------------------------------------------------

    /**
     * Method called when the cancel button is pressed
     *
     * <p>This Method is called when the cancel button is pressed. It posts an instance of the
     * JoinOrCreateCanceledEvent to the EventBus the SceneManager is subscribed to.
     *
     * @param actionEvent The ActionEvent generated by pressing the cancel button
     * @see JoinOrCreateCanceledEvent
     * @see de.uol.swp.client.SceneManager
     * @author Maxim Erden
     * @since 2022-11-30
     */
    @FXML
    void onCancelButtonPressed(ActionEvent actionEvent) {
        eventBus.post(new JoinOrCreateCanceledEvent());
    }

    /**
     * Method called when the create lobby button is pressed
     *
     * <p>This Method is called when the create lobby button is pressed. It posts an instance of the
     * ShowCreateLobbyViewEvent to the EventBus the SceneManager is subscribed to.
     *
     * @param actionEvent The ActionEvent generated by pressing the create lobby button
     * @see ShowCreateLobbyViewEvent
     * @see de.uol.swp.client.SceneManager
     * @author Maxim Erden
     * @since 2022-11-30
     */
    @FXML
    void onCreateLobbyButtonPressed(ActionEvent actionEvent) {
        eventBus.post(new ShowCreateLobbyViewEvent());
    }

    /**
     * This Method is called when the join lobby button in the Join or Create view is pressed. It
     * checks if a Lobby in the Listview is selected and if it is, a screen will open where the
     * Password is required to join the lobby.
     *
     * @param actionEvent The ActionEvent generated by pressing the Join Lobby button in the Join or
     *     Create view
     * @author Maxim Erden
     * @since 2022-12-11
     */
    @FXML
    void onButtonJoinLobbyInJoinOrCreateViewPressed(ActionEvent actionEvent) {
        LobbyDTO selectedLobby = lobbiesView.getSelectionModel().getSelectedItem();

        if (selectedLobby != null) {
            if (selectedLobby.getPassword().equals("")) {
                eventBus.post(new UserJoinLobbyEvent(selectedLobby, (UserDTO) loggedInUser, ""));
            } else {
                updatePasswordView();
            }
        }
    }

    /**
     * This Method is called when the List view is double-clicked it opens the PasswordView where
     * you need to put in the password in require to join the selected Lobby
     *
     * @param click The MouseEvent generated by double-clicking the Listview
     * @author Maxim Erden
     * @since 2022-12-11
     */
    public void onMouseClick(MouseEvent click) {
        LobbyDTO selectedLobby = lobbiesView.getSelectionModel().getSelectedItem();

        if (click.getClickCount() == 2 && selectedLobby != null) {
            if (selectedLobby.getPassword().equals("")) {
                eventBus.post(new UserJoinLobbyEvent(selectedLobby, (UserDTO) loggedInUser, ""));
            } else {
                updatePasswordView();
            }
        }
    }

    /**
     * This Method is called when the view of The PasswordView needs to change so whenever you want
     * to join a lobby, or you want to leave the PasswordView if the method is called it checks
     * whether the screen is currently visible or not and changes it to the opposite Also the
     * buttons will change its visibility to the opposite and the LobbiesView (Listview) changes so
     * the lobby you selected is not changeable while the PasswordField is active
     *
     * @author Maxim Erden
     * @since 2022-12-11
     */
    private void updatePasswordView() {
        if (!PasswordView.isVisible()) {
            headerPasswordView.setText(
                    "Join Lobby " + lobbiesView.getSelectionModel().getSelectedItem().getName());
            headerPasswordView.setVisible(true);
            lobbiesView.setMouseTransparent(true);
            lobbiesView.setFocusTraversable(false);
            PasswordView.setVisible(true);
            passwordLabel.setVisible(true);
            textFieldPassword.setVisible(true);
            passwordViewJoinButton.setVisible(true);
            passwordViewCancelButton.setVisible(true);
        } else {
            lobbiesView.setMouseTransparent(false);
            lobbiesView.setFocusTraversable(true);
            PasswordView.setVisible(false);
            textFieldPassword.clear();
            errorMessage1.setVisible(false);
            errorMessage2.setVisible(false);
            passwordLabel.setVisible(false);
            textFieldPassword.setVisible(false);
            passwordViewJoinButton.setVisible(false);
            passwordViewCancelButton.setVisible(false);
        }
    }

    /**
     * This Method is called when the Join Lobby Button in the PasswordView is pressed It takes the
     * Lobby that is selected and the Password that is typed in the PasswordField With these
     * Parameters it tries to join the lobby and if it fails an Error message is shown and the
     * PasswordField clears
     *
     * @param actionEvent The ActionEvent is created when you press the Join Lobby button in the
     *     PasswordView
     * @author Maxim Erden
     * @since 2022-12-11
     */
    public void onButtonJoinLobbyButtonPressed(ActionEvent actionEvent) {
        eventBus.post(
                new UserJoinLobbyEvent(
                        lobbiesView.getSelectionModel().getSelectedItem(),
                        (UserDTO) loggedInUser,
                        textFieldPassword.getText()));
        textFieldPassword.clear();
        updatePasswordView();
    }

    /**
     * This Method is called when the Cancel Button in the PasswordView is pressed and acts like a
     * back button It changes the current passwordView so that the PasswordView is not visible
     * anymore
     *
     * @param actionEvent The ActionEvent is created when you press the cancel button in the
     *     PasswordView
     * @author Maxim Erden
     * @since 2022-12-11
     */
    public void onButtonPasswordViewCancelButtonPressed(ActionEvent actionEvent) {
        updatePasswordView();
    }
}
