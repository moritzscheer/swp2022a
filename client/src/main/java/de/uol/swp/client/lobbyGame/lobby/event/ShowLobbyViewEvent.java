package de.uol.swp.client.lobbyGame.lobby.event;

/**
 * Show Lobby Event
 *
 * @author Moritz Scheer
 * @since 2023-03-09
 */
public class ShowLobbyViewEvent {

    private Integer lobbyID;
    /**
     * Constructor for the class
     *
     * @author Moritz Scheer
     * @since 2023-03-09
     */
    public ShowLobbyViewEvent(Integer lobbyID) {
        this.lobbyID = lobbyID;
    }
    /**
     * Getter for the lobbyID variable
     *
     * @author Moritz Scheer
     * @since 2023-03-09
     */
    public Integer getLobbyID() {
        return lobbyID;
    }
}
