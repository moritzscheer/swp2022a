package de.uol.swp.server.usermanagement.store;

import com.google.common.base.Strings;

import de.uol.swp.common.user.User;
import de.uol.swp.common.user.UserDTO;
import de.uol.swp.server.database.SQLHelper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * This is a user store.
 *
 * <p>This user store is based on a database.
 *
 * @implNote This store will never return the password of a user!
 * @see AbstractUserStore
 * @see UserStore
 * @author Marco Grawunder & Jann Bruns
 * @since 2022-12-02
 */
public class DatabaseBasedUserStore extends AbstractUserStore implements UserStore {

    private final Map<String, User> users = new HashMap<>();
    private static final Logger LOG = LogManager.getLogger(DatabaseBasedUserStore.class);

    /**
     * Finds a User by its Username and Password.
     *
     * @param username The user to be found.
     * @param password The password of the user to be found.
     * @return the UserDTO if the user exists.
     * @throws NullPointerException If the users are null.
     * @see de.uol.swp.server.database.SQLHelper
     */
    @Override
    public Optional<User> findUser(String username, String password) {
        UserDTO usr = null;
        try {
            ResultSet result =
                    SQLHelper.Select(
                            String.format("SELECT * FROM rr.user where username = '%s'", username));
            result.next();
            usr =
                    new UserDTO(
                            result.getString("username"),
                            result.getString("password"),
                            result.getString("email"));

        } catch (SQLException e) {
            LOG.error("SQL Error: " + e.getMessage());
        }

        if (usr != null && Objects.equals(usr.getPassword(), hash(password))) {
            return Optional.of(usr.getWithoutPassword());
        }
        return Optional.empty();
    }

    /**
     * Finds a user by its username.
     *
     * @param username The username of the user.
     * @return The UserDTO.
     * @author Jann Bruns & Ole Zimmermann
     * @see de.uol.swp.server.database.SQLHelper
     * @since 2022-12-02
     * @throws IllegalArgumentException If the username is null or empty.
     */
    @Override
    public Optional<User> findUser(String username) {
        UserDTO usr = null;
        try {
            ResultSet result =
                    SQLHelper.Select(
                            String.format("SELECT * FROM rr.user where username = '%s'", username));
            result.next();
            usr =
                    new UserDTO(
                            result.getString("username"),
                            result.getString("password"),
                            result.getString("email"));
        } catch (SQLException e) {
            return Optional.empty();
        }
        if (usr != null) {
            return Optional.of(usr.getWithoutPassword());
        }
        return Optional.empty();
    }

    /**
     * Creates a new user in the Database.
     *
     * @param username The username of the user.
     * @param password The password of the user.
     * @param eMail The eMail of the user.
     * @return The created UserDTO.
     * @author Jann Bruns & Ole Zimmermann
     * @see de.uol.swp.server.database.SQLHelper
     * @since 2022-12-02
     * @throws IllegalArgumentException If the username is null or empty.
     */
    @Override
    public User createUser(String username, String password, String eMail) {
        if (Strings.isNullOrEmpty(username)) {
            throw new IllegalArgumentException("Username must not be null");
        }
        Optional<User> user = null;

        String passwordHash = hash(password);
        int rowsAffected = 0;
        try {
            rowsAffected =
                    SQLHelper.Update(
                            String.format(
                                    "INSERT INTO rr.user (username, password, email) VALUES ('%s',"
                                            + " '%s', '%s')",
                                    username, passwordHash, eMail));
            user = findUser(username);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return rowsAffected == 1
                ? new UserDTO(user.get().getUsername(), passwordHash, eMail)
                : null;
    }

    /**
     * Updates a user in the Database.
     *
     * @param username The username of the user.
     * @param password The password of the user.
     * @param eMail The eMail of the user.
     * @return The updated UserDTO.
     * @author Jann Bruns & Ole Zimmermann
     * @see de.uol.swp.server.database.SQLHelper
     * @since 2022-12-02
     * @throws IllegalArgumentException If the username is null or empty.
     */
    @Override
    public User updateUser(String username, String password, String eMail) {
        String passwordHash = hash(password);
        int rowsAffected = 0;
        try {
            rowsAffected =
                    SQLHelper.Update(
                            String.format(
                                    "UPDATE rr.user SET password = '%s', email = '%s' WHERE"
                                            + " username = '%s'",
                                    passwordHash, eMail, username));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return rowsAffected == 1 ? new UserDTO(username, passwordHash, eMail) : null;
    }

    /**
     * Removes a user from the Database.
     *
     * @param username The username of the user.
     * @author Jann Bruns
     * @see de.uol.swp.server.database.SQLHelper
     * @since 2022-12-02
     * @throws IllegalArgumentException If the username is null or empty.
     */
    @Override
    public void removeUser(String username) {
        try {
            SQLHelper.Update(String.format("DELETE FROM rr.user WHERE username = '%s'", username));
        } catch (Exception e) {

        }
    }

    /**
     * Gets all users from the Database.
     *
     * @return The list of all UserDTOs.
     * @author Jann Bruns & Ole Zimmermann
     * @see de.uol.swp.server.database.SQLHelper
     * @since 2022-12-02
     */
    @Override
    public List<User> getAllUsers() {
        List<User> retUsers = new ArrayList<>();
        try {
            ResultSet result = SQLHelper.Select("SELECT * FROM rr.user");

            while (result.next()) {
                retUsers.add(
                        new UserDTO(
                                result.getString("username"),
                                result.getString("password"),
                                result.getString("email")));
            }
        } catch (Exception e) {
            return null;
        }
        return retUsers;
    }
}
