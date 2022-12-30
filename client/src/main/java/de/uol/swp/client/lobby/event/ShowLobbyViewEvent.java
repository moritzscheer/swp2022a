package de.uol.swp.client.lobby.event;

import de.uol.swp.common.user.User;

/**
 * * Event used to show the Lobby window
 *
 * In order to show the Lobby window using this event, post an instance of it
 * onto the eventBus the SceneManager is subscribed to.
 *
 * @author Moritz Scheer
 * @see de.uol.swp.client.SceneManager
 * @since 2022-15-11
 *
 */
public class ShowLobbyViewEvent {

    private final User user;
    public ShowLobbyViewEvent(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
