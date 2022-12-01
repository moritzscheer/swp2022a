package de.uol.swp.client.lobby.event;

import de.uol.swp.common.user.User;

public class JoinOrCreateCanceledEvent {

    private final User user;

    public JoinOrCreateCanceledEvent(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
