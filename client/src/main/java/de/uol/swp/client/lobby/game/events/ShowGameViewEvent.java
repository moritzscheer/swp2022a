package de.uol.swp.client.lobby.game.events;
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

    public ShowGameViewEvent(LobbyDTO lobby) {
        this.lobby = lobby;
    }

    public Integer getLobbyID() {
        return lobby.getLobbyID();
    }

    public LobbyDTO getLobby() {
        return lobby;
    }

}
