package de.uol.swp.client.tab;

import de.uol.swp.client.tab.event.ShowNodeEvent;
import javafx.scene.Parent;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ShowNodeEventTest {

    private final Parent parent = new Parent() {
    };
    private final int tabID = 1;

    @Test
    public void testConstructorAndGetters() {
        ShowNodeEvent event = new ShowNodeEvent(tabID, parent);

        assertEquals(parent, event.getParent());
        assertEquals(tabID, event.getTabID());
    }
}
