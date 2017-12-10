package Server.Game;

import Server.Player.AbstractPlayer;
import Server.Player.HumanPlayer;
import Server.StateDesignPattern.LobbyState;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

//Game-Lobby class

public class Game{
    //list of players connected to the game
    private List<AbstractPlayer> players;
    //variable with the name of the game
    String gameName = "Name of the game";
    LobbyState lobbyState;
    //TODO: ADD VARIABLE WITH SETTINGS


    public Game(int number){
        players = new ArrayList<>();
        gameName = "Game: " + Integer.toString(number);
        lobbyState = new LobbyState();
    }

    public LobbyState getLobbyState() {
        return lobbyState;
    }

    public String getGameData(){
        return "GMDT;" + gameName + ";" + Integer.toString(players.size());
    }

    public void addPlayer(HumanPlayer client){
        players.add(client);
        client.setGame(this);
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


    /*
    public void addPlayer(Socket socket){
        players.add(new HumanPlayer(socket, this));

        //TODO: ADD ALL PLAYERS SET THEIR STATE TO READY
        //TODO: CORRECT NUMBER OF PLAYERS
        if(players.size() == 1){
            start();
        }
    }



    public void start(){
        System.out.println("Starting");
        for(AbstractPlayer player : players){
            player.start();
            System.out.println("Player started");
        }
    }*/


}
