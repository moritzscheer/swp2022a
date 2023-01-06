package de.uol.swp.client.tab.event;

import javafx.scene.Parent;

/**
 * Event used to switch the Node in the TabPane to the given Node
 *
 * In order to switch the Node in the tab using this event, post an instance of it
 * onto the eventBus the SceneManager is subscribed to.
 *
 * @author Moritz Scheer
 * @see de.uol.swp.client.tab.TabPresenter
 * @since 2022-12-27
 */
public class ShowNodeEvent {

    private final Parent parent;
    private final int tabID;

    public ShowNodeEvent(int tabID, Parent parent) {
        this.parent = parent;
        this.tabID = tabID;

    }

    public Parent getParent() {
        return parent;
    }

    public int getTabID() {
        return tabID;
    }
}
