package it.polimi.ingsw.server.model.map;

import it.polimi.ingsw.server.enumerations.PawnColors;
import it.polimi.ingsw.server.exception.DifferentColorTowerException;
import it.polimi.ingsw.server.exception.FullGroupIDListException;
import it.polimi.ingsw.server.model.component.charactercards.CharacterCard;
import it.polimi.ingsw.server.observer.ObserverIsland;

import java.util.ArrayList;

public class Map {

    private ArrayList<Island> islands;
    private ArrayList<Cloud> clouds;

    private final ObserverIsland obsIs;

    private int motherNaturePos;

    private CharacterCard[] characterCards;

    private GhostIsland[] groupIDsGhostIsland = {null, null, null, null, null, null};

    /**
     * Default constructor.
     */
    public Map(int playerNumber) {
        // Create 12 island object with his default constructor
        islands = new ArrayList<>(12);
        for(int i=0; i<12; i++){
            islands.add(new Island(i));
        }
        // Create clouds object based on PlayerNumber
        clouds = new ArrayList<>();
        for(int i=0; i<playerNumber; i++){
            clouds.add(new Cloud());
        }

        obsIs = new ObserverIsland(this);
    }

    public Island getIsland(int islandNumber){
        return islands.get(islandNumber);
    }

    public GhostIsland getGhostIsland(int islandNumber){
        if(getIsland(islandNumber).isDisabled())
            return groupIDsGhostIsland[getIsland(islandNumber).getGroupID()];
        return null;
    }

    public ArrayList<Island> getIslands(){
        return islands;
    }

    public Cloud getCloud(int cloudNumber){
        return clouds.get(cloudNumber);
    }

    public ArrayList<Cloud> getClouds(){
        return this.clouds;
    }

    //choice is 0,1 or 2
    public CharacterCard getCard(int choice){
        return this.characterCards[choice];
    }

    public void setMotherNaturePos(int motherNaturePos) {
        this.motherNaturePos = motherNaturePos;

    }

    public int getMotherNaturePosition(){
        return motherNaturePos;
    }

    public void notifyMergingIslands(){
        obsIs.onUpdate();
    }

    public void merge(int IDIsland1, int IDIsland2) throws DifferentColorTowerException, FullGroupIDListException {
        //da implementare
        int groupID = 0;
        Integer[] noc = new Integer[PawnColors.values().length];
        Integer[] noc_e;
        Integer[] noc_d;
        noc_e = islands.get(IDIsland1).getNumberOfColors();
        noc_d = islands.get(IDIsland2).getNumberOfColors();
        //noc parameter initialization
        for(int i = 0; i < PawnColors.values().length - 1; i++) noc[i] = noc_e[i] + noc_d[i];

        for(int i = 0; i < groupIDsGhostIsland.length; i++){
            if (groupIDsGhostIsland[i] == null) {
                if(islands.get(IDIsland1).getTowerColor() == islands.get(IDIsland2).getTowerColor()){
                    groupIDsGhostIsland[i] = new GhostIsland(i, noc, islands.get(IDIsland1).getTowerNumber() + islands.get(IDIsland2).getTowerNumber(), islands.get(IDIsland1).getTowerColor());
                    groupID = i;
                    break;
                }
                else throw new DifferentColorTowerException("Different tower. Impossible merging.");
            } else throw new FullGroupIDListException("Impossible to create a new island group.");
        }
        islands.get(IDIsland1).disable();
        islands.get(IDIsland1).setGroupID(groupID);
        islands.get(IDIsland2).disable();
        islands.get(IDIsland2).setGroupID(groupID);
        notifyMergingIslands();
    }

    public void mergeHybridGhostFeature(int IDGhostIsland, int IDIsland) throws DifferentColorTowerException {
        //da implementare
        /*
        Integer[] noc, nocIsland;
        noc = groupIDsGhostIsland[IDGhostIsland].getNumberOfColors();
        nocIsland = islands.get(IDIsland).getNumberOfColors();
        //noc parameter initialization
        for(int i = 0; i < PawnColors.values().length - 1; i++) noc[i] += nocIsland[i];
         */

        if (islands.get(IDIsland).getTowerColor() == groupIDsGhostIsland[IDGhostIsland].getTowerColor()) {
            groupIDsGhostIsland[IDGhostIsland].addStudents(islands.get(IDIsland).getNumberOfColors());
            groupIDsGhostIsland[IDGhostIsland].addTowers(islands.get(IDIsland).getTowerNumber());
        } else throw new DifferentColorTowerException("Different tower. Impossible merging");

        islands.get(IDIsland).disable();
        islands.get(IDIsland).setGroupID(IDGhostIsland);
        notifyMergingIslands();
    }

    public void mergeGhost(int IDGhostIsland1, int IDGhostIsland2) throws DifferentColorTowerException {
        /*
        Integer[] noc1, noc2;
        noc1 = groupIDsGhostIsland[IDGhostIsland1].getNumberOfColors();
        noc2 = groupIDsGhostIsland[IDGhostIsland2].getNumberOfColors();


        //merge dei noc
        for(int i = 0; i < PawnColors.values().length - 1; i++) noc1[i] += noc2[i];
        */

        //merge towers
        if (groupIDsGhostIsland[IDGhostIsland1].getTowerColor() == groupIDsGhostIsland[IDGhostIsland2].getTowerColor()) {
            groupIDsGhostIsland[IDGhostIsland1].addStudents(groupIDsGhostIsland[IDGhostIsland2].getNumberOfColors());
            groupIDsGhostIsland[IDGhostIsland1].addTowers(groupIDsGhostIsland[IDGhostIsland2].getTowerNumber());
        } else throw new DifferentColorTowerException("Different tower. Impossible merging");

        //set isole seconda con groupID prima
        for( Island island : islands) {
            if( island.getGroupID() == groupIDsGhostIsland[IDGhostIsland2].getID() )
                island.setGroupID(groupIDsGhostIsland[IDGhostIsland1].getID());
        }

        //eliminare la seconda
        groupIDsGhostIsland[IDGhostIsland2] = null;

        notifyMergingIslands();

    }

}
