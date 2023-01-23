package de.uol.swp.client.tab.event;

import de.uol.swp.common.user.User;

/**
 * * Event used to show the TabView window
 *
 * <p>In order to show the TabView window using this event, post an instance of it onto the eventBus
 * the SceneManager is subscribed to.
 *
 * @author Moritz Scheer
 * @see de.uol.swp.client.SceneManager
 * @since 2022-12-27
 */
public class ShowTabViewEvent {

    private final User user;

    public ShowTabViewEvent(User user) {
        this.user = user;
    }

    public User getUser() {
        return this.user;
    }
}
