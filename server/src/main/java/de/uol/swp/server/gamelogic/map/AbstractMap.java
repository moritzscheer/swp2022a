package de.uol.swp.server.gamelogic.map;

import de.uol.swp.common.game.Position;
import de.uol.swp.server.gamelogic.Block;
import de.uol.swp.server.gamelogic.tiles.AbstractTileBehaviour;

/**
 * @author Merden
 * @since 2023-06-06
 */
public abstract class AbstractMap {
    protected Block[][] map = new Block[12][12];

    /**
     * Generates a block with a tile
     *
     * @author Merden
     * @since 2023-06-06
     */
    public AbstractMap() {
        // Template
        int x = 0;
        int y = 0;

        generateBlock(map, x, y);
        x = 1;
        generateBlock(map, x, y);
        x = 2;
        generateBlock(map, x, y);
        x = 3;
        generateBlock(map, x, y);
        x = 4;
        generateBlock(map, x, y);
        x = 5;
        generateBlock(map, x, y);
        x = 6;
        generateBlock(map, x, y);
        x = 7;
        generateBlock(map, x, y);
        x = 8;
        generateBlock(map, x, y);
        x = 9;
        generateBlock(map, x, y);
        x = 10;
        generateBlock(map, x, y);
        x = 11;
        generateBlock(map, x, y);

        y = 1;

        x = 0;
        generateBlock(map, x, y);
        x = 1;
        generateBlock(map, x, y);
        x = 2;
        generateBlock(map, x, y);
        x = 3;
        generateBlock(map, x, y);
        x = 4;
        generateBlock(map, x, y);
        x = 5;
        generateBlock(map, x, y);
        x = 6;
        generateBlock(map, x, y);
        x = 7;
        generateBlock(map, x, y);
        x = 8;
        generateBlock(map, x, y);
        x = 9;
        generateBlock(map, x, y);
        x = 10;
        generateBlock(map, x, y);
        x = 11;
        generateBlock(map, x, y);

        y = 2;

        x = 0;
        generateBlock(map, x, y);
        x = 1;
        generateBlock(map, x, y);
        x = 2;
        generateBlock(map, x, y);
        x = 3;
        generateBlock(map, x, y);
        x = 4;
        generateBlock(map, x, y);
        x = 5;
        generateBlock(map, x, y);
        x = 6;
        generateBlock(map, x, y);
        x = 7;
        generateBlock(map, x, y);
        x = 8;
        generateBlock(map, x, y);
        x = 9;
        generateBlock(map, x, y);
        x = 10;
        generateBlock(map, x, y);
        x = 11;
        generateBlock(map, x, y);

        y = 3;

        x = 0;
        generateBlock(map, x, y);
        x = 1;
        generateBlock(map, x, y);
        x = 2;
        generateBlock(map, x, y);
        x = 3;
        generateBlock(map, x, y);
        x = 4;
        generateBlock(map, x, y);
        x = 5;
        generateBlock(map, x, y);
        x = 6;
        generateBlock(map, x, y);
        x = 7;
        generateBlock(map, x, y);
        x = 8;
        generateBlock(map, x, y);
        x = 9;
        generateBlock(map, x, y);
        x = 10;
        generateBlock(map, x, y);
        x = 11;
        generateBlock(map, x, y);
        y = 4;
        x = 0;
        generateBlock(map, x, y);
        x = 1;
        generateBlock(map, x, y);
        x = 2;
        generateBlock(map, x, y);
        x = 3;
        generateBlock(map, x, y);
        x = 4;
        generateBlock(map, x, y);
        x = 5;
        generateBlock(map, x, y);
        x = 6;
        generateBlock(map, x, y);
        x = 7;
        generateBlock(map, x, y);
        x = 8;
        generateBlock(map, x, y);
        x = 9;
        generateBlock(map, x, y);
        x = 10;
        generateBlock(map, x, y);
        x = 11;
        generateBlock(map, x, y);
        y = 5;
        x = 0;
        generateBlock(map, x, y);
        x = 1;
        generateBlock(map, x, y);
        x = 2;
        generateBlock(map, x, y);
        x = 3;
        generateBlock(map, x, y);
        x = 4;
        generateBlock(map, x, y);
        x = 5;
        generateBlock(map, x, y);
        x = 6;
        generateBlock(map, x, y);
        x = 7;
        generateBlock(map, x, y);
        x = 8;
        generateBlock(map, x, y);
        x = 9;
        generateBlock(map, x, y);
        x = 10;
        generateBlock(map, x, y);
        x = 11;
        generateBlock(map, x, y);

        y = 6;

        x = 0;
        generateBlock(map, x, y);
        x = 1;
        generateBlock(map, x, y);
        x = 2;
        generateBlock(map, x, y);
        x = 3;
        generateBlock(map, x, y);
        x = 4;
        generateBlock(map, x, y);
        x = 5;
        generateBlock(map, x, y);
        x = 6;
        generateBlock(map, x, y);
        x = 7;
        generateBlock(map, x, y);
        x = 8;
        generateBlock(map, x, y);
        x = 9;
        generateBlock(map, x, y);
        x = 10;
        generateBlock(map, x, y);
        x = 11;
        generateBlock(map, x, y);

        y = 7;

        x = 0;
        generateBlock(map, x, y);
        x = 1;
        generateBlock(map, x, y);
        x = 2;
        generateBlock(map, x, y);
        x = 3;
        generateBlock(map, x, y);
        x = 4;
        generateBlock(map, x, y);
        x = 5;
        generateBlock(map, x, y);
        x = 6;
        generateBlock(map, x, y);
        x = 7;
        generateBlock(map, x, y);
        x = 8;
        generateBlock(map, x, y);
        x = 9;
        generateBlock(map, x, y);
        x = 10;
        generateBlock(map, x, y);
        x = 11;
        generateBlock(map, x, y);

        y = 8;

        x = 0;
        generateBlock(map, x, y);
        x = 1;
        generateBlock(map, x, y);
        x = 2;
        generateBlock(map, x, y);
        x = 3;
        generateBlock(map, x, y);
        x = 4;
        generateBlock(map, x, y);
        x = 5;
        generateBlock(map, x, y);
        x = 6;
        generateBlock(map, x, y);
        x = 7;
        generateBlock(map, x, y);
        x = 8;
        generateBlock(map, x, y);
        x = 9;
        generateBlock(map, x, y);
        x = 10;
        generateBlock(map, x, y);
        x = 11;
        generateBlock(map, x, y);

        y = 9;

        x = 0;
        generateBlock(map, x, y);
        x = 1;
        generateBlock(map, x, y);
        x = 2;
        generateBlock(map, x, y);
        x = 3;
        generateBlock(map, x, y);
        x = 4;
        generateBlock(map, x, y);
        x = 5;
        generateBlock(map, x, y);
        x = 6;
        generateBlock(map, x, y);
        x = 7;
        generateBlock(map, x, y);
        x = 8;
        generateBlock(map, x, y);
        x = 9;
        generateBlock(map, x, y);
        x = 10;
        generateBlock(map, x, y);
        x = 11;
        generateBlock(map, x, y);

        y = 10;

        x = 0;
        generateBlock(map, x, y);
        x = 1;
        generateBlock(map, x, y);
        x = 2;
        generateBlock(map, x, y);
        x = 3;
        generateBlock(map, x, y);
        x = 4;
        generateBlock(map, x, y);
        x = 5;
        generateBlock(map, x, y);
        x = 6;
        generateBlock(map, x, y);
        x = 7;
        generateBlock(map, x, y);
        x = 8;
        generateBlock(map, x, y);
        x = 9;
        generateBlock(map, x, y);
        x = 10;
        generateBlock(map, x, y);
        x = 11;
        generateBlock(map, x, y);

        y = 11;

        x = 0;
        generateBlock(map, x, y);
        x = 1;
        generateBlock(map, x, y);
        x = 2;
        generateBlock(map, x, y);
        x = 3;
        generateBlock(map, x, y);
        x = 4;
        generateBlock(map, x, y);
        x = 5;
        generateBlock(map, x, y);
        x = 6;
        generateBlock(map, x, y);
        x = 7;
        generateBlock(map, x, y);
        x = 8;
        generateBlock(map, x, y);
        x = 9;
        generateBlock(map, x, y);
        x = 10;
        generateBlock(map, x, y);
        x = 11;
        generateBlock(map, x, y);
    }

    /**
     * Generate a block with a position and a list of behaviours
     *
     * @author Merden
     * @since 2023-06-06
     */
    public void generateBlock(Block[][] map, int x, int y, AbstractTileBehaviour... behaviours) {
        this.map[x][y] = new Block(behaviours, null, new Position(x, y));
    }

    /**
     * get method for the map
     *
     * @author Maxim Erden and Finn Oldeboershuis
     * @since 2023-05-21
     */
    public Block[][] getMap() {
        return this.map;
    }
}
