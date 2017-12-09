package Server.Game;

import Server.Player.AbstractPlayer;
import Server.Player.HumanPlayer;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Game{
    private List<AbstractPlayer> players;

    public Game(){
        players = new ArrayList<>();
    }

    public void addPlayer(Socket socket){
        players.add(new HumanPlayer(socket, this));

        //TODO: ADD ALL PLAYERS SET THEIR STATE TO READY
        //TODO: CORRECT NUMBER OF PLAYERS
        if(players.size() == 3){
            start();
        }
    }

    public synchronized void resendMessage(String message){
        if (message == null){
            return;
        }
        System.out.println("Sending!");
        for(AbstractPlayer player : players){
            if(!player.isPlaying()){
                //TODO: REMOVE PLAYER
            }
            else {
                player.sendMessage(message);
            }
        }
    }

    public void start(){
        System.out.println("Starting");
        for(AbstractPlayer player : players){
            player.start();
            System.out.println("Player started");
        }
    }


}
