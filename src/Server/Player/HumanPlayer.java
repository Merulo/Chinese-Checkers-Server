package Server.Player;

import Server.Game.Game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class HumanPlayer extends AbstractPlayer {
    Socket socket;
    BufferedReader input;
    PrintWriter output;

    public HumanPlayer(Socket socket, Game game) {
        this.socket = socket;
        this.game = game;
        try {
            input = new BufferedReader( new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(), true);
            output.println("WELCOME");
        }
        catch (IOException e) {
            System.out.println("Player died: " + e);
        }
    }

    @Override
    public void sendMessage(String message){
        output.println(message);
    }

    @Override
    public void run() {
        try {
            while (true) {
                String string = input.readLine();
                //System.out.println("Got message: " + string);
                if(string == null){
                    playing = false;
                    return;
                }
                game.resendMessage(string);
            }
        }
        catch (IOException e) {
            System.out.println("Player died: " + e);
        }
        finally {
            try {
                socket.close();
            }
            catch (IOException e) {

            }
        }
    }

}
