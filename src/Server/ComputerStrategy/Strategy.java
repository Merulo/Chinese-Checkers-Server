package Server.ComputerStrategy;

import Server.Player.AbstractPlayer;
import Server.Rules.MoveDecorator;

public interface Strategy {
    String getMove(MoveDecorator moveDecorator, AbstractPlayer abstractPlayer);
}
