package de.uol.swp.client.tab.event;

import javafx.scene.Parent;

/**
 * Event used to switch the Node in the TabPane to the given Node
 *
 * <p>In order to switch the Node in the tab using this event, post an instance of it onto the
 * eventBus the SceneManager is subscribed to.
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

    /**
     * Getter Method to get the parent of the node
     *
     * @author Moritz Scheer
     * @return the parent of the node
     * @since 2022-12-27
     */
    public Parent getParent() {
        return parent;
    }

    /**
     * Getter Method to get the tabID
     *
     * @author Moritz Scheer
     * @return the tabID
     * @since 2022-12-27
     */
    public int getTabID() {
        return tabID;
    }
}
