package Server.Game;

import Server.Player.AbstractPlayer;
import Server.Player.HumanPlayer;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

//Hub class, extends thread
//this allows to handle the games!
public class Hub extends Thread{
    //list of games opened on status
    private List<Game> games;
    //list of players IN HUB
    private List<AbstractPlayer> clients;

    //creates the lists and adds 10 games
    public Hub(){
        games = new ArrayList<>();
        clients = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            games.add(new Game(i));
        }
    }

    //moves one player from the hub to the game
    public synchronized void enterGame(HumanPlayer client, int number){
        System.out.println("MOVING FORM HUB TO GAME");
        clients.remove(client);
        games.get(number).addPlayer(client);
        client.sendMessage("YOU ARE NOW IN LOBBY: " + Integer.toString(number));
        client.sendMessage("NEXT MESSAGE CONTAINS DATA");
        client.sendMessage(games.get(number).getGameData());

    }
    //add new socket to the players list
    public synchronized void addClient(Socket socket){
        System.out.println("NEW HUB CLIENT");
        clients.add(new HumanPlayer(socket, this));
        clients.get(clients.size() - 1).start();

        sendGameList(clients.get(clients.size() - 1));
    }

    //sends one game data to all the players
    public synchronized void sendGame(Game game){
        for (AbstractPlayer client : clients){
            client.sendMessage(game.getGameData());
        }
    }

    //sends the game data about all games to one client
    public synchronized void sendGameList(AbstractPlayer client){
        for (Game game : games){
            client.sendMessage(game.getGameData());
        }
    }

    //removes player when necessary
    public synchronized void removePlayers(){
        try {
            for(int i = 0; i < clients.size(); i++){
                if(!clients.get(i).isPlaying()){
                    clients.remove(i);
                    i = i - 1;
                }
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void run(){
        while(true){
            //for each game in games
            for(Game game : games){
                //get lobby state which has the method handle lobby
                //TODO: ADD LOGIC IN HANDLE LOBBY
                game.getLobbyState().handleLobby();
            }
        }
    }


}
