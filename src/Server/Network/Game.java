package Server.Network;

import Server.Map.Map;
import Server.Map.MapPoint;
import Server.Player.AbstractPlayer;
import Server.Player.ComputerPlayer;
import Server.Rules.MoveDecorator;
import Server.SimpleParser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class Game implements NetworkManager {
    //list of players
    volatile private List<AbstractPlayer> players;
    //the main hub
    private Hub hub;
    //the main lobby
    private Lobby lobby;
    //map
    private Map map;
    //MoveDecorator
    private MoveDecorator moveDecorator;
    //current player
    private AbstractPlayer currentPlayer;

    Game(ArrayList<AbstractPlayer> players, Hub hub, Lobby lobby, MoveDecorator moveDecorator){
        this.hub = hub;
        this.lobby = lobby;
        this.players = players;
        this.moveDecorator = moveDecorator;
        map = new Map(moveDecorator.getPawnNumber());
        moveDecorator.setMap(map);
        map.setPlayers(players);
        map.setUpMap();
        currentPlayer = players.get(new Random().nextInt(players.size()));

        for(AbstractPlayer player : this.players){
          if (player instanceof ComputerPlayer){
              ((ComputerPlayer)player).setMoveDecorator(moveDecorator);
          }
        }
    }


    @Override
    public synchronized void addPlayer(AbstractPlayer player) {
        //no more adding players : )
    }

    @Override
    public synchronized void removePlayers(){
        try {
            for(int i = 0; i < players.size(); i++){
                if(!players.get(i).isPlaying()){
                    replacePlayerWithComputer(players.get(i));
                    for(AbstractPlayer abstractPlayer : players){
                        if(abstractPlayer != players.get(i))
                            abstractPlayer.sendMessage( "Remove:" +  players.get(i).getNick() + ";");
                    }
                    players.remove(i);
                    i = i - 1;
                }
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        hub.sendGame(lobby);
    }

    //moves the player from game to hub
    @Override
    public synchronized void enter(AbstractPlayer client, int number){
        System.out.println("MOVING FROM GAME TO HUB");
        players.remove(client);
        replacePlayerWithComputer(client);
        for(AbstractPlayer abstractPlayer : players){
            abstractPlayer.sendMessage("Remove;" + client.getNick() + ";");
        }

        hub.sendGame(lobby);
        hub.addPlayer(client);
    }

    @Override
    public synchronized void parse(AbstractPlayer abstractPlayer, String message){
        String type = SimpleParser.parse(message);
        switch (type) {
            case "Moves": {
                parseMoveMessage(message, abstractPlayer);
                break;
            }
            case "Msg":{
                resendMessage(message, abstractPlayer);
                break;
            }
        }
    }

    private void replacePlayerWithComputer(AbstractPlayer player){
        System.out.println(player.getNick() + " should be replaced by Computer");
        //TODO: IMPLEMENT THIS METHOD
    }

    //returns the current time stamp
    private String getTimeStamp(){
        return "<" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + "> ";
    }

    private synchronized void resendMessage(String message, AbstractPlayer abstractPlayer){
        message = SimpleParser.cut(message);
        String result = "Msg;" + getTimeStamp() + abstractPlayer.getNick() + ": " + message;

        for(AbstractPlayer player : players){
            if(player.isPlaying()){
                player.sendMessage(result);
            }
        }
    }

    private void parseMoveMessage(String message, AbstractPlayer abstractPlayer){
        if(abstractPlayer != currentPlayer){
            abstractPlayer.sendMessage("NotYourTurn;");
            abstractPlayer.sendMessage("But you can still move, since this is BETA");
        }

        ArrayList<MapPoint> mapPoints = new ArrayList<>();
        message = SimpleParser.cut(message);
        MapPoint first;
        MapPoint last;


        while (!message.isEmpty()){
            String point = SimpleParser.parse(message);
            message = SimpleParser.cut(message);
            int y = Integer.parseInt(SimpleParser.parse(point, ","));
            point = SimpleParser.cut(point, ",");
            int x = Integer.parseInt(point);
            System.out.println("New point in list: (" + x + "," + y + ")");
            mapPoints.add(new MapPoint(x, y));

        }
        boolean result =  moveDecorator.checkMove(mapPoints, abstractPlayer);

        System.out.println("BOOLEAN: " + result);
        if(result){
            //TODO: RESULT == TRUE SEND THE MOVE TO ALL PLAYERS
            //TODO: RESULT == TRUE CHECK IF PLAYER HAS WON
            first = mapPoints.get(0).copy();
            last = mapPoints.get(mapPoints.size() - 1).copy();

            String tmp = "Move;";
            tmp+=Integer.toString(first.getY()) + "," + Integer.toString(first.getX()) + ";";
            tmp+=Integer.toString(last.getY()) + "," + Integer.toString(last.getX()) + ";";

            for (AbstractPlayer player : players){
                player.sendMessage( tmp);
            }
        }
        else{
            abstractPlayer.sendMessage("IncorrectMove;");
        }

        //TODO: RESULT == FALSE SEND INFO TO abstractPlayer


    }



}
