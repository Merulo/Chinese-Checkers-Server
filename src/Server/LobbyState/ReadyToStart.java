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
public class ReadyToStart implements State {
    @Override
    public void changeStateNext(LobbyState lobbyState){
        lobbyState.setState(new Playing());
    }
    @Override
    public void changeStatePrev(LobbyState lobbyState){
        lobbyState.setState(new Full());
        lobbyState.getLobby().resetCountdown();
    }
    @Override
    public void handleLobby(LobbyState lobbyState){
        if (!lobbyState.getLobby().validatePlayerCount()){
            lobbyState.prevPhase();
        }
        if(!lobbyState.getLobby().validatePlayerReady()){
            lobbyState.prevPhase();
        }
        lobbyState.getLobby().countDown();
        if(lobbyState.getLobby().getCountDown() == 0){
            lobbyState.getLobby().startGame();
            lobbyState.setGame(lobbyState.getLobby().getGame());
            lobbyState.nextPhase();
        }

    }
    @Override
    public String getName(){
        return "Ready to start";
    }
}
