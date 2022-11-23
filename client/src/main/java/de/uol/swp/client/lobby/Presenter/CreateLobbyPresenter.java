package de.uol.swp.client.lobby.Presenter;

import com.google.inject.Inject;
import de.uol.swp.client.AbstractPresenter;
import de.uol.swp.client.lobby.LobbyService;
import de.uol.swp.client.lobby.events.CreateLobbyCanceledEvent;
import de.uol.swp.client.main.MainMenuPresenter;
import de.uol.swp.common.user.User;
import de.uol.swp.common.user.UserDTO;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CreateLobbyPresenter extends AbstractPresenter {

    private static final CreateLobbyCanceledEvent createLobbyCanceledEvent = new CreateLobbyCanceledEvent();
    public static final String FXML = "/fxml/CreateLobbyView.fxml";

    private static final Logger LOG = LogManager.getLogger(MainMenuPresenter.class);

    private ObservableList<String> users;

    private User loggedInUser;

    @Inject
    private LobbyService lobbyService;

    @FXML
    private ListView<String> usersView;

    @FXML
    private TextField nameField;
    @FXML
    private TextField passwordRoomField;
    @FXML
    private TextField roomSize;
    @FXML
    void onCancelButtonPressed(ActionEvent event) {
        eventBus.post(createLobbyCanceledEvent);
    }

    public void onCreateLobbyPressed(ActionEvent actionEvent) {
        lobbyService.createNewLobby(nameField.getText(), (UserDTO) loggedInUser, true, passwordRoomField.getText());
    }
}
