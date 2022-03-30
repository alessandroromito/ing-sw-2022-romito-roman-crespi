package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.exception.GameAlreadyStartedException;
import it.polimi.ingsw.server.exception.MaxPlayerException;
import it.polimi.ingsw.server.exception.MissingPlayersException;
import it.polimi.ingsw.server.model.bag.Bag;
import it.polimi.ingsw.server.model.component.Component;
import it.polimi.ingsw.server.model.map.Map;
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
    private ArrayList<Component> components;
    private Map map;
    private Bag bag;

    private boolean gameStarted;
    private boolean endGame;

    int playerNumber;

    /**
     * Default constructor
     */
    public Model() {
        players = new ArrayList<>();
        components = null;
        //map = new Map(playerNumber);
        bag = new Bag();
        expertMode = false;
        playerNumber = 0;
        gameStarted = false;
    }

    public void autoSave(){

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
        playerNumber = players.size();
        return playerNumber;
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

        if(players.size() < 2) throw new MissingPlayersException("Minimum players is 2!");
        if(!expertMode){
            game = new Game(players, components, map, bag);
        }
        else game = new ExpertGame(players, components, map, bag);

        return true;
    }

    /**
     * @return true if the game gameStarted
     */
    public boolean isGameStarted() {
        return gameStarted;
    }


    public void turnController(){

    }
    /**
     *
     */
    public void turn(Player p){

    }

    /**
     * Set EndGame true
     */
    public void setEndGame(){
        game.currentState = GameState.GAME_ENDED;
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



    // da aggiungere metodo che traduce json per importare i components

}

