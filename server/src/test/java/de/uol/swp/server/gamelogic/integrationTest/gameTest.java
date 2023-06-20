package de.uol.swp.server.gamelogic.integrationTest;

import com.google.common.eventbus.EventBus;
import de.uol.swp.common.game.Position;
import de.uol.swp.common.game.dto.CardDTO;
import de.uol.swp.common.game.enums.CardinalDirection;
import de.uol.swp.common.user.request.LoginRequest;
import de.uol.swp.server.gamelogic.AbstractPlayer;
import de.uol.swp.server.gamelogic.Block;
import de.uol.swp.common.user.User;
import de.uol.swp.common.user.UserDTO;
import de.uol.swp.server.gamelogic.Game;
import de.uol.swp.server.gamelogic.Robot;
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
    private AbstractPlayer player;
    private Robot robot;
    /** Setup for gamelogic integration tests
     *
     * @author Jann Erik Bruns, Tommy Dang
     * @since 2023-06-19
     */
    @Before
    public void setup(){
        User user = new UserDTO("test1", "test1", "");
        List<User> users = new ArrayList<User>();

        users.add(user);
        Set<User> usersSet = new HashSet<>(users);

        game = new Game(1,  usersSet, "MapOne", 0, 3);
        Position firstCP = game.getStartCheckpoint();
        while(firstCP.x != 4 && firstCP.y != 3){
            game = new Game(1,  usersSet, "MapOne", 0, 3);
            firstCP = game.getStartCheckpoint();
        }
        player = game.getPlayers().get(0);
        robot = player.getRobot();
    }
    /** execute the 5 chosen cards of the players
     *
     * @author Jann Erik Bruns, Tommy Dang
     * @since 2023-06-19
     */
    public void executeCards(){
        try{
            game.register();
                for(int i = 0; i < 5; i++){
                    game.calcGameRoundCards();
                }
        }catch (Exception ex){
            System.out.println(ex);
        }
    }


    /** Test for move1ForwardCard
     *
     * @author Jann Erik Bruns, Tommy Dang
     * @since 2023-06-19
     */
    @Test
    public void moveForward1Test() throws InterruptedException {
        try {
            // Create 5 Move1ForwardCards
            Card[] cards= new Card[5];
            for(int i = 0; i < 5; i++)
                cards[i] = new Card(52, "6", 520, "");

            player.chooseCardsOrder(cards);
            executeCards();
            assertEquals(robot.getPosition(), new Position(4, 0));

        } catch (Exception ex){
            System.out.println(ex);
        }
    }
    /** Test for moving and turning in one round
     *
     * @author Jann Erik Bruns
     * @since 2023-06-20
     */
    @Test
    public void moveForwardAndTurnTest() throws InterruptedException {
        try {
            Card[] cards= new Card[5];
            for(int i = 0; i < 3; i++)
                cards[i] = new Card(52, "6", 520, "");
            cards[3] = new Card(20, "3", 100, "");
            cards[4] = new Card(52, "6", 520, "");

            player.chooseCardsOrder(cards);
            executeCards();
            assertEquals(robot.getPosition(), new Position(5, 0));
            assertEquals(robot.getDirection().ordinal(), 1);

        } catch (Exception ex){
            System.out.println(ex);
        }
    }
}

