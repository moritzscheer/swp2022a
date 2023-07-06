package de.uol.swp.common.lobby;

import static org.junit.jupiter.api.Assertions.assertTrue;

import de.uol.swp.common.SerializationTestHelper;
import de.uol.swp.common.lobby.message.*;
import de.uol.swp.common.lobby.request.CreateLobbyRequest;
import de.uol.swp.common.lobby.request.JoinLobbyRequest;
import de.uol.swp.common.lobby.request.LeaveLobbyRequest;
import de.uol.swp.common.user.UserDTO;

import org.junit.jupiter.api.Test;

class LobbyMessageSerializableTest {

    private static final UserDTO defaultUser = new UserDTO("marco", "marco", "marco@grawunder.de");

    @Test
    void testLobbyMessagesSerializable() {
        assertTrue(
                SerializationTestHelper.checkSerializableAndDeserializable(
                        new CreateLobbyRequest("test", defaultUser, true, "1234"),
                        CreateLobbyRequest.class));
        assertTrue(
                SerializationTestHelper.checkSerializableAndDeserializable(
                        new JoinLobbyRequest(1, "test", defaultUser, "1234"),
                        JoinLobbyRequest.class));
        assertTrue(
                SerializationTestHelper.checkSerializableAndDeserializable(
                        new LeaveLobbyRequest(1, "test", defaultUser, true),
                        LeaveLobbyRequest.class));
        assertTrue(
                SerializationTestHelper.checkSerializableAndDeserializable(
                        new UserJoinedLobbyMessage(1, "test", defaultUser),
                        UserJoinedLobbyMessage.class));
        assertTrue(
                SerializationTestHelper.checkSerializableAndDeserializable(
                        new UserLeftLobbyMessage(1, "test", defaultUser, null),
                        UserLeftLobbyMessage.class));
    }
}
