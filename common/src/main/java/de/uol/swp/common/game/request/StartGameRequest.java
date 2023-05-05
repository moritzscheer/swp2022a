package de.uol.swp.common.game.request;


/**
 * Request sent to the server when the game has been started
 *
 * @see de.uol.swp.common.game.request.StartGameRequest
 * @author Moritz Scheer & Maxim Erden
 * @since 2023-02-28
 */
public class StartGameRequest extends AbstractGameRequest {

    private Integer lobbyID;

    /**
     * constructor
     *
     * @param lobbyID Integer containing the ID of the lobby
     * @author Maria Eduarda Costa Leite Andrade, WKempel
     * @since 2022-02-28
     */
    public StartGameRequest(Integer lobbyID) {
        this.lobbyID = lobbyID;
    }

    /**
     * Getter for the lobbyID variable
     *
     * @return Integer containing the Lobby ID
     * @author Maria Eduarda Costa Leite Andrade, WKempel
     * @since 2022-02-28
     */
    public Integer getLobbyID() {
        return lobbyID;
    }
}
