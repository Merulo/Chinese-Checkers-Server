package Server.Network;

import Server.LobbyState.Open;
import Server.Player.AbstractPlayer;
import Server.Player.ComputerPlayer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import Server.Rules.Settings;
import Server.SimpleParser;

//Lobby/Lobby class
public class Lobby implements NetworkManager {
    //list of players connected to the game
    volatile private ArrayList<AbstractPlayer> players;
    //Hub variable
    private Hub hub;
    //variable with the settings of the game
    private Settings settings;
    //max countdown
    private final int maxCountDown = 3;
    //countdown variable
    private int countDown = maxCountDown;
    //countdown time variable
    private long startMillis;
    //the game variable
    private Game game;


    //creates game, sets hub, and gives the game number
    Lobby(Hub hub, int number){
        players = new ArrayList<>();
        settings = new Settings(hub, this, "Lobby: " + Integer.toString(number + 1), number);
        this.hub = hub;
        startMillis = System.currentTimeMillis();
    }

    //adds player to the lobby, sets the player variables
    @Override
    public synchronized void addPlayer(AbstractPlayer player){
        player.setNetworkManager(this);
        player.sendMessage(settings.getDetailedData(players.size()));

        for(AbstractPlayer abstractPlayer : players){
            abstractPlayer.sendMessage(player.getData());
        }

        players.add(player);

        for(AbstractPlayer abstractPlayer : players){
            player.sendMessage(abstractPlayer.getData());
        }

        hub.sendGame(this);
    }

    //removes the client form the list if the connection was closed
    @Override
    public synchronized void removePlayers(){
        try {
            for(int i = 0; i < players.size(); i++){
                if(!players.get(i).isPlaying()){
                    for(AbstractPlayer abstractPlayer : players){
                        if(abstractPlayer != players.get(i))
                            abstractPlayer.sendMessage( "Remove:" +  players.get(i).getNick() + ";");
                    }
                    players.remove(i);
                    i = i - 1;
                }
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        removeAdminBots();
        hub.sendGame(this);
    }

    //moves the player from game to hub
    @Override
    public synchronized void enter(AbstractPlayer client, int number){
        System.out.println("MOVING FROM LOBBY TO HUB");
        players.remove(client);
        for(AbstractPlayer abstractPlayer : players){
            abstractPlayer.sendMessage("Remove;" + client.getNick() + ";");
        }

        removeAdminBots();
        hub.sendGame(this);
        hub.addPlayer(client);
    }

    @Override
    public synchronized void parse(AbstractPlayer abstractPlayer, String message){
        String type = SimpleParser.parse(message);
        switch (type) {
            case "Settings": {
                handleSettings(message, abstractPlayer);
                break;
            }
            case "Leave":{
                enter(abstractPlayer, 0);
                break;
            }
            case "Msg":{
                message = SimpleParser.cut(message);
                resendMessage(message, abstractPlayer);
                break;
            }
            case "AddBot":{
                addBot();
                break;
            }
            case "RemoveBot":{
                removeAbstractPlayer(message);
                break;
            }
            case "Kick":{
                removePlayer(message, abstractPlayer);
            }

        }
    }

    private void removePlayer(String message, AbstractPlayer abstractPlayer){
        if (abstractPlayer == players.get(0)){
            message = SimpleParser.cut(message);
            int i = Integer.parseInt(message);
            i--;
            if(i < players.size() && i != 0) {
                removeAbstractPlayer(players.get(i).getNick());
            }
        }
    }

    private void removeAdminBots(){
        if(players.size() > 0) {
            if (players.get(0) instanceof ComputerPlayer) {
                AbstractPlayer computer = players.get(0);
                players.remove(computer);

                for(AbstractPlayer player : players){
                    player.sendMessage("Remove;" + computer.getNick());
                }
                removeAdminBots();
            }
        }
    }

    //returns game data which can be send to other players
    String getGameData(){
        return settings.getGeneralData(players.size());
    }

    //returns true if it is possible to join the game
    boolean canJoin(){
        return settings.getMaxPlayerNumber()>players.size()
                && (settings.getLobbyState().getState() instanceof Open);
    }

    //resend messages to other players in current game
    private synchronized void resendMessage(String message, AbstractPlayer abstractPlayer){
        message = SimpleParser.cut(message);
        String result = "Msg;" + getTimeStamp();
        if (abstractPlayer == null){
            result+= "Server";
        }
        else{
            result += abstractPlayer.getNick();
        }

        result+= ": " + message;

        for(AbstractPlayer player : players){
            if(player.isPlaying()){
                player.sendMessage(result);
            }
        }
    }

    //handles settings changes
    private void handleSettings(String message, AbstractPlayer p){
        boolean result;
        if(p == players.get(0)){
            result = settings.handleSettingsChange(message, players.size());
        }
        else{
            p.sendMessage(settings.getDetailedData(players.size()));
            return;
        }

        if(result) {
            for (AbstractPlayer player : players) {
                player.sendMessage(settings.getDetailedData(players.size()));
            }
            hub.sendGame(this);
        }
        else {
            p.sendMessage(settings.getDetailedData(players.size()));
        }
    }

    //sends detailed game data
    public void sendDetailedGameData(){
        hub.sendGame(this);
    }

    //handles lobby
    void handleLobby(){
        settings.getLobbyState().handleLobby();
    }

    //validates the player count
    public boolean validatePlayerCount(){
        int playerCount = players.size();
        return (playerCount == 2 || playerCount == 3 || playerCount == 4|| playerCount == 6);
    }

    //validates readiness of player
    public boolean validatePlayerReady(){
        if(players.size() > 0 && players.get(0).isReady()) {
            if (settings.getMoveDecorator().getMoveRules().size() == 0) {
                resendMessage("Select more rules!", null);
                players.get(0).setReady(false);
                players.get(0).sendMessage("Cancel");
                return false;
            }
            return true;
        }

        return false;
    }

    //resets countdown
    public void resetCountdown(){
        countDown = maxCountDown;
        startMillis = System.currentTimeMillis();
    }

    //counts down
    public void countDown(){
        if ((System.currentTimeMillis() - this.startMillis) / 1000.0 > 1) {
            startMillis = System.currentTimeMillis();
            countDown--;
            for (AbstractPlayer player : players) {
                player.sendMessage("Countdown;" + Integer.toString(countDown) + ";");
            }
        }
    }

    //returns countdown
    public int getCountDown(){
        return countDown;
    }

    //cares the game instance
    public void startGame(){
        for (AbstractPlayer player : players) {
                player.sendMessage("Start;TBA;");
        }
        game = new Game(players, hub, this, settings.getMoveDecorator());

        for (AbstractPlayer player : players) {
            player.setNetworkManager(game);
        }
    }

    //returns the current time stamp
    private String getTimeStamp(){
        return "<" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + "> ";
    }

    //returns true if lobby is full
    public boolean isFull(){
        return (settings.getMaxPlayerNumber() == players.size());
    }

    //adds bot
    private void addBot(){
        if(settings.getMaxPlayerNumber() == players.size()){
            return;
        }

        AbstractPlayer player = new ComputerPlayer();

        for(AbstractPlayer abstractPlayer : players){
            abstractPlayer.sendMessage(player.getData());
        }
        players.add(player);
        hub.sendGame(this);
    }

    //removes player
    private void removeAbstractPlayer(String message){
        boolean removed = false;
        String nick = "";
        for (AbstractPlayer abstractPlayer : players) {
            if (abstractPlayer.getNick().equals(message)) {
                if (abstractPlayer instanceof ComputerPlayer) {
                    nick = abstractPlayer.getNick();
                    players.remove(abstractPlayer);
                    hub.sendGame(this);
                    removed = true;
                    break;
                }
                else {
                    //removes normal players from lobby
                    abstractPlayer.sendMessage("Leave;");
                    nick = abstractPlayer.getNick();
                    enter(abstractPlayer, 0);
                    removed = true;
                    break;
                }
            }
        }

        if(removed) {
            for (AbstractPlayer player : players) {
                player.sendMessage("Remove;" + nick + ";");
            }
        }
    }

    public Game getGame() {
        return game;
    }
}
