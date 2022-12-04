package de.uol.swp.server.usermanagement;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import de.uol.swp.common.message.MessageContext;
import de.uol.swp.common.message.ResponseMessage;
import de.uol.swp.common.user.User;
import de.uol.swp.common.user.exception.RegistrationExceptionMessage;
import de.uol.swp.common.user.request.DropUserRequest;
import de.uol.swp.common.user.request.RegisterUserRequest;
import de.uol.swp.common.user.request.ShowAccountOptionsRequest;
import de.uol.swp.common.user.request.UpdateUserRequest;
import de.uol.swp.common.user.response.RegistrationSuccessfulResponse;
import de.uol.swp.common.user.response.ShowAccountOptionsSuccessfulResponse;
import de.uol.swp.common.user.response.UpdatedUserSuccessfulResponse;
import de.uol.swp.common.user.response.UserDroppedSuccessfulResponse;
import de.uol.swp.server.AbstractService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

/**
 * Mapping vom event bus calls to user management calls
 *
 * @see de.uol.swp.server.AbstractService
 * @author Marco Grawunder
 * @since 2019-08-05
 */
@SuppressWarnings("UnstableApiUsage")
@Singleton
public class UserService extends AbstractService {

    private static final Logger LOG = LogManager.getLogger(UserService.class);

    private final UserManagement userManagement;

    /**
     * Constructor
     *
     * @param eventBus the EventBus used throughout the entire server (injected)
     * @param userManagement object of the UserManagement to use
     * @see de.uol.swp.server.usermanagement.UserManagement
     * @since 2019-08-05
     */
    @Inject
    public UserService(EventBus eventBus, UserManagement userManagement) {
        super(eventBus);
        this.userManagement = userManagement;
    }

    /**
     * Handles RegisterUserRequests found on the EventBus
     *
     * If a RegisterUserRequest is detected on the EventBus, this method is called.
     * It tries to create a new user via the UserManagement. If this succeeds a
     * RegistrationSuccessfulResponse is posted on the EventBus otherwise a RegistrationExceptionMessage
     * gets posted there.
     *
     * @param msg The RegisterUserRequest found on the EventBus
     * @see de.uol.swp.server.usermanagement.UserManagement#createUser(User)
     * @see de.uol.swp.common.user.request.RegisterUserRequest
     * @see de.uol.swp.common.user.response.RegistrationSuccessfulResponse
     * @see de.uol.swp.common.user.exception.RegistrationExceptionMessage
     * @since 2019-09-02
     */
    @Subscribe
    private void onRegisterUserRequest(RegisterUserRequest msg) {
        if (LOG.isDebugEnabled()){
            LOG.debug("Got new registration message with {}", msg.getUser());
        }
        ResponseMessage returnMessage;
        try {
            userManagement.createUser(msg.getUser());
            returnMessage = new RegistrationSuccessfulResponse();
        }catch (Exception e){
            LOG.error(e);
            returnMessage = new RegistrationExceptionMessage("Cannot create user "+msg.getUser()+" "+e.getMessage());
        }
        msg.getMessageContext().ifPresent(returnMessage::setMessageContext);
        post(returnMessage);
    }

    /**
     * Handles DropUserRequest found on the EventBus
     *
     * If a DropUserRequest is detected on the EventBus, this method is called.
     * It tries to create a new user via the UserManagement. If this succeeds a
     * RegistrationSuccessfulResponse is posted on the EventBus otherwise a RegistrationExceptionMessage
     * gets posted there.
     *
     * @param msg The RegisterUserRequest found on the EventBus
     * @see de.uol.swp.server.usermanagement.UserManagement#createUser(User)
     * @see de.uol.swp.common.user.request.RegisterUserRequest
     * @see de.uol.swp.common.user.response.RegistrationSuccessfulResponse
     * @see de.uol.swp.common.user.exception.RegistrationExceptionMessage
     * @since 2019-09-02
     */
    @Subscribe
    private void onDropUserRequest(DropUserRequest msg) {
        if (LOG.isDebugEnabled()){
            LOG.debug("Got new registration message with {}", msg.getUser());
        }
        ResponseMessage returnMessage;
        try {
            userManagement.dropUser(msg.getUser());
            returnMessage = new UserDroppedSuccessfulResponse(msg.getUsername());
        }catch (Exception e){
            LOG.error(e);
            returnMessage = new RegistrationExceptionMessage("Cannot drop user "+msg.getUser()+" "+e.getMessage());
        }
        msg.getMessageContext().ifPresent(returnMessage::setMessageContext);
        post(returnMessage);
    }

    /**
     * Handles ShowAccountOptionsRequest found on the EventBus
     *
     * If an ShowAccountOptionsRequest is detected on the EventBus, this method is called.
     * It tries to open the account view. If this succeeds a
     * ShowAccountOptionsSuccessfulResponse is posted on the EventBus.
     *
     * @param msg The ShowAccountOptionsRequest found on the EventBus
     * @see de.uol.swp.common.user.request.ShowAccountOptionsRequest
     * @see de.uol.swp.common.user.response.ShowAccountOptionsSuccessfulResponse
     * @author Waldemar Kempel and Maria Eduarda Costa Leite Andrade
     * @since 2022-12-02
     */
    @Subscribe
    private void onShowAccountOptionsRequest(ShowAccountOptionsRequest msg){
        if (LOG.isDebugEnabled()){
            LOG.debug("Got new account options message with {}", msg.getLoggedInUser());
        }
        ResponseMessage returnMessage;
        try {
            returnMessage = new ShowAccountOptionsSuccessfulResponse(msg.getLoggedInUser());
        }catch (Exception e){
            LOG.error(e);
            returnMessage = new RegistrationExceptionMessage("Cannot show the account view for user "+msg.getLoggedInUser()+" "+e.getMessage());
        }
        msg.getMessageContext().ifPresent(returnMessage::setMessageContext);
        post(returnMessage);
    }

    /**
     * Handles UpdateUserRequest found on the EventBus
     *
     * If a UpdateUserRequest is detected on the EventBus, this method is called.
     * It tries to update a new user via the UserManagement. If this succeeds a
     * UpdatedUserSuccessfulResponse is posted on the EventBus.
     *
     * @param msg The UpdateUserRequest found on the EventBus
     * @see de.uol.swp.server.usermanagement.UserManagement#updateUser(User)
     * @see de.uol.swp.common.user.request.UpdateUserRequest
     * @see de.uol.swp.common.user.response.UpdatedUserSuccessfulResponse
     * @author Waldemar Kempel and Maria Eduarda Costa Leite Andrade
     * @since 2019-09-02
     */
    @Subscribe
    private void onUpdateUserRequest(UpdateUserRequest msg){
        if (LOG.isDebugEnabled()){
            LOG.debug("Got new update message with {}", msg.getUser().getUsername());
        }
        ResponseMessage returnMessage;
        try {
            userManagement.updateUser(msg.getUser());
            returnMessage = new UpdatedUserSuccessfulResponse(msg.getUser());
        }catch (Exception e){
            LOG.error(e);
            returnMessage = new RegistrationExceptionMessage("Cannot update user "+msg.getUser().getUsername()+" "+e.getMessage());
        }
        msg.getMessageContext().ifPresent(returnMessage::setMessageContext);
        post(returnMessage);
    }
}
