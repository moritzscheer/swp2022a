package de.uol.swp.server.gamelogic.tiles;

import de.uol.swp.common.game.Position;
import de.uol.swp.common.game.enums.CardinalDirection;
import de.uol.swp.server.gamelogic.Block;
import de.uol.swp.server.gamelogic.MoveIntent;
import de.uol.swp.server.gamelogic.Robot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * If a robot steps on the pusher tile, then the robot gets pushed in the specific direction
 *
 * @author Tommy Dang
 * @since 28.02.2023
 */
public class PusherBehaviour2 extends AbstractTileBehaviour {


    public PusherBehaviour2(
            List<Robot> robotStates,
            Block[][] board,
            Position blockPos,
            int[] activeInProgramSteps,
            CardinalDirection direction) {
        super(robotStates, board, blockPos);


        /*
        if(activeInProgramSteps.length == 3){
            this.type = 42;
        }
        else if (activeInProgramSteps.length == 2){
            this.type = 43;
        }
        else{
            this.type = activeInProgramSteps[0] + 38;
        }
        */
    }

    /**
     * When a robot is on the pusher tile detected, then it pushes the robot in the direction of the
     * pusher.
     *
     * @author Tommy Dang and Finn Oldeboershuis
     * @see AbstractTileBehaviour
     * @see MoveIntent
     * @since 28.02.2023
     */

    @Override
    public List<int[]> getImage() {
        return new ArrayList<>(List.of(new int[] {42 , 0}));
    }
}
