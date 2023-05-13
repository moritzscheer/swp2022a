package de.uol.swp.server.utils;

import de.uol.swp.common.game.dto.RobotDTO;
import de.uol.swp.common.user.User;
import de.uol.swp.common.user.UserDTO;
import de.uol.swp.common.game.Position;
import de.uol.swp.server.gamelogic.Robot;

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
        RobotDTO robotDTO = new RobotDTO(robot.getImgPath());
        Position robotPosition = robot.getPosition();
        robotDTO.setPosX(robotPosition.x);
        robotDTO.setPoxY(robotPosition.y);
        return robotDTO;
    }
}
