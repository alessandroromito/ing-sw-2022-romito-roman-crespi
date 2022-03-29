package it.polimi.ingsw.server.model;
import it.polimi.ingsw.server.model.bag.*;
import it.polimi.ingsw.server.model.component.PawnColors;
import it.polimi.ingsw.server.model.component.Component;
import it.polimi.ingsw.server.model.component.MapPositions;
import it.polimi.ingsw.server.model.player.*;

import java.util.ArrayList;
import java.util.Collections;

/**
 * This Class represents the core of the game.
 */
public class Model {
    public static final int MIN_PLAYERS = 2;
    public static final int MAX_PLAYERS = 4;

    private Game game;
    private boolean endGame;
    private boolean expertMode;
    private int playerNumber;

    /**
     * Default constructor
     */
    public Model() {

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
        // Create all the object necessary for the game according to the number of player
        this.game = new Game(expertMode, playerNumber);
        // 2 set mother nature to one island
        int randPos = getRandomNumber(1, 12);
        game.getComponent(1).setPosition(MapPositions.valueOf("ISLANDS"), randPos);
        // 3 Move 1 student to each island
        Bag bag = game.getBag();
        ArrayList<Component> tempArrayStudents = new ArrayList<>();
        for(PawnColors color: PawnColors.values()){
            Component student1 = game.getComponent(bag.getColored(color));
            Component student2 = game.getComponent(bag.getColored(color));
            tempArrayStudents.add(student1);
            tempArrayStudents.add(student2);
        }
        Collections.shuffle(tempArrayStudents);
        for (Component stud: tempArrayStudents) {
            int islandNum = 0;
            if(!(islandNum == oppositePosition()))
            stud.setPosition(MapPositions.valueOf("ISLANDS"),islandNum);
        }
        // 8 Take 6/8 towers
        if(getNumberOfPlayer() == 2){
            for (Player p: game.getPlayers()) {
                p.getScoreboard().setNumTowers(8);
            }
        }
        else if(getNumberOfPlayer() == 3){
            for (Player p: game.getPlayers()) {
                p.getScoreboard().setNumTowers(6);
            }
        }

    }

    private int oppositePosition() {
        int motherNaturePosition = game.getComponent(1).getPositionDetailed();
        int oppositePos = (motherNaturePosition + 12) / 2;
        return oppositePos;
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
     * @param min minimum number can be generated
     * @param max manximum nuber can be generated
     * @return a random number in range min - max
     */
    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    // da aggiungere metodo che traduce json per importare i components

}

