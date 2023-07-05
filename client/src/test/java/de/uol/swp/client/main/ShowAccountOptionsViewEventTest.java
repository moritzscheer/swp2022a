package de.uol.swp.client.main;

import static org.junit.jupiter.api.Assertions.assertEquals;

import de.uol.swp.client.main.event.ShowAccountOptionsViewEvent;

import org.junit.jupiter.api.Test;

/**
 * Test ShowNodeEventTest
 *
 * @author WKempel
 * @see de.uol.swp.client.main.ShowAccountOptionsViewEventTest
 * @since 2023-06-26
 */
public class ShowAccountOptionsViewEventTest {

    @Test
    public void testConstructor() {
        ShowAccountOptionsViewEvent event = new ShowAccountOptionsViewEvent();
        assertEquals(ShowAccountOptionsViewEvent.class, event.getClass());
    }
}
