package de.uol.swp.server.gamelogic;

import de.uol.swp.server.gamelogic.tiles.*;
import de.uol.swp.server.gamelogic.tiles.enums.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ole Zimmermann
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

    public boolean getObstruction(CardinalDirection dir) {
        for (AbstractTileBehaviour behaviour : behaviourList) {
            if (behaviour.getObstruction(dir)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @author Ole Zimmermann
     * @see de.uol.swp.server.gamelogic.tiles.AbstractTileBehaviour
     * @since 13-03-2023
     */
    public List<MoveIntent> OnConveyorStage(int programStep) {
        List<MoveIntent> moves = new ArrayList<>();
        for (AbstractTileBehaviour behaviour : this.behaviourList) {
            if (behaviour.OnConveyorStage(programStep) != null) {
                moves.addAll(behaviour.OnConveyorStage(programStep));
            }
        }
        return moves;
    }

    /**
     * @author Ole Zimmermann
     * @see de.uol.swp.server.gamelogic.tiles.AbstractTileBehaviour
     * @since 13-03-2023
     */
    public List<MoveIntent> OnExpressConveyorStage(int programStep) {
        List<MoveIntent> moves = new ArrayList<>();
        for (AbstractTileBehaviour behaviour : this.behaviourList) {
            if (behaviour.OnExpressConveyorStage(programStep) != null) {
                moves.addAll(behaviour.OnExpressConveyorStage(programStep));
            }
        }
        return moves;
    }

    /**
     * @author Ole Zimmermann
     * @see de.uol.swp.server.gamelogic.tiles.AbstractTileBehaviour
     * @since 13-03-2023
     */
    public List<MoveIntent> OnPusherStage(int programStep) {
        List<MoveIntent> moves = new ArrayList<>();
        for (AbstractTileBehaviour behaviour : this.behaviourList) {
            if (behaviour.OnPusherStage(programStep) != null) {
                moves.addAll(behaviour.OnPusherStage(programStep));
            }
        }
        return moves;
    }

    /**
     * @author Ole Zimmermann
     * @see de.uol.swp.server.gamelogic.tiles.AbstractTileBehaviour
     * @since 13-03-2023
     */
    public List<MoveIntent> OnLaserStage(int programStep) {
        List<MoveIntent> moves = new ArrayList<>();
        for (AbstractTileBehaviour behaviour : this.behaviourList) {
            if (behaviour.OnLaserStage(programStep) != null) {
                moves.addAll(behaviour.OnLaserStage(programStep));
            }
        }
        return moves;
    }

    /**
     * @author Ole Zimmermann
     * @see de.uol.swp.server.gamelogic.tiles.AbstractTileBehaviour
     * @since 13-03-2023
     */
    public List<MoveIntent> OnPresserStage(int programStep) {
        List<MoveIntent> moves = new ArrayList<>();
        for (AbstractTileBehaviour behaviour : this.behaviourList) {
            if (behaviour.OnPressorStage(programStep) != null) {
                moves.addAll(behaviour.OnPressorStage(programStep));
            }
        }
        return moves;
    }

    public <T extends  AbstractTileBehaviour> T GetBehaviour(Class<T> type) {
        for (AbstractTileBehaviour behaviour : behaviourList) {
            if(type.isInstance(behaviour)){
                return (T) behaviour;
            }
        }
        return null;
    }
}
