package Server.Player;

import javafx.scene.paint.Color;

import java.util.Random;

public class ComputerPlayer extends AbstractPlayer {
    //TODO: ADD STRATEGY

    public ComputerPlayer() {
        color = new Color(Math.random(), Math.random(), Math.random() ,0.5);
        String names[] = {"Bob", "Alice"};
        nick = "Bot " + names[new Random().nextInt(names.length)];

    }

    @Override
    public void sendMessage(String message){
        //System.out.println("TEST");
    }

    @Override
    public void run() {
        try {
            while (playing) {
                this.wait();
                //lobby.parseMyMove() //something like this
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
