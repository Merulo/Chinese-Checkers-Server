package Server.Network;

import Server.Map.Map;
import Server.Map.MapPoint;
import Server.Player.AbstractPlayer;
import Server.Player.ComputerPlayer;
import Server.Player.HumanPlayer;
import Server.Rules.MoveDecorator;
import Server.SimpleParser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

/**@author Damian Nowak
 * The Game class focuses on handling in game events
 */
public class Game implements NetworkManager {
    //list of players
    volatile private ArrayList<AbstractPlayer> players;
    //the main hub
    private Hub hub;
    //the main lobby
    private Lobby lobby;
    //MoveDecorator
    private MoveDecorator moveDecorator;
    //current player
    private AbstractPlayer currentPlayer;

    public Game(ArrayList<AbstractPlayer> players, Hub hub, Lobby lobby, MoveDecorator moveDecorator){
        this.hub = hub;
        this.lobby = lobby;
        this.players = players;
        this.moveDecorator = moveDecorator;
        Map map = new Map(moveDecorator.getPawnNumber());
        moveDecorator.setMap(map);
        map.setUpMap(players);

        players.get(0).setReady(false);

        currentPlayer = players.get(new Random().nextInt(players.size()));

        for(AbstractPlayer player : this.players){
          if (player instanceof ComputerPlayer){
              ((ComputerPlayer)player).setMoveDecorator(moveDecorator);
              player.setNetworkManager(this);
          }
        }

        for(AbstractPlayer player : this.players){
            player.sendMessage("YourColor;" + player.getData());
        }

        currentPlayer.sendMessage("YourTurn;");
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
        System.out.println("GAME MESSAGE: " + message);
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
            case "Skip":{
                handleSkip(abstractPlayer);
                break;
            }
            case "Leave":{
                replacePlayerWithComputer(abstractPlayer);
                break;
            }
        }
    }

    public boolean areThereOnlyBotsInGame(){
        for(AbstractPlayer abstractPlayer : players){
            if (abstractPlayer instanceof HumanPlayer){
                return false;
            }
        }

        return true;
    }

    private void replacePlayerWithComputer(AbstractPlayer player){
        System.out.println(player.getNick() + " should be replaced by Computer");


        for(int i =0; i < players.size(); i++){
            if(players.get(i) == player){
                ComputerPlayer computerPlayer = new ComputerPlayer();
                computerPlayer.setMoveDecorator(moveDecorator);
                computerPlayer.setNetworkManager(this);
                players.add(i, computerPlayer);
                players.remove(player);

                if(areThereOnlyBotsInGame()){
                    break;
                }

                if(currentPlayer == player){
                    currentPlayer = getNextCurrentPlayer();
                    currentPlayer.sendMessage("YourTurn;");
                }

                moveDecorator.replacePlayer(player, computerPlayer);

                break;
            }
        }

        player.setNetworkManager(hub);
        hub.sendGame(lobby);
        hub.addPlayer(player);
    }

    //returns the current time stamp
    private String getTimeStamp(){
        return "<" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + "> ";
    }

    private synchronized void resendMessage(String message, AbstractPlayer abstractPlayer){
        String result = "Msg;" + getTimeStamp();
        if (abstractPlayer == null){
            result+= "Server";
        }
        else{
            message = SimpleParser.cut(message);
            System.out.println("TESTING: " + message);
            result += abstractPlayer.getNick();
        }

        result+= ": " + message;

        System.out.println("Result: " + result);

        for(AbstractPlayer player : players){
            if(player.isPlaying()){
                player.sendMessage(result);
            }
        }
    }

    private void parseMoveMessage(String message, AbstractPlayer abstractPlayer){
        if(abstractPlayer != currentPlayer){
            abstractPlayer.sendMessage("NotYourTurn;");
            return;
        }

        if(abstractPlayer instanceof ComputerPlayer && haveAllHumansWon()){
            return;
        }

        ArrayList<MapPoint> mapPoints = parseStringToArrayOfMapPoints(message);
        if(!(mapPoints.size() > 0)){
            abstractPlayer.sendMessage("IncorrectMove;");
            return;
        }
        MapPoint first = mapPoints.get(0).copy();
        MapPoint last = mapPoints.get(mapPoints.size() - 1).copy();

        boolean result =  moveDecorator.checkMove(mapPoints, abstractPlayer);

        if(result){
            handleConfirmedMove(first, last, abstractPlayer);

            if(checkWinning(abstractPlayer)){
                resendMessage(abstractPlayer.getNick() + " has won the game!", null);
                abstractPlayer.setHasWon(true);
            }
        }
        else{
            abstractPlayer.sendMessage("IncorrectMove;");
        }


    }

    private boolean hasEverybodyWon(){
        for(AbstractPlayer abstractPlayer : players){
            if (!abstractPlayer.getHasWon()){
                return false;
            }
        }
        return true;
    }

    private boolean haveAllHumansWon(){
        for(AbstractPlayer abstractPlayer : players){
            if(abstractPlayer instanceof HumanPlayer){
                if(!abstractPlayer.getHasWon()){
                    return false;
                }
            }
        }


        return true;
    }

    private AbstractPlayer getNextCurrentPlayer(){
        int tmp = 0;

        if(hasEverybodyWon()){
            resendMessage("Everybody has won! End of game!", null);
        }

        for(int i = 0; i < players.size(); i++){
            if(players.get(i) == currentPlayer){
                tmp = (i + 1) % players.size();
                break;
            }
        }

        if(players.get(tmp).getHasWon()){
            currentPlayer = players.get(tmp);
            return getNextCurrentPlayer();
        }

        return players.get(tmp);
    }

    private void handleSkip(AbstractPlayer abstractPlayer){
        if(abstractPlayer instanceof ComputerPlayer && haveAllHumansWon()){
            return;
        }
        if(currentPlayer == abstractPlayer) {
            currentPlayer = getNextCurrentPlayer();
            currentPlayer.sendMessage("YourTurn;");
        }
    }

    private ArrayList<MapPoint> parseStringToArrayOfMapPoints(String message){
        message = SimpleParser.cut(message);
        ArrayList<MapPoint> mapPoints = new ArrayList<>();
        while (!message.isEmpty()){
            String point = SimpleParser.parse(message);
            message = SimpleParser.cut(message);
            int y = Integer.parseInt(SimpleParser.parse(point, ","));
            point = SimpleParser.cut(point, ",");
            int x = Integer.parseInt(point);
            mapPoints.add(new MapPoint(x, y));
        }
        return mapPoints;
    }

    private void handleConfirmedMove(MapPoint first, MapPoint last, AbstractPlayer abstractPlayer){
        moveDecorator.doMove(first, last, abstractPlayer);

        String tmp = "Move;";
        //map.printMap();
        tmp+=Integer.toString(first.getY()) + "," + Integer.toString(first.getX()) + ";";
        tmp+=Integer.toString(last.getY()) + "," + Integer.toString(last.getX()) + ";";

        for (AbstractPlayer player : players){
            player.sendMessage(tmp);
        }

        currentPlayer = getNextCurrentPlayer();
        currentPlayer.sendMessage("YourTurn;");
    }

    private boolean checkWinning(AbstractPlayer abstractPlayer){
        Map map = moveDecorator.getMap();
        return map.checkWin(abstractPlayer);
    }
}
