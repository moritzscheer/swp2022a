package de.uol.swp.client.tab.event;

import javafx.scene.Parent;

public class ShowNewNodeEvent {

    private Parent parent;
    private int mainTab;

    public ShowNewNodeEvent(int Tab, Parent parent) {
        this.parent = parent;
        this.mainTab = mainTab;

    }

    public Parent getParent() {
        return parent;
    }

    public int getTab() {
        return mainTab;
    }
}
