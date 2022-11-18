package de.uol.swp.client.lobby.Presenter;

import com.google.inject.Inject;
import de.uol.swp.client.AbstractPresenter;
import de.uol.swp.client.lobby.LobbyService;
import de.uol.swp.client.lobby.event.ShowLobbyViewEvent;
import de.uol.swp.client.main.MainMenuPresenter;
import de.uol.swp.common.user.User;
import de.uol.swp.common.user.UserDTO;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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

    private static final Logger LOG = LogManager.getLogger(MainMenuPresenter.class);

    private ObservableList<String> users;

    private User loggedInUser;
    private String lobbyName;
    @Inject
    private LobbyService lobbyService;

    @FXML
    private Label labelPlayer;

    @FXML
    private Label labelMap;

    @FXML
    private Label labelMapName;

    /**
     * Default Constructor
     * @since 2022-11-15
     *
     */
    public LobbyPresenter() {
        // needed for javafx
    }

    @FXML
    private void onButtonBackPressed(ActionEvent event) {
        lobbyService.dropLobby(lobbyName, new UserDTO("ich", "", ""));
    }

}
