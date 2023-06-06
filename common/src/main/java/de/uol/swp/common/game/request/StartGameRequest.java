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
    private int numberBots;

    /**
     * constructor
     *
     * @param lobbyID    Integer containing the ID of the lobby
     * @param numberBots
     * @author Maria Eduarda Costa Leite Andrade, WKempel
     * @since 2022-02-28
     */
    public StartGameRequest(LobbyDTO lobby, int numberBots) {
        this.lobbyID = lobby.getLobbyID();
        this.numberBots = numberBots;
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

    public int getNumberBots() {
        return numberBots;
    }
}
