package de.uol.swp.common.game.message;

import de.uol.swp.common.lobby.dto.LobbyDTO;
import de.uol.swp.common.lobby.message.AbstractLobbyMessage;

/**
 * Message sent by the server when a game is started
 *
 * @see AbstractLobbyMessage
 * @see de.uol.swp.common.user.User
 * @author Moritz Scheer & Maxim Erden
 * @since 2022-02-28
 */
public class StartGameMessage extends AbstractLobbyMessage {

    private Integer lobbyID;
    private LobbyDTO lobby;

    /**
     * constructor
     *
     * @param lobbyID Integer containing the ID of the lobby
     * @param lobby LobbyDTO Object containing all the information of the lobby
     * @author Moritz Scheer & Maxim Erden
     * @since 2022-03-23
     */
    public StartGameMessage(Integer lobbyID, LobbyDTO lobby) {
        this.lobbyID = lobbyID;
        this.lobby = lobby.createWithoutUserPassword(lobby);
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

    /**
     * Getter for the lobbyID variable
     *
     * @return LobbyDTO Object containing all the information of the lobby
     * @author Moritz Scheer
     * @since 2022-03-23
     */
    public LobbyDTO getLobby() {
        return lobby;
    }
}
