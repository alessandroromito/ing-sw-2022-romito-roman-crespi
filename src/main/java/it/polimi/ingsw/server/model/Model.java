package it.polimi.ingsw.server.model;
import it.polimi.ingsw.server.model.bag.*;
import it.polimi.ingsw.server.model.component.Component;
import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.player.*;
import it.polimi.ingsw.server.model.map.*;

import java.util.ArrayList;
import java.util.List;

/**
 * This Class represents the core of the game.
 */
public class Model {

    public static final int MAX_PLAYERS = 4;

    private Game game;
    private boolean endGame;
    private boolean expertMode;
    private int playerNumber;

    /**
     * Default constructor
     */
    public Model() {
        this.game = new Game(expertMode, playerNumber);
    }

    public void autosave(){

    }

    /**
     * Return the number of player
     */
    public int getNumberOfPlayer() {
        playerNumber = game.getPlayers().size();
        return playerNumber;
    }


    /**
     * Adds a player to the game.
     * Change the playersNumber.
     */
    public void addPlayer(Player player) {
        if (playerNumber < MAX_PLAYERS) {
            game.getPlayers().add(player);
            playerNumber++;
        }
    }

    /**
     *
     */
    public void gameInitialization(){

    }

    /**
     *
     */
    public void round(Player p){

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

    // da aggiungere metodo che traduce json per importare i components

}

