package ServerTest.Players;

import Server.Map.Map;
import Server.Network.Hub;
import Server.Player.AbstractPlayer;
import Server.Player.ComputerPlayer;
import Server.GameProperties.MoveDecorator;
import org.junit.Test;

import java.util.ArrayList;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ComputerPlayerTest {
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


        ComputerPlayer computerPlayer = new ComputerPlayer();
        computerPlayer.setNetworkManager(hub);


        computerPlayer.setMoveDecorator(moveDecorator);

        computerPlayer.sendMessage("YourTurn;");

        verify(hub).parse(computerPlayer, "Skip;");
    }

    @Test
    public void run() throws Exception {
        ComputerPlayer computerPlayer = mock(ComputerPlayer.class);
        computerPlayer.run();

        verify(computerPlayer).run();
    }

}