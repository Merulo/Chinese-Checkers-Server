package Server.LobbyState;

//Design-pattern Sate
//used by the Hub to change state of the game!

public interface State {
    //go to next phase
    void changeStateNext(LobbyState lobbyState);
    //go to previous phase
    void changeStatePrev(LobbyState lobbyState);
    //lobby logic should be implemented here
    void handleLobby(LobbyState lobbyState);
    //return name of the state as string
    String getName();
}
