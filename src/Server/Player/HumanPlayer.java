package Server.Player;

import Server.Network.Game;
import Server.Network.Hub;
import Server.SimpleParser;
import javafx.scene.paint.Color;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;

public class HumanPlayer extends AbstractPlayer {
    Socket socket;
    BufferedReader input;
    PrintWriter output;
    Hub hub;

    public HumanPlayer(Socket socket, Hub hub) {
        this.socket = socket;
        this.hub = hub;
        Random rand = new Random();
        color = new Color(Math.random(), Math.random(), Math.random() ,0.5);

        try {
            input = new BufferedReader( new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(), true);
        }
        catch (IOException e) {
            System.out.println("Player died: " + e);
        }
        output.println("WELCOME");
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public void parseMessage(String message){
        //message is null, removing client
        if(message == null){
            System.out.println("QUITTING");
            playing = false;
            if (game != null) {
                game.removePlayers();
            }
            else{
                hub.removePlayers();
            }
            return;
        }
        //Join;LobbyNumber
        else if(SimpleParser.parse(message).equals("Join")){
            message = SimpleParser.cut(message);
            int number = Integer.parseInt(SimpleParser.parse(message));
            hub.enter(this, number);
        }
        //Nick;PlayerNick
        else if(SimpleParser.parse(message).equals("Nick")){
            message = SimpleParser.cut(message);
            nick = message;
        }
        //Leave;
        else if(SimpleParser.parse(message).equals("Leave")){
            if(game != null) {
                game.enter(this, 0);
            }
        }
        //MESSAGE TO RESEND
        else if (game != null){
            game.resendMessage(message);
        }
    }

    @Override
    public void sendMessage(String message){
        output.println(message);
    }

    @Override
    public void run() {
        try {
            System.out.println("HUMAN PLAYER STARTING" );
            while (playing) {
                String string = input.readLine();
                System.out.println("GAME MESSAGE" + string);
                parseMessage(string);
            }
        }
        catch (IOException e) {
            System.out.println("Player died: " + e);
        }
    }







}
