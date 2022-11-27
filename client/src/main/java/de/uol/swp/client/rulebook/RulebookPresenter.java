package de.uol.swp.client.rulebook;

import com.google.common.eventbus.EventBus;
import com.google.inject.Inject;
import de.uol.swp.client.AbstractPresenter;
import de.uol.swp.client.main.event.ShowMainMenuViewEvent;
import de.uol.swp.client.rulebook.event.ShowRulebookViewEvent;
import de.uol.swp.client.user.ClientUserService;
import de.uol.swp.common.user.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class RulebookPresenter extends AbstractPresenter {
/**
 * Manages the rulebook window
 *
 * @author Tommy Dang
 * @see de.uol.swp.client.AbstractPresenter
 * @since 2022-11-27
 *
 */

    public static final String FXML = "/fxml/RulebookView.fxml";

    private User loggedInUser;

    /**
     * Default Constructor
     *
     * @since 2022-11-27
     */
    public RulebookPresenter() {
    }

    @FXML
    void onBackButtonPressed(ActionEvent event){
        eventBus.post(new ShowMainMenuViewEvent());
    }

}
