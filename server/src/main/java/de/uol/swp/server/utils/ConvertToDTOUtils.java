package de.uol.swp.server.utils;

import de.uol.swp.common.game.dto.CardDTO;
import de.uol.swp.common.game.dto.RobotDTO;
import de.uol.swp.common.user.User;
import de.uol.swp.common.user.UserDTO;
import de.uol.swp.server.gamelogic.Robot;
import de.uol.swp.server.gamelogic.cards.Card;

import java.util.ArrayList;
import java.util.List;

/** This is a utility class
 *
 * The purpose of this class is to contain functions
 * to help converting objects in the server into DTOs
 *
 * @author Maria Andrade
 * @since 2023-05-13
 */
public final class ConvertToDTOUtils {


    /** This function converts User to UserDTO
     *
     * This is necessary because the lobbyDTO saves a Set<User>
     *
     * @param user user to be converted to DTO
     * @return userDTO
     * @author Maria Andrade
     * @since 2023-05-13
     */
    public static UserDTO convertUserToUserDTO(User user){
        UserDTO userDTO = new UserDTO(user.getUsername(), user.getPassword(), user.getEMail());
        return userDTO;
    }

    /** This function converts Robot to RobotDTO
     *
     *
     * @param robot robot to be converted to DTO
     * @return robotDTO
     * @author Maria Andrade
     * @since 2023-05-13
     */
    public static RobotDTO convertRobotToRobotDTO(Robot robot){
        RobotDTO robotDTO = new RobotDTO(robot.getImgPath(), robot.getPosition(), robot.getDirection());
        robotDTO.setLifeToken(robot.getLifeToken());
        robotDTO.setDamageToken(robot.getDamageToken());
        robotDTO.setLastCheckpoint(robot.getLastCheckPoint());
        return robotDTO;
    }

    /** This function converts Card[] to CardDTO[]
     *
     * This is necessary to send a response with array of cardsDTO
     *
     * @param cards cards to be converted to DTO
     * @return List<CardDTO>
     * @author Maria Andrade
     * @since 2023-05-13
     */
    public static List<CardDTO> convertCardToCardDTO(Card[] cards){
        List<CardDTO> cardDTOS = new ArrayList<>();
        for(Card card: cards){
            cardDTOS.add(
                    new CardDTO(
                            card.getId(),
                            card.getPriority()
                    )
            );
        }

        return cardDTOS;
    }

}
