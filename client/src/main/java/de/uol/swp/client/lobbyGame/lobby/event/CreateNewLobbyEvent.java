package de.uol.swp.client.lobbyGame.lobby.event;

import de.uol.swp.common.user.UserDTO;

/**
 * This event class is used by the presenters to tell the LobbyService to create a new request to
 * the server
 *
 * @author Maria Andrade
 * @since 2023-05-16
 */
public class CreateNewLobbyEvent {
    private final String name;
    private final UserDTO user;
    private final Boolean multiplayer;
    private final String password;

    /**
     * Constructor for the class
     *
     * @author Maria Andrade
     * @since 2023-05-16
     */
    public CreateNewLobbyEvent(String name, UserDTO user, Boolean multiplayer, String password) {
        this.name = name;
        this.user = user;
        this.multiplayer = multiplayer;
        this.password = password;
    }

    /**
     * Getter for name variable
     *
     * @author Maria Andrade
     * @since 2023-05-16
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for User variable
     *
     * @author Maria Andrade
     * @since 2023-05-16
     */
    public UserDTO getUser() {
        return user;
    }

    /**
     * Getter for Multiplayer variable
     *
     * @author Maria Andrade
     * @since 2023-05-16
     */
    public Boolean getMultiplayer() {
        return multiplayer;
    }

    /**
     * Getter for Password variable
     *
     * @author Maria Andrade
     * @since 2023-05-16
     */
    public String getPassword() {
        return password;
    }
}
