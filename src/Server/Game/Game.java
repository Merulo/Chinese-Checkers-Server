package Server.Game;

import Server.Player.AbstractPlayer;
import Server.Player.HumanPlayer;
import Server.StateDesignPattern.LobbyState;

import java.util.ArrayList;
import java.util.List;

//Game-Lobby class
public class Game{
    //list of players connected to the game
    volatile private List<AbstractPlayer> players;
    //variable with the name of the game
    String gameName = "Name of the game";
    LobbyState lobbyState;
    //TODO: ADD VARIABLE WITH SETTINGS

    //creates the game array
    //sets the game name
    //sets lobby states
    public Game(int number){
        players = new ArrayList<>();
        gameName = "Game: " + Integer.toString(number);
        lobbyState = new LobbyState();
    }

    //returns lobby states
    public LobbyState getLobbyState() {
        return lobbyState;
    }

    //return gameData as string
    //use it to display gameinfo
    public String getGameData(){
        String result = "GAMEDATA;";
        result += gameName + ";";
        result += Integer.toString(players.size()) + ";";
        result += lobbyState.getState().getName() + ";";
        return result;
    }

    public String getGameDetailedData(){
        String result = "GAMEDETAILEDDATA;";
        result += gameName + ";";
        result += Integer.toString(players.size()) + ";";
        result += lobbyState.getState().getName() + ";";
        return result;
    }

    //adds client to the players list in the current game
    //sets the player game to this
    public void addPlayer(HumanPlayer client){
        players.add(client);
        client.setGame(this);
    }

    //resend messages to other players in current game
    public synchronized void resendMessage(String message){
        if (message == null){
            return;
        }
        System.out.println("Sending!");
        for(AbstractPlayer player : players){
            if(player.isPlaying()){
                player.sendMessage(message);
            }
        }
    }

    //removes player when necessary
    public synchronized void removePlayers(){
        try {
            for(int i = 0; i < players.size(); i++){
                if(!players.get(i).isPlaying()){
                    players.remove(i);
                    i = i - 1;
                }
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
