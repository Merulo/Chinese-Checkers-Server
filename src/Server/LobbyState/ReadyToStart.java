package Server.LobbyState;

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
        lobbyState.setState(new Open());
        lobbyState.getGame().resetCountdown();
    }
    @Override
    public void handleLobby(LobbyState lobbyState){
        if (!lobbyState.getGame().validatePlayerCount()){
            lobbyState.prevPhase();
        }
        if(!lobbyState.getGame().validatePlayerReady()){
            lobbyState.prevPhase();
        }
        lobbyState.getGame().countDown();
        if(lobbyState.getGame().getCountDown() == 0){
            lobbyState.getGame().startGame();
        }

    }
    @Override
    public String getName(){
        return "Ready to start";
    }
}
