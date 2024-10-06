package de.uol.swp.common.game;

import static org.junit.jupiter.api.Assertions.*;

import de.uol.swp.common.game.dto.CardDTO;
import de.uol.swp.common.game.dto.PlayerDTO;
import de.uol.swp.common.game.dto.RobotDTO;
import de.uol.swp.common.game.enums.CardinalDirection;
import de.uol.swp.common.user.UserDTO;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class PlayerDTOTest {

    private final Position position = new Position(1, 1);
    private final RobotDTO robotDTO = new RobotDTO(1, position, CardinalDirection.North);
    private final RobotDTO robotDTO2 = new RobotDTO(2, position, CardinalDirection.North);

    private final UserDTO userDTO = new UserDTO("Player1", "pw", "ml");

    /**
     * Tests the constructor
     *
     * @author WKempel
     * @result The constructor should return the correct robotDTO and userDTO
     * @see de.uol.swp.common.game.dto.PlayerDTO
     * @since 2023-06-13
     */
    @Test
    public void constructorTest() {
        PlayerDTO playerDTO = new PlayerDTO(robotDTO, userDTO);

        assertNotNull(playerDTO);
        assertEquals(robotDTO, playerDTO.getRobotDTO());
        assertEquals(userDTO, playerDTO.getUser());
    }

    /**
     * Tests the setRobotDTO method
     *
     * @author WKempel
     * @result The method should set the correct robotDTO
     * @see de.uol.swp.common.game.dto.PlayerDTO
     * @since 2023-06-13
     */
    @Test
    public void setRobotDTOTest() {
        PlayerDTO playerDTO = new PlayerDTO(robotDTO, userDTO);

        playerDTO.setRobotDTO(robotDTO2);

        assertEquals(robotDTO2, playerDTO.getRobotDTO());
    }

    /**
     * Tests the setCurrentCards method
     *
     * @author WKempel
     * @result The method should set the correct cards
     * @see de.uol.swp.common.game.dto.PlayerDTO
     * @since 2023-06-13
     */
    @Test
    public void setCurrentCardsTest() {
        List<CardDTO> cards = new ArrayList<>();
        cards.add(new CardDTO(1, 1));
        cards.add(new CardDTO(2, 2));

        PlayerDTO playerDTO = new PlayerDTO(robotDTO, userDTO);

        playerDTO.setCurrentCards(cards);

        assertEquals(cards, playerDTO.getCurrentCards());
    }

    /**
     * Tests the getCurrentCards method
     *
     * @author WKempel
     * @result The method should return the correct cards
     * @see de.uol.swp.common.game.dto.PlayerDTO
     * @since 2023-06-13
     */
    @Test
    public void getCurrentCardsTest() {
        List<CardDTO> cards = new ArrayList<>();
        cards.add(new CardDTO(1, 1));
        cards.add(new CardDTO(2, 2));

        PlayerDTO playerDTO = new PlayerDTO(robotDTO, userDTO);
        playerDTO.setCurrentCards(cards);

        assertEquals(cards, playerDTO.getCurrentCards());
    }

    /**
     * Tests getUser method
     *
     * @author WKempel
     * @result The method should return the correct user
     * @see de.uol.swp.common.game.dto.PlayerDTO
     * @since 2023-06-13
     */
    @Test
    public void getUserTest() {
        PlayerDTO playerDTO = new PlayerDTO(robotDTO, userDTO);

        assertEquals(userDTO, playerDTO.getUser());
    }

    /**
     * Tests the setCurrentCards method with null
     *
     * @author WKempel
     * @result The method should set the currentCards to null
     * @see de.uol.swp.common.game.dto.PlayerDTO
     * @since 2023-06-13
     */
    @Test
    public void setCurrentCardsNullTest() {
        PlayerDTO playerDTO = new PlayerDTO(robotDTO, userDTO);

        playerDTO.setCurrentCards(null);

        assertNull(playerDTO.getCurrentCards());
    }

    /**
     * Tests the setRobotDTO method with null
     *
     * @author WKempel
     * @result The method should set the robotDTO to null
     * @see de.uol.swp.common.game.dto.PlayerDTO
     * @since 2023-06-13
     */
    @Test
    public void setRobotDTONullTest() {
        PlayerDTO playerDTO = new PlayerDTO(robotDTO, userDTO);

        playerDTO.setRobotDTO(null);

        assertNull(playerDTO.getRobotDTO());
    }

    /**
     * Tests the setUser method with null
     *
     * @author WKempel
     * @result The method should set the user to null
     * @see de.uol.swp.common.game.dto.PlayerDTO
     * @since 2023-06-13
     */
    @Test
    public void setNullUserTest() {
        PlayerDTO playerDTO = new PlayerDTO(robotDTO, null);

        assertNull(playerDTO.getUser());
    }
}
