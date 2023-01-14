package de.uol.swp.common.lobby.message;

import de.uol.swp.common.user.UserDTO;

/**
 * Message sent by the server when a user successfully leaves a lobby
 *
 * @see de.uol.swp.common.lobby.message.AbstractLobbyMessage
 * @see de.uol.swp.common.user.User
 * @author Marco Grawunder
 * @since 2019-10-08
 */
public class UserLeftLobbyMessage extends AbstractLobbyMessage {

    private UserDTO newOwner;
    private Integer lobbyID;

    /**
     * Default constructor
     *
     * @implNote this constructor is needed for serialization
     * @since 2019-10-08
     */
    public UserLeftLobbyMessage() {}

    /**
     * Constructor
     *
     * @param lobbyName name of the lobby
     * @param user user who left the lobby
     * @param newOwner new lobby owner
     * @since 2019-10-08
     */
    public UserLeftLobbyMessage(Integer lobbyID, String lobbyName, UserDTO user, UserDTO newOwner) {
        super(lobbyName, user);
        this.newOwner = newOwner;
        this.lobbyID = lobbyID;
    }

    /**
     * getter for the new Lobby owner
     *
     * @return UserDTO containing the user information
     * @author Moritz Scheer
     * @since 2022-12-18
     */
    public UserDTO getNewOwner() {
        return newOwner;
    }

    /**
     * getter for the new Lobby data
     *
     * @return LobbyDTO containing the lobby information
     * @author Moritz Scheer
     * @since 2023-01-03
     */
    public Integer getLobbyID() {
        return lobbyID;
    }
}
