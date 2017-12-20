package Server.Network;

import Server.Player.AbstractPlayer;

public interface NetworkManager {

    void addPlayer(AbstractPlayer player);

    void removePlayers();

    void enter(AbstractPlayer humanPlayer, int number);

    void parse(AbstractPlayer abstractPlayer, String message);


}
