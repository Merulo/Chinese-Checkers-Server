package Server;

import Server.Map.Map;
import Server.Network.Server;

public class Main {

    public static void main(String[] args) {
        /*
        Map map = new Map(15);
        map.setUpMap();
        map.printMap();
        */

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
