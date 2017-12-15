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

public class HumanPlayer extends AbstractPlayer {
    private BufferedReader input;
    private PrintWriter output;
    private Hub hub;

    public HumanPlayer(Socket socket, Hub hub) {
        this.hub = hub;
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

    private void parseMessage(String message){
        //message is null, removing client
        if(message == null){
            handleNullMessage();
            return;
        }
        String type = SimpleParser.parse(message);
        //Join;LobbyNumber
        if(type.equals("Join")){
            handleJoinMessage(message);
        }
        //Nick;PlayerNick
        else if(type.equals("Nick")){
            handleNickMessage(message);
        }
        //Leave;
        else if(type.equals("Leave")){
            handleLeaveMessage();
        }
        //MESSAGE TO RESEND
        else if(type.equals("Msg")){
            handleMessage(message);
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

    private void handleNullMessage(){
        System.out.println("QUITTING");
        playing = false;
        if (game != null) {
            game.removePlayers();
        }
        else{
            hub.removePlayers();
        }
    }

    private void handleJoinMessage(String message){
        message = SimpleParser.cut(message);
        int number = Integer.parseInt(SimpleParser.parse(message));
        hub.enter(this, number);
    }

    private void handleNickMessage(String message){
        message = SimpleParser.cut(message);
        nick = message;
    }

    private void handleLeaveMessage(){
        if(game != null) {
            game.enter(this, 0);
        }
        else if (hub != null){
            playing = false;
            hub.removePlayers();
        }
    }

    private void handleMessage(String message){
        if (game != null){
            game.resendMessage(message);
        }
    }







}
