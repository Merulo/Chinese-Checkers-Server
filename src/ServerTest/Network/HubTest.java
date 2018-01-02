package ServerTest.Network;

import Server.Network.Hub;
import Server.Network.Lobby;
import Server.Player.AbstractPlayer;
import Server.Player.ComputerPlayer;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.Socket;
import java.util.ArrayList;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class HubTest {
    @Test
    public void addPlayer() throws Exception {
        ArrayList<AbstractPlayer> players = new ArrayList<>();
        players.add(new ComputerPlayer());
        players.add(new ComputerPlayer());

        Hub hub = new Hub();

        Class gameClass = hub.getClass();
        Field field = gameClass.getDeclaredField("players");
        field.setAccessible(true);
        field.set(hub, players);

        hub.addPlayer(new ComputerPlayer());

        Field field2 = gameClass.getDeclaredField("players");
        field2.setAccessible(true);

        assertEquals(field, field2);
    }

    @Test
    public void removePlayers() throws Exception {
        ArrayList<AbstractPlayer> players = new ArrayList<>();
        ComputerPlayer computerPlayer = new ComputerPlayer();
        players.add(computerPlayer);
        players.add(new ComputerPlayer());

        Hub hub = new Hub();

        Class gameClass = hub.getClass();
        Field field = gameClass.getDeclaredField("players");
        field.setAccessible(true);
        field.set(hub, players);

        computerPlayer.setPlaying(false);
        hub.removePlayers();

        Field field2 = gameClass.getDeclaredField("players");
        field2.setAccessible(true);

        assertEquals(field, field2);
    }

    @Test
    public void enter() throws Exception {
        ArrayList<AbstractPlayer> players = new ArrayList<>();
        ComputerPlayer computerPlayer = new ComputerPlayer();
        players.add(computerPlayer);
        players.add(new ComputerPlayer());

        Hub hub = new Hub();

        Class gameClass = hub.getClass();
        Field field = gameClass.getDeclaredField("players");
        field.setAccessible(true);
        field.set(hub, players);

        hub.enter(computerPlayer, 0);

        Field field2 = gameClass.getDeclaredField("players");
        field2.setAccessible(true);

        assertEquals(field, field2);
    }

    @Test
    public void parse() throws Exception {
        ArrayList<AbstractPlayer> players = new ArrayList<>();
        ComputerPlayer computerPlayer = new ComputerPlayer();
        players.add(computerPlayer);

        Hub hub = new Hub();

        hub.parse(computerPlayer, "Leave");

        Class gameClass = hub.getClass();
        Field field = gameClass.getDeclaredField("players");
        field.setAccessible(true);
        field.set(hub, players);

        assertNotNull(field);


    }

    @Test
    public void getMoveRules() throws Exception {
        Hub hub = new Hub();

        assertNotNull(hub.getMoveRules());
    }

    @Test
    public void addClient() throws Exception {
        Socket mockClientSocket = mock(Socket.class);

        when(mockClientSocket.getInputStream()).thenReturn(new InputStream() {
            @Override
            public int read() throws IOException {
                return 0;
            }
        });

        when(mockClientSocket.getOutputStream()).thenReturn(new OutputStream() {
            @Override
            public void write(int i) throws IOException {

            }
        });

        Hub hub = new Hub();

        hub.addClient(mockClientSocket);

        Class gameClass = hub.getClass();
        Field field = gameClass.getDeclaredField("players");
        field.setAccessible(true);

        assertNotNull(field);

    }

    @Test
    public void sendGame() throws Exception {
        Hub hub = new Hub();
        AbstractPlayer abstractPlayer = mock(ComputerPlayer.class);

        hub.addPlayer(abstractPlayer);

        hub.sendGame(new Lobby(new Hub(), 0));

        verify(abstractPlayer, times(2)).sendMessage("GameData;0;Lobby: 1;0;4;Ready to start;");
    }

}