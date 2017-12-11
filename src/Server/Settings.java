package Server;
//TODO: FIND COZY PLACE FOR THIS CLASS

import Server.LobbyState.LobbyState;
import Server.Rules.Move;
import java.util.List;

public class Settings {
    private LobbyState lobbyState;
    String gameName;
    int pawnNumber = 10;
    int maxPlayerNumber = 6;
    int gameNumber;
    List<Move> moves;

    public Settings(String name, int gameNumber){
        gameName = name;
        this.gameNumber = gameNumber;
        lobbyState = new LobbyState();
    }

    public String getGeneralData( int playerCount){
        String result = "GameData;";
        result += Integer.toString(gameNumber + 1) + ";";
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


}
