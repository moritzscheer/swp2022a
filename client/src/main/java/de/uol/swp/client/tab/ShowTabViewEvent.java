package de.uol.swp.client.tab;

import de.uol.swp.common.user.User;

public class ShowTabViewEvent {

    private final User user;

    public ShowTabViewEvent(User user) {
        this.user = user;
    }

    public User getUser() {
        return this.user;
    }
}
