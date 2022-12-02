package de.uol.swp.server.usermanagement.store;

import com.google.common.base.Strings;
import de.uol.swp.common.user.User;
import de.uol.swp.common.user.UserDTO;
import de.uol.swp.server.database.SQLHelper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * This is a user store.
 *
 * This is the user store that is used for the start of the software project. The
 * user accounts in this user store only reside within the RAM of your computer
 * and only for as long as the server is running. Therefore the users have to be
 * added every time the server is started.
 *
 * @implNote This store will never return the password of a user!
 * @see AbstractUserStore
 * @see UserStore
 * @author Marco Grawunder
 * @since 2019-08-05
 */

public class DatabaseBasedUserStore extends AbstractUserStore implements UserStore {

    private final Map<String, User> users = new HashMap<>();

    @Override
    public Optional<User> findUser(String username, String password){
        UserDTO usr = null;
        try {
            ResultSet result = SQLHelper.Select(
                    String.format("SELECT * FROM user where display_name = '%s'", username));
            result.next();
            usr = new UserDTO(
                    result.getString("display_name"),
                    result.getString("password"),
                    result.getString("email"));

        }catch (SQLException e){
            //TO IMPLEMENT
        }


        if (usr != null && Objects.equals(usr.getPassword(),hash(password))) {
            return Optional.of(usr.getWithoutPassword());
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> findUser(String username) {
        UserDTO usr = null;
        try{
            ResultSet result = SQLHelper.Select(String.format("SELECT * FROM user where display_name = '%s'", username));
            result.next();
            usr = new UserDTO(result.getString("display_name"), result.getString("password"), result.getString("email"));
        }catch(SQLException e){
            return Optional.empty();
        }
        if (usr != null) {
            return Optional.of(usr.getWithoutPassword());
        }
        return Optional.empty();
    }

    @Override
    public User createUser(String username, String password, String eMail) {
        if (Strings.isNullOrEmpty(username)){
            throw new IllegalArgumentException("Username must not be null");
        }
        Optional<User> user = null;

        String passwordHash = hash(password);
        int rowsAffected = 0;
        try {
            rowsAffected = SQLHelper.Update(String.format("INSERT INTO user nickname, password, email values '%s', '%s', '%s'", username, passwordHash, eMail));
            user = findUser(username);
        }catch (Exception e) {
            return null;
        }
        return rowsAffected == 1 ? new UserDTO(user.get().getUsername(), passwordHash, eMail) : null;
    }

    @Override
    public User updateUser(String username, String password, String eMail) {
        String passwordHash = hash(password);
        int rowsAffected = 0;
        try{
            rowsAffected = SQLHelper.Update(String.format("UPDATE user SET password = '%s', email = '%s' WHERE display_name = '%s'", passwordHash, eMail, username));
        }catch (Exception e){
            return null;
        }

        return rowsAffected == 1 ? new UserDTO(username, passwordHash, eMail) : null;
    }

    @Override
    public void removeUser(String username){
        try{
            SQLHelper.Update(String.format("DELETE FROM user WHERE display_name = '%s'", username));
        }catch(Exception e){

        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> retUsers = new ArrayList<>();
        try{
            ResultSet result = SQLHelper.Select("SELECT * FROM user");

            while(result.next()){
                retUsers.add(new UserDTO(result.getString("display_name"), result.getString("password"), result.getString("email")));
            }
        }catch (Exception e){
            return null;
        }
        return retUsers;
    }

}
