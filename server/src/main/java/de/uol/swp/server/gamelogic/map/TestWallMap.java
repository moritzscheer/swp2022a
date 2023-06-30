package de.uol.swp.server.gamelogic.map;

import de.uol.swp.common.game.Position;
import de.uol.swp.common.game.enums.CardinalDirection;
import de.uol.swp.server.gamelogic.tiles.CheckPointBehaviour;
import de.uol.swp.server.gamelogic.tiles.WallBehaviour;

import java.util.Random;

/**
 *
 *
 * @author
 * @since
 */
public class TestWallMap extends AbstractMap {

    /**
     *
     *
     * @author
     * @since
     */
    public TestWallMap() {
        // WallTestMap
        int x = 0;
        int y = 0;

        for (y = 0; y <= 11; y++) {
            for (x = 0; x <= 11; x++) {

                if (y == 6 && x == 6) {
                    generateBlock(
                            map, x, y, new CheckPointBehaviour(null, map, new Position(x, y), 1));
                    generateBlock(
                            map,
                            x + 2,
                            y,
                            new CheckPointBehaviour(null, map, new Position(x + 2, y), 3));
                    generateBlock(
                            map,
                            x + 4,
                            y,
                            new CheckPointBehaviour(null, map, new Position(x + 4, y), 2));
                    generateBlock(
                            map,
                            x + 5,
                            y,
                            new CheckPointBehaviour(null, map, new Position(x + 5, y), 4));
                    break;
                }

                Random random = new Random();
                int randomSpawn = random.nextInt(10);

                if (randomSpawn < 6) {
                    int directionRDM = random.nextInt(4);
                    CardinalDirection direction;

                    switch (directionRDM) {
                        case 1:
                            direction = CardinalDirection.South;
                            break;
                        case 2:
                            direction = CardinalDirection.West;
                            break;
                        case 3:
                            direction = CardinalDirection.East;
                            break;
                        default:
                            direction = CardinalDirection.North;
                            break;
                    }
                    generateBlock(
                            map, x, y, new WallBehaviour(null, map, new Position(x, y), direction));
                }
            }
        }
    }
}
