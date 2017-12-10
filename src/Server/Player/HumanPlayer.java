package Server.Player;

import Server.Game.Game;
import Server.Game.Hub;
import Server.SimpleParser;
import com.sun.prism.null3d.NULL3DPipeline;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class HumanPlayer extends AbstractPlayer {
    Socket socket;
    BufferedReader input;
    PrintWriter output;
    Hub hub;
    Game game;

    public HumanPlayer(Socket socket, Hub hub) {
        this.socket = socket;
        this.hub = hub;
        try {
            input = new BufferedReader( new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(), true);
        }
        catch (IOException e) {
            System.out.println("Player died: " + e);
        }
        this.game = game;
        output.println("WELCOME");
    }

    public void setGame(Game game) {
        this.game = game;
    }

    @Override
    public void sendMessage(String message){
        output.println(message);
    }

    @Override
    public void run() {
        try {
            System.out.println("HUMAN PLAYER STARTING" );
            while (true) {
                String string = input.readLine();
                System.out.println("GAME MESSAGE" + string);
                if(string == null){
                    playing = false;
                    return;
                }
                else if(SimpleParser.parse(string).equals("JOIN")){
                    System.out.println("ROBIE JOIN");
                    string = SimpleParser.cut(string);
                    System.out.println("HUB MESSAGE:" + string + "X");
                    int number = Integer.parseInt(SimpleParser.parse(string));
                    hub.enterGame(this, number);
                }
                else if (game != null){
                    game.resendMessage(string);
                }

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
