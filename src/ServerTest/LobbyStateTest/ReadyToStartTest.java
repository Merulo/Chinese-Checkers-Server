package ServerTest.LobbyStateTest;

import Server.LobbyState.*;
import Server.Network.Hub;
import Server.Network.Lobby;
import org.junit.Test;

import static org.junit.Assert.*;

public class ReadyToStartTest {
    @Test
    public void changeStateNext() throws Exception {
        LobbyState lobbyState = new LobbyState(null);
        State state = new ReadyToStart();
        state.changeStateNext(lobbyState);

        assertTrue(lobbyState.getState() instanceof Playing);
    }

    @Test
    public void changeStatePrev() throws Exception {
        LobbyState lobbyState = new LobbyState(new Lobby(null, 0));
        State state = new ReadyToStart();
        state.changeStatePrev(lobbyState);

        assertTrue(lobbyState.getState() instanceof Full);
    }

    @Test
    public void handleLobby() throws Exception {
        LobbyState lobbyState = new LobbyState(new Lobby(new Hub(), 0));
        State state = new ReadyToStart();

        state.handleLobby(lobbyState);

        assertFalse(lobbyState.getState() instanceof Full);
    }

    @Test
    public void getName() throws Exception {
        State state = new ReadyToStart();
        assertEquals("Ready to start", state.getName());
    }

}