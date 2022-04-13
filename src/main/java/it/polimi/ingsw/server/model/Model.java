package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.enumerations.GameState;
import it.polimi.ingsw.server.exception.GameAlreadyStartedException;
import it.polimi.ingsw.server.exception.MaxPlayerException;
import it.polimi.ingsw.server.exception.MissingPlayersException;
import it.polimi.ingsw.server.model.component.Component;
import it.polimi.ingsw.server.model.player.Player;

import java.util.ArrayList;
import java.util.List;

public class Model {
    public static final int MIN_PLAYERS = 2;
    public static final int MAX_PLAYERS = 4;

    private static Model model;
    private Game game;

    private boolean expertMode;

    private List<Player> players;
    private final ArrayList<Component> components;

    protected GameState currentState;

    private boolean gameStarted;
    private boolean endGame;

    int playerNumber;

    /**
     * Default constructor
     */
    public Model() {
        //init
        players = new ArrayList<>();
        components = null;
        expertMode = false;
        playerNumber = 0;
        gameStarted = false;
        currentState = GameState.GAME_ROOM;
    }

    public static Model getModel(){
        if (model == null)
            model = new Model();
        return model;
    }

    /**
     * Return the number of player
     */
    public int getNumberOfPlayer() {
        return players.size();
    }

    /**
     * Adds a player to the game.
     * Update the playersNumber.
     */
    public void addPlayer(Player player) throws GameAlreadyStartedException, MaxPlayerException {
        if (gameStarted)
            throw new GameAlreadyStartedException("NOT possible to add players when game is already started!");
        if (player == null) throw new NullPointerException("Player cannot be NULL");
        if (playerNumber >= MAX_PLAYERS) throw new MaxPlayerException("Max Number of players reached!");
        players.add(player);
        playerNumber++;
    }

    /**
     * Starts the game
     */
    public boolean startGame() throws GameAlreadyStartedException, MissingPlayersException {

        if (gameStarted) throw new GameAlreadyStartedException("Game is already in progress!");
        gameStarted = true;
        if(players.size() < MIN_PLAYERS) throw new MissingPlayersException("Minimum players is 2!");
        setState(GameState.GAME_STARTED);
        if(!expertMode){
            game = new Game(players);
        }
        else game = new ExpertGame(players);

        return true;
    }

    /**
     * @return true if the game gameStarted
     */
    public boolean isGameStarted() {
        return gameStarted;
    }

    /**
     * Set EndGame true
     */
    public void setEndGame(){
        currentState = GameState.GAME_ENDED;
        endGame = true;
    }

    /**
     * @return the state of EndGame
     */
    public boolean getEndGame() {
        return endGame;
    }

    /**
     * Set of operation to do in the endgame phase
     */
    public void endGamePhase(String message){
        while(endGame){
            switch(message){
                case "LAST_TOWER":
                case "ASSISTANT_CARD":
                case "ISLAND":
                case "LAST_STUDENT":
            }
        }
    }
    /**
     * @return the current state Game
     */
    public GameState getState() {
        return currentState;
    }

    /**
     * @param gameState state to be applied
     * @return the updated gameState
     */
    public GameState setState(GameState gameState){
        currentState = gameState;
        return currentState;
    }

    /**
     * Set expertMode equals to @param bool
     */
    public void setExpertMode(boolean bool){
        expertMode = bool;
    }

    /**
     *
     * @return the instance of the created game
     */
    public Game getGame(){
        return game;
    }

}

