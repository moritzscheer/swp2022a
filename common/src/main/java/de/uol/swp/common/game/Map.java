package de.uol.swp.common.game;

import java.io.Serializable;
import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Map implements Serializable {

    private int mapIndex;

    public Map()
    {}

    public Map(int mapIndex)
    {
        this.mapIndex = mapIndex;
    }

    public String getName() {
        return mapList.get(mapIndex).name;
    }

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

    public URL getImageResource() {
        String imgName = mapList.get(mapIndex).imageName;
        return this.getClass().getClassLoader().getResource("./mapImages/" + imgName);
    }

    public int getIndex() {
        return mapIndex;
    }

    private void setIndex(int index) {
        this.mapIndex = index;
    }

    public String toString()
    {
        return this.getName();
    }




    /*
    Note: The data structure below fits better in a database or file,
    meaning: TODO: Refactor this
     */
    private static class MapDataTuple implements Serializable {
        public MapDataTuple(String name, String imageName)
        {
            this.name = name;
            this.imageName = imageName;
        }

        public String name;
        public String imageName;
    }

    private final List<MapDataTuple> mapList = List.of(new MapDataTuple[] {
            new MapDataTuple("Test 1", "map1.png"),
            new MapDataTuple("Test 2", "map2.png")
    });

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
