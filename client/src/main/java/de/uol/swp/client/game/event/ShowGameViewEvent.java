package de.uol.swp.client.game.event;


import javafx.collections.ObservableList;

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

    private ObservableList<String> users;
    private Integer lobbyID;

    public ShowGameViewEvent(ObservableList<String> users, Integer lobbyID) {
        this.users = users;
        this.lobbyID = lobbyID;
    }

    public ObservableList<String> getUsers() {
        return users;
    }

    public Integer getLobbyID() {
        return lobbyID;
    }
}
