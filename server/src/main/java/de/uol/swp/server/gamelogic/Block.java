package de.uol.swp.server.gamelogic;

import de.uol.swp.server.gamelogic.tiles.AbstractTileBehaviour;

/**
 * @author
 * @see
 * @since
 */
public class Block {

    private AbstractTileBehaviour[] behaviourList;
    private String imgPath;
    private Position pos;
    private boolean isRobotHere;
    private Robot robot;

    /**
     * Constructor
     *
     * @author Maria Eduarda Costa Leite Andrade
     * @see de.uol.swp.server.gamelogic.tiles.AbstractTileBehaviour
     * @since 20-02-2023
     */
    public Block(AbstractTileBehaviour[] behaviourList, String imgPath, Position pos) {
        this.behaviourList = behaviourList;
        this.imgPath = imgPath;
        this.pos = pos;
    }

    /**
     * @author
     * @see
     * @since
     */
    public void executeBehaviours(Robot robot) {}
}
