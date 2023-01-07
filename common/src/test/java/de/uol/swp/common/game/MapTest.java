package de.uol.swp.common.game;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class MapTest {

    /**
     * Tests whether a list of Maps is returned by getMapList or not
     *
     * @since 2023-01-07
     */
    @Test
    void getMapListTest()
    {
        List<Map> maps = Map.getMapList();
        assertNotNull(maps);
        assertNotEquals(maps.size(), 0);
    }

}
