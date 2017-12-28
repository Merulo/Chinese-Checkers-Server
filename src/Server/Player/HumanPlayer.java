package Server.Player;

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

    public HumanPlayer(Socket socket, Hub hub) {
        networkManager = hub;
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
                parseMessage(string);
            }
        }
        catch (IOException e) {
            System.out.println("Player died: " + e);
        }
    }

    private void parseMessage(String message){
        //message is null, removing client
        if(message == null){
            handleNullMessage();
            return;
        }
        String type = SimpleParser.parse(message);

        switch (type){
            case "Nick": {
                handleNickMessage(message);
                break;
            }
            case "Ready":
                ready = true;
                break;
            case "Cancel":
                ready = false;
                break;
            default:{
                networkManager.parse(this, message);
            }
        }
    }

    private void handleNullMessage(){
        System.out.println("QUITTING");
        playing = false;
        networkManager.removePlayers();
    }

    private void handleNickMessage(String message){
        message = SimpleParser.cut(message);
        nick = message;
    }







}
