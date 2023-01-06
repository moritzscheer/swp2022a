package de.uol.swp.client.tab.event;

/**
 * Event used to enable and disable the buttons in the lobby presenter.
 *
 * In order to change elements using this event, post an instance of it
 * onto the eventBus the SceneManager is subscribed to.
 *
 * @author Moritz Scheer
 * @see de.uol.swp.client.tab.event.ChangeElementEvent
 * @author Moritz Scheer
 * @since 2023-01-06
 */
public class ChangeElementEvent {

    private Integer lobbyID;

    /**
     * Constructor
     *
     * @param lobbyID Integer containing the lobbyID
     * @author Moritz Scheer
     * @since 2023-01-06
     */
    public ChangeElementEvent(Integer lobbyID) {
        this.lobbyID = lobbyID;
    }

    /**
     * Getter for the lobbyID variable
     *
     * @return Integer containing the lobbyID
     * @author Moritz Scheer
     * @since 2023-01-06
     */
    public Integer getLobbyID() {
        return lobbyID;
    }
}
