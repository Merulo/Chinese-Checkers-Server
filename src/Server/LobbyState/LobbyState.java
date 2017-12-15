package Server.LobbyState;

import Server.Network.Game;

public class LobbyState {
    //current state of lobby
    private State state;
    private Game game;

    //newly created lobby is open
    public LobbyState(Game game){
        this.game = game;
        state = new ReadyToStart();
    }

    //changes the state of lobby
    void setState(State state) {
        this.state = state;
    }

    //moves lobby to the next phase
    void nextPhase(){
        state.changeStateNext(this);
    }

    //moves lobby to the previous phase
    void prevPhase(){
        state.changeStatePrev(this);
    }

    //handles the logic lobby
    public void handleLobby(){
        state.handleLobby(this);
    }

    //returns the state of lobby
    public State getState(){return state;}

    public Game getGame() {
        return game;
    }
}
