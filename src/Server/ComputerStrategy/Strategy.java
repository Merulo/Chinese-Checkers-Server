package Server.ComputerStrategy;

import Server.Player.AbstractPlayer;
import Server.GameProperties.MoveDecorator;

/**@author Damian Nowak
 * Strategy interface. Open-closed principle
 */
public interface Strategy {
    String getMove(MoveDecorator moveDecorator, AbstractPlayer abstractPlayer);
}
