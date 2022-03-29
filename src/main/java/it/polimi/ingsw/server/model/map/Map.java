package it.polimi.ingsw.server.model.map;

import it.polimi.ingsw.server.model.Model;
import it.polimi.ingsw.server.observer.ObserverIsland;

import java.util.ArrayList;

public class Map {

    private ArrayList<Island> islands;
    private ArrayList<Cloud> clouds;
    private ObserverIsland obsIs;

    /**
     * Default constructor.
     */
    public Map() {
        // Create 12 island object with his default constructor
        for(int i=0; i<12; i++){
            islands.set(i, new Island());
        }
        // Create 2 clouds object
        for(int i=0; i<2; i++){
            clouds.set(i, new Cloud());
        }
        obsIs = new ObserverIsland(this);
    }

    public Island getIsland(int islandNumber){return islands.get(islandNumber);}
}
