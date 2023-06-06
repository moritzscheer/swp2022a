package de.uol.swp.common.game.request;


import de.uol.swp.common.lobby.dto.LobbyDTO;

/**
 * Request sent to the server when the game has been started
 *
 * @see de.uol.swp.common.game.request.StartGameRequest
 * @author Moritz Scheer & Maxim Erden
 * @since 2023-02-28
 */
public class StartGameRequest extends AbstractGameRequest {

    private int lobbyID;
    private String mapName;

    /**
     * constructor
     *
     * @param lobbyID Integer containing the ID of the lobby
     * @author Maria Eduarda Costa Leite Andrade, WKempel
     * @since 2022-02-28
     */
    public StartGameRequest(LobbyDTO lobby) {
        this.lobbyID = lobby.getLobbyID();
        this.lobby = lobby;


    }

    /**
     * Getter for the lobbyID variable
     *
     * @return Integer containing the Lobby ID
     * @author Maria Eduarda Costa Leite Andrade, WKempel
     * @since 2022-02-28
     */
    public int getLobbyID() {
        return lobbyID;
    }
}
