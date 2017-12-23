package Server.LobbyState;

/**CONDITION: (ALL MUST BE TRUE)
 * The lobby is full
 * BEHAVIOUR:
 * No joining allowed
 * Settings can be changed
 */
public class Full implements State {
    @Override
    public void changeStateNext(LobbyState lobbyState){
        lobbyState.setState(new ReadyToStart());
    }
    @Override
    public void changeStatePrev(LobbyState lobbyState){
        lobbyState.setState(new Open());
        lobbyState.getLobby().resetCountdown();
    }
    @Override
    public void handleLobby(LobbyState lobbyState){
        if (!lobbyState.getLobby().isFull()){
            lobbyState.prevPhase();
        }
        else if(lobbyState.getLobby().validatePlayerReady()){
            lobbyState.nextPhase();
        }

    }
    @Override
    public String getName(){
        return "Full";
    }
}
