package de.uol.swp.server.gamelogic;

import de.uol.swp.server.gamelogic.cards.Card;
import de.uol.swp.server.gamelogic.tiles.enums.CardinalDirection;

import javax.swing.text.Position;

/**
 *
 *
 * @author
 * @see
 * @since
 */
public class Robot {

    private int id;
    private String imgPath;
    private Position currentPosition;
    private boolean alive;
    private int damageToken;
    private CardinalDirection direction;
    private boolean backupCopy;
    private int lastCheckPoint;
    private Position lastCheckPointPosition;
    private Position lastRepairSite;
    private int lifePoints;
    private boolean powerDown;

    //TODO: In my opinion this has to be moved to a new class named "Player" ~Finn
    private Card[] cards;               //max 9 cards
    private int[] registerCardIDs;      //max 5 cards
    private int optionCard;
    private int lockedRegisters;

    /**
     *
     *
     * @author
     * @see
     * @since
     */
    public boolean registerReady(){
        //TODO
        return false;
    }

    /**
     *
     *
     * @author
     * @see
     * @since
     */
    public void fireLaser(){
        //TODO
    }

    /**
     * @author
     * @see
     * @since
     */
    public Card[] revealCard(int programStep){
        //TODO
        return cards;
    }

    /**
     *
     *
     * @author
     * @see
     * @since
     */
    public void move(Card[] cards[]){
        //TODO
    }

    /**
     *
     *
     * @author
     * @see
     * @since
     */
    public void setPowerDown(){
        //TODO
    }

    /**
     *
     *
     * @author
     * @see
     * @since
     */
    public int lockRegister(){
        //TODO
        return 0;
    }
}
