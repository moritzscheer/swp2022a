package de.uol.swp.client.lobby.game.events;

import de.uol.swp.common.user.UserDTO;

/**
 * Event used to show in the Game window if the game is finished
 *
 *
 * @author Daniel Merzo
 * @see de.uol.swp.client.SceneManager
 * @since 2023-05-23
 */

public class ShowGameOverEvent {
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
    public ShowGameOverEvent(int lobbyID, UserDTO userWon) {
        this.lobbyID = lobbyID;
        this.userWon = userWon;
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

    /**
     * Getter for the lobbyID variable
     *
     * @return Integer containing the Lobby ID
     * @author Daniel Merzo
     * @since 2023-05-23
     */
    public int getLobbyID() {
        return lobbyID;
    }
}
