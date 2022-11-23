package de.uol.swp.client.main.event;

import de.uol.swp.common.user.User;

public class ShowMainMenuViewEvent {

    private final User user;
    public ShowMainMenuViewEvent(User user) {
        this.user = user;
    }

    public User getUser() {
        return this.user;
    }
}
