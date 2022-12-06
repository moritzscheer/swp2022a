package de.uol.swp.client.auth.events;

import de.uol.swp.common.user.User;

/**
 * Event used to show the login window
 *
 * In order to show the login window using this event, post an instance of it
 * onto the eventBus the SceneManager is subscribed to.
 *
 * @author Marco Grawunder
 * @see de.uol.swp.client.SceneManager
 * @since 2019-09-03
 *
 */
public class ShowLoginViewEvent {


    private final User user;
    public ShowLoginViewEvent(User user) {
        this.user = user;
    }

    public User getUser() {
        return this.user;
    }
}

