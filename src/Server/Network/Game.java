package Server.Network;

import Server.Player.AbstractPlayer;
import Server.Player.HumanPlayer;

import java.util.ArrayList;
import java.util.List;
import Server.Settings;

//Game/Lobby class
public class Game implements NetworkManager {
    //list of players connected to the game
    volatile private List<AbstractPlayer> players;
    //Hub variable
    private Hub hub;
    //variable with the settings of the game
    private Settings settings;

    //creates game, sets hub, and gives the game number
    public Game(Hub hub, int number){
        players = new ArrayList<>();
        settings = new Settings("Game: " + Integer.toString(number + 1), number);
        this.hub = hub;
    }

    //adds player to the game, sets the player variables
    @Override
    public synchronized void addPlayer(HumanPlayer player){
        player.setGame(this);
        player.sendMessage(settings.getDetailedData(players.size()));

        for(AbstractPlayer abstractPlayer : players){
            abstractPlayer.sendMessage(player.getData());
        }

        players.add(player);

        for(AbstractPlayer abstractPlayer : players){
            player.sendMessage(abstractPlayer.getData());
        }
    }

    //removes the client form the list if the connection was closed
    @Override
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

    //moves the player from game to hub
    @Override
    public synchronized void enter(HumanPlayer client, int number){
        System.out.println("MOVING FORM HUB TO GAME");
        players.remove(client);
        hub.addPlayer(client);
    }

    public String getGameData(){
        return settings.getGeneralData(players.size());
    }

    public boolean canJoin(){
        if(settings.getMaxPlayerNumber() <= players.size()){
            return false;
        }
        System.out.println("1");
        //TODO: FINISH THIS CODE
        //if(settings.getLobbyState().getState()){
        //    return false;
        //}
        System.out.println("2");
        return true;

    }


    //resend messages to other players in current game
    public synchronized void resendMessage(String message){
        if (message == null){
            return;
        }
        System.out.println("Sending " + message);
        for(AbstractPlayer player : players){
            if(player.isPlaying()){
                player.sendMessage(message);
            }
        }
    }

}
