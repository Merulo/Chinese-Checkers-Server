package Server.Network;

import Server.Game.Game;

import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket serverSocket;
    int port;
    Game game;

    //create object and set up port
    public Server(int port){
        this.port = port;
    }

    //creates server socket, returns if successful
    public boolean createServer(){
        try{
            serverSocket = new ServerSocket(port);
        }
        catch (Exception ex){
            return false;
        }
        return true;
    }

    //listens for connecting players
    public void listen(){

        while (true){
            try {
                //waits for connections
                Socket clientSocket = serverSocket.accept();
                //creates game if needed
                if(game == null){
                    game = new Game();
                }
                //adds player to the game
                game.addPlayer(clientSocket);

            }
            catch (Exception ex){
                System.out.println("Cant establish connection");
                return;
            }
        }
    }

}
