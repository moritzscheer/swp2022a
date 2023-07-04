package de.uol.swp.client.main;

import static org.junit.jupiter.api.Assertions.assertEquals;

import de.uol.swp.client.main.event.ShowAccountOptionsViewEvent;

import org.junit.jupiter.api.Test;

public class ShowAccountOptionsViewEventTest {

    @Test
    public void testConstructor() {
        ShowAccountOptionsViewEvent event = new ShowAccountOptionsViewEvent();
        assertEquals(ShowAccountOptionsViewEvent.class, event.getClass());
    }
}
