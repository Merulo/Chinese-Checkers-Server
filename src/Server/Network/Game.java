package Server.Network;

import Server.LobbyState.Open;
import Server.Player.AbstractPlayer;
import Server.Player.HumanPlayer;

import java.util.ArrayList;
import java.util.List;
import Server.Rules.Settings;
import com.sun.org.apache.bcel.internal.generic.INSTANCEOF;

//Game/Lobby class
public class Game implements NetworkManager {
    //list of players connected to the game
    volatile private List<AbstractPlayer> players;
    //Hub variable
    private Hub hub;
    //variable with the settings of the game
    private Settings settings;
    //countdown variable
    private int countDown = 10;
    //countdown time variable
    private long startMillis;

    //creates game, sets hub, and gives the game number
    Game(Hub hub, int number){
        players = new ArrayList<>();
        settings = new Settings(this, "Game: " + Integer.toString(number + 1), number);
        this.hub = hub;
        startMillis = System.currentTimeMillis();
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

        hub.sendGame(this);
    }

    //removes the client form the list if the connection was closed
    @Override
    public synchronized void removePlayers(){
        try {
            for(int i = 0; i < players.size(); i++){
                if(!players.get(i).isPlaying()){
                    //TODO: VERIFY THIS CODE
                    for(AbstractPlayer abstractPlayer : players){
                        if(abstractPlayer != players.get(i))
                            abstractPlayer.sendMessage("Remove:" +  players.get(i).getData() + ";");
                    }
                    players.remove(i);
                    i = i - 1;
                }
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        hub.sendGame(this);

    }

    //moves the player from game to hub
    @Override
    public synchronized void enter(HumanPlayer client, int number){
        System.out.println("MOVING FROM GAME TO HUB");
        players.remove(client);
        //TODO: VERIFY THIS CODE
        for(AbstractPlayer abstractPlayer : players){
            abstractPlayer.sendMessage("Remove;" + client.getData() + ";");
        }

        hub.sendGame(this);
        hub.addPlayer(client);
    }

    //returns game data which can be send to other players
    String getGameData(){
        return settings.getGeneralData(players.size());
    }

    //returns true if it is possible to join the game
    boolean canJoin(){
        return settings.getMaxPlayerNumber()>players.size()
                && (settings.getLobbyState().getState() instanceof Open);
    }

    //resend messages to other players in current game
    public synchronized void resendMessage(String message){
        if (message == null){
            return;
        }
        System.out.println("Sending " + message);
        for(AbstractPlayer player : players){
            if(player.isPlaying()){
                System.out.println("Sending to " + player.getData());
                player.sendMessage(message);
            }
        }
    }

    void handleLobby(){
        settings.getLobbyState().handleLobby();
    }

    public boolean validatePlayerCount(){
        int playerCount = players.size();
        return (playerCount == 2 || playerCount == 3 || playerCount == 4|| playerCount == 6);
    }

    public boolean validatePlayerReady(){
        for(AbstractPlayer player : players){
            if (!player.isReady()){
                return false;
            }
        }
        return true;
    }

    public void resetCountdown(){
        countDown = 10;
        startMillis = System.currentTimeMillis();
    }

    public void countDown(){
        if ((System.currentTimeMillis() - this.startMillis) / 1000.0 > 1) {
            startMillis = System.currentTimeMillis();
            countDown--;
            for (AbstractPlayer player : players) {
                player.sendMessage("Countdown;" + Integer.toString(countDown) + ";");
            }
        }
    }

    public int getCountDown(){
        return countDown;
    }

    public void startGame(){
        for (AbstractPlayer player : players) {
                player.sendMessage("Start" + settings.getGeneralData(players.size()) +";");
        }
        //TODO: CHOOSE STARTING PLAYER
        //TODO: SEND STARTING POSITIONS
        //TODO: SEND STARTING PLAYER INFORMATION
    }



}
