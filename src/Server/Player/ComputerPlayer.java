package Server.Player;

import Server.ComputerStrategy.Strategy;
import Server.ComputerStrategy.StrategyFactory;
import Server.Rules.MoveDecorator;
import javafx.scene.paint.Color;

import java.util.Random;

public class ComputerPlayer extends AbstractPlayer {
    private MoveDecorator moveDecorator;

    public ComputerPlayer() {
        color = new Color(Math.random(), Math.random(), Math.random() ,0.5);
        String names[] = {"Bob", "Alice"};
        nick = "Bot " + names[new Random().nextInt(names.length)];

    }

    @Override
    public void sendMessage(String message){
        if (message.equals("YourTurn;")){
            doMove();
        }
    }

    private void doMove(){
        StrategyFactory strategyFactory = StrategyFactory.getStrategyFactory();
        Strategy strategy = strategyFactory.getStrategy(nick);
        String move = strategy.getMove(moveDecorator, this);
        networkManager.parse(this, move);
    }

    @Override
    public void run() {
        //no need to run computer player as thread
    }

    public void setMoveDecorator(MoveDecorator moveDecorator) {
        this.moveDecorator = moveDecorator;
    }
}
