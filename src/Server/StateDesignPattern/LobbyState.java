package Server.StateDesignPattern;

public class LobbyState {
    private State state;
    public LobbyState(){
        state = new Open();
    }

    public void setState(State state) {
        this.state = state;
    }

    public void nextPhase(){
        state.changeStateNext(this);
    }

    public void prevPhase(){
        state.changeStatePrev(this);
    }

    public void handleLobby(){
        state.handleLobby(this);
    }
}
