package it.polimi.ingsw.server.extra;

import it.polimi.ingsw.server.enumerations.TowerColors;
import it.polimi.ingsw.server.model.component.StudentDisc;
import it.polimi.ingsw.server.model.map.GhostIsland;
import it.polimi.ingsw.server.model.map.Island;

import java.io.Serializable;
import java.util.ArrayList;

public class SerializableIsland implements Serializable {
    private static final long serialVersionUID = 102L;

    private int id;
    private final boolean isGhost;
    private ArrayList<Integer> referencedIslands;
    private final ArrayList<Integer> islandsPawnsID = new ArrayList<>();
    private int redStudents = 0;
    private int greenStudents = 0;
    private int blueStudents = 0;
    private int yellowStudents = 0;
    private int pinkStudents = 0;
    private int towerNumber;
    private final TowerColors towerColor;

    public SerializableIsland(Island island){
        this.id  = island.getID();
        this.isGhost = false;
        setColors(island);
        this.towerColor = island.getTowerColor();
        this.towerNumber = island.getTowerNumber();
        for(StudentDisc s: island.getStudents())
            islandsPawnsID.add(s.getID());
    }

    public SerializableIsland(GhostIsland ghostIsland){
        this.isGhost = true;
        setColors(ghostIsland);
        this.towerColor = ghostIsland.getTowerColor();
        this.towerNumber = ghostIsland.getTowerNumber();
        for(StudentDisc s: ghostIsland.getStudents())
            islandsPawnsID.add(s.getID());
    }

    private void setColors(Island island) {
        for(StudentDisc studentDisc : island.getStudents()){
            switch (studentDisc.getColor()) {
                case RED -> this.redStudents++;
                case BLUE -> this.blueStudents++;
                case GREEN -> this.greenStudents++;
                case YELLOW -> this.yellowStudents++;
                case PINK -> this.pinkStudents++;
            }
        }
    }

    public int getRedStudents() {
        return redStudents;
    }

    public int getGreenStudents() {
        return greenStudents;
    }

    public int getBlueStudents() {
        return blueStudents;
    }

    public int getYellowStudents() {
        return yellowStudents;
    }

    public int getPinkStudents() {
        return pinkStudents;
    }

    public int getId() {
        return id;
    }

    public int getTowerNumber() {
        return towerNumber;
    }

    public TowerColors getTowerColor() {
        return towerColor;
    }

    public boolean isGhost() {
        return isGhost;
    }

    public ArrayList<Integer> getReferencedIslands() {
        return referencedIslands;
    }

    public ArrayList<Integer> getIslandsPawnsID() { return islandsPawnsID; }

    public void setReferencedIslands(ArrayList<Integer> referencedIslands) {
        this.referencedIslands = referencedIslands;
    }
}
