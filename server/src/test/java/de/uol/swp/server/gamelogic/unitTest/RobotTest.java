package de.uol.swp.server.gamelogic.unitTest;

import de.uol.swp.common.game.Position;
import de.uol.swp.common.game.enums.CardinalDirection;
import de.uol.swp.server.gamelogic.Robot;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RobotTest {

    private Robot robot;
    private int id = 123;
    private Position currentPosition =  new Position(0, 0);
    private CardinalDirection direction = CardinalDirection.North;

    @BeforeEach
    public void setUp() {
        robot = new Robot(id, currentPosition, direction);
    }

    @Test
    public void testSetLifeToken() {
        int lifeToken = 2;
        robot.setLifeToken(lifeToken);
        Assertions.assertEquals(lifeToken, robot.getLifeToken());
    }

    @Test
    public void testSetDamageToken() {
        int damageToken = 5;
        robot.setDamageToken(damageToken);
        Assertions.assertEquals(damageToken, robot.getDamageToken());
    }

    // That test makes no scenes because the method was not implemented
    @Test
    public void testMove() {
        Position targetPosition = new Position(0, 0);
        robot.move(targetPosition);
        Assertions.assertEquals(targetPosition, robot.getPosition());
    }

    @Test
    public void testSetAlive() {
        robot.setLifeToken(2);
        robot.setAlive(true);
        Assertions.assertTrue(robot.isAlive());

        robot.setLifeToken(1);
        robot.setAlive(true);
        Assertions.assertTrue(robot.isAlive());

        robot.setLifeToken(0);
        robot.setAlive(false);
        Assertions.assertFalse(robot.isAlive());
        Assertions.assertTrue(robot.isDeadForever());
        Assertions.assertTrue(robot.isDeadForTheRound());
    }

    @Test
    public void testFixDamageToken() {
        int initialDamageToken = 3;
        robot.setDamageToken(initialDamageToken);
        robot.fixDamageToken();
        Assertions.assertEquals(initialDamageToken - 1, robot.getDamageToken());

        robot.setDamageToken(0);
        robot.fixDamageToken();
        Assertions.assertEquals(0, robot.getDamageToken());
    }

    @Test
    public void testFireLaser() {
        // TODO: Implement this test
    }

    @Test
    public void testGetPosition() {
        Position position = robot.getPosition();
        Assertions.assertEquals(currentPosition, position);
    }

    @Test
    public void testGetID() {
        int robotID = robot.getID();
        Assertions.assertEquals(id, robotID);
    }

    @Test
    public void testSetDirection() {
        CardinalDirection newDirection = CardinalDirection.South;
        robot.setDirection(newDirection);
        Assertions.assertEquals(newDirection, robot.getDirection());
    }

    @Test
    public void testSetLastCheckPoint() {
        int checkPointNumber = 2;
        robot.setLastCheckPoint(checkPointNumber);
        Assertions.assertEquals(checkPointNumber, robot.getLastCheckPoint());
    }

    @Test
    public void testSetLastBackupCopyPosition() {
        Position backupPosition = new Position(1, 1);
        robot.setLastBackupCopyPosition(backupPosition);
        Assertions.assertEquals(backupPosition, robot.getLastBackupCopyPosition());
    }

    @Test
    public void testSetDamageByReenteringRace() {
        int initialDamageToken = robot.getDamageToken();
        robot.setDamageByReenteringRace();
        Assertions.assertEquals(initialDamageToken + 2, robot.getDamageToken());
    }

    @Test
    public void testGetOptionCard() {
        int optionCard = robot.getOptionCard();
        Assertions.assertEquals(0, optionCard);
    }

    @Test
    public void testIsPowerDown() {
        boolean powerDown = robot.isPowerDown();
        Assertions.assertFalse(powerDown);
    }

    @Test
    public void testSetPowerDown() {
        boolean powerDown = true;
        robot.setPowerDown(powerDown);
        Assertions.assertTrue(robot.isPowerDown());
    }

    @Test
    public void testIsDeadForTheRound() {
        boolean deadForTheRound = robot.isDeadForTheRound();
        Assertions.assertFalse(deadForTheRound);
    }

    @Test
    public void testIsDeadForever() {
        boolean deadForever = robot.isDeadForever();
        Assertions.assertFalse(deadForever);
    }
}
