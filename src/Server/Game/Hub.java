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
    private List<AbstractPlayer> clients;

    public Hub(){
        games = new ArrayList<>();
        clients = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            games.add(new Game(i));
        }
    }

    public synchronized void enterGame(HumanPlayer client, int number){

        System.out.println("MOVING FORM HUB TO GAME");
        clients.remove(client);
        games.get(number).addPlayer(client);

    }

    public synchronized void addClient(Socket socket){
        System.out.println("NEW HUB CLIENT");
        clients.add(new HumanPlayer(socket, this));
        clients.get(clients.size() - 1).run();
        //sendGameList(clients.get(clients.size() - 1));
    }

    public synchronized void sendGame(Game game){
        for (AbstractPlayer client : clients){
            client.sendMessage(game.getGameData());
        }
    }

    public synchronized void sendGameList(HumanPlayer client){
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
                game.getLobbyState().handleLobby();
            }
        }
    }


}
