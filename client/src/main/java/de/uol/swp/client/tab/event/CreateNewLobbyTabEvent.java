package de.uol.swp.client.tab.event;

import de.uol.swp.common.lobby.dto.LobbyDTO;
import javafx.scene.Parent;

public class CreateNewLobbyTabEvent {

    private LobbyDTO lobby;
    private Parent parent;


    public CreateNewLobbyTabEvent(LobbyDTO lobby, Parent parent) {
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
