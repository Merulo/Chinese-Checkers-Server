package Server.Network;

import Server.Player.HumanPlayer;

public interface NetworkManager {

    void addPlayer(HumanPlayer player);

    void removePlayers();

    void enter(HumanPlayer humanPlayer, int number);


}
