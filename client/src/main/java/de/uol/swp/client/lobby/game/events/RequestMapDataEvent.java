package de.uol.swp.client.lobby.game.events;

import de.uol.swp.common.lobby.dto.LobbyDTO;

public class RequestMapDataEvent {
    private  final LobbyDTO lobbyDTO;

    public RequestMapDataEvent(LobbyDTO lobbyDTO){

        this.lobbyDTO = lobbyDTO;
    }


    public LobbyDTO getLobbyDTO() {
        return lobbyDTO;
    }
}
