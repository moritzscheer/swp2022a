package de.uol.swp.client.lobby.lobby.event;

public class ShowLobbyViewEvent {

    private Integer lobbyID;

    public ShowLobbyViewEvent(Integer lobbyID) {
        this.lobbyID = lobbyID;
    }

    public Integer getLobbyID() {
        return lobbyID;
    }
}
