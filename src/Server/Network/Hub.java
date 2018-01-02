package Server.Network;

import Server.Player.AbstractPlayer;
import Server.Player.HumanPlayer;
import Server.GameProperties.AdjacentMoveRule;
import Server.GameProperties.MoveRule;
import Server.GameProperties.OneTileAnyPawnMoveRule;
import Server.GameProperties.TwoTilesNoPawnsRule;
import Server.SimpleParser;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/** @author Damian Nowak
 * Hub class, creates new Human players
 * Sends general lobby information
 */
public class Hub extends Thread implements NetworkManager {
    //list of lobbies opened on status
    private List<Lobby> lobbies;
    //list of players IN HUB
    private List<AbstractPlayer> players;
    //global move rule list
    private List<MoveRule> moveRules;

    //creates the lists and adds 10 lobbies
    public Hub(){
        lobbies = new ArrayList<>();
        players = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            lobbies.add(new Lobby(this, i));
        }

        moveRules = new ArrayList<>();
        moveRules.add(new AdjacentMoveRule());
        moveRules.add(new OneTileAnyPawnMoveRule());
        moveRules.add(new TwoTilesNoPawnsRule());


    }

    //adds player to client list when moving from Lobby to Hub
    @Override
    public void addPlayer(AbstractPlayer player){
        players.add(player);
        sendGameList(player);
        player.setNetworkManager(this);
    }

    //removes the client form the list
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

    //moves one player from the hub to the game
    @Override
    public synchronized void enter(AbstractPlayer client, int number){
        if(lobbies.get(number).canJoin()) {
            players.remove(client);
            lobbies.get(number).addPlayer(client);
        }
        else{
            client.sendMessage("Full;");
        }
    }

    //parses the message
    @Override
    public synchronized void parse(AbstractPlayer abstractPlayer, String message){
        String type = SimpleParser.parse(message);
        switch (type) {
            case "Join": {
                message = SimpleParser.cut(message);
                int number = Integer.parseInt(SimpleParser.parse(message));
                enter(abstractPlayer, number);
                break;
            }
            case "Leave":{
                abstractPlayer.setPlaying(false);
                removePlayers();
                break;
            }
        }
    }

    //returns list of rules
    public List<MoveRule> getMoveRules() {
        return moveRules;
    }

    //add new socket to the players list
    public synchronized void addClient(Socket socket){
        System.out.println("NEW HUB CLIENT");
        players.add(new HumanPlayer(socket, this));
        players.get(players.size() - 1).start();
        sendGameList(players.get(players.size() - 1));
    }

    //sends one lobby data to all the players
    public synchronized void sendGame(Lobby lobby){
        for (AbstractPlayer client : players){
            client.sendMessage(lobby.getGameData());
        }
    }

    //sends the game data about all lobbies to one client
    private synchronized void sendGameList(AbstractPlayer client){
        for (Lobby lobby : lobbies){
            client.sendMessage(lobby.getGameData());
        }
    }

    //handles the build-in logic
    @Override
    public void run(){
        while(true){
            try {
                Thread.sleep(200);
            }
            catch (Exception ex){
                ex.printStackTrace();
            }
            for(Lobby lobby : lobbies){
                lobby.handleLobby();
            }
        }
    }

}
