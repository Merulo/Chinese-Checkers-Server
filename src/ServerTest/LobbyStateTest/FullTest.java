package ServerTest.LobbyStateTest;

import Server.LobbyState.*;
import Server.Network.Hub;
import Server.Network.Lobby;
import org.junit.Test;

import static org.junit.Assert.*;

public class FullTest {
    @Test
    public void changeStateNext() throws Exception {
        LobbyState lobbyState = new LobbyState(null);
        State state = new Full();
        state.changeStateNext(lobbyState);

        assertTrue(lobbyState.getState() instanceof ReadyToStart);
    }

    @Test
    public void changeStatePrev() throws Exception {
        LobbyState lobbyState = new LobbyState(new Lobby(null, 0));
        State state = new Full();
        state.changeStatePrev(lobbyState);

        assertTrue(lobbyState.getState() instanceof Open);
    }

    @Test
    public void handleLobby() throws Exception {
        LobbyState lobbyState = new LobbyState(new Lobby(new Hub(), 0));
        State state = new Full();

        state.handleLobby(lobbyState);

        assertFalse(lobbyState.getState() instanceof Open);
    }

    @Test
    public void getName() throws Exception {
        State state = new Full();
        assertEquals("Full", state.getName());
    }

}