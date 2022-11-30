package de.uol.swp.client.rulebook;

import com.google.common.eventbus.EventBus;
import com.google.inject.Inject;
import de.uol.swp.client.AbstractPresenter;
import de.uol.swp.client.user.ClientUserService;
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

    /**
     * Default Constructor
     *
     * @since 2022-11-27
     */
    public RulebookPresenter() {
    }

    /**
     * Constructor
     *
     * @param eventBus The EventBus set in ClientModule
     * @param userService The injected ClientUserService
     * @see de.uol.swp.client.di.ClientModule
     * @since 2022-11-27
     */
    @Inject
    public RulebookPresenter(EventBus eventBus, ClientUserService userService) {
        setEventBus(eventBus);
    }

    @FXML
    void onBackButtonPressed(ActionEvent event){

    }
}
