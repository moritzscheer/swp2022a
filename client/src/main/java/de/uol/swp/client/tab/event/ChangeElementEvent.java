package de.uol.swp.client.tab.event;

public class ChangeElementEvent {

    private Integer lobbyID;

    public ChangeElementEvent(Integer lobbyID) {
        this.lobbyID = lobbyID;
    }

    public Integer getLobbyID() {
        return lobbyID;
    }
}
