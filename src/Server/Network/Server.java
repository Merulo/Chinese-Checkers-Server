package Server.Network;

import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket serverSocket;
    int port;
    Hub hub;


    //create object and set up port
    public Server(int port){
        this.port = port;
        hub = new Hub();
        hub.start();
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
                //adds new connecting socket to client list
                hub.addClient(clientSocket);

            }
            catch (Exception ex){
                System.out.println("Cant establish connection");
                return;
            }
        }
    }

}
