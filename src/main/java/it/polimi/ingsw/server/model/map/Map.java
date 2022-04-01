package it.polimi.ingsw.server.model.map;

import it.polimi.ingsw.server.model.component.CharacterCard;
import it.polimi.ingsw.server.observer.ObserverIsland;

import java.util.ArrayList;

public class Map {

    private ArrayList<Island> islands;
    private ArrayList<Cloud> clouds;
    private ObserverIsland obsIs;
    private int motherNaturePos;
    private CharacterCard[] characterCards;

    /**
     * Default constructor.
     */
    public Map(int playerNumber) {
        // Create 12 island object with his default constructor
        islands = new ArrayList<>(12);
        for(int i=0; i<12; i++){
            islands.set(i, new Island());
        }
        // Create clouds object based on PlayerNumber
        clouds = new ArrayList<>();
        for(int i=0; i<playerNumber; i++){
            clouds.set(i, new Cloud());
        }

        obsIs = new ObserverIsland(this);
    }

    public Island getIsland(int islandNumber){
        return islands.get(islandNumber);
    }

    public Cloud getCloud(int cloudNumber){
        return clouds.get(cloudNumber);
    }

    public ArrayList<Cloud> getClouds(){return this.clouds;}

    public void notifyMergingIslands(){
        // da implementare
    }

    public void merge(int IDIslandEnable, int IDIslandDisable){
        //da implementare
    }
}
