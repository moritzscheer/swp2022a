package de.uol.swp.server.gamelogic.tiles;

import de.uol.swp.common.game.Position;
import de.uol.swp.common.game.enums.CardinalDirection;
import de.uol.swp.server.gamelogic.Block;
import de.uol.swp.server.gamelogic.Robot;
import de.uol.swp.server.gamelogic.moves.MoveIntent;

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

    private boolean start = false;

    private boolean fullLaser;

    /**
     * @author Merden
     * @since 2023-03-05
     */
    public LaserBehaviour(
            List<Robot> robotStates,
            Block[][] board,
            int[] activeInProgramSteps,
            Position blockPos,
            CardinalDirection laserDir,
            int laserBeam,
            boolean fullLaser,
            boolean start) {
        super(robotStates, board, blockPos);
        this.activeInProgramSteps = activeInProgramSteps;
        this.direction = laserDir;
        this.laserBeam = laserBeam;
        this.start = start;
        this.fullLaser = fullLaser;
    }

    /**
     * @author Merden
     * @since 2023-03-05
     */
    public LaserBehaviour(
            List<Robot> robotStates,
            Block[][] board,
            int[] activeInProgramSteps,
            Position blockPos,
            CardinalDirection laserDir,
            int laserBeam,
            boolean fullLaser) {
        super(robotStates, board, blockPos);
        this.activeInProgramSteps = activeInProgramSteps;
        this.direction = laserDir;
        this.laserBeam = laserBeam;
        this.fullLaser = fullLaser;
    }

    /**
     * When the robot is before a laser then it will get so much damage like laserBeam exist.
     *
     * @author WKempel, Maria
     * @see de.uol.swp.server.gamelogic.Robot
     * @since 2023-03-13
     */
    @Override
    public List<MoveIntent> onLaserStage(int programStep) {
        // this is now called only on the block which starts
        for (int i : activeInProgramSteps) {
            if (i == programStep) {
                switch (direction){
                    case North:
                    case West:
                        searchRobot(direction, -1);
                        break;
                    case East:
                    case South:
                        searchRobot(direction, 1);
                        break;
                }
                break;
            }
        }
        return null;
    }

    /** Search for the first robot that the laser finds
     * and spare the others
     *
     * @author Maria
     * @see de.uol.swp.server.gamelogic.Robot
     * @since 2023-07-03
     */
    private void searchRobot(CardinalDirection direction, int op){
        boolean foundRobot = false;
        int x = blockPos.x;
        int y = blockPos.y;

        while(true){ // iterate through blocks
            boolean foundWall = false;
            for(AbstractTileBehaviour behaviour: board[x][y].getBehaviourList()){
                if(behaviour instanceof LaserBehaviour){
                    for (Robot robotState : robotStates) {
                        if(!robotState.isAlive())
                            continue;
                        if (robotState.getPosition().equals(new Position(x, y))) {
                            foundRobot = true;
                            robotState.setDamageToken(robotState.getDamageToken() + laserBeam);
                            break;
                        }
                    }
                }
                if (behaviour instanceof WallBehaviour) {
                    if(behaviour.getObstruction(direction))
                        foundWall = true;
                }
            }
            if(foundWall || foundRobot)
                break;

            if(direction == CardinalDirection.West || direction == CardinalDirection.East) { // x
                x = x + op;
            }else { // y
                y = y + op;
            }
            // test if there is wall in next block
            try {
                for (AbstractTileBehaviour behaviour: board[x][y].getBehaviourList()) {
                    // check the opposite direction for a wall in the next block
                    if(behaviour.getObstruction(CardinalDirection.values()[(direction.ordinal() + 2) % 4])){
                        foundWall = true;
                        break; // do not go to next block
                    }
                }
            }catch (IndexOutOfBoundsException ignored){
                ;;
            }
            if(foundWall || y < 0 || y == board[0].length || x < 0 || x == board.length)
                break;
        }
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

    /**
     * @author Maria
     * @since 2023-03-05
     */
    public boolean getStart() {
        return this.start;
    }

    /**
     * @author Ole Zimmermann
     * @since 2023-03-05
     */
    public void setStart(boolean start) {
        this.start = start;
    }

    /**
     * @author Merden
     * @since 2023-03-05
     */
    public void setLaserBeam(int beam) {
        this.laserBeam = beam;
    }

    /**
     * @author Merden
     * @since 2023-03-05
     */
    @Override
    public List<int[]> getImage() {
        int placeholder = 17;
        if (this.start) {
            placeholder = 15;
        } else if (this.fullLaser) {
            placeholder = 18;
        }
        return new ArrayList<>(List.of(new int[] {placeholder + laserBeam, direction.ordinal()}));
    }

    /**
     * @author WKempel
     * @since 2023-03-05
     */
    public CardinalDirection getDirection() {
        return direction;
    }

    /**
     * @author WKempel
     * @since 2023-03-05
     */
    public void setDirection(CardinalDirection direction) {
        this.direction = direction;
    }

    /**
     * @author WKempel
     * @since 2023-03-05
     */
    public boolean isFullLaser() {
        return fullLaser;
    }
}
