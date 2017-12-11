package Server.Player;

import Server.Network.Game;

public abstract class AbstractPlayer extends Thread implements Player {
    Game game;
    Boolean playing = true;
    String nick;

    public boolean isPlaying(){
        return playing;
    }




}
