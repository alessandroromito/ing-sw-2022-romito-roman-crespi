package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.exception.GameAlreadyStartedException;
import it.polimi.ingsw.server.exception.MaxPlayerException;
import it.polimi.ingsw.server.exception.MissingPlayersException;
import it.polimi.ingsw.server.model.player.Player;

import java.util.ArrayList;
import java.util.List;

public class Model {
    public static final int MIN_PLAYERS = 2;
    public static final int MAX_PLAYERS = 4;
    public static final String SERVER_NAME = "server";

    private Game game;

    private boolean expertMode;

    private List<Player> players;

    private boolean gameStarted;
    private boolean endGame;

    int playerNumber;

    /**
     * Default constructor
     */
    public Model() {
        //init
        players = new ArrayList<>();
        expertMode = false;
        playerNumber = 0;
        gameStarted = false;
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
        if (gameStarted) throw new GameAlreadyStartedException("NOT possible to add players when game is already started!");
        if (player == null) throw new NullPointerException("Player cannot be NULL");
        if (playerNumber >= MAX_PLAYERS) throw new MaxPlayerException("Max Number of players reached!");
        players.add(player);
        playerNumber++;
    }

    /**
     * Starts the game
     */
    public boolean startGame() throws GameAlreadyStartedException, MissingPlayersException {

        if(expertMode){
            game = new ExpertGame(players);
        }
        else game = new Game(players);

        return true;
    }

    /**
     * Set EndGame true
     */
    public void setEndGame(){
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
     * Set expertMode equals to @param bool
     */
    public void setExpertMode(boolean bool){
        expertMode = bool;
    }

    /**
     *
     * @return the instance of the game
     */
    public Game getGame(){
        return game;
    }

    /**
     * Search a nickname in the current players.
     *
     * @param nickname the nickname of the player.
     * @return {@code true} if the nickname is found, {@code false} otherwise.
     */
    public boolean isNicknameTaken(String nickname) {
        return players.stream()
                .anyMatch(p -> nickname.equals(p.getNickname()));
    }
}

