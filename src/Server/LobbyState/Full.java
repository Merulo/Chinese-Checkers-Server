package Server.LobbyState;

/**@author Damian Nowak
 * CONDITION: (ALL MUST BE TRUE)
 * The lobby is full
 * BEHAVIOUR:
 * No joining allowed
 * Settings can be changed
 */
public class Full implements State {

    //moves to the next step
    @Override
    public void changeStateNext(LobbyState lobbyState){
        lobbyState.setState(new ReadyToStart());
    }

    //moves to the previous step
    @Override
    public void changeStatePrev(LobbyState lobbyState){
        lobbyState.setState(new Open());
        lobbyState.getLobby().resetCountdown();
    }

    //handles the lobby in this state
    @Override
    public void handleLobby(LobbyState lobbyState){
        if (!lobbyState.getLobby().isFull()){
            lobbyState.prevPhase();
        }
        else if(lobbyState.getLobby().validatePlayerReady()){
            lobbyState.nextPhase();
        }

    }

    //returns state name
    @Override
    public String getName(){
        return "Full";
    }
}
