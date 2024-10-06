package de.uol.swp.common.game.message;

import de.uol.swp.common.lobby.message.AbstractLobbyMessage;

/**
 * Message sent by the server when a game is started to all player
 *
 * @see de.uol.swp.common.lobby.message.AbstractLobbyMessage
 * @see de.uol.swp.common.user.User
 * @author Moritz Scheer
 * @since 2022-04-07
 */
public class StartGameMessage extends AbstractLobbyMessage {

    private final Integer lobbyID;

    /**
     * constructor
     *
     * @param lobbyID Integer containing the ID of the lobby
     * @author Moritz Scheer
     * @since 2022-04-07
     */
    public StartGameMessage(Integer lobbyID) {
        this.lobbyID = lobbyID;
    }

    /**
     * Getter for the lobbyID variable
     *
     * @return Integer containing the Lobby ID
     * @author Moritz Scheer
     * @since 2022-04-07
     */
    public Integer getLobbyID() {
        if (lobbyID < 0) {
            throw new IllegalArgumentException("Invalid lobbyID: " + lobbyID);
        }
        return lobbyID;
    }
}
