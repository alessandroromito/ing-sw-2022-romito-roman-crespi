package it.polimi.ingsw.server.model.map;

import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.server.exception.CloudNotFoundException;
import it.polimi.ingsw.server.model.component.StudentDisc;
import it.polimi.ingsw.server.model.component.Tower;

import java.io.Serializable;
import java.util.ArrayList;

public class Map extends Observable implements Serializable {

    private final ArrayList<Island> islands;
    private final ArrayList<Cloud> clouds;

    private int motherNaturePos;

    private final GhostIsland[] ghostIslands = {null, null, null, null, null, null};

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

    public Island getIsland(int islandID){
        return islands.get(islandID);
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

    public void merge(int IDIsland1, int IDIsland2) {
        int groupID = 0;

        Island island1 = islands.get(IDIsland1);
        if(island1.isDisabled())
            island1 = getGhostIsland(IDIsland1);
        Island island2 = islands.get(IDIsland2);
        if(island2.isDisabled())
            island2 = getGhostIsland(IDIsland2);

        ArrayList<StudentDisc> students = new ArrayList<>(island1.getStudents());
        ArrayList<Tower> towers = new ArrayList<>(island1.getTowers());
        students.addAll(island2.getStudents());
        towers.addAll(island2.getTowers());

        //DUE GHOST ISLAND
        if(island1.getGroupID() != -1 && island2.getGroupID() != -1) {
            System.out.println("Merging ghost islands "+ IDIsland1 + " " + IDIsland2);

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
            System.out.println("Merging ghost island "+ IDIsland1 + " no ghost " + IDIsland2);

            groupID = island1.getGroupID();
            ghostIslands[groupID] = new GhostIsland(groupID, students, towers);

            island2.disable();
            island2.setGroupID(groupID);
        }
        //ISLAND 1 NO, ISLAND 2 GHOST
        else if (island1.getGroupID() == -1 && island2.getGroupID() != -1) {
            System.out.println("Merging island "+ IDIsland1 + " with ghost " + IDIsland2);

            groupID = island2.getGroupID();
            ghostIslands[groupID] = new GhostIsland(groupID, students, towers);

            island1.disable();
            island1.setGroupID(groupID);
        }
        // DUE NO GHOST
        else {
            System.out.println("Merging island " + IDIsland1 + " " + IDIsland2);

            int i = 0;
            for(GhostIsland ghostIsland : ghostIslands) {
                if (ghostIsland == null) {
                    ghostIslands[i] = new GhostIsland(i, students, towers);
                    groupID = i;
                    break;
                }
                i++;
            }

            System.out.println("Students: " + ghostIslands[i].getStudents().size() + " Towers: " + ghostIslands[i].getTowers().size());

            island1.disable();
            island2.disable();
            island1.setGroupID(groupID);
            island2.setGroupID(groupID);
        }
    }

    public int getNumberOfGhostIsland() {
        int c=0;
        while(ghostIslands[c]!=null)
            c++;
        return c;
    }

    /**
     * Get next island/ghostIsland
     * @param islandID island of the starting island
     * @return the next island
     */
    public Island getNext(int islandID){
        Island island = getIsland(islandID);
        Island islandSucc;

        if(islandID == 11)
            islandSucc = getIsland(0);
        else
            islandSucc = getIsland(islandID + 1);

        if(island.isDisabled() && islandSucc.isDisabled() && island.getGroupID() == islandSucc.getGroupID())
            getNext(islandSucc.getID());

        return islandSucc.isDisabled() ? getGhostIsland(islandSucc.getID()) : islandSucc;
    }

    /**
     * Get previous island/ghostIsland
     * @param islandID island of the starting island
     * @return the previous island
     */
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