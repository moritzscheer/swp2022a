package de.uol.swp.server.gamelogic;

import de.uol.swp.common.game.Position;
import de.uol.swp.common.game.enums.CardinalDirection;
import de.uol.swp.server.gamelogic.moves.MoveIntent;
import de.uol.swp.server.gamelogic.tiles.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/** @author Ole Zimmermann */
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
            List<MoveIntent> thisMoves = behaviour.onConveyorStage(programStep);
            if (thisMoves != null) {
                moves.addAll(thisMoves);
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
            List<MoveIntent> thisMoves = behaviour.onExpressConveyorStage(programStep);
            if (thisMoves != null) {
                moves.addAll(thisMoves);
            }
        }
        return moves;
    }

    /**
     * @author Ole Zimmermann
     * @see de.uol.swp.server.gamelogic.tiles.AbstractTileBehaviour
     * @since 13-03-2023
     */
    public List<MoveIntent> onPusherStage(int programStep) {
        List<MoveIntent> moves = new ArrayList<>();
        for (AbstractTileBehaviour behaviour : this.behaviourList) {
            List<MoveIntent> thisMoves = behaviour.onPusherStage(programStep);
            if (thisMoves != null) {
                moves.addAll(thisMoves);
            }
        }
        return moves;
    }

    /**
     * @author Ole Zimmermann
     * @see de.uol.swp.server.gamelogic.tiles.AbstractTileBehaviour
     * @since 13-03-2023
     */
    public List<MoveIntent> onLaserStage(int programStep) {
        List<MoveIntent> moves = new ArrayList<>();
        for (AbstractTileBehaviour behaviour : this.behaviourList) {
            List<MoveIntent> thisMoves = behaviour.onLaserStage(programStep);
            if (thisMoves != null) {
                moves.addAll(thisMoves);
            }
        }
        return moves;
    }

    /**
     * @author Ole Zimmermann
     * @see de.uol.swp.server.gamelogic.tiles.AbstractTileBehaviour
     * @since 13-03-2023
     */
    public List<MoveIntent> onPressorStage(int programStep) {
        List<MoveIntent> moves = new ArrayList<>();
        for (AbstractTileBehaviour behaviour : this.behaviourList) {
            List<MoveIntent> thisMoves = behaviour.onPressorStage(programStep);
            if (thisMoves != null) {
                moves.addAll(thisMoves);
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
            List<MoveIntent> thisMoves = behaviour.onRotatorStage(programStep);
            if (thisMoves != null) {
                moves.addAll(thisMoves);
            }
        }
        return moves;
    }

    public List<MoveIntent> OnCheckPointStage(int programStep) {
        List<MoveIntent> moves = new ArrayList<>();
        for (AbstractTileBehaviour behaviour : this.behaviourList) {
            List<MoveIntent> thisMoves = behaviour.onCheckPointStage(programStep);
            if (thisMoves != null) {
                moves.addAll(thisMoves);
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
