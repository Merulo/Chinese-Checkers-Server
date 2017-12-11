package Server.Player;

import Server.Network.Game;

import javafx.scene.paint.Color;

public abstract class AbstractPlayer extends Thread implements Player {

    Game game;
    Boolean playing = true;
    String nick;
    Color color;

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




}
