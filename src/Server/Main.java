package Server;

import Server.Server.Server;

public class Main {

    public static void main(String[] args) {
        Server server = new Server(4444);

        if(!server.createServer()){
            System.out.println("Nie udało się stworzyć serwer!");
            return;
        }

        server.listen();




    }










    public static String addStrings(String a, String b){
        return a + b;
    }

    public static String addThreeStrings(String a, String b, String c){
        return a + b + c;
    }
}
