package de.uol.swp.common.game;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class MapTest {

    /**
     * Tests whether a list of Maps is returned by getMapList or not
     *
     * @since 2023-01-07
     */
    @Test
    void getMapListTest() {
        Map[] maps = Map.getMapList();
        assertNotNull(maps);
        assertNotEquals(maps.length, 0);
    }
}
