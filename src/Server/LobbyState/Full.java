package Server.LobbyState;

//TODO: UPDATE CONDITIONS
/**CONDITION: (ALL MUST BE TRUE)
 * All players are ready
 * Correct number of players
 * BEHAVIOUR:
 * No changes allowed
 * No joining allowed
 * Counting down from 10 to 0 to begin game
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
