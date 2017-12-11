package Server.Network;

import Server.Player.AbstractPlayer;
import Server.Player.HumanPlayer;
import Server.LobbyState.LobbyState;

import java.util.ArrayList;
import java.util.List;
import Server.Settings;

//Game-Lobby class
public class Game implements NetworkManager {
    //list of players connected to the game
    volatile private List<AbstractPlayer> players;
    //Hub variable
    private Hub hub;
    private Settings settings;
    //TODO: ADD VARIABLE WITH SETTINGS

    //creates the game array
    //sets the game name
    //sets lobby states
    public Game(Hub hub, int number){
        players = new ArrayList<>();
        settings = new Settings("Game: " + Integer.toString(number), number);
        this.hub = hub;
    }

    //adds player to client list when moving from Lobby to Hub
    @Override
    public void addPlayer(HumanPlayer player){
        players.add(player);
        player.setGame(this);
    }

    //removes the client form the list
    @Override
    public void removePlayers(){
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

    @Override
    public synchronized void enter(HumanPlayer client, int number){
        System.out.println("MOVING FORM HUB TO GAME");
        players.remove(client);
        hub.addPlayer(client);
    }

    //returns lobby states
    /*
    public LobbyState getLobbyState() {
        return lobbyState;
    }
    */

    public String getGameData(){
        return settings.getGeneralData(players.size());
    }

    public String getGameDetailedData(){
        return settings.getDetailedData(players.size());
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

}
