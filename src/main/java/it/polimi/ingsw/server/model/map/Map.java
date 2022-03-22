package it.polimi.ingsw.server.model.map;

import it.polimi.ingsw.server.model.Model;

import java.util.ArrayList;

public class Map {

    private ArrayList<Island> islands;

    /**
     * Default constructor.
     */
    public Map() {
        mapInit(Model.getNumberOfPlayer());
    }


    public void mapInit(int playerNumber) {
        // Create 12 island object with his default constructor
        for(int i=0; i<12; i++){
            islands.set(i, new Island());
        }
        // Create 2 clouds object
        for(int i=0; i<2; i++){
            clouds.set(i, new Cloud());
        }
    }
}
