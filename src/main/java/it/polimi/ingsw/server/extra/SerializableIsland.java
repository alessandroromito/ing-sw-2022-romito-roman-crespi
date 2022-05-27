package it.polimi.ingsw.server.extra;

import it.polimi.ingsw.server.enumerations.TowerColors;
import it.polimi.ingsw.server.model.component.StudentDisc;
import it.polimi.ingsw.server.model.map.Island;

import java.io.Serializable;

public class SerializableIsland implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private int redStudents = 0;
    private int greenStudents = 0;
    private int blueStudents = 0;
    private int yellowStudents = 0;
    private int pinkStudents = 0;
    private int towerNumber = 0;
    private TowerColors towerColor;

    public SerializableIsland(Island island){
        this.id  = island.getID();
        setColors(island);
        this.towerColor = island.getTowerColor();
        this.towerNumber = island.getTowerNumber();
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
}
