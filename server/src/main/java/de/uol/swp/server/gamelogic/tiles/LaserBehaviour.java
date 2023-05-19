package de.uol.swp.server.gamelogic.tiles;

import de.uol.swp.server.gamelogic.Block;
import de.uol.swp.server.gamelogic.MoveIntent;
import de.uol.swp.common.game.Position;
import de.uol.swp.server.gamelogic.Robot;
import de.uol.swp.common.game.enums.CardinalDirection;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Maria Eduarda Costa Leite Andrade & WKempel
 * @see de.uol.swp.server.gamelogic.tiles.AbstractTileBehaviour
 * @since 2023-03-13
 */
public class LaserBehaviour extends AbstractTileBehaviour {

    private CardinalDirection direction;

    private int[] activeInProgramSteps;
    private int laserBeam; // laserBeam is directed related to damage

    public LaserBehaviour(
            List<Robot> robotStates,
            Block[][] board,
            int[] activeInProgramSteps,
            Position blockPos,
            CardinalDirection laserDir,
            int laserBeam) {
        super(robotStates, board, blockPos);
        this.activeInProgramSteps = activeInProgramSteps;
        this.direction = laserDir;
        this.laserBeam = laserBeam;
    }

    /**
     * When the robot is before a laser then it will get so much damage like laserBeam exist.
     *
     * @author WKempel
     * @see de.uol.swp.server.gamelogic.Robot
     * @since 2023-03-13
     */
    @Override
    public List<MoveIntent> onLaserStage(int programStep) {
        for (int i : activeInProgramSteps) {
            if (i == programStep) {
                for (Robot robotState : robotStates) {
                    if (robotState.getPosition().equals(blockPos)) {
                        robotState.setDamageToken(robotState.getDamageToken() + laserBeam);
                        break;
                    }
                }
                break;
            }
        }
        return null;
    }

    /**
     * Getter for test
     *
     * @author Maria Eduarda Costa Leite Andrade
     * @since 2023-03-13
     */
    public CardinalDirection getLaserDirection() {
        return this.direction;
    }
    /**
     * Getter for test
     *
     * @author Maria Eduarda Costa Leite Andrade
     * @since 2023-03-13
     */
    public int getLaserBeam() {
        return this.laserBeam;
    }

    @Override
    public List<int[]> getImage() {
        return new ArrayList<>(List.of(new int[] {17 + laserBeam * 3 , 0}));
    }
}
