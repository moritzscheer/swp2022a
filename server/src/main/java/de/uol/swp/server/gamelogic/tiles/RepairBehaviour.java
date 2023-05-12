package de.uol.swp.server.gamelogic.tiles;

import de.uol.swp.server.gamelogic.Block;
import de.uol.swp.server.gamelogic.MoveIntent;
import de.uol.swp.server.gamelogic.Position;
import de.uol.swp.server.gamelogic.Robot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Maria Eduarda Costa Leite Andrade & WKempel
 * @see de.uol.swp.server.gamelogic.tiles.AbstractTileBehaviour
 * @since 2023-03-13
 */
public class RepairBehaviour extends AbstractTileBehaviour {

    // (1)single-wrench: discard 1 Damage token
    // (2)crossed wrench/hammer: discard 1 Damage token AND draw one Option card.
    private int repairSiteKey; // 1 or 2

    /**
     * Constructor
     *
     * @author Maria Eduarda Costa Leite Andrade
     * @see de.uol.swp.server.gamelogic.tiles.AbstractTileBehaviour
     * @since 2023-02-26
     */
    public RepairBehaviour(
            List<Robot> robotStates, Block[][] board, Position blockPos, int repairSiteKey) {
        super(robotStates, board, blockPos);
        this.repairSiteKey = repairSiteKey;
    }

    /**
     * Getter CheckPoint Position
     *
     * @author Maria Eduarda Costa Leite Andrade
     * @see de.uol.swp.server.gamelogic.tiles.AbstractTileBehaviour
     * @since 2023-02-26
     */
    public Position getCheckPointPosition() {
        return this.blockPos;
    }

    /**
     * When the robot arrive a repair station then it will lose one / two DamageTokens. Also, it
     * will update the new lastCheckPointPosition.
     *
     * @author Wkempel
     * @see de.uol.swp.server.gamelogic.tiles.RepairBehaviour
     * @since 2023-03-13
     */
    @Override
    public List<MoveIntent> onCardEnding(int programmStep) {
        for (Robot robotState : robotStates) {
            if (Objects.equals(robotState.getPosition(), blockPos)) {
                if (programmStep == 4) {
                    robotState.setDamageToken(robotState.getDamageToken() - repairSiteKey);
                    robotState.setLastCheckPointPosition(blockPos);
                }
            }
        }
        return null;
    }

    @Override
    public List<int[]> getImage() {
        return new ArrayList<>(List.of(new int[] {25 , 0}));
    }
}
