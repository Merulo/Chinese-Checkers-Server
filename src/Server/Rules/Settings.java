package Server.Rules;
//TODO: FIND COZY PLACE FOR THIS CLASS

import Server.LobbyState.LobbyState;
import Server.Network.Game;
import Server.SimpleParser;

public class Settings {
    private LobbyState lobbyState;
    private String gameName;
    private int size = 5;
    private int maxPlayerNumber = 4;
    private int gameNumber;
    private int recentyChanged;

    public Settings(Game game, String name, int gameNumber){
        gameName = name;
        this.gameNumber = gameNumber;
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
        result += "Nazwa Zasady nr1;";
        result += "Nazwa Zasady nr2;";
        return result;
    }

    public int getRecentlyChanged(){
        return recentyChanged;
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
                recentyChanged = maxPlayerNumber;
                return true;
            }
            return false;
        }
        else if(option.equals("Size")){
            if(size != Integer.parseInt(values)) {
                size = Integer.parseInt(values);
                recentyChanged = size;
                return true;
            }
            return false;
        }


        return false;
    }
}
