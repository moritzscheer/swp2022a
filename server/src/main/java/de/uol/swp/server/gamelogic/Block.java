package de.uol.swp.server.gamelogic;

import de.uol.swp.server.gamelogic.tiles.*;
import de.uol.swp.server.gamelogic.tiles.enums.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/** @author Ole Zimmermann */
public class Block implements Serializable {

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
     * @since 2023-02-20
     */
    public Block(AbstractTileBehaviour[] behaviourList, String imgPath, Position pos) {
        this.behaviourList = behaviourList;
        this.imgPath = imgPath;
        this.pos = pos;
    }

    /**
     * Constructor with only one behaviour
     *
     * @author Maria Eduarda Costa Leite Andrade
     * @see de.uol.swp.server.gamelogic.tiles.AbstractTileBehaviour
     * @since 2023-04-25
     */
    public Block(AbstractTileBehaviour behaviour, String imgPath, Position pos) {
        AbstractTileBehaviour[] behaviourList = new AbstractTileBehaviour[1];
        behaviourList[0] = behaviour;

        this.behaviourList = behaviourList;
        this.imgPath = imgPath;
        this.pos = pos;
    }

    /**
     * Constructor with no behaviour
     *
     * @author Maria Eduarda Costa Leite Andrade
     * @see de.uol.swp.server.gamelogic.tiles.AbstractTileBehaviour
     * @since 2023-04-25
     */
    public Block(String imgPath, Position pos) {

        this.behaviourList = null;
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
            if (behaviour.onConveyorStage(programStep) != null) {
                moves.addAll(behaviour.onConveyorStage(programStep));
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
            if (behaviour.onExpressConveyorStage(programStep) != null) {
                moves.addAll(behaviour.onExpressConveyorStage(programStep));
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
            if (behaviour.onPusherStage(programStep) != null) {
                moves.addAll(behaviour.onPusherStage(programStep));
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
            if (behaviour.onLaserStage(programStep) != null) {
                moves.addAll(behaviour.onLaserStage(programStep));
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
            if (behaviour.onPressorStage(programStep) != null) {
                moves.addAll(behaviour.onPressorStage(programStep));
            }
        }
        return moves;
    }

    public <T extends AbstractTileBehaviour> T GetBehaviour(Class<T> type) {
        for (AbstractTileBehaviour behaviour : behaviourList) {
            if (type.isInstance(behaviour)) {
                return (T) behaviour;
            }
        }
        return null;
    }

    public List<MoveIntent> OnRotatorStage(int programStep) {
        List<MoveIntent> moves = new ArrayList<>();
        for (AbstractTileBehaviour behaviour : this.behaviourList) {
            if (behaviour.onRotatorStage(programStep) != null) {
                moves.addAll(behaviour.onRotatorStage(programStep));
            }
        }
        return moves;
    }

    public List<MoveIntent> OnCheckPointStage(int programStep) {
        List<MoveIntent> moves = new ArrayList<>();
        for (AbstractTileBehaviour behaviour : this.behaviourList) {
            if (behaviour.onCheckPointStage(programStep) != null) {
                moves.addAll(behaviour.onCheckPointStage(programStep));
            }
        }
        return moves;
    }

    /**
     * Getter behaviouList
     *
     * @author Maria Eduarda Costa Leite Andrade
     * @see de.uol.swp.server.gamelogic.tiles.AbstractTileBehaviour
     * @since 2023-04-25
     */
    public AbstractTileBehaviour[] getBehaviourList() {
        return this.behaviourList;
    }

    /**
     * Getter imagePath
     *
     * @author Maria Eduarda Costa Leite Andrade
     * @see de.uol.swp.server.gamelogic.tiles.AbstractTileBehaviour
     * @since 2023-04-25
     */
    public String getImgPath() {
        return this.imgPath;
    }

    public void setRobotsInfo(Robot[] robots) {
        for (int i = 0; i < behaviourList.length; i++) {
            behaviourList[i].setRobotStates(robots);
        }
    }
}