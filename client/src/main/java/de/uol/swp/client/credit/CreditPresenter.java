package de.uol.swp.client.credit;

import com.google.common.eventbus.Subscribe;

import de.uol.swp.client.AbstractPresenter;
import de.uol.swp.client.main.event.ShowMainMenuViewEvent;
import de.uol.swp.common.user.User;
import de.uol.swp.common.user.response.LoginSuccessfulResponse;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

/**
 * Manages the credit window
 *
 * @author Tommy Dang
 * @see de.uol.swp.client.AbstractPresenter
 * @since 2022-11-29
 */
public class CreditPresenter extends AbstractPresenter {

    public static final String FXML = "/fxml/CreditView.fxml";
    private User loggedInUser;

    /**
     * Default Constructor
     *
     * @since 2022-11-30
     */
    public CreditPresenter() {}

    /**
     * Handles successful login
     *
     * <p>If a LoginSuccessfulResponse is posted to the EventBus the loggedInUser of this client is
     * set to the one in the message received.
     *
     * @param message the LoginSuccessfulResponse object seen on the EventBus
     * @see de.uol.swp.common.user.response.LoginSuccessfulResponse
     * @since 2022-12-06
     */
    @Subscribe
    public void onLoginSuccessfulResponse(LoginSuccessfulResponse message) {
        this.loggedInUser = message.getUser();
    }

    /**
     * Method called when the back button is pressed
     *
     * <p>This Method is called when the back button is pressed. It posts an instance of the
     * ShowMainMenuViewEvent to the EventBus the SceneManager is subscribed to.
     *
     * @param event The ActionEvent generated by pressing the back button
     * @see de.uol.swp.client.main.event.ShowMainMenuViewEvent
     * @see de.uol.swp.client.SceneManager
     * @author Tommy Dang
     * @since 2022-12-06
     */
    @FXML
    void onBackButtonPressed(ActionEvent event) {
        eventBus.post(new ShowMainMenuViewEvent());
    }
}
