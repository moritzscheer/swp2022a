package de.uol.swp.common.game;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.URL;

public class MapTest {

    /**
     * Tests whether a list of Maps is returned by getMapList or not
     *
     * @since 2023-01-07
     */
    @Test
    void getMapListTest() {
        Map[] maps = Map.getMapList();
        Assertions.assertNotNull(maps);
        Assertions.assertEquals(5, maps.length);
        Assertions.assertEquals("Map 1", maps[0].getName());
        assertNotEquals(maps.length, 0);
    }

    /**
     * Tests getName method
     *
     * @author WKempel
     * @result The method should return the correct name
     * @see de.uol.swp.common.game.Map
     * @since 2023-06-15
     */
    @Test
    public void testGetName() {
        Map map = new Map(0);
        String expectedName = "Map 1";
        String actualName = map.getName();
        Assertions.assertEquals(expectedName, actualName);
    }

    /**
     * Tests toGetNullImageResource method
     *
     * @author WKempel
     * @result The method should return null
     * @see de.uol.swp.common.game.Map
     * @since 2023-06-15
     */
    @Test
    public void testToGetNullImageResource() {
        Map map = new Map(0);
        URL imageResource = map.getImageResource();
        Assertions.assertNull(imageResource);
    }

    /**
     * Tests getIndex method
     *
     * @author WKempel
     * @result The method should return the correct index
     * @see de.uol.swp.common.game.Map
     * @since 2023-06-15
     */
    @Test
    public void testGetIndex() {
        Map map = new Map(0);
        int expectedIndex = 0;
        int actualIndex = map.getIndex();
        Assertions.assertEquals(expectedIndex, actualIndex);
    }

    /**
     * Tests setIndex method
     *
     * @author WKempel
     * @result The method should set the correct index
     * @see de.uol.swp.common.game.Map
     * @since 2023-06-15
     */
    @Test
    public void testSetIndex() {
        Map map = new Map(0);
        int newIndex = 1;
        map.setIndex(newIndex);
        int expectedIndex = 1;
        int actualIndex = map.getIndex();
        Assertions.assertEquals(expectedIndex, actualIndex);
    }

    /**
     * Tests getMapList method
     *
     * @author WKempel
     * @result The method should return the correct list of maps
     * @see de.uol.swp.common.game.Map
     * @since 2023-06-15
     */
    @Test
    public void testGetMapList() {
        Map[] maps = Map.getMapList();
        Assertions.assertNotNull(maps);
        Assertions.assertEquals(5, maps.length);
        Assertions.assertEquals("Map 1", maps[0].getName());
    }
}
