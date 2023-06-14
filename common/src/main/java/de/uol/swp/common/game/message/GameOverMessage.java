package de.uol.swp.common.game.message;

import de.uol.swp.common.lobby.message.AbstractLobbyMessage;
import de.uol.swp.common.user.UserDTO;

/**
 * Message sent by the server when the game is over
 *
 * @see AbstractLobbyMessage
 * @author Daniel Merzo
 * @since 2023-05-23
 */
public class GameOverMessage extends AbstractLobbyMessage {
    private final int lobbyID;
    private final UserDTO userWon;

    /**
     * Constructor
     *
     * @param lobbyID Integer containing the ID of the lobby
     * @param userWon GameDTO object
     * @author Daniel Merzo
     * @since 2023-05-23
     */
    public GameOverMessage(int lobbyID, UserDTO userWon) {
        this.lobbyID = lobbyID;
        this.userWon = userWon;
    }
    /**
     * Getter for the lobbyID variable
     *
     * @return Integer containing the Lobby ID
     * @author Daniel Merzo
     * @since 2023-05-23
     */
    public int getLobbyID() {
        if (lobbyID < 0) {
            throw new IllegalArgumentException("Invalid lobbyID: " + lobbyID);
        }
        return lobbyID;
    }
    /**
     * Getter for the userWon variable
     *
     * @return UserDTO containing the user which has won
     * @author Daniel Merzo
     * @since 2023-05-23
     */
    public UserDTO getUserWon() {
        return userWon;
    }
}
