package de.uol.swp.common.lobby;

import de.uol.swp.common.user.User;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

/**
 * Interface to unify lobby objects
 *
 * <p>This is an Interface to allow for multiple types of lobby objects since it is possible that
 * not every client has to have every information of the lobby.
 *
 * @author Marco Grawunder
 * @see de.uol.swp.common.lobby.dto.LobbyDTO
 * @since 2019-10-08
 */
public interface Lobby extends Serializable {

    /**
     * Adds a new user to the lobby
     *
     * @param user The new user to add to the lobby
     * @since 2019-10-08
     */
    void joinUser(User user, String password);

    /**
     * Removes a user from the lobby
     *
     * @param user The user to remove from the lobby
     * @since 2019-10-08
     */
    void leaveUser(User user);

    /**
     * Changes the owner of the lobby
     *
     * @param user The user who should be the new owner
     * @since 2019-10-08
     */
    void updateOwner(User user);

    /**
     * Changes the ready status of a user to ready
     *
     * @param user The user who is now ready
     * @author Moritz Scheer
     * @since 2023-05-28
     */
    void makePlayerReady(User user);

    /**
     * Changes the ready status of a user to not ready
     *
     * @param user The user who is now not ready
     * @author Moritz Scheer
     * @since 2023-05-28
     */
    void makePlayerNotReady(User user);

    /**
     * Getter for the lobby's name
     *
     * @return A String containing the name of the lobby
     * @since 2019-10-08
     */
    String getName();

    /**
     * Getter for the current owner of the lobby
     *
     * @return A User object containing the owner of the lobby
     * @since 2019-10-08
     */
    User getOwner();

    /**
     * Getter for all users in the lobby
     *
     * @return A Set containing all user in this lobby
     * @since 2019-10-08
     */
    Set<User> getUsers();

    /**
     * Getter for all users that are not ready in the lobby
     *
     * @return A Set containing all user that are not ready in this lobby
     * @author Moritz Scheer
     * @since 2023-05-28
     */
    Set<User> getNotReadyUsers();

    /**
     * Getter for the Password of the lobby
     *
     * @return A String containing the password of this lobby
     * @since 2022-11-22
     */
    String getPassword();

    /**
     * Getter for game mode of the lobby
     *
     * @return a Boolean.If true, the lobby is a multiplayer lobby, if false, the lobby is a
     *     singleplayer lobby
     * @since 2022-11-22
     */
    Boolean isMultiplayer();

    /**
     * Getter for lobbyID of the lobby
     *
     * @return an Integer containing the lobby ID
     * @since 2022-11-22
     */
    Integer getLobbyID();

    /**
     * Getter for TextChatChannel UUID
     *
     * @return Text Chat UUID
     * @since 2023-01-03
     */
    UUID getTextChatID();

    /**
     * Getter for lobbyStarted attribute
     *
     * @return A boolean indicating if a game has started
     * @since 2023-07-05
     */
    Boolean isLobbyStarted();
}
