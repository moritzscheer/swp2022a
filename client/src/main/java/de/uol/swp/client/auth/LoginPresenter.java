package de.uol.swp.client.auth;

import de.uol.swp.client.AbstractPresenter;
import de.uol.swp.client.CloseClientEvent;
import de.uol.swp.client.register.event.ShowRegistrationViewEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * Manages the login window
 *
 * @author Marco Grawunder
 * @see de.uol.swp.client.AbstractPresenter
 * @since 2019-08-08
 *
 */
public class LoginPresenter extends AbstractPresenter {

    public static final String FXML = "/fxml/LoginView.fxml";

    private static final ShowRegistrationViewEvent showRegViewMessage = new ShowRegistrationViewEvent();

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField loginField;


    /**
     * Default Constructor
     * @since 2019-08-18
     *
     */
    public LoginPresenter() {
        // needed for javafx
    }

    /**
     * Method called when the login button is pressed
     *
     * This Method is called when the login button is pressed. It takes the text
     * entered in the login and password field and gives the user service a request
     * to log in the user specified by those fields.
     *
     * @param event The ActionEvent generated by pressing the login button
     * @see de.uol.swp.client.user.UserService
     * @since 2019-08-13
     *
     */
    @FXML
    private void onLoginButtonPressed(ActionEvent event) {
        userService.login(loginField.getText(), passwordField.getText());
        passwordField.clear();
    }

    /**
     * Method called when the register button is pressed
     *
     * This Method is called when the register button is pressed. It posts an instance
     * of the ShowRegistrationViewEvent to the EventBus the SceneManager is subscribed
     * to.
     *
     * @param event The ActionEvent generated by pressing the register button
     * @see de.uol.swp.client.register.event.ShowRegistrationViewEvent
     * @see de.uol.swp.client.SceneManager
     * @since 2019-08-13
     *
     */
    @FXML
    private void onRegisterButtonPressed(ActionEvent event) {
        eventBus.post(showRegViewMessage);
    }

    /**
     * Method called when the register button is pressed
     *
     * This Method is called when the register button is pressed. It posts an instance
     * of the ShowRegistrationViewEvent to the EventBus the SceneManager is subscribed
     * to.
     *
     * @param event The ActionEvent generated by pressing the register button
     * @see de.uol.swp.client.register.event.ShowRegistrationViewEvent
     * @see de.uol.swp.client.SceneManager
     * @since 2019-08-13
     *
     */
    @FXML
    private void onExitButtonPressed(ActionEvent event) {
        eventBus.post(new CloseClientEvent());
    }
}
