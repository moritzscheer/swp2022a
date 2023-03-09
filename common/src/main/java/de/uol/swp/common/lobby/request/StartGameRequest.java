package de.uol.swp.common.lobby.request;

public class StartGameRequest extends AbstractLobbyRequest {

    private Integer lobbyID;

    /**
     * constructor
     *
     * @param lobbyID Integer containing the ID of the lobby
     * @author Moritz Scheer & Maxim Erden
     * @since 2022-02-28
     */
    public StartGameRequest(Integer lobbyID) {
        this.lobbyID = lobbyID;
    }

    /**
     * Getter for the lobbyID variable
     *
     * @return Integer containing the Lobby ID
     * @author Moritz Scheer & Maxim Erden
     * @since 2022-02-28
     */
    public Integer getLobbyID() {
        return lobbyID;
    }
}
