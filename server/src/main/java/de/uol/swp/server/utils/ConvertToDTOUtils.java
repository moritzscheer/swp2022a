package de.uol.swp.server.utils;

import de.uol.swp.common.user.User;
import de.uol.swp.common.user.UserDTO;

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
     * @return
     */
    public static UserDTO convertUserToUserDTO(User user){
        UserDTO userDTO = new UserDTO(user.getUsername(), user.getPassword(), user.getEMail());
        return userDTO;
    }
}
