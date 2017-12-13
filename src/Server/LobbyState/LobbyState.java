package Server.LobbyState;

public class LobbyState {
    //current state of lobby
    private State state;

    //newly created lobby is open
    public LobbyState(){
        state = new Open();
    }

    //changes the state of lobby
    void setState(State state) {
        this.state = state;
    }

    //moves lobby to the next phase
    public void nextPhase(){
        state.changeStateNext(this);
    }

    //moves lobby to the previous phase
    public void prevPhase(){
        state.changeStatePrev(this);
    }

    //handles the logic lobby
    public void handleLobby(){
        state.handleLobby(this);
    }

    //returns the state of lobby
    public State getState(){return state;}
}
