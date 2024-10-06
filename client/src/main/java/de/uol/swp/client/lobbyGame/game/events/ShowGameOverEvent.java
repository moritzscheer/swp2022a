package de.uol.swp.client.lobbyGame.game.events;

import de.uol.swp.common.user.UserDTO;

import javafx.scene.Node;
import javafx.scene.image.ImageView;

/**
 * Event used to show in the Game window if the game is finished
 *
 * @author Daniel Merzo
 * @see de.uol.swp.client.SceneManager
 * @since 2023-05-23
 */
public class ShowGameOverEvent {
    private final int lobbyID;
    private final UserDTO userWon;
    private final ImageView userImage;
    private final UserDTO loggedInUser;
    private final String lobbyName;
    private final boolean multiplayer;

    /**
     * Constructor
     *
     * @param lobbyID Integer containing the ID of the lobby
     * @param userWon GameDTO object
     * @param userImage ImageView
     * @param loggedInUser UserDTO
     * @param lobbyName String
     * @param multiplayer boolean
     * @author Daniel Merzo
     * @since 2023-05-23
     */
    public ShowGameOverEvent(
            int lobbyID,
            UserDTO userWon,
            ImageView userImage,
            UserDTO loggedInUser,
            String lobbyName,
            boolean multiplayer) {
        this.lobbyID = lobbyID;
        this.userWon = userWon;
        this.userImage = userImage;
        this.loggedInUser = loggedInUser;
        this.lobbyName = lobbyName;
        this.multiplayer = multiplayer;
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

    /**
     * Getter for the user image
     *
     * @author Maria
     * @since 2023-07-04
     */
    public Node getUserImage() {
        return userImage;
    }

    /**
     * Getter for the logged in User
     *
     * @author Maria
     * @since 2023-07-04
     */
    public UserDTO getLoggedInUser() {
        return loggedInUser;
    }

    /**
     * Getter for the lobby name
     *
     * @author Maria
     * @since 2023-07-04
     */
    public String getLobbyName() {
        return lobbyName;
    }

    /**
     * Getter if the lobby is multiplayer
     *
     * @author Maria
     * @since 2023-07-04
     */
    public boolean isMultiplayer() {
        return multiplayer;
    }
}
