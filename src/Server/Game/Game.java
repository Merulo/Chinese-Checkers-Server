package Server.Game;

import Server.Player.AbstractPlayer;
import Server.Player.Player;

import javax.swing.*;
import java.net.Socket;
import java.util.ArrayList;

public class Game{
    AbstractPlayer player1;
    AbstractPlayer player2;

    public Game(){

    }

    public void addPlayer(Socket socket){
        if (player1 == null){
            player1 = new AbstractPlayer(socket);
            System.out.println("Nowy klient 1");
            return;
        }
        else if (player2 == null){
            player2 = new AbstractPlayer(socket);
            System.out.println("Nowy klient 2");
            start();
        }

    }

    public void start(){
        System.out.println("Rozpoczynam");
        player1.start();
        player2.start();
    }


}
