package de.uol.swp.client.tab.event;

import de.uol.swp.common.lobby.dto.LobbyDTO;
import javafx.scene.Parent;

/**
 * Event used to create a new tab for the newly created lobby
 *
 * In order to create a new tab using this event, post an instance of it
 * onto the eventBus the SceneManager is subscribed to.
 *
 * @author Moritz Scheer
 * @see de.uol.swp.client.tab.TabPresenter
 * @since 2022-12-27
 */
public class CreateLobbyTabEvent {

    private final LobbyDTO lobby;
    private final Parent parent;


    public CreateLobbyTabEvent(LobbyDTO lobby, Parent parent) {
        this.lobby = lobby;
        this.parent = parent;
    }

    public Parent getParent() {
        return parent;
    }

    public LobbyDTO getLobby() {
        return lobby;
    }
}
