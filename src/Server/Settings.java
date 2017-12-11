package Server;
//TODO: FIND COZY PLACE FOR THIS CLASS

import Server.LobbyState.LobbyState;
import Server.Rules.Move;
import java.util.List;

public class Settings {
    private LobbyState lobbyState;
    private String gameName;
    private int pawnNumber = 10;
    private int maxPlayerNumber = 6;
    private int gameNumber;
    private List<Move> moves;

    public Settings(String name, int gameNumber){
        gameName = name;
        this.gameNumber = gameNumber;
        lobbyState = new LobbyState();
        pawnNumber = 10;
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
        result += Integer.toString(pawnNumber) + ";";
        result += lobbyState.getState().getName() + ";";
        result += "Nazwa Zasady nr1;";
        result += "Nazwa Zasady nr2;";
        return result;
    }

    public int getMaxPlayerNumber(){
        return maxPlayerNumber;
    }

    public LobbyState getLobbyState() {
        return lobbyState;
    }
}
