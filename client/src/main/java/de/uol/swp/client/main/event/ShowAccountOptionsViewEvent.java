package de.uol.swp.client.auth.events;

import de.uol.swp.common.user.User;

/**
 * Event used to show the account options in the main menu
 *
 * In order to show the account options in the main menu, using this event, post an instance of it
 * onto the eventBus the SceneManager is subscribed to.
 *
 * @author Waldemar Kempel
 * @see de.uol.swp.client.SceneManager
 * @since 2022-11-25
 *
 */

public class ShowAccountOptionsViewEvent {


    //private final User user;

    public ShowAccountOptionsViewEvent(){
    }
    //public ShowAccountOptionsViewEvent(User user) {
      //  this.user = user;
   // }

    //public User getUser() {
      //  return this.user;
    //}
}
