package de.uol.swp.server.gamelogic;

import java.util.Timer;

import javax.swing.text.Position;

/**
 * @author
 * @see
 * @since
 */
public class Game {

    private Block[][] board;
    private Position[] dockingBays;
    private Robot[] robots;
    private int nRobots;
    private int rowCount;
    private int columCount;
    private int programStep;
    private Timer timer;
    private int readyRegister;

    /**
     * Constructor
     *
     * @author Maria Eduarda Costa Leite Andrade
     * @see de.uol.swp.server.gamelogic.Block
     * @see de.uol.swp.server.gamelogic.Position
     * @see de.uol.swp.server.gamelogic.Robot
     * @since 20-02-2023
     */
    public Game(Block[][] board, Position[] dockingBays, Robot[] robots) {

        this.board = board;
        this.dockingBays = dockingBays;
        this.robots = robots;
        this.nRobots = robots.length;
        this.rowCount = board.length;
        this.columCount = board[0].length;
        this.programStep = 0;
    }

    /**
     * @author
     * @see
     * @since
     */
    public void setDockingBaysPositions(Position[] position) {
        // TODO
    }

    /**
     * @author
     * @see
     * @since
     */
    public void register() {
        // TODO
    }

    /**
     * @author
     * @see
     * @since
     */
    public void revealProgramCards() {
        // TODO
    }

    /**
     * @author
     * @see
     * @since
     */
    public void moveRobots() {
        // TODO
    }

    /**
     * @author
     * @see
     * @since
     */
    public void moveBoardElements() {
        // TODO
    }

    /**
     * @author
     * @see
     * @since
     */
    public void fireLaser() {
        // TODO
    }

    /**
     * @author
     * @see
     * @since
     */
    public void touchCheckPoints() {
        // TODO
    }

    /**
     * @author
     * @see
     * @since
     */
    public void startTimer(int readyRegister, int nRobots) {
        // TODO
    }
}
