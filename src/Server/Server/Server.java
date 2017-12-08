package Server.Server;

import Server.Game.Game;

import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket serverSocket;
    int port;
    Game game;

    public Server(int port){
        this.port = port;
    }

    public boolean createServer(){
        try{
            serverSocket = new ServerSocket(port);
        }
        catch (Exception ex){
            return false;
        }
        return true;
    }

    public void listen(){

        while (true){
            try {
                Socket clientSocket = serverSocket.accept();
                if(game == null){
                    game = new Game();
                }
                game.addPlayer(clientSocket);

            }
            catch (Exception ex){
                System.out.println("Cant establish connection");
            }
        }
    }

}
