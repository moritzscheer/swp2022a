package de.uol.swp.common.game;

import de.uol.swp.common.game.dto.CardDTO;
import de.uol.swp.common.game.dto.PlayerDTO;
import de.uol.swp.common.game.dto.RobotDTO;
import de.uol.swp.common.game.enums.CardinalDirection;
import de.uol.swp.common.user.UserDTO;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerDTOTest {

    private final Position position = new Position(1, 1);
    private final RobotDTO robotDTO = new RobotDTO(1,position, CardinalDirection.North);
    private final RobotDTO robotDTO2 = new RobotDTO(2,position, CardinalDirection.North);

    private final UserDTO userDTO = new UserDTO("Player1","pw","ml");


        @Test
        void constructorTest() {
            PlayerDTO playerDTO = new PlayerDTO(robotDTO, userDTO);

            assertNotNull(playerDTO);
            assertEquals(robotDTO, playerDTO.getRobotDTO());
            assertEquals(userDTO, playerDTO.getUser());
        }

        @Test
        void setRobotDTOTest() {
            PlayerDTO playerDTO = new PlayerDTO(robotDTO, userDTO);

            playerDTO.setRobotDTO(robotDTO2);

            assertEquals(robotDTO2, playerDTO.getRobotDTO());
        }

        @Test
        void setCurrentCardsTest() {
            List<CardDTO> cards = new ArrayList<>();
            cards.add(new CardDTO(1, 1));
            cards.add(new CardDTO(2, 2));


            PlayerDTO playerDTO = new PlayerDTO(robotDTO, userDTO);

            playerDTO.setCurrentCards(cards);

            assertEquals(cards, playerDTO.getCurrentCards());
        }

        @Test
        void getCurrentCardsTest() {
            List<CardDTO> cards = new ArrayList<>();
            cards.add(new CardDTO(1, 1));
            cards.add(new CardDTO(2, 2));


            PlayerDTO playerDTO = new PlayerDTO(robotDTO, userDTO);
            playerDTO.setCurrentCards(cards);

            assertEquals(cards, playerDTO.getCurrentCards());
        }

        @Test
        void getUserTest() {
            PlayerDTO playerDTO = new PlayerDTO(robotDTO, userDTO);

            assertEquals(userDTO, playerDTO.getUser());
        }

        @Test
        void setCurrentCardsNullTest() {
            PlayerDTO playerDTO = new PlayerDTO(robotDTO, userDTO);

            playerDTO.setCurrentCards(null);

            assertNull(playerDTO.getCurrentCards());
        }

        @Test
        void setRobotDTONullTest() {
            PlayerDTO playerDTO = new PlayerDTO(robotDTO, userDTO);

            playerDTO.setRobotDTO(null);

            assertNull(playerDTO.getRobotDTO());
        }

        @Test
        void setNullUserTest() {
            PlayerDTO playerDTO = new PlayerDTO(robotDTO, null);

            assertNull(playerDTO.getUser());
        }
    }
