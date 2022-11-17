package de.uol.swp.client.lobby.create;

import com.google.common.base.Strings;
import com.google.common.eventbus.EventBus;
import com.google.inject.Inject;
import de.uol.swp.client.AbstractPresenter;
import de.uol.swp.client.lobby.LobbyService;
import de.uol.swp.client.lobby.MultiplayerCanceledEvent;
import de.uol.swp.client.main.MainMenuPresenter;
import de.uol.swp.client.register.event.RegistrationCanceledEvent;
import de.uol.swp.client.register.event.RegistrationErrorEvent;
import de.uol.swp.client.user.ClientUserService;
import de.uol.swp.common.user.User;
import de.uol.swp.common.user.UserDTO;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
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
    void onCancelButtonPressed(ActionEvent event) {
        eventBus.post(createLobbyCanceledEvent);
    }

    public void onCreateLobbyPressed(ActionEvent actionEvent) {

        //Lobby anhand drei parameter erstellen
    }
}
