package de.uol.swp.common.game.message;

import de.uol.swp.common.game.Game;
import de.uol.swp.common.game.dto.GameDTO;
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

    private Integer gameID;

    private LobbyDTO lobby;
    private GameDTO game;
    /**
     * constructor
     *
     * @param lobbyID Integer containing the ID of the lobby
     * @param lobby   LobbyDTO Object containing all the information of the lobby
     * @param game GameDTO object
     * @author Maria Eduarda Costa Leite Andrade, WKempel
     * @since 2022-03-23
     */
    public StartGameMessage(Integer lobbyID, LobbyDTO lobby, GameDTO game) {
        this.lobbyID = lobbyID;
        this.gameID = game.getGameID();
        this.lobby = lobby;
        this.game = game; // TODO: add more attributes to gameDTO class
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
     * Getter for the gameID variable
     *
     * @return Integer containing the gameID
     * @author Maria Eduarda Costa Leite Andrade
     * @since 2023-05-01
     */
    public Integer getGameID() {
        return gameID;
    }

    /**
     * Getter for the gameDTO variable
     *
     * @return GameDTO Object containing all the information of the lobby
     * @author Maria Eduarda Costa Leite Andrade
     * @since 2022-05-07
     */
    public GameDTO getGame() {
        return game;
    }

    /**
     * Getter for the lobbyDTO variable
     *
     * @return LobbyDTO Object containing all the information of the lobby
     * @author Maria Eduarda Costa Leite Andrade, WKempel
     * @since 2022-03-23
     */
    public LobbyDTO getLobby() {
        return lobby;
    }
}
