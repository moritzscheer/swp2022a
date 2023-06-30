package de.uol.swp.server.gamelogic;

import de.uol.swp.common.game.Position;
import de.uol.swp.common.game.enums.CardinalDirection;
import de.uol.swp.server.gamelogic.moves.MoveIntent;
import de.uol.swp.server.gamelogic.tiles.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ole Zimmermann
 */
public class Block implements Serializable, Cloneable {

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
    public List<MoveIntent> onExpressConveyorStage(int programStep) {
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

    /**
     * @author
     * @since
     */
    public <T extends AbstractTileBehaviour> T GetBehaviour(Class<T> type) {
        for (AbstractTileBehaviour behaviour : behaviourList) {
            if (type.isInstance(behaviour)) {
                return (T) behaviour;
            }
        }
        return null;
    }

    /**
     * @author
     * @since
     */
    public List<MoveIntent> OnRotatorStage(int programStep) {
        List<MoveIntent> moves = new ArrayList<>();
        for (AbstractTileBehaviour behaviour : this.behaviourList) {
            if (behaviour.onRotatorStage(programStep) != null) {
                moves.addAll(behaviour.onRotatorStage(programStep));
            }
        }
        return moves;
    }

    /**
     * @author
     * @since
     */
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
     * Getter behaviourList
     *
     * @author Maria Eduarda Costa Leite Andrade
     * @see de.uol.swp.server.gamelogic.tiles.AbstractTileBehaviour
     * @since 2023-04-25
     */
    public AbstractTileBehaviour[] getBehaviourList() {
        return this.behaviourList;
    }

    /**
     * Setter behaviourList
     *
     * @author Maxim
     * @see de.uol.swp.server.gamelogic.tiles.AbstractTileBehaviour
     * @since 2023-05-31
     */
    public void setBehaviourList(AbstractTileBehaviour[] behaviourList) {
        this.behaviourList = behaviourList;
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

    /**
     * @author
     * @since
     */
    public void setRobotsInfo(List<Robot> robots) {
        for (int i = 0; i < behaviourList.length; i++) {
            behaviourList[i].setRobotStates(robots);
        }
    }

    /**
     * Get all imagesIDs as Array to send to client
     *
     * @author Finn & Maria
     * @return imagesIDs
     */
    public int[][] getImages() {
        ArrayList<int[]> images = new ArrayList<>(List.of(new int[] {0, 0}));
        for (int i = 0; i < behaviourList.length; i++) {
            images.addAll(behaviourList[i].getImage());
        }

        int[][] imagesArr = new int[images.size()][];
        for (int i = 0; i < images.size(); i++) {
            imagesArr[i] = images.get(i);
        }

        return imagesArr;
    }

    /**
     * @author
     * @since
     */
    @Override
    public Block clone() {
        try {
            return (Block) super.clone();
        } catch (CloneNotSupportedException e) {
            // Diese Exception sollte normalerweise nicht auftreten,
            // da Block das Cloneable-Interface implementiert.
            // Handle sie entsprechend deinen Anforderungen.
            e.printStackTrace();
            return null;
        }
    }
}
