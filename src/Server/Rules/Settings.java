package Server.Rules;
//TODO: FIND COZY PLACE FOR THIS CLASS

import Server.LobbyState.LobbyState;
import Server.Network.Game;
import Server.Network.Hub;
import Server.SimpleParser;

import java.util.ArrayList;

public class Settings {
    private LobbyState lobbyState;
    private String gameName;
    private int size = 5;
    private int maxPlayerNumber = 4;
    private int gameNumber;
    private Hub hub;
    private MoveDecorator moveDecorator;

    public Settings(Hub hub, Game game, String name, int gameNumber){
        gameName = name;
        this.hub = hub;
        this.gameNumber = gameNumber;
        moveDecorator = new MoveDecorator();
        moveDecorator.setPawnNumber(sizeToPawnCount());
        lobbyState = new LobbyState(game);
        size = 5;
    }

    public int maxPlayerNumber(){
        return maxPlayerNumber;
    }

    public String getGeneralData( int playerCount){
        String result = "GameData;";
        result += Integer.toString(gameNumber) + ";";
        result += gameName + ";";
        result += Integer.toString(playerCount) + ";";
        result += maxPlayerNumber + ";";
        result += lobbyState.getState().getName() + ";";
        return result;
    }

    public String getDetailedData(int playerCount){
        String result = "GameDetailedData;";
        result += gameName + ";";
        result += Integer.toString(playerCount) + ";";
        result += maxPlayerNumber + ";";
        result += Integer.toString(size) + ";";
        result += lobbyState.getState().getName() + ";";

        StringBuilder builder = new StringBuilder();
        builder.append("RuleOn;");

        for(MoveRule moveRule : moveDecorator.getMoveRules()){
            builder.append(moveRule.getName()).append(";");
        }

        builder.append("RuleOff;");

        for(MoveRule moveRule : hub.getMoveRules()){
            if(!builder.toString().contains(moveRule.getName()))
                builder.append(moveRule.getName()).append(";");
        }
        result += builder.toString();
        return result;
    }

    public int getMaxPlayerNumber(){
        return maxPlayerNumber;
    }

    public LobbyState getLobbyState() {
        return lobbyState;
    }

    public boolean handleSettingsChange(String message){
        message = SimpleParser.cut(message);
        String option = SimpleParser.parse(message);
        String values = SimpleParser.cut(message);
        if(option.equals("Players")){
            if(maxPlayerNumber != Integer.parseInt(values)) {
                maxPlayerNumber = Integer.parseInt(values);
                return true;
            }
            return false;
        }
        else if(option.equals("Size")){
            if(size != Integer.parseInt(values)) {
                size = Integer.parseInt(values);
                moveDecorator.setPawnNumber(sizeToPawnCount());
                return true;
            }
            return false;
        }
        else if(option.equals("RuleOn")){
            for(MoveRule moveRule : hub.getMoveRules()){
                if(moveRule.getName().equals(values)){
                    moveDecorator.addRule(moveRule.makeCopy());
                    return true;
                }
            }
        }
        else if(option.equals("RuleOff")){
            for(MoveRule moveRule : hub.getMoveRules()){
                if(moveRule.getName().equals(values)){
                    moveDecorator.removeRule(moveRule.makeCopy());
                    return true;
                }
            }
        }


        return false;
    }

    private int sizeToPawnCount(){
        int count = 0;
        for(int i = 0; i < size; i++){
            count+= i;
        }
        return count;
    }
}
