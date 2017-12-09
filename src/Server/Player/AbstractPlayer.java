package Server.Player;

import Server.Game.Game;

public abstract class AbstractPlayer extends Thread implements Player {
    Game game;
    Boolean playing = true;

    public boolean isPlaying(){
        return playing;
    }




}
