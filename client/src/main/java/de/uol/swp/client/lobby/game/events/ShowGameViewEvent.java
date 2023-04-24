package de.uol.swp.client.lobby.game.events;

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

    private Integer lobbyID;

    public ShowGameViewEvent(Integer lobbyID) {
        this.lobbyID = lobbyID;
    }

    public Integer getLobbyID() {
        return lobbyID;
    }
}
