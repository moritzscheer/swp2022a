package de.uol.swp.client.lobby.event;

import de.uol.swp.common.user.User;

/**
 * Event used to show the Main Menu window if the back button was pressed in the JoinOrCreate Window
 *
 * <p>In order to show the login window using this event, post an instance of it onto the eventBus
 * the SceneManager is subscribed to.
 *
 * @author Marco Grawunder
 * @see de.uol.swp.client.SceneManager
 * @since 2019-09-03
 */
public class JoinOrCreateCanceledEvent {

    private final User user;

    public JoinOrCreateCanceledEvent(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
