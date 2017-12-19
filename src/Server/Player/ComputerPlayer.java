package Server.Player;

import Server.Network.Hub;
import javafx.scene.paint.Color;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
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
        System.out.println("TEST");
    }

    @Override
    public void run() {
        try {
            while (playing) {
                this.wait();
                //game.parseMyMove() //something like this
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
