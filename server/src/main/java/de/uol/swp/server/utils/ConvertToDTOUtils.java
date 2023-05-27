package de.uol.swp.server.utils;

import de.uol.swp.common.game.dto.CardDTO;
import de.uol.swp.common.game.dto.PlayerDTO;
import de.uol.swp.common.game.dto.RobotDTO;
import de.uol.swp.common.user.User;
import de.uol.swp.common.user.UserDTO;
import de.uol.swp.server.gamelogic.AbstractPlayer;
import de.uol.swp.server.gamelogic.Player;
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
        RobotDTO robotDTO = new RobotDTO(robot.getID(), robot.getPosition(), robot.getDirection());
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
    public static List<CardDTO> convertCardsToCardsDTO(Card[] cards){
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

    /** This function converts Card to CardDTO
     *
     * This is necessary to send a response with array of cardsDTO
     *
     * @param card card to be converted to DTO
     * @return List<CardDTO>
     * @author Maria Andrade
     * @since 2023-05-20
     */
    public static CardDTO convertCardToCardDTO(Card card){
        return new CardDTO(
                card.getId(),
                card.getPriority()
        );
    }


    /** This function converts Player to PlayerDTO
     *
     * This is necessary to send a response to move board elements
     *
     * @param player Player to be converted to DTO
     * @return List<PlayerDTO>
     * @author Maria Andrade
     * @since 2023-05-27
     */
    public static PlayerDTO convertPlayerToPlayerDTO(AbstractPlayer player){

        PlayerDTO playerDTO = new PlayerDTO(
                convertRobotToRobotDTO(player.getRobot())
        );
        if(player instanceof Player)
            playerDTO.setUser(((Player)player).getUser());

        return playerDTO;
    }

    public static List<PlayerDTO> convertPlayerListToPlayerDTOList(List<AbstractPlayer> players){

        List<PlayerDTO> playersDTO = new ArrayList<>();
        for (AbstractPlayer player: players){
            playersDTO.add(convertPlayerToPlayerDTO(player));
        }

        return playersDTO;
    }

}
