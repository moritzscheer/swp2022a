package de.uol.swp.client.main.event;

/**
 * Event used to show the account options in the main menu
 *
 * <p>In order to show the account options in the main menu, using this event, post an instance of
 * it onto the eventBus the SceneManager is subscribed to.
 *
 * @author Waldemar Kempel
 * @see de.uol.swp.client.SceneManager
 * @since 2022-11-25
 */
public class ShowAccountOptionsViewEvent {

    /**
     * Default Constructor
     *
     * @author Waldemar Kempel
     * @see de.uol.swp.client.SceneManager
     * @since 2022-11-25
     */
    public ShowAccountOptionsViewEvent() {}
}
