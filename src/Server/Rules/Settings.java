package Server.Rules;

import Server.LobbyState.LobbyState;
import Server.LobbyState.ReadyToStart;
import Server.Network.Lobby;
import Server.Network.Hub;
import Server.SimpleParser;

public class Settings {
    private LobbyState lobbyState;
    private String gameName;
    private int size = 5;
    private int maxPlayerNumber = 4;
    private int gameNumber;
    private Hub hub;
    private MoveDecorator moveDecorator;

    public Settings(Hub hub, Lobby lobby, String name, int gameNumber){
        gameName = name;
        this.hub = hub;
        this.gameNumber = gameNumber;
        moveDecorator = new MoveDecorator();
        moveDecorator.setPawnNumber(sizeToPawnCount());
        lobbyState = new LobbyState(lobby);
        size = 5;
    }

    public int maxPlayerNumber(){
        return maxPlayerNumber;
    }

    public MoveDecorator getMoveDecorator(){
        return moveDecorator;
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
        int i = 0;

        for(MoveRule moveRule : moveDecorator.getMoveRules()){
            if(!hub.getMoveRules().contains(moveRule))
                builder.append(Integer.toString(i)).append(";").append(moveRule.getName()).append(";");
            i++;
        }

        i = 0;
        builder.append("RuleOff;");

        for(MoveRule moveRule : hub.getMoveRules()){
            if(!builder.toString().contains(moveRule.getName()))
                builder.append(Integer.toString(i)).append(";").append(moveRule.getName()).append(";");
            i++;
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

    public boolean handleSettingsChange(String message, int count){
        message = SimpleParser.cut(message);
        String option = SimpleParser.parse(message);
        String values = SimpleParser.cut(message);

        if (lobbyState.getState() instanceof ReadyToStart){
            return false;
        }

        switch (option){
            case "Players":{
                if(Integer.parseInt(values) >= count) {
                    maxPlayerNumber = Integer.parseInt(values);
                    return true;
                }
                return false;
            }
            case "Size":{
                if(size != Integer.parseInt(values)) {
                    size = Integer.parseInt(values);
                    moveDecorator.setPawnNumber(sizeToPawnCount());
                    return true;
                }
                return false;
            }
            case "RuleOn":{
                for(MoveRule moveRule : hub.getMoveRules()){
                    if(moveRule.getName().equals(values)){
                        moveDecorator.addRule(moveRule.makeCopy());
                        return true;
                    }
                }
                return false;
            }
            case "RuleOff":{
                for(MoveRule moveRule : hub.getMoveRules()){
                    if(moveRule.getName().equals(values)){
                        moveDecorator.removeRule(moveRule.makeCopy());
                        return true;
                    }
                }
                return false;
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
