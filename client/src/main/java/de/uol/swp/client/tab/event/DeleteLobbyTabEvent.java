package de.uol.swp.client.tab.event;

/**
 * Event used to delete the tab for the lobby with the given lobbyID
 *
 * <p>In order to delete the tab using this event, post an instance of it onto the eventBus the
 * SceneManager is subscribed to.
 *
 * @author Moritz Scheer
 * @see de.uol.swp.client.tab.TabPresenter
 * @since 2022-12-27
 */
public class DeleteLobbyTabEvent {

    private final Integer lobbyID;

    public DeleteLobbyTabEvent(Integer lobbyID) {
        this.lobbyID = lobbyID;
    }

    public Integer getLobbyID() {
        return lobbyID;
    }
}
