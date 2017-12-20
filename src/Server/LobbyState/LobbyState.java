package Server.LobbyState;

import Server.Network.Game;
import Server.Network.Lobby;

public class LobbyState {
    //current state of lobby
    private State state;
    //the lobby the lobbyState belongs to
    private Lobby lobby;
    //the game that lobbyState is playing
    private Game game;

    //newly created lobby is open
    public LobbyState(Lobby lobby){
        this.lobby = lobby;
        state = new ReadyToStart();
    }

    //handles the logic lobby
    public void handleLobby(){
        state.handleLobby(this);
    }

    //changes the state of lobby
    void setState(State state) {
        this.state = state;
    }

    //moves lobby to the next phase
    void nextPhase(){
        state.changeStateNext(this);
        lobby.sendDetailedGameData();
    }

    //moves lobby to the previous phase
    void prevPhase(){
        state.changeStatePrev(this);
        lobby.sendDetailedGameData();
    }

    //returns the state of lobby
    public State getState(){return state;}

    //return the lobby
    Lobby getLobby() {
        return lobby;
    }

    //sets game
    public void setGame(Game game) {
        this.game = game;
    }
}
