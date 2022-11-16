package de.uol.swp.client.lobby;

import de.uol.swp.client.AbstractPresenter;
import de.uol.swp.client.lobby.event.ShowLobbyViewEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

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
    public static final ShowLobbyViewEvent showLobbyViewEvent = new ShowLobbyViewEvent();

    @FXML
    private Label labelPlayer;

    @FXML
    private Label labelMap;

    @FXML
    private Label labelMapName;

    @FXML
    private Button buttonBack;

    @FXML
    private Button buttonGameSettings;

    @FXML
    private Button buttonStart;

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
        eventBus.post(showLobbyViewEvent);
    }

}
