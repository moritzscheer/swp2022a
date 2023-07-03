package de.uol.swp.server.gamelogic.integrationTest;

import com.google.common.eventbus.EventBus;
import de.uol.swp.common.game.Position;
import de.uol.swp.common.game.dto.CardDTO;
import de.uol.swp.common.game.enums.CardinalDirection;
import de.uol.swp.common.user.request.LoginRequest;
import de.uol.swp.server.gamelogic.*;
import de.uol.swp.common.user.User;
import de.uol.swp.common.user.UserDTO;
import de.uol.swp.server.gamelogic.cards.Card;
import de.uol.swp.server.gamelogic.map.MapBuilder;
import de.uol.swp.server.gamelogic.tiles.AbstractTileBehaviour;
import de.uol.swp.server.gamelogic.tiles.CheckPointBehaviour;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class gameTest {
    private Game game;
    private Game gameSaveOnePlayer;
    private Game gameSaveTwoPlayers;
    private Game gameOnMapOneWithFourCP;
    private Card[] cards= new Card[5];
    private AbstractPlayer playerOne;
    private Robot robotOne;
    private AbstractPlayer playerTwo;
    private Robot robotTwo;

    /** Cloning gameSaveOnePlayer object in game to reset the game
     *
     * @author Jann Erik Bruns, Tommy Dang
     * @since 2023-06-19
     */
    public void SetGameOnePlayer(){
        List<User> users = new ArrayList<User>();
        users.add(new UserDTO("test1", "test1", ""));
        Set<User> usersSet = new HashSet<>(users);

        game = new Game(1,  usersSet, "MapOne", 0, 2, 1);

        playerOne = game.getPlayers().get(0);
        robotOne = playerOne.getRobot();
    }
    /** Cloning gameSaveTwoPlayers object in game to reset the game
     *
     * @author Jann Erik Bruns, Tommy Dang
     * @since 2023-06-19
     */
    public void SetGameTwoPlayers(){
        List<User> users = new ArrayList<>();
        users.add(new UserDTO("test1", "test1", ""));
        users.add(new UserDTO("test2", "test2", ""));
        Set<User> usersSet = new HashSet<>(users);

        game = new Game(1,  usersSet, "MapOne", 0, 2, 1);

        playerOne = game.getPlayers().get(0);
        robotOne = playerOne.getRobot();
        playerTwo = game.getPlayers().get(1);
        robotTwo = playerTwo.getRobot();
    }

    /** Cloning gameSaveOnePlayer object in game to reset the game
     *
     * @author Jann Erik Bruns, Tommy Dang
     * @since 2023-06-19
     */
    public void SetGameOnMapOneWithMoreCP(){
        List<User> users = new ArrayList<User>();
        users.add(new UserDTO("test1", "test1", ""));
        Set<User> usersSet = new HashSet<>(users);

        gameOnMapOneWithFourCP = new Game(1, usersSet, "MapOne", 0, 4, 1);

        game = gameOnMapOneWithFourCP;
        playerOne = game.getPlayers().get(0);
        robotOne = playerOne.getRobot();
    }

    /** execute the 5 chosen cards of the players and the board elements
     *
     * @author Jann Erik Bruns, Tommy Dang
     * @since 2023-06-19
     */
    public void calcGameRound(){
        try{
            boolean gameOver = false;
            for(int i = 0; i < game.getPlayers().size(); i++){
                game.register();
            }
            for(int i = 0; i < 5; i++){
                game.calcAllGameRound();
                game.increaseProgramStep();
                for (AbstractPlayer player : game.getPlayers()) {
                    if (player.getRobot().getLastCheckPoint() == game.getLastCheckPoint()) {
                        gameOver = true;
                    }
                }
                if(gameOver)
                    break;
            }

        }catch (Exception ex){
            System.out.println(ex);
        }
    }

    /** Test for moveForwardCard
     *
     * @author Jann Erik Bruns, Tommy Dang
     * @since 2023-06-19
     */
    @Test
    public void moveForward(){
        try {
            SetGameOnePlayer();
            // Create 5 Move1ForwardCards
            for(int i = 0; i < 5; i++)
                cards[i] = new Card(52, "6", 520);

            playerOne.chooseCardsOrder(cards);
            calcGameRound();
            assertEquals(robotOne.getPosition(), new Position(4, 8));

        } catch (Exception ex){
            System.out.println(ex);
        }
    }

    /** Test for moving backwards, u turn and left turn
     *
     * @author Tommy Dang
     * @since 2023-06-20
     */
    @Test
    public void moveBackwardAndTurnLeft(){
        try {
            SetGameOnePlayer();
            cards[0] = new Card(84, "5", 840);      // Move 3
            cards[1] = new Card(43, "8", 430);      // Back-Up
            cards[2] = new Card(43, "8", 430);      // Back-Up
            cards[3] = new Card(43, "8", 430);      // Back-Up
            cards[4] = new Card(7, "1", 190);       // Left Turn

            playerOne.chooseCardsOrder(cards);
            calcGameRound();

            assertEquals(robotOne.getPosition(), new Position(4, 11));
            assertEquals(robotOne.getDirection().ordinal(), 3);

        } catch (Exception ex){
            System.out.println(ex);
        }
    }
    /** Test for staying in a laser
     *
     * @author Jann Erik Bruns
     * @since 2023-06-20
     */
    @Test
    public void stayInLaser(){
        try {
            SetGameOnePlayer();

            robotOne.setCurrentPosition(new Position(3, 3));
            robotOne.setDirection(CardinalDirection.South);

            cards[0] = new Card(51, "6", 510);
            cards[1] = new Card(7, "1", 190);
            cards[2] = new Card(7, "1", 190);
            cards[3] = new Card(7, "1", 190);
            cards[4] = new Card(7, "1", 190);

            playerOne.chooseCardsOrder(cards);
            calcGameRound();

            assertEquals(robotOne.getPosition(), new Position(3, 4));
            assertEquals(robotOne.getDirection().ordinal(), 2);
            assertEquals(robotOne.getDamageToken(), 5);

        } catch (Exception ex){
            System.out.println(ex);
        }
    }


    /** Test for moving through laser and takes no damage
     *
     * @author Tommy Dang
     * @since 2023-06-20
     */
    @Test
    public void moveTroughLaser(){
        try {
            SetGameOnePlayer();

            robotOne.setCurrentPosition(new Position(3, 3));
            cards[0] = new Card(7, "1", 190);       // Left Turn
            cards[1] = new Card(7, "1", 190);       // Left Turn
            cards[2] = new Card(75, "7", 750);      // Forward 2
            cards[3] = new Card(51, "6", 510);      // Forward 1
            cards[4] = new Card(37, "4", 10);       // U Turn

            playerOne.chooseCardsOrder(cards);
            calcGameRound();

            assertEquals(robotOne.getDamageToken(), 0);
            assertEquals(robotOne.getPosition(), new Position(3, 6));
            assertEquals(robotOne.getDirection().ordinal(), 0);

        } catch (Exception ex){
            System.out.println(ex);
        }
    }
    /** Test for moving against wall
     *
     * @author Jann Erik Bruns
     * @since 2023-06-20
     */
    @Test
    public void moveAgainstWall(){
        try {
            SetGameOnePlayer();
            cards[0] = new Card(0, "5", 0);
            cards[1] = new Card(0, "5", 0);
            cards[2] = new Card(0, "5", 0);
            cards[3] = new Card(0, "5", 0);
            cards[4] = new Card(0, "5", 0);

            playerOne.chooseCardsOrder(cards);
            calcGameRound();

            assertEquals(robotOne.getPosition(), new Position(4, 8));
            assertEquals(robotOne.getDirection().ordinal(), 0);
            assertEquals(robotOne.getDamageToken(), 0);
            assertEquals(robotOne.isAlive(), true);

        } catch (Exception ex){
            System.out.println(ex);
        }
    }

    /** Test for moving into pit and lose 1 life token
     *
     * @author Tommy Dang
     * @since 2023-06-20
     */
    @Test
    public void movingIntoPit(){
        try {
            SetGameOnePlayer();
            robotOne.setCurrentPosition(new Position(4, 3));

            cards[0] = new Card(51, "6", 510);      // Forward 1
            cards[1] = new Card(7, "1", 190);       // Left Turn
            cards[2] = new Card(51, "6", 510);      // Forward 1
            cards[3] = new Card(51, "6", 510);      // Forward 1
            cards[4] = new Card(51, "6", 510);      // Forward 1

            playerOne.chooseCardsOrder(cards);
            calcGameRound();

            assertEquals(robotOne.getDamageToken(), 0);
            assertEquals(robotOne.getLifeToken(), 2);
            assertEquals(robotOne.getDirection().ordinal(), 3);
            assertEquals(robotOne.isAlive(), false);

            game.roundIsOver();
            assertEquals(robotOne.getPosition(), new Position(4, 11));
            assertEquals(robotOne.getDirection().ordinal(), 3);
            assertEquals(robotOne.isAlive(), true);
            assertEquals(robotOne.getLifeToken(), 2);

        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
    /** Test for moving against wall
     *
     * @author Jann Erik Bruns
     * @since 2023-06-20
     */
    @Test
    public void moveOutOfMap(){
        try {
            SetGameOnePlayer();
            //robotOne.setCurrentPosition(new Position(4,11));
            cards[0] = new Card(0, "6", 0);
            cards[1] = new Card(0, "3", 0);
            cards[2] = new Card(0, "6", 0);
            cards[3] = new Card(0, "3", 0);
            cards[4] = new Card(0, "5", 0);

            playerOne.chooseCardsOrder(cards);
            calcGameRound();

            assertEquals(robotOne.getDirection().ordinal(), 2);
            assertEquals(robotOne.getDamageToken(), 0);
            assertEquals(robotOne.getLifeToken(), 2);
            assertEquals(robotOne.isAlive(), false);
            game.roundIsOver();

            cards[0] = new Card(0, "4", 0);
            cards[1] = new Card(0, "6", 0);
            cards[2] = new Card(0, "6", 0);
            cards[3] = new Card(0, "6", 0);
            cards[4] = new Card(0, "6", 0);
            playerOne.chooseCardsOrder(cards);
            calcGameRound();
            assertEquals(robotOne.getPosition(), new Position(4, 8));
        } catch (Exception ex){
            System.out.println(ex);
        }
    }

    /** Test for getting moved by pusher
     *
     * @author Tommy Dang
     * @since 2023-06-20
     */
    @Test
    public void movedByPusher(){
        try {
            SetGameOnePlayer();

            robotOne.setCurrentPosition(new Position(9, 5));
            cards[0] = new Card(7, "1", 190);       // Left Turn
            cards[1] = new Card(7, "1", 190);       // Left Turn
            cards[2] = new Card(7, "1", 190);       // Left Turn
            cards[3] = new Card(7, "1", 190);       // Left Turn
            cards[4] = new Card(7, "1", 190);       // Left Turn

            playerOne.chooseCardsOrder(cards);
            calcGameRound();

            assertEquals(robotOne.getPosition(), new Position(10, 5));
            assertEquals(robotOne.getDirection().ordinal(), 3);

        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
    /** Test for roboters pushing each other
     *
     * @author Jann Erik Bruns
     * @since 2023-06-20
     */
    @Test
    public void roboterPushesRoboter(){
        try {
            SetGameTwoPlayers();

            robotOne.setCurrentPosition(new Position(5, 3));
            robotOne.setDirection(CardinalDirection.East);
            robotTwo.setCurrentPosition(new Position(6, 3));
            robotTwo.setDirection(CardinalDirection.East);
            Card[] cards1 = new Card[5];
            cards1[0] = new Card(0, "6", 10);
            cards1[1] = new Card(0, "6", 20);
            cards1[2] = new Card(0, "6", 30);
            cards1[3] = new Card(0, "6", 40);
            cards1[4] = new Card(0, "6", 50);

            playerOne.chooseCardsOrder(cards1);

            Card[] cards2 = new Card[5];
            cards2[0] = new Card(0, "8", 5);
            cards2[1] = new Card(0, "8", 15);
            cards2[2] = new Card(0, "8", 25);
            cards2[3] = new Card(0, "8", 35);
            cards2[4] = new Card(0, "6", 45);

            playerTwo.chooseCardsOrder(cards2);
            calcGameRound();

            assertEquals(robotOne.getPosition(), new Position(6, 3));
            assertEquals(robotTwo.getPosition(), new Position(8, 3));
            assertEquals(robotOne.getDirection().ordinal(), 1);
            assertEquals(robotTwo.getDirection().ordinal(), 1);

        } catch (Exception ex){
            System.out.println(ex);
        }
    }

    /** Test for getting turned by GearLeft
     *
     * @author Tommy Dang
     * @since 2023-06-20
     */
    @Test
    public void turnedByGearLeft(){
        try {
            SetGameOnePlayer();

            robotOne.setCurrentPosition(new Position(0, 1));
            cards[0] = new Card(7, "1", 190);       // Left Turn
            cards[1] = new Card(7, "1", 190);       // Left Turn
            cards[2] = new Card(7, "1", 190);       // Left Turn
            cards[3] = new Card(7, "1", 190);       // Left Turn
            cards[4] = new Card(7, "1", 190);       // Left Turn

            playerOne.chooseCardsOrder(cards);
            calcGameRound();

            assertEquals(robotOne.getPosition(), new Position(0, 1));
            assertEquals(robotOne.getDirection().ordinal(), 0);

        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    /** Test for getting turned by GearLeft
     *
     * @author Tommy Dang
     * @since 2023-06-20
     */
    @Test
    public void turnedByGearRight(){
        try {
            SetGameOnePlayer();

            robotOne.setCurrentPosition(new Position(1, 8));
            cards[0] = new Card(36, "3", 420);       // Right Turn
            cards[1] = new Card(36, "3", 420);       // Right Turn
            cards[2] = new Card(36, "3", 420);       // Right Turn
            cards[3] = new Card(36, "3", 420);       // Right Turn
            cards[4] = new Card(36, "3", 420);       // Right Turn

            playerOne.chooseCardsOrder(cards);
            calcGameRound();

            assertEquals(robotOne.getPosition(), new Position(1, 8));
            assertEquals(robotOne.getDirection().ordinal(), 0);

        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    /** Test for moving and turning in one round
     *
     * @author Jann Erik Bruns
     * @since 2023-06-20
     */
    @Test
    public void moveForwardAndTurn(){
        try {
            SetGameOnePlayer();
            for(int i = 0; i < 3; i++)
                cards[i] = new Card(52, "6", 520);
            cards[3] = new Card(20, "3", 420);
            cards[4] = new Card(52, "6", 520);

            playerOne.chooseCardsOrder(cards);
            calcGameRound();

            assertEquals(robotOne.getPosition(), new Position(5, 8));
            assertEquals(robotOne.getDirection().ordinal(), 1);

        } catch (Exception ex){
            System.out.println(ex);
        }
    }

    /** Test for stoying on Express conveyor belt
     *
     * @author Tommy Dang
     * @since 2023-06-20
     */
    @Test
    public void staysOnExpressConveyorBelt(){
        try {
            SetGameOnePlayer();
            robotOne.setCurrentPosition(new Position(1, 0));
            cards[0] = new Card(36, "3", 420);      // Right Turn
            cards[1] = new Card(36, "3", 420);      // Right Turn
            cards[2] = new Card(36, "3", 420);      // Right Turn
            cards[3] = new Card(7, "1", 190);       // Left Turn
            cards[4] = new Card(52, "6", 520);      // Forward move 1

            playerOne.chooseCardsOrder(cards);
            calcGameRound();

            assertEquals(robotOne.getPosition(), new Position(3, 1));
            assertEquals(robotOne.getDirection().ordinal(), 1);

        } catch (Exception ex){
            System.out.println(ex);
        }
    }

    /** Test for moving over Express conveyor belt
     *
     * @author Tommy Dang
     * @since 2023-06-20
     */
    @Test
    public void movingOverExpressConveyorBelt(){
        try {
            SetGameOnePlayer();
            robotOne.setCurrentPosition(new Position(2, 0));
            cards[0] = new Card(36, "3", 420);      // Right Turn
            cards[1] = new Card(75, "7", 750);      // Forward 2
            cards[2] = new Card(51, "6", 510);      // Forward 1
            cards[3] = new Card(75, "7", 750);      // Forward 2
            cards[4] = new Card(51, "6", 510);      // Forward 1

            playerOne.chooseCardsOrder(cards);
            calcGameRound();

            assertEquals(robotOne.getPosition(), new Position(8, 0));
            assertEquals(robotOne.getDirection().ordinal(), 1);

        } catch (Exception ex){
            System.out.println(ex);
        }
    }

    /** Test for moving over conveyorbelt
     *
     * @author Jann Erik Bruns
     * @since 2023-06-20
     */
    @Test
    public void movingOverConveyor(){
        try {
            SetGameOnePlayer();
            robotOne.setCurrentPosition(new Position(7, 11));
            for(int i = 0; i < 5; i++)
                cards[i] = new Card(0, "7", 0);

            playerOne.chooseCardsOrder(cards);
            calcGameRound();

            assertEquals(robotOne.getPosition(), new Position(7, 8));
            assertEquals(robotOne.getDirection().ordinal(), 0);

        } catch (Exception ex){
            System.out.println(ex);
        }
    }

    /** Test for on the next checkpoint
     *
     * @author Tommy Dang
     * @since 2023-06-20
     */
    @Test
    public void movingOnNextRightCheckPoint(){
        try {
            SetGameOnMapOneWithMoreCP();
            robotOne.setCurrentPosition(new Position(9, 4));
            cards[0] = new Card(51, "6", 510);      // Forward 1
            cards[1] = new Card(36, "3", 420);      // Right Turn
            cards[2] = new Card(36, "3", 420);      // Right Turn
            cards[3] = new Card(36, "3", 420);      // Right Turn
            cards[4] = new Card(36, "3", 420);      // Right Turn

            playerOne.chooseCardsOrder(cards);
            calcGameRound();

            assertEquals(robotOne.getPosition(), new Position(9, 3));
            assertEquals(robotOne.getDirection().ordinal(), 0);
            assertEquals(robotOne.getLastCheckPoint(), 2);

        } catch (Exception ex){
            System.out.println(ex);
        }
    }

    /** Test for on multiple checkpoints in one round
     *
     * @author Tommy Dang
     * @since 2023-06-20
     */
    @Test
    public void movingOnMultipleRightCheckPoint(){
        try {
            SetGameOnMapOneWithMoreCP();

            robotOne.setCurrentPosition(new Position(9, 4));
            cards[0] = new Card(51, "6", 510);      // Forward 1
            cards[1] = new Card(51, "6", 510);      // Forward 1
            cards[2] = new Card(51, "6", 510);      // Forward 1
            cards[3] = new Card(36, "3", 420);      // Right Turn
            cards[4] = new Card(36, "3", 420);      // Right Turn

            playerOne.chooseCardsOrder(cards);

            try{

                for(int i = 0; i < game.getPlayers().size(); i++){
                    game.register();
                }

                robotOne.setCurrentPosition(new Position(9, 4));
                game.calcAllGameRound();
                game.increaseProgramStep();


                robotOne.setCurrentPosition(new Position(2, 3));
                game.calcAllGameRound();
                game.increaseProgramStep();

                robotOne.setCurrentPosition(new Position(4, 6));
                game.calcAllGameRound();
                game.increaseProgramStep();

                game.calcAllGameRound();
                game.increaseProgramStep();

                game.calcAllGameRound();
                game.increaseProgramStep();


            }catch (Exception ex){
                System.out.println(ex);
            }

            assertEquals(robotOne.getPosition(), new Position(4, 5));
            assertEquals(robotOne.getDirection().ordinal(), 2);
            assertEquals(robotOne.getLastCheckPoint(), 4);


        } catch (Exception ex){
            System.out.println(ex);
        }
    }

    /** Test for moving over the next checkpoint
     *
     * @author Tommy Dang
     * @since 2023-06-20
     */
    @Test
    public void movingOverNextRightCheckPoint(){
        try {
            SetGameOnePlayer();
            robotOne.setCurrentPosition(new Position(9, 4));
            cards[0] = new Card(75, "7", 750);      // Forward 2
            cards[1] = new Card(36, "3", 420);      // Right Turn
            cards[2] = new Card(36, "3", 420);      // Right Turn
            cards[3] = new Card(36, "3", 420);      // Right Turn
            cards[4] = new Card(36, "3", 420);      // Right Turn

            playerOne.chooseCardsOrder(cards);
            calcGameRound();

            assertEquals(robotOne.getPosition(), new Position(9, 2));
            assertEquals(robotOne.getDirection().ordinal(), 0);
            assertEquals(robotOne.getLastCheckPoint(), 2);

        } catch (Exception ex){
            System.out.println(ex);
        }
    }


    /** Test for moving on conveyor belt
     *
     * @author Jann Erik Bruns
     * @since 2023-06-20
     */
    @Test
    public void movingOnConveyor(){
        try {
            SetGameOnePlayer();
            robotOne.setCurrentPosition(new Position(7, 11));
            for(int i = 0; i < 5; i++)
                cards[i] = new Card(0, "6", 0);

            playerOne.chooseCardsOrder(cards);
            calcGameRound();

            assertEquals(robotOne.getDirection().ordinal(), 0);
            assertEquals(robotOne.getLifeToken(), 2);
            assertEquals(robotOne.isAlive(), false);

            game.roundIsOver();
            assertEquals(robotOne.getPosition(), new Position(4, 11));

        } catch (Exception ex){
            System.out.println(ex);
        }
    }
    /** Test for moving on a wrong checkpoint
     *
     * @author Jann Erik Bruns
     * @since 2023-06-20
     */
    @Test
    public void movingOnWrongCheckpoint(){
        try {
            SetGameOnePlayer();
            robotOne.setCurrentPosition(new Position(3, 11));

            cards[0] = new Card(0, "3", 0);
            cards[1] = new Card(0, "6", 0);
            cards[2] = new Card(0, "7", 0);
            cards[3] = new Card(0, "6", 0);
            cards[4] = new Card(0, "6", 0);

            playerOne.chooseCardsOrder(cards);
            calcGameRound();
            assertEquals(robotOne.getPosition().x, 5);
            assertEquals(robotOne.getPosition().y, 10);
            assertEquals(robotOne.getDirection().ordinal(), 3);
            assertEquals(robotOne.getLifeToken(), 3);
            assertEquals(robotOne.isAlive(), true);
        } catch (Exception ex){
            System.out.println(ex);
        }
    }
    /** Test for moving on a wrong checkpoint
     *
     * @author Jann Erik Bruns
     * @since 2023-06-20
     */
    @Test
    public void movingOnLastCheckpoint(){
        try {
            SetGameOnePlayer();
            robotOne.setCurrentPosition(new Position(9, 4));

            for(int i = 0; i < 5; i++)
                cards[i] = new Card(0, "6", 0);

            game.getStartCheckpoint();
            playerOne.chooseCardsOrder(cards);
            calcGameRound();
            assertEquals(robotOne.getPosition().y, 3);
            assertEquals(robotOne.getPosition().x, 9);
            assertEquals(robotOne.getDirection().ordinal(), 0);
            assertEquals(robotOne.getLifeToken(), 3);
            assertEquals(robotOne.isAlive(), true);
            assertEquals(robotOne.getLastCheckPoint(), 2);
        } catch (Exception ex){
            System.out.println(ex);
        }
    }

    /** Test for checking player has Lost
     *
     * @author Tommy Dang
     * @since 2023-06-20
     */
    @Test
    public void checkingPlayerHasLost(){
        try {
            SetGameOnePlayer();

            for(int i = 1; i < 5; i++) {
                cards[0] = new Card(43, "8", 430);      // Back-Up
                cards[1] = new Card(43, "8", 430);      // Back-Up
                cards[2] = new Card(43, "8", 430);      // Back-Up
                cards[3] = new Card(43, "8", 430);      // Back-Up
                cards[4] = new Card(43, "8", 430);      // Back-Up

                playerOne.chooseCardsOrder(cards);
                calcGameRound();
                game.roundIsOver();
            }

            assertEquals(robotOne.getLifeToken(), 0);

        } catch (Exception ex){
            System.out.println(ex);
        }
    }

    /** Test for checking if player has more than nine damage token
     *
     * @author Tommy Dang
     * @since 2023-07-02
     */
    @Test
    public void playerGetsMoreThanNineDamageToken(){
        try {
            SetGameOnePlayer();
            robotOne.setCurrentPosition(new Position(3, 4));

            cards[0] = new Card(36, "3", 420);      // Right Turn
            cards[1] = new Card(36, "3", 420);      // Right Turn
            cards[2] = new Card(36, "3", 420);      // Right Turn
            cards[3] = new Card(36, "3", 420);      // Right Turn
            cards[4] = new Card(36, "3", 420);      // Right Turn

            playerOne.chooseCardsOrder(cards);
            calcGameRound();

            cards[0] = new Card(36, "3", 420);      // Right Turn
            cards[1] = new Card(36, "3", 420);      // Right Turn
            cards[2] = new Card(36, "3", 420);      // Right Turn
            cards[3] = new Card(36, "3", 420);      // Right Turn
            cards[4] = new Card(36, "3", 420);      // Right Turn

            playerOne.chooseCardsOrder(cards);
            calcGameRound();

            assertEquals(robotOne.getDamageToken(), 0);
            assertEquals(robotOne.getLifeToken(), 2);

        } catch (Exception ex){
            System.out.println(ex);
        }
    }
}

