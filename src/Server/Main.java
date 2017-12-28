package Server;

import Server.Map.MapPoint;
import Server.Network.Server;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        //create server object
        Server server = new Server(5555);

        //start server and check if it was successful
        if(!server.createServer()){
            System.out.println("Could not create server");
            return;
        }
        //start listening for clients
        server.listen();

    }

}
