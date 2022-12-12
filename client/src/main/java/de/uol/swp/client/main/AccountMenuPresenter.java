package de.uol.swp.client.main;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import de.uol.swp.client.AbstractPresenter;
import de.uol.swp.client.user.ClientUserService;
import de.uol.swp.common.user.User;
import de.uol.swp.common.user.UserDTO;
import de.uol.swp.common.user.request.DropUserRequest;
import de.uol.swp.common.user.request.ReturnToMainMenuRequest;
import de.uol.swp.common.user.request.UpdateUserRequest;
import de.uol.swp.common.user.response.LoginSuccessfulResponse;
import de.uol.swp.common.user.response.UpdatedUserSuccessfulResponse;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AccountMenuPresenter extends AbstractPresenter {

    public static final String FXML = "/fxml/AccountView.fxml";

    private static final Logger LOG = LogManager.getLogger(AccountMenuPresenter.class);

    private User loggedInUser;

    @FXML
    private Button DeleteAccountButton, PasswordResetButton,EmailResetButton,BackButton;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    /**
     * Default Constructor
     * @since 2022-11-25
     *
     */
    public AccountMenuPresenter() {
        // needed for javafx
    }

    /**
     * Constructor
     *
     * @param eventBus The EventBus set in ClientModule
     * @param userService The injected ClientUserService
     * @see de.uol.swp.client.di.ClientModule
     * @since 2022-12-02
     */
    @Inject
    public AccountMenuPresenter(EventBus eventBus, ClientUserService userService) {
        setEventBus(eventBus);
    }

    /**
     * Handles successful login
     *
     * If a LoginSuccessfulResponse is posted to the EventBus the loggedInUser
     * of this client is set to the one in the message received.
     *
     * @param message the LoginSuccessfulResponse object seen on the EventBus
     * @see de.uol.swp.common.user.response.LoginSuccessfulResponse
     * @since 2019-09-05
     */
    @Subscribe
    public void onLoginSuccessfulResponse(LoginSuccessfulResponse message) {
        this.loggedInUser = message.getUser();
    }


    /**
     * Handles successful updates
     *
     * If a UpdatedUserSuccessfulResponse is posted to the EventBus the UpdatedUser
     * of this client is set to the one in the message received.
     *
     * @see de.uol.swp.common.user.response.UpdatedUserSuccessfulResponse
     * @see de.uol.swp.common.user.User
     * @author Waldemar Kempel and Maria Eduarda Costa Leite Andrade
     * @since 2022-12-02
     */
    @Subscribe
    public void onUpdatedUserSuccessfulResponse(UpdatedUserSuccessfulResponse message) {
        this.loggedInUser = message.getUpdatedUser();
    }

    /**
     * Method called when the delete account button is pressed
     *
     * This Method is called when the delete account button is pressed. It deletes the user and send a request
     * to the DropUserRequest.
     *
     * @param event The ActionEvent generated by pressing the to delete account button
     * @see de.uol.swp.common.user.request.DropUserRequest
     * @author Waldemar Kempel and Maria Eduarda Costa Leite Andrade
     * @since 2022-12-02
     *
     */
    @FXML
    private void onDeleteAccountButtonPressed(ActionEvent event) {
        userService.dropUser(loggedInUser); // call userService (client side), instead of creating request directly
    }

    /**
     * Method called when the reset password button is pressed
     *
     * This Method is called when the reset password button is pressed. It deletes the user and reset the password from the old user.
     * After that it create a new user and send a request
     * to the UpdateUserRequest and to the userService.
     *
     * @param event The ActionEvent generated by pressing the to delete password button
     * @see de.uol.swp.common.user.request.UpdateUserRequest
     * @author Waldemar Kempel
     * @since 2022-12-02
     */

    // Option = A message for the user that the password was successfully deleted
    @FXML
    private void onPasswordResetButtonPressed(ActionEvent event) {
        if(emailField.getText().isEmpty() && !passwordField.getText().isEmpty()) {
            UserDTO userToUpdate = new UserDTO(loggedInUser.getUsername(),passwordField.getText(),loggedInUser.getEMail());
            userService.dropUser(loggedInUser);
            userService.createUser(userToUpdate);
            eventBus.post(new UpdateUserRequest(userToUpdate)); // TODO
            passwordField.clear();
        }
    }

    /**
     * Method called when the reset email button is pressed
     *
     * This Method is called when the reset email button is pressed. It deletes the user and reset the email from the old user.
     * After that it create a new user and send a request
     * to the UpdateUserRequest and to the userService.
     *
     * @param event The ActionEvent generated by pressing the to delete password button
     * @see de.uol.swp.common.user.request.UpdateUserRequest
     * @author Waldemar Kempel
     * @since 2022-12-02
     */
    @FXML
    private void onEmailResetButtonPressed(ActionEvent event) {
        if(passwordField.getText().isEmpty() && !emailField.getText().isEmpty()) {
            UserDTO userToUpdate = new UserDTO(loggedInUser.getUsername(), loggedInUser.getPassword(), emailField.getText());
            userService.dropUser(loggedInUser);
            userService.createUser(userToUpdate);
            eventBus.post(new UpdateUserRequest(userToUpdate)); // TODO
            emailField.clear();
        }

    }
    /**
     * Method called when the back to main button is pressed
     *
     * This Method is called when the back to main button is pressed. It sends a request
     * to the ReturnToMainMenuRequest.
     *
     * @param event The ActionEvent generated by pressing the to back to main account button
     * @see de.uol.swp.common.user.request.ReturnToMainMenuRequest
     * @author Waldemar Kempel and Maria Eduarda Costa Leite Andrade
     * @since 2022-12-02
     *
     */
    @FXML
    private void onReturnButtonPressed(ActionEvent event) {
        eventBus.post(new ReturnToMainMenuRequest(loggedInUser));
    }

    // Setter
    public void setEmailField(TextField emailField) {
        this.emailField = emailField;
    }

    public void setPasswordField(PasswordField passwordField) {
        this.passwordField = passwordField;
    }
}
