package de.uol.swp.client.tab.event;

import de.uol.swp.common.lobby.dto.LobbyDTO;

import javafx.scene.Parent;

/**
 * Event used to create a new tab for the newly created lobby
 *
 * <p>In order to create a new tab using this event, post an instance of it onto the eventBus the
 * SceneManager is subscribed to.
 *
 * @author Moritz Scheer
 * @see de.uol.swp.client.tab.TabPresenter
 * @since 2022-12-27
 */
public class CreateLobbyTabEvent {

    private final LobbyDTO lobby;
    private final Parent parent;

    /**
     * Constructor
     *
     * @param parent Parent containing the content of the fxml file
     * @param lobby LobbyDTO containing the lobby data
     * @author Moritz Scheer
     * @since 2023-01-06
     */
    public CreateLobbyTabEvent(LobbyDTO lobby, Parent parent) {
        this.lobby = lobby;
        this.parent = parent;
    }

    /**
     * Getter for the parent variable
     *
     * @return Parent containing the content of the fxml file
     * @author Moritz Scheer
     * @since 2023-01-06
     */
    public Parent getParent() {
        return parent;
    }

    /**
     * Getter for the lobby variable
     *
     * @return LobbyDTO containing the lobby data
     * @author Moritz Scheer
     * @since 2023-01-06
     */
    public LobbyDTO getLobby() {
        return lobby;
    }
}
