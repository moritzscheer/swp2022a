package de.uol.swp.common.game;

import static org.junit.jupiter.api.Assertions.*;

import de.uol.swp.common.game.dto.RobotDTO;
import de.uol.swp.common.game.enums.CardinalDirection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RobotDTOTest {

    @Test
    void constructorTest() {
        int robotID = 1;
        Position position = new Position(0, 0);
        CardinalDirection direction = CardinalDirection.North;

        RobotDTO robotDTO = new RobotDTO(robotID, position, direction);

        assertNotNull(robotDTO);
        assertEquals(robotID, robotDTO.getRobotID());
        assertEquals(position, robotDTO.getPosition());
        assertEquals(direction, robotDTO.getDirection());
    }

    @Test
    void setRobotIDTest() {
        int robotID1 = 1;
        int robotID2 = 2;
        Position position = new Position(0, 0);
        CardinalDirection direction = CardinalDirection.North;

        RobotDTO robotDTO = new RobotDTO(robotID1, position, direction);

        robotDTO.setRobotID(robotID2);

        assertEquals(robotID2, robotDTO.getRobotID());
    }

    @Test
    void setPositionTest() {
        int robotID = 1;
        Position position1 = new Position(0, 0);
        Position position2 = new Position(1, 1);
        CardinalDirection direction = CardinalDirection.North;

        RobotDTO robotDTO = new RobotDTO(robotID, position1, direction);

        robotDTO.setPosition(position2);

        assertEquals(position2, robotDTO.getPosition());
    }

    @Test
    void setDirectionTest() {
        int robotID = 1;
        Position position = new Position(0, 0);
        CardinalDirection direction1 = CardinalDirection.North;
        CardinalDirection direction2 = CardinalDirection.East;

        RobotDTO robotDTO = new RobotDTO(robotID, position, direction1);

        robotDTO.setDirection(direction2);

        assertEquals(direction2, robotDTO.getDirection());
    }

    @Test
    void setLifeTokenTest() {
        int robotID = 1;
        Position position = new Position(0, 0);
        CardinalDirection direction = CardinalDirection.North;
        int lifeToken = 3;

        RobotDTO robotDTO = new RobotDTO(robotID, position, direction);

        robotDTO.setLifeToken(lifeToken);

        assertEquals(lifeToken, robotDTO.getLifeToken());
    }

    @Test
    void setDamageTokenTest() {
        int robotID = 1;
        Position position = new Position(0, 0);
        CardinalDirection direction = CardinalDirection.North;
        int damageToken = 2;

        RobotDTO robotDTO = new RobotDTO(robotID, position, direction);

        robotDTO.setDamageToken(damageToken);

        assertEquals(damageToken, robotDTO.getDamageToken());
    }

    @Test
    void setLastCheckpointTest() {
        int robotID = 1;
        Position position = new Position(0, 0);
        CardinalDirection direction = CardinalDirection.North;
        int lastCheckpoint = 5;

        RobotDTO robotDTO = new RobotDTO(robotID, position, direction);

        robotDTO.setLastCheckpoint(lastCheckpoint);

        assertEquals(lastCheckpoint, robotDTO.getLastCheckpoint());
    }

    @Test
    void setAliveTest() {
        int robotID = 1;
        Position position = new Position(0, 0);
        CardinalDirection direction = CardinalDirection.North;
        boolean alive = false;

        RobotDTO robotDTO = new RobotDTO(robotID, position, direction);

        robotDTO.setAlive(alive);

        assertFalse(robotDTO.isAlive());
    }

    @Test
    public void setAlive_ValidValue_UpdateAliveStatus() {
        RobotDTO robotDTO = new RobotDTO(1, new Position(0, 0), CardinalDirection.North);

        robotDTO.setAlive(false);

        Assertions.assertFalse(robotDTO.isAlive());
    }

    @Test
    public void setLifeToken_ValidValue_UpdateLifeToken() {
        RobotDTO robotDTO = new RobotDTO(1, new Position(0, 0), CardinalDirection.North);

        robotDTO.setLifeToken(1);
        robotDTO.setLifeToken(3);
        Assertions.assertEquals(3, robotDTO.getLifeToken());
    }

    @Test
    public void setLifeToken_NegativeValue_ThrowException() {
        RobotDTO robotDTO = new RobotDTO(1, new Position(0, 0), CardinalDirection.North);

        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> {
                    robotDTO.setLifeToken(-1);
                });
    }

    @Test
    public void setDamageToken_ValidValue_UpdateDamageToken() {
        RobotDTO robotDTO = new RobotDTO(1, new Position(0, 0), CardinalDirection.North);

        robotDTO.setDamageToken(2);
        robotDTO.setDamageToken(3);
        Assertions.assertEquals(3, robotDTO.getDamageToken());
    }

    @Test
    public void setDamageToken_NegativeValue_ThrowException() {
        RobotDTO robotDTO = new RobotDTO(1, new Position(0, 0), CardinalDirection.North);

        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> {
                    robotDTO.setDamageToken(-1);
                });
    }
}
