package Server.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class AbstractPlayer extends Thread implements Player {
    AbstractPlayer otherPlayer;
    Socket socket;
    BufferedReader input;
    PrintWriter output;

    public AbstractPlayer(Socket socket) {
        this.socket = socket;
        try {
            input = new BufferedReader( new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(), true);
            output.println("WELCOME ");
            output.println("MESSAGE Waiting for opponent to connect");
        }
        catch (IOException e) {
            System.out.println("Player died: " + e);
        }
    }

    public void setOpponent(AbstractPlayer opponent) {
        this.otherPlayer = opponent;
    }

    public void run() {
        try {
            // The thread is only started after everyone connects.
            output.println("MESSAGE All players connected");

            while (true) {
                String string = input.readLine();
                System.out.println(string);
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
