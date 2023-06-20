package de.uol.swp.server.gamelogic.map;

import de.uol.swp.common.game.Position;
import de.uol.swp.common.game.enums.CardinalDirection;
import de.uol.swp.server.gamelogic.tiles.*;

import java.util.Random;

public class TestPusherMap extends AbstractMap {

    public TestPusherMap() {
        // LASERTEST
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

                int randomSpawn = random.nextInt(2);

                if (randomSpawn == 0) {
                    int directionRDM = random.nextInt(4);
                    CardinalDirection direction;
                    int typeRDM = random.nextInt(5);

                    int[] type;

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

                    switch (typeRDM) {
                        case 0:
                            int[] type1 = {1, 3, 5};
                            type = type1;
                            break;
                        case 1:
                            int[] type2 = {2, 4};
                            type = type2;
                            break;
                        case 2:
                            int[] type3 = {1};
                            type = type3;
                            break;
                        case 3:
                            int[] type4 = {2};
                            type = type4;
                            break;
                        default:
                            int[] type5 = {3};
                            type = type5;
                            break;
                    }

                    generateBlock(
                            map,
                            x,
                            y,
                            new WallBehaviour(null, map, new Position(x, y), direction),
                            new PusherBehaviour(null, map, new Position(x, y), type, direction));
                }
            }
        }
    }
}
