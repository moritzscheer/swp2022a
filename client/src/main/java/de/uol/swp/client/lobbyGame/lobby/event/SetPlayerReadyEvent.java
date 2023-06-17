package de.uol.swp.client.lobbyGame.lobby.event;

import de.uol.swp.common.user.User;

/**
 * This event class is used by the presenters to tell the LobbyService to set the player who send
 * this event to ready. This is important for the start of the lobby
 *
 * @author Moritz Scheer
 * @since 2023-05-28
 */
public class SetPlayerReadyEvent {

    private final User user;
    private final Integer lobbyID;
    private final Boolean ready;

    /**
     * Constructor
     *
     * @param lobbyID Integer containing the identifier of the lobby
     * @param user the loggedIn user that pressed the ready / not ready button
     * @param ready status if a player is ready
     * @author Moritz Scheer
     * @since 2023-05-28
     */
    public SetPlayerReadyEvent(Integer lobbyID, User user, Boolean ready) {
        this.lobbyID = lobbyID;
        this.user = user;
        this.ready = ready;
    }

    /**
     * Getter for the user variable
     *
     * @return User that is now ready
     * @author Moritz Scheer
     * @since 2023-05-28
     */
    public User getUser() {
        return user;
    }

    /**
     * Getter for the lobbyID variable
     *
     * @return Integer containing the identifier of the lobby
     * @author Moritz Scheer
     * @since 2023-05-28
     */
    public int getLobbyID() {
        return lobbyID;
    }

    /**
     * Getter for the ready variable
     *
     * @return status if a player is ready
     * @author Moritz Scheer
     * @since 2023-05-28
     */
    public Boolean isReady() {
        return ready;
    }
}
