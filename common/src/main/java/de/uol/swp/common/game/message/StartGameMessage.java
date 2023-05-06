package de.uol.swp.common.game.message;

import de.uol.swp.common.lobby.dto.LobbyDTO;
import de.uol.swp.common.lobby.message.AbstractLobbyMessage;

/**
 * Message sent by the server when a game is started
 *
 * @see AbstractLobbyMessage
 * @see de.uol.swp.common.user.User
 * @author Maria Eduarda Costa Leite Andrade, WKempel
 * @since 2022-02-28
 */
public class StartGameMessage extends AbstractLobbyMessage {

    private Integer lobbyID;
    private LobbyDTO lobby;

    private Integer gameID;

    /**
     * constructor
     *
     * @param lobbyID Integer containing the ID of the lobby
     * @param lobby   LobbyDTO Object containing all the information of the lobby
     * @param gameID
     * @author Maria Eduarda Costa Leite Andrade, WKempel
     * @since 2022-03-23
     */
    public StartGameMessage(Integer lobbyID, LobbyDTO lobby, Integer gameID) {
        this.lobbyID = lobbyID;
        // TODO: create new Game and not Lobby
        this.gameID = gameID;
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

    /**
     * Getter for the lobbyID variable
     *
     * @return LobbyDTO Object containing all the information of the lobby
     * @author Maria Eduarda Costa Leite Andrade, WKempel
     * @since 2022-03-23
     */
    public LobbyDTO getLobby() {
        return lobby;
    }

    /**
     * Getter for the gameID variable
     *
     * @return Integer containing the gameID
     * @author Maria Eduarda Costa Leite Andrade
     * @since 2023-05-01
     */
    public Integer getGameID() {
        return gameID;
    }
}
