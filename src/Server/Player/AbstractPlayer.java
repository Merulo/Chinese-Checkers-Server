package Server.Player;

import Server.Network.Game;

import com.sun.org.apache.xpath.internal.operations.Bool;
import javafx.scene.paint.Color;

import java.util.concurrent.ArrayBlockingQueue;

public abstract class AbstractPlayer extends Thread {

    Game game;
    Boolean playing = true;
    String nick;
    Color color;
    private Boolean ready = false;

    public abstract void sendMessage(String message);

    public boolean isPlaying(){
        return playing;
    }

    public String getData(){
        String result = "PlayerList;";
        result+=nick + ";";
        result+=Double.toString(color.getRed()) + ";";
        result+=Double.toString(color.getGreen()) + ";";
        result+=Double.toString(color.getBlue()) + ";";
        return result;
    }

    public boolean isReady(){return ready;}




}
