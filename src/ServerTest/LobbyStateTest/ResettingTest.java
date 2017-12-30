package ServerTest.LobbyStateTest;

import Server.LobbyState.*;
import Server.Network.Hub;
import Server.Network.Lobby;
import org.junit.Test;

import static org.junit.Assert.*;

public class ResettingTest {
    @Test
    public void changeStateNext() throws Exception {
        LobbyState lobbyState = new LobbyState(null);
        State state = new Resetting();
        state.changeStateNext(lobbyState);

        assertTrue(lobbyState.getState() instanceof Open);
    }

    @Test
    public void changeStatePrev() throws Exception {
        LobbyState lobbyState = new LobbyState(new Lobby(null, 0));
        State state = new Resetting();
        state.changeStatePrev(lobbyState);

        assertTrue(lobbyState.getState() instanceof Open);
    }

    @Test
    public void handleLobby() throws Exception {
        LobbyState lobbyState = new LobbyState(new Lobby(new Hub(), 0));
        State state = new Resetting();

        state.handleLobby(lobbyState);

        assertTrue(lobbyState.getState() instanceof Playing);
    }

    @Test
    public void getName() throws Exception {
        State state = new Resetting();
        assertEquals("Resetting", state.getName());
    }

}