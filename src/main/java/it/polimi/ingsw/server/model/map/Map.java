package it.polimi.ingsw.server.model.map;

import it.polimi.ingsw.server.exception.CloudNotFoundException;
import it.polimi.ingsw.server.exception.DifferentColorTowerException;
import it.polimi.ingsw.server.model.component.StudentDisc;
import it.polimi.ingsw.server.model.component.Tower;

import java.util.ArrayList;

public class Map {

    private ArrayList<Island> islands;
    private ArrayList<Cloud> clouds;

    private int motherNaturePos;

    private GhostIsland[] ghostIslands = {null, null, null, null, null, null};

    /**
     * Default constructor.
     */
    public Map(int playerNumber) {

        islands = new ArrayList<>(12);
        for(int i=0; i<12; i++){
            islands.add(new Island(i));
        }

        clouds = new ArrayList<>();
        for(int i=0; i<playerNumber; i++){
            clouds.add(new Cloud(i));
        }
    }

    public Island getIsland(int islandNumber){
        return islands.get(islandNumber);
    }

    public GhostIsland getGhostIsland(int islandNumber){
        return ghostIslands[getIsland(islandNumber).getGroupID()];
    }

    public ArrayList<Island> getIslands(){
        return islands;
    }

    public Cloud getCloud(int cloudID){
        try{
            if(cloudID < clouds.size()) {
                for (Cloud cloud : clouds) {
                    if (cloud.getCloudID() == cloudID)
                        return cloud;
                }
            }
            else throw new CloudNotFoundException("Cloud not found!");
        } catch (CloudNotFoundException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public ArrayList<Cloud> getClouds(){
        return clouds;
    }

    public void setMotherNaturePos(int motherNaturePos) {
        this.motherNaturePos = motherNaturePos;
    }

    public int getMotherNaturePosition(){
        return motherNaturePos;
    }

    /*
    public void merge(int IDIsland1, int IDIsland2) {
        int groupID = 0;
        ArrayList<StudentDisc> students = islands.get(IDIsland1).getStudents();
        ArrayList<Tower> towers = islands.get(IDIsland1).getTowers();

        students.addAll(islands.get(IDIsland2).getStudents());
        towers.addAll(islands.get(IDIsland2).getTowers());

        try{
            for(int i = 0; i < ghostIslands.length; i++){
                if (ghostIslands[i] == null) {
                    if(islands.get(IDIsland1).getTowerColor() == islands.get(IDIsland2).getTowerColor()){
                        ghostIslands[i] = new GhostIsland(i, students , towers );
                        break;
                    }
                    else throw new DifferentColorTowerException("Different tower. Impossible merging.");
                }
            }
            islands.get(IDIsland1).disable();
            islands.get(IDIsland1).setGroupID(groupID);
            islands.get(IDIsland2).disable();
            islands.get(IDIsland2).setGroupID(groupID);

        } catch (DifferentColorTowerException e) {
            e.printStackTrace();
        }
    }

     */

    public void merge(int IDIsland1, int IDIsland2) {
        int groupID = 0;

        Island island1 = islands.get(IDIsland1);
        if(island1.isDisabled())
            island1 = getGhostIsland(IDIsland1);
        Island island2 = islands.get(IDIsland2);
        if(island1.isDisabled())
            island2 = getGhostIsland(IDIsland2);

        ArrayList<StudentDisc> students = new ArrayList<>(island1.getStudents());
        ArrayList<Tower> towers = new ArrayList<>(island1.getTowers());
        students.addAll(island2.getStudents());
        towers.addAll(island2.getTowers());

        //DUE GHOST ISLAND
        if(island1.getGroupID() != -1 && island2.getGroupID() != -1) {
            groupID = Math.min(island1.getGroupID(), island2.getGroupID());
            ghostIslands[groupID] = new GhostIsland(groupID, students, towers);
            for(Island island : islands) {
                if(island.getGroupID() == Math.max(island1.getGroupID(), island2.getGroupID()))
                    island.setGroupID(groupID);
            }
            ghostIslands[Math.max(island1.getGroupID(), island2.getGroupID())] = null;
        }
        //ISLAND 1 GHOST, ISLAND 2 NO
        else if (island1.getGroupID() != -1 && island2.getGroupID() == -1) {
            groupID = island1.getGroupID();
            ghostIslands[groupID] = new GhostIsland(groupID, students, towers);

            island2.disable();
            island2.setGroupID(groupID);
        }
        //ISLAND 1 NO, ISLAND 2 GHOST
        else if (island1.getGroupID() == -1 && island2.getGroupID() != -1) {
            groupID = island2.getGroupID();
            ghostIslands[groupID] = new GhostIsland(groupID, students, towers);

            island1.disable();
            island1.setGroupID(groupID);
        }
        // DUE NO GHOST
        else {
            int i = 0;
            for(GhostIsland ghostIsland : ghostIslands) {
                if (ghostIsland == null) {
                    ghostIslands[i] = new GhostIsland(i, students, towers);
                    groupID = i;
                }
                i++;
            }
            island1.disable();
            island2.disable();
            island1.setGroupID(groupID);
            island2.setGroupID(groupID);
        }
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

        if (islands.get(IDIsland).getTowerColor() == ghostIslands[IDGhostIsland].getTowerColor()) {
            ghostIslands[IDGhostIsland].getStudents().addAll(islands.get(IDIsland).getStudents());
            ghostIslands[IDGhostIsland].getTowers().addAll(islands.get(IDIsland).getTowers());
        } else throw new DifferentColorTowerException("Different tower color. Impossible merging");

        islands.get(IDIsland).disable();
        islands.get(IDIsland).setGroupID(IDGhostIsland);
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
        if (ghostIslands[IDGhostIsland1].getTowerColor() == ghostIslands[IDGhostIsland2].getTowerColor()) {
            ghostIslands[IDGhostIsland1].getStudents().addAll(ghostIslands[IDGhostIsland2].getStudents());
            ghostIslands[IDGhostIsland1].getTowers().addAll(ghostIslands[IDGhostIsland2].getTowers());
        } else throw new DifferentColorTowerException("Different tower. Impossible merging");

        //set isole seconda con groupID prima
        for( Island island : islands) {
            if( island.getGroupID() == ghostIslands[IDGhostIsland2].getID() )
                island.setGroupID(ghostIslands[IDGhostIsland1].getID());
        }

        //eliminare la seconda
        ghostIslands[IDGhostIsland2] = null;
    }

    public int getNumberOfGhostIsland() {
        int c=0;
        while(ghostIslands[c]!=null)
            c++;
        return c;
    }

    public Island getNext(int islandID){
        Island island = getIsland(islandID);
        Island islandSucc;

        if(islandID == 0)
            islandSucc = getIsland(11);
        else
            islandSucc = getIsland(islandID - 1);

        if(island.isDisabled() && islandSucc.isDisabled() && island.getGroupID() == islandSucc.getGroupID())
            getNext(islandSucc.getID());

        return islandSucc.isDisabled() ? getGhostIsland(islandSucc.getID()) : islandSucc;
    }

    public Island getPrev(int islandID){
        Island island = getIsland(islandID);
        Island islandPrev;

        if(islandID == 0)
            islandPrev = getIsland(11);
        else
            islandPrev = getIsland(islandID - 1);

        if(island.isDisabled() && islandPrev.isDisabled() && island.getGroupID() == islandPrev.getGroupID())
            getPrev(islandPrev.getID());

        return islandPrev.isDisabled() ? getGhostIsland(islandPrev.getID()) : islandPrev;
    }
}