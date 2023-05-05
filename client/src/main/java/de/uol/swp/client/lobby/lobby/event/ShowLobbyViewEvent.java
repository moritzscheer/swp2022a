package de.uol.swp.client.lobby.lobby.event;

/**
 * Show Lobby Event
 *
 * @author Moritz Scheer
 * @since 2023-03-09
 */
public class ShowLobbyViewEvent {

    private Integer lobbyID;

    public ShowLobbyViewEvent(Integer lobbyID) {
        this.lobbyID = lobbyID;
    }

    public Integer getLobbyID() {
        return lobbyID;
    }
}
