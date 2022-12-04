package de.uol.swp.client.credit;

import com.google.common.eventbus.Subscribe;
import de.uol.swp.client.AbstractPresenter;
import de.uol.swp.client.main.event.ShowMainMenuViewEvent;
import de.uol.swp.common.user.User;
import de.uol.swp.common.user.response.LoginSuccessfulResponse;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class CreditPresenter extends AbstractPresenter {
    /**
     * Manages the credit window
     *
     * @author Tommy Dang
     * @see de.uol.swp.client.AbstractPresenter
     * @since 2022-11-29
     *
     */
    public static final String FXML = "/fxml/CreditView.fxml";
    private User loggedInUser;

    /**
     * Default Constructor
     *
     * @since 2022-11-29
     */
    public CreditPresenter() {
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


    @FXML
    void onBackButtonPressed(ActionEvent event){
        eventBus.post(new ShowMainMenuViewEvent(loggedInUser));
    }
}
