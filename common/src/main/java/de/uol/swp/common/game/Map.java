package de.uol.swp.common.game;

import java.io.Serializable;
import java.net.URL;
import java.nio.file.Path;

public class Map implements Serializable {

    private int mapIndex;

    /**
     * Empty Constructor
     *
     * @author Mathis Eilers
     * @since 2022-12-23
     */
    public Map() {}

    /**
     * Constructor
     *
     * @param mapIndex The Index of the map in the list returned by getMapList()
     * @author Mathis Eilers
     * @since 2022-12-23
     */
    public Map(int mapIndex)
    {
        this.mapIndex = mapIndex;
    }

    /**
     * Getter for the name attribute
     *
     * @return The name of the map that the object represents
     * @author Mathis Eilers
     * @since 2022-12-23
     */
    public String getName() {
        return mapList[mapIndex][0];
    }

    /**
     * Gets the path of the image displaying the map
     *
     * @return The image path as Path object
     * @author Mathis Eilers
     * @since 2022-12-23
     */
    public Path getImagePath() {
        URL resPath = this.getImageResource();
        try {
            return Path.of(resPath.toURI().getPath());
        } catch (java.net.URISyntaxException e) {
            return Path.of(".");
        }
    }

    /**
     * Gets the path of the image displaying the map as resource
     *
     * @return The image URL
     * @author Mathis Eilers
     * @since 2022-12-23
     */
    public URL getImageResource() {
        String imgName = mapList[mapIndex][1];
        return this.getClass().getClassLoader().getResource("./mapImages/" + imgName);
    }

    /**
     * Getter for the index of the map
     *
     * @return The index of the map
     * @author Mathis Eilers
     * @since 2022-12-23
     */
    public int getIndex() {
        return mapIndex;
    }

    /**
     * Sets the index of the map object. WARNING: This is functionally equivalent to replacing your
     * object with a new one.
     *
     * @param index The index of the new map
     * @author Mathis Eilers
     * @since 2022-12-23
     */
    public void setIndex(int index) {
        this.mapIndex = index;
    }

    /**
     * Useful to display maps in Listviews
     *
     * @return The name of the map
     * @author Mathis Eilers
     * @since 2022-12-23
     */
    public String toString()
    {
        return this.getName();
    }

    // Due to the instantiations of MapDataTuple this can not be done with 'static'
    private static final String[][] mapList = {
            {"Map 1", "MapOne.png"},
            {"Map 2", "MapTwo.png"},
            {"Map 3", "MapThree.png"},
            {"TEST_LaserMap", "TestLaserMap.png"},
            {"TEST_PusherMap", "TestPusherMap.png"},
            {"TEST_WallMap", "TestWallMap.png"},
            {"TEST_ConveyorMap", "TestConveyorMap.png"}
    };

    /**
     * @return An immutable array of all available maps
     * @author Mathis Eilers
     * @since 2022-12-23
     */
    public static Map[] getMapList()
    {
        Map[] maps = new Map[mapList.length];
        for(int i = 0; i < maps.length; i++)
        {
            maps[i] = new Map(i);
        }

        return maps;
    }
}
