package de.uol.swp.common.lobby.message;

public class StartGameMessage extends AbstractLobbyMessage {

    private Integer lobbyID;

    /**
     * constructor
     *
     * @param lobbyID Integer containing the ID of the lobby
     * @author Moritz Scheer & Maxim Erden
     * @since 2022-02-28
     */
    public StartGameMessage(Integer lobbyID) {
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
