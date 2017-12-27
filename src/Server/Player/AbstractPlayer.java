package Server.Player;

import Server.Network.NetworkManager;
import javafx.scene.paint.Color;

public abstract class AbstractPlayer extends Thread {

    Boolean playing = true;
    String nick;
    Color color;
    Boolean ready = false;
    NetworkManager networkManager;

    public void setNetworkManager(NetworkManager networkManager) {
        this.networkManager = networkManager;
    }

    public abstract void sendMessage(String message);

    public boolean isPlaying(){
        return playing;
    }

    public void setReady(boolean value){ready = value;}

    public String getData(){
        String result = "PlayerList;";
        result+=nick + ";";
        result+=Double.toString(color.getRed()) + ";";
        result+=Double.toString(color.getGreen()) + ";";
        result+=Double.toString(color.getBlue()) + ";";
        return result;
    }

    public String getNick(){
        return nick;
    }

    public boolean isReady(){return ready;}

    public void setPlaying(boolean value){
        playing = value;
    }




}
