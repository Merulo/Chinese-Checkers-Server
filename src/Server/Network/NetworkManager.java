package Server.Network;

import Server.Player.AbstractPlayer;
import Server.Player.HumanPlayer;

public interface NetworkManager {

    void addPlayer(AbstractPlayer player);

    void removePlayers();

    void enter(AbstractPlayer humanPlayer, int number);


}
