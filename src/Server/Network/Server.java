package Server.Network;

import java.net.ServerSocket;
import java.net.Socket;

/**@author Damian Nowak
 * Accepts new clients as sockets and sends them to Hub
 */

public class Server {
    //server socket
    private ServerSocket serverSocket;
    //port number
    private int port;
    //main game hub
    private Hub hub;

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
        System.out.println("START LISTENING");
        while (true){
            try {
                //waits for connections
                Socket clientSocket = serverSocket.accept();
                //adds new connecting socket to client list
                hub.addClient(clientSocket);

            }
            catch (Exception ex){
                ex.printStackTrace();
                System.out.println("Cant establish connection");
                return;
            }
        }
    }

}
