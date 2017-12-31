package ServerTest.Players;

import Server.Map.Map;
import Server.Network.Hub;
import Server.Player.AbstractPlayer;
import Server.Player.ComputerPlayer;
import Server.Player.HumanPlayer;
import Server.Rules.MoveDecorator;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class HumanPlayerTest {
    @Test
    public void sendMessage() throws Exception {
        Hub hub = mock(Hub.class);
        MoveDecorator moveDecorator = mock(MoveDecorator.class);
        ArrayList<AbstractPlayer> players = new ArrayList<>();
        players.add(new ComputerPlayer());
        players.add(new ComputerPlayer());
        Map map = new Map(10);
        map.setUpMap(players);

        when(moveDecorator.getMap()).thenReturn(map);

        OutputStream outputStream = mock(OutputStream.class);

        Socket socket = mock(Socket.class);
        when(socket.getInputStream()).thenReturn(new InputStream() {
            @Override
            public int read() throws IOException {
                return 0;
            }
        });
        when(socket.getOutputStream()).thenReturn(outputStream);


        HumanPlayer humanPlayer = new HumanPlayer(socket, new Hub());
        humanPlayer.setNetworkManager(hub);

        humanPlayer.sendMessage("TestMessage;");

        verify(outputStream).flush();
    }

    @Test
    public void run() throws Exception {
        ComputerPlayer computerPlayer = mock(ComputerPlayer.class);
        computerPlayer.run();

        verify(computerPlayer).run();
    }
}