package Server.Network;

import Server.Player.AbstractPlayer;

/**@author Damian Nowak
 * General interface for network classes
 */

public interface NetworkManager {

    void addPlayer(AbstractPlayer player);

    void removePlayers();

    void enter(AbstractPlayer humanPlayer, int number);

    void parse(AbstractPlayer abstractPlayer, String message);


}
