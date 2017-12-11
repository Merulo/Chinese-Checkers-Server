package Server.Network;

import Server.Player.AbstractPlayer;
import Server.Player.HumanPlayer;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

//Hub class, extends thread
//this allows to handle the games!

public class Hub extends Thread implements NetworkManager {
    //list of games opened on status
    private List<Game> games;
    //list of players IN HUB
    private List<AbstractPlayer> players;

    //creates the lists and adds 10 games
    public Hub(){
        games = new ArrayList<>();
        players = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            games.add(new Game(this, i));
        }
    }

    //adds player to client list when moving from Lobby to Hub
    @Override
    public void addPlayer(HumanPlayer player){
        players.add(player);
        sendGameList(player);
        player.setGame(null);
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

    //moves one player from the hub to the game
    @Override
    public synchronized void enter(HumanPlayer client, int number){
        System.out.println("MOVING FORM HUB TO GAME");
        players.remove(client);
        games.get(number).addPlayer(client);
        client.sendMessage("YOU ARE NOW IN LOBBY: " + Integer.toString(number));
        client.sendMessage("NEXT MESSAGE CONTAINS DATA");
        client.sendMessage(games.get(number).getGameDetailedData());
    }

    //add new socket to the players list
    public synchronized void addClient(Socket socket){
        System.out.println("NEW HUB CLIENT");
        players.add(new HumanPlayer(socket, this));
        players.get(players.size() - 1).start();
        sendGameList(players.get(players.size() - 1));
    }

    //sends one game data to all the players
    public synchronized void sendGame(Game game){
        for (AbstractPlayer client : players){
            client.sendMessage(game.getGameData());
        }
    }

    //sends the game data about all games to one client
    public synchronized void sendGameList(AbstractPlayer client){
        for (Game game : games){
            client.sendMessage(game.getGameData());
        }
    }

    @Override
    public void run(){
        while(true){
            //for each game in games
            for(Game game : games){
                //get lobby state which has the method handle lobby
                //TODO: ADD LOGIC IN HANDLE LOBBY

            }
        }
    }


}
