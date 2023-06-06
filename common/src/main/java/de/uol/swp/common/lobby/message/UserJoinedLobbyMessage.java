package de.uol.swp.common.lobby.message;

import de.uol.swp.common.game.Map;
import de.uol.swp.common.user.UserDTO;

/**
 * Message sent by the server when a user successfully joins a lobby
 *
 * @see de.uol.swp.common.lobby.message.AbstractLobbyMessage
 * @see de.uol.swp.common.user.User
 * @author Marco Grawunder
 * @since 2019-10-08
 */
public class UserJoinedLobbyMessage extends AbstractLobbyMessage {

    private Integer lobbyID;
    private Map m;

    /**
     * Default constructor
     *
     * @implNote this constructor is needed for serialization
     * @since 2019-10-08
     */
    public UserJoinedLobbyMessage() {}

    /**
     * Constructor
     *
     * @param lobbyName name of the lobby
     * @param user user who joined the lobby
     * @since 2019-10-08
     */
    public UserJoinedLobbyMessage(Integer lobbyID, String lobbyName, UserDTO user) {
        super(lobbyName, user);
        this.lobbyID = lobbyID;
    }

    /**
     * Getter for the lobby variable
     *
     * @return LobbyDTO containing the lobby's data
     * @author Moritz Scheer
     * @since 2023-01-03
     */
    public Integer getLobbyID() {
        return lobbyID;
    }

    /**
     * Getter for the map variable
     *
     * @return Map containing the map's data
     * @author Mathis Eilers
     * @since 2023-05-12
     */
    public Map getMap() {
        return this.m;
    }
}
