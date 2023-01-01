package de.uol.swp.common.game;

import java.io.Serializable;
import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Map implements Serializable {

    private int mapIndex;


    /**
     * Empty Constructor
     *
     * @author Mathis Eilers
     * @since 2022-12-23
     */
    public Map()
    {}

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
     * @return The name of the map that the object represents
     * @author Mathis Eilers
     * @since 2022-12-23
     */
    public String getName() {
        return mapList.get(mapIndex).name;
    }

    /**
     * Gets the path of the image displaying the map
     * @return The image path as Path object
     * @author Mathis Eilers
     * @since 2022-12-23
     */
    public Path getImagePath() {
        URL resPath = this.getImageResource();
        try {
            return Path.of(resPath.toURI().getPath());
        }
        catch(java.net.URISyntaxException e)
        {
            return Path.of(".");
        }
    }

    /**
     * Gets the path of the image displaying the map as resource
     * @return The image URL
     * @author Mathis Eilers
     * @since 2022-12-23
     */
    public URL getImageResource() {
        String imgName = mapList.get(mapIndex).imageName;
        return this.getClass().getClassLoader().getResource("./mapImages/" + imgName);
    }

    /**
     * Getter for the index of the map
     * @return The index of the map
     * @author Mathis Eilers
     * @since 2022-12-23
     */
    public int getIndex() {
        return mapIndex;
    }

    /**
     * Sets the index of the map object.
     * WARNING: This is functionally equivalent to replacing your object with a new one.
     * @param index The index of the new map
     * @author Mathis Eilers
     * @since 2022-12-23
     */
    private void setIndex(int index) {
        this.mapIndex = index;
    }

    /**
     * Useful to display maps in Listviews
     * @return The name of the map
     * @author Mathis Eilers
     * @since 2022-12-23
     */
    public String toString()
    {
        return this.getName();
    }




    /*
    Note: The data structure below fits better in a database or file,
    meaning: TODO: Refactor this
     */

    /*
     * This class exists so that attributes of multiple different data types can be stored for each map.
     */
    private static class MapDataTuple implements Serializable {
        public MapDataTuple(String name, String imageName)
        {
            this.name = name;
            this.imageName = imageName;
        }

        public final String name;
        public final String imageName;
    }

    // Due to the instantiations of MapDataTuple this can not be done with 'static'
    private final List<MapDataTuple> mapList = List.of(new MapDataTuple[] {
            new MapDataTuple("Test 1", "map1.png"),
            new MapDataTuple("Test 2", "map2.png")
    });

    /**
     * @return An immutable list of all available maps
     * @author Mathis Eilers
     * @since 2022-12-23
     */
    public static List<Map> getMapList()
    {
        // Can't access the non-static mapList from here,
        // so it has to be done like this
        int mapNum = 0;
        ArrayList<Map> mapList = new ArrayList<>();
        Map m = new Map(mapNum);
        try {
            while (m.getName() != null) {
                mapList.add(m);
                mapNum++;
                m = new Map(mapNum);
            }
        }
        catch(IndexOutOfBoundsException e)
        {}

        return mapList;
    }
}
