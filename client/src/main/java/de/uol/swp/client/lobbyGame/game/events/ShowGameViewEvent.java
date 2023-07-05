package de.uol.swp.client.lobbyGame.game.events;

import de.uol.swp.common.lobby.dto.LobbyDTO;

/**
 * Event used to show the Game window if the start button was pressed in the Lobby Window
 *
 * <p>In order to show the game window using this event, post an instance of it onto the eventBus
 * the SceneManager is subscribed to.
 *
 * @author Maxim Erden
 * @see de.uol.swp.client.SceneManager
 * @since 2023-02-20
 */
public class ShowGameViewEvent {

    private LobbyDTO lobby;

    /**
     * Constructor
     *
     * @author Finn Oldeboershuis, Maria Andrade, Ole Zimmermann
     * @return the lobby
     * @since 2023-05-09
     */
    public ShowGameViewEvent(LobbyDTO lobby) {
        this.lobby = lobby;
    }

    /**
     * Getter Method for the lobby ID
     *
     * @author Finn Oldeboershuis, Ole Zimmermann
     * @return the lobby id
     * @since 2023-05-23
     */
    public Integer getLobbyID() {
        return lobby.getLobbyID();
    }

    /**
     * Getter method fot the current lobby
     *
     * @author Finn Oldeboershuis, Ole Zimmermann
     * @return the lobby
     * @since 2023-05-09
     */
    public LobbyDTO getLobby() {
        return lobby;
    }
}
