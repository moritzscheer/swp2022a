package de.uol.swp.common.lobby.response;

public class StartGameResponse extends AbstractLobbyResponse{

    private Integer lobbyID;

    /**
     * constructor
     *
     * @param lobbyID Integer containing the ID of the lobby
     * @author Moritz Scheer & Maxim Erden
     * @since 2022-02-28
     */
    public StartGameResponse(Integer lobbyID) {
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
