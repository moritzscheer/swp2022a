package de.uol.swp.server.gamelogic.unitTest;

import de.uol.swp.common.game.Position;
import de.uol.swp.server.gamelogic.Block;
import de.uol.swp.server.gamelogic.map.AbstractMap;
import de.uol.swp.server.gamelogic.tiles.AbstractTileBehaviour;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AbstractMapTest {

    private AbstractMap map;
    @BeforeEach
    void setUp() {
        map = new AbstractMap() {
            @Override
            public void generateBlock(Block[][] map, int x, int y, AbstractTileBehaviour... behaviours) {
                map[x][y] = new Block(behaviours, null, new Position(x, y));
            }
        };
    }

    @Test
    void testGetMap() {
        Block[][] actualMap = map.getMap();
        Assertions.assertNotNull(actualMap);
        Assertions.assertEquals(12, actualMap.length);
        Assertions.assertEquals(12, actualMap[0].length);
    }

    @Test
    void testGenerateBlock() {
        map.generateBlock(map.getMap(), 0, 0);
        Block[][] actualMap = map.getMap();
        Assertions.assertNotNull(actualMap[0][0]);
    }
}
