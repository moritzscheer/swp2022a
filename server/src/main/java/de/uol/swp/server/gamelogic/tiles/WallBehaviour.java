package de.uol.swp.server.gamelogic.tiles;

import de.uol.swp.common.game.Position;
import de.uol.swp.common.game.enums.CardinalDirection;
import de.uol.swp.server.gamelogic.Block;
import de.uol.swp.server.gamelogic.Robot;

import java.util.ArrayList;
import java.util.List;

/** @author Ole Zimemrmann */
public class WallBehaviour extends AbstractTileBehaviour {

    private final CardinalDirection wallDirection;

    /**
     * Constructor
     *
     * @author Ole Zimmermann
     * @see de.uol.swp.server.gamelogic.tiles.AbstractTileBehaviour
     * @since 13-03-2023
     */
    public WallBehaviour(
            List<Robot> robotStates,
            Block[][] board,
            Position blockPos,
            CardinalDirection wallDirection) {
        super(robotStates, board, blockPos);
        this.wallDirection = wallDirection;
    }

    /**
     * Return if there is a wall in the given direction
     *
     * @author Ole Zimmermann
     * @see de.uol.swp.server.gamelogic.tiles.AbstractTileBehaviour
     * @since 13-03-2023
     */
    @Override
    public boolean getObstruction(CardinalDirection direction) {
        return direction == this.wallDirection;
    }

    @Override
    public List<int[]> getImage() {
        return new ArrayList<>(List.of(new int[] {1, wallDirection.ordinal()}));
    }
}
