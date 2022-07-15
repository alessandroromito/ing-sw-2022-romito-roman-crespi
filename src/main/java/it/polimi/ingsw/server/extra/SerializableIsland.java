package it.polimi.ingsw.server.extra;

import it.polimi.ingsw.server.enumerations.TowerColors;
import it.polimi.ingsw.server.model.component.StudentDisc;
import it.polimi.ingsw.server.model.map.GhostIsland;
import it.polimi.ingsw.server.model.map.Island;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Game data serializable for islands.
 */
public class SerializableIsland implements Serializable {
    @Serial
    private static final long serialVersionUID = -6510869516084911664L;

    private int id;
    private final boolean isGhost;
    private ArrayList<Integer> referencedIslands;
    private final ArrayList<Integer> islandsPawnsID = new ArrayList<>();
    private int redStudents = 0;
    private int greenStudents = 0;
    private int blueStudents = 0;
    private int yellowStudents = 0;
    private int pinkStudents = 0;
    private final int towerNumber;
    private final TowerColors towerColor;
    private final boolean noEntryTile;

    /**
     * Default Constructor for an Island
     * @param island island to be serialized
     */
    public SerializableIsland(Island island){
        this.id  = island.getID();
        this.isGhost = false;
        setColors(island);
        this.towerColor = island.getTowerColor();
        this.towerNumber = island.getTowerNumber();
        for(StudentDisc s: island.getStudents())
            islandsPawnsID.add(s.getID());
        this.noEntryTile = island.checkNoEntryTile();
    }

    /**
     * Default Constructor for a GhostIsland
     * @param ghostIsland ghostIsland to be serialized
     */
    public SerializableIsland(GhostIsland ghostIsland){
        this.isGhost = true;
        setColors(ghostIsland);
        this.towerColor = ghostIsland.getTowerColor();
        this.towerNumber = ghostIsland.getTowerNumber();
        for(StudentDisc s: ghostIsland.getStudents())
            islandsPawnsID.add(s.getID());
        this.noEntryTile = ghostIsland.checkNoEntryTile();
    }

    /**
     * Set the students on the island
     * @param island island to take te students
     */
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

    /**
     * Getter method
     * @return
     */
    public int getRedStudents() {
        return redStudents;
    }

    /**
     * Getter method
     * @return
     */
    public int getGreenStudents() {
        return greenStudents;
    }

    /**
     * Getter method
     * @return
     */
    public int getBlueStudents() {
        return blueStudents;
    }

    /**
     * Getter method
     * @return
     */
    public int getYellowStudents() {
        return yellowStudents;
    }

    /**
     * Getter method
     * @return
     */
    public int getPinkStudents() {
        return pinkStudents;
    }

    /**
     * Getter method
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     * Getter method
     * @return
     */
    public int getTowerNumber() {
        return towerNumber;
    }

    /**
     * Getter method
     * @return
     */
    public TowerColors getTowerColor() {
        return towerColor;
    }

    /**
     * Getter method
     * @return
     */
    public boolean isGhost() {
        return isGhost;
    }

    /**
     * Getter method
     * @return
     */
    public ArrayList<Integer> getReferencedIslands() {
        return referencedIslands;
    }

    /**
     * Getter method
     * @return
     */
    public ArrayList<Integer> getIslandsPawnsID() { return islandsPawnsID; }

    /**
     * Getter method
     * @return
     */
    public void setReferencedIslands(ArrayList<Integer> referencedIslands) {
        this.referencedIslands = referencedIslands;
    }

    /**
     * Return if there is a no entry tile
     * @return true if it's present
     */
    public boolean getNoEntryTile() {
        return noEntryTile;
    }
}
