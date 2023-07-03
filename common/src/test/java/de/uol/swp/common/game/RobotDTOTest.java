package de.uol.swp.common.game;

import static org.junit.jupiter.api.Assertions.*;

import de.uol.swp.common.game.dto.RobotDTO;
import de.uol.swp.common.game.enums.CardinalDirection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RobotDTOTest {

    /**
     * Tests the constructor
     *
     * @author WKempel
     * @result The constructor should return the correct values like robotID, position and direction
     * @see de.uol.swp.common.game.dto.RobotDTO
     * @see de.uol.swp.common.game.enums.CardinalDirection
     * @see de.uol.swp.common.game.Position
     * @since 2023-06-13
     */
    @Test
    public void constructorTest() {
        int robotID = 1;
        Position position = new Position(0, 0);
        CardinalDirection direction = CardinalDirection.North;

        RobotDTO robotDTO = new RobotDTO(robotID, position, direction);

        assertNotNull(robotDTO);
        assertEquals(robotID, robotDTO.getRobotID());
        assertEquals(position, robotDTO.getPosition());
        assertEquals(direction, robotDTO.getDirection());
    }

    /**
     * Tests the setRobotID method
     *
     * @author WKempel
     * @result The method should set the correct robotID
     * @see de.uol.swp.common.game.dto.RobotDTO
     * @see de.uol.swp.common.game.enums.CardinalDirection
     * @see de.uol.swp.common.game.Position
     * @since 2023-06-13
     */
    @Test
    public void setRobotIDTest() {
        int robotID1 = 1;
        int robotID2 = 2;
        Position position = new Position(0, 0);
        CardinalDirection direction = CardinalDirection.North;

        RobotDTO robotDTO = new RobotDTO(robotID1, position, direction);

        robotDTO.setRobotID(robotID2);

        assertEquals(robotID2, robotDTO.getRobotID());
    }

    /**
     * Tests the setPosition method
     *
     * @author WKempel
     * @result The method should set the correct position
     * @see de.uol.swp.common.game.dto.RobotDTO
     * @see de.uol.swp.common.game.enums.CardinalDirection
     * @see de.uol.swp.common.game.Position
     * @since 2023-06-13
     */
    @Test
    public void setPositionTest() {
        int robotID = 1;
        Position position1 = new Position(0, 0);
        Position position2 = new Position(1, 1);
        CardinalDirection direction = CardinalDirection.North;

        RobotDTO robotDTO = new RobotDTO(robotID, position1, direction);

        robotDTO.setPosition(position2);

        assertEquals(position2, robotDTO.getPosition());
    }

    /**
     * Tests the setDirection method
     *
     * @author WKempel
     * @result The method should set the correct direction
     * @see de.uol.swp.common.game.dto.RobotDTO
     * @see de.uol.swp.common.game.enums.CardinalDirection
     * @see de.uol.swp.common.game.Position
     * @since 2023-06-13
     */
    @Test
    public void setDirectionTest() {
        int robotID = 1;
        Position position = new Position(0, 0);
        CardinalDirection direction1 = CardinalDirection.North;
        CardinalDirection direction2 = CardinalDirection.East;

        RobotDTO robotDTO = new RobotDTO(robotID, position, direction1);

        robotDTO.setDirection(direction2);

        assertEquals(direction2, robotDTO.getDirection());
    }

    /**
     * Tests the setLifeToken method
     *
     * @author WKempel
     * @result The method should set the correct lifeToken
     * @see de.uol.swp.common.game.dto.RobotDTO
     * @see de.uol.swp.common.game.enums.CardinalDirection
     * @see de.uol.swp.common.game.Position
     * @since 2023-06-13
     */
    @Test
    public void setLifeTokenTest() {
        int robotID = 1;
        Position position = new Position(0, 0);
        CardinalDirection direction = CardinalDirection.North;
        int lifeToken = 3;

        RobotDTO robotDTO = new RobotDTO(robotID, position, direction);

        robotDTO.setLifeToken(lifeToken);

        assertEquals(lifeToken, robotDTO.getLifeToken());
    }

    /**
     * Tests the setDamageToken method
     *
     * @author WKempel
     * @result The method should set the correct damageToken
     * @see de.uol.swp.common.game.dto.RobotDTO
     * @see de.uol.swp.common.game.enums.CardinalDirection
     * @see de.uol.swp.common.game.Position
     * @since 2023-06-13
     */
    @Test
    public void setDamageTokenTest() {
        int robotID = 1;
        Position position = new Position(0, 0);
        CardinalDirection direction = CardinalDirection.North;
        int damageToken = 2;

        RobotDTO robotDTO = new RobotDTO(robotID, position, direction);

        robotDTO.setDamageToken(damageToken);

        assertEquals(damageToken, robotDTO.getDamageToken());
    }

    /**
     * Tests the setLastCheckpoint method
     *
     * @author WKempel
     * @result The method should set the correct lastCheckpoint
     * @see de.uol.swp.common.game.dto.RobotDTO
     * @see de.uol.swp.common.game.enums.CardinalDirection
     * @see de.uol.swp.common.game.Position
     * @since 2023-06-13
     */
    @Test
    public void setLastCheckpointTest() {
        int robotID = 1;
        Position position = new Position(0, 0);
        CardinalDirection direction = CardinalDirection.North;
        int lastCheckpoint = 5;

        RobotDTO robotDTO = new RobotDTO(robotID, position, direction);

        robotDTO.setLastCheckpoint(lastCheckpoint);

        assertEquals(lastCheckpoint, robotDTO.getLastCheckpoint());
    }

    /**
     * Tests the setAlive method
     *
     * @author WKempel
     * @result The method should set the correct alive status
     * @see de.uol.swp.common.game.dto.RobotDTO
     * @see de.uol.swp.common.game.enums.CardinalDirection
     * @see de.uol.swp.common.game.Position
     * @since 2023-06-13
     */
    @Test
    public void setAliveTest() {
        int robotID = 1;
        Position position = new Position(0, 0);
        CardinalDirection direction = CardinalDirection.North;
        boolean alive = false;

        RobotDTO robotDTO = new RobotDTO(robotID, position, direction);

        robotDTO.setAlive(alive);

        assertFalse(robotDTO.isAlive());
    }

    /**
     * Tests the setAlive method with a valid value
     *
     * @author WKempel
     * @result The method should set the correct alive status and update it
     * @see de.uol.swp.common.game.dto.RobotDTO
     * @see de.uol.swp.common.game.enums.CardinalDirection
     * @since 2023-06-13
     */
    @Test
    public void testSetAliveValidValueUpdateAliveStatus() {
        RobotDTO robotDTO = new RobotDTO(1, new Position(0, 0), CardinalDirection.North);

        robotDTO.setAlive(false);

        Assertions.assertFalse(robotDTO.isAlive());
    }

    /**
     * Tests the setAliveToken method with a valid value
     *
     * @author WKempel
     * @result The method should set the correct live token and update it
     * @see de.uol.swp.common.game.dto.RobotDTO
     * @see de.uol.swp.common.game.enums.CardinalDirection
     * @since 2023-06-13
     */
    @Test
    public void testSetLifeTokenValidValueUpdateLifeToken() {
        RobotDTO robotDTO = new RobotDTO(1, new Position(0, 0), CardinalDirection.North);

        robotDTO.setLifeToken(1);
        robotDTO.setLifeToken(3);
        Assertions.assertEquals(3, robotDTO.getLifeToken());
    }

    /**
     * Tests the setAliveToken method with a negative value
     *
     * @throws IllegalArgumentException
     * @see de.uol.swp.common.game.dto.RobotDTO
     * @see de.uol.swp.common.game.enums.CardinalDirection
     * @since 2023-06-13
     */
    @Test
    public void testSetLifeTokenNegativeValueThrowException() {
        RobotDTO robotDTO = new RobotDTO(1, new Position(0, 0), CardinalDirection.North);

        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> {
                    robotDTO.setLifeToken(-1);
                });
    }

    /**
     * Tests the setDamageToken method with a valid value
     *
     * @author WKempel
     * @result The method should set the correct damage token and update it
     * @see de.uol.swp.common.game.dto.RobotDTO
     * @see de.uol.swp.common.game.enums.CardinalDirection
     * @since 2023-06-13
     */
    @Test
    public void testSetDamageTokenValidValueUpdateDamageToken() {
        RobotDTO robotDTO = new RobotDTO(1, new Position(0, 0), CardinalDirection.North);

        robotDTO.setDamageToken(2);
        robotDTO.setDamageToken(3);
        Assertions.assertEquals(3, robotDTO.getDamageToken());
    }

    /**
     * Tests the setDamageToken method with a negative value
     *
     * @author WKempel
     * @throws IllegalArgumentException
     * @result The method should throw an IllegalArgumentException
     * @see de.uol.swp.common.game.dto.RobotDTO
     * @see de.uol.swp.common.game.enums.CardinalDirection
     * @since 2023-06-13
     */
    @Test
    public void testSetDamageTokenNegativeValueThrowException() {
        RobotDTO robotDTO = new RobotDTO(1, new Position(0, 0), CardinalDirection.North);

        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> {
                    robotDTO.setDamageToken(-1);
                });
    }
}
