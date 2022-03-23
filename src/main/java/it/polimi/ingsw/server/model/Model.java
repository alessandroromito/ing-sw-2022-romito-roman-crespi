package it.polimi.ingsw.server.model;
import it.polimi.ingsw.server.model.bag.*;
import it.polimi.ingsw.server.model.player.*;
import it.polimi.ingsw.server.model.map.*;

import java.util.ArrayList;
import java.util.List;

/**
 * This Class represents the core of the game.
 * It manages the players, the map, the bag, the player scoreboard and all the components
 */
public class Model {

    public static final int MAX_PLAYERS = 4;

    private final Map map;
    private final Bag bag;
    private boolean endGame;
    private int playerNumber;
    private List<Player> players;

    /**
     * Default constructor.
     */
    private Model() {
        this.players = new ArrayList<>();
        this.map = new Map();
        this.bag = new Bag();
    }

    /**
     * Returns a list of players.
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * Return the number of player
     */
    public int getNumberOfPlayer() {
        playerNumber = players.size();
        return playerNumber;
    }
    /**
     * Returns a player given his {@code nickname}.
     * If no player is found {@code null} is returned.
     *
     * @param nickname the nickname of the player to be found.
     * @return Returns the player given his {@code nickname}, {@code null} otherwise.
     */
    public Player getPlayerByNickname(String nickname) {
        return players.stream()
                .filter(player -> nickname.equals(player.getNickname()))
                .findFirst()
                .orElse(null);
    }

    /**
     * Adds a player to the game.
     * Change the playersNumber.
     */
    public void addPlayer(Player player) {
        if (playerNumber < MAX_PLAYERS) {
            players.add(player);
            playerNumber++;
        }
    }

    public void GameInitialization(){

        map.mapInit(playerNumber);
    }

    /**
     * Set the boolean variable EndGame true
     */
    public void SetEndGame(){

        endGame = true;
    }

    /**
     * Return the state of EndGame
     * @return EndGame
     */
    public boolean getEndGame() {
        return endGame;
    }

    /**
     * Set of operation to do in the endgame phase
     */
    public void EndGame(String message){
        while(endGame){
            switch(message){
                case "LAST_TOWER":
                case "ASSISTANT_CARD":
                case "ISLAND":
                case "LAST_STUDENT":
            }
        }
    }


}

