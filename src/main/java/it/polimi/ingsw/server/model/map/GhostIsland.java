package it.polimi.ingsw.server.model.map;

import it.polimi.ingsw.server.model.component.StudentDisc;
import it.polimi.ingsw.server.model.component.Tower;

import java.util.ArrayList;


public class GhostIsland extends Island{

    /**
     * Default constructor.
     */
    public GhostIsland(int ghostID, ArrayList<StudentDisc> students , ArrayList<Tower> towers){
        super(ghostID);
        this.students = students;
        this.towers = towers;
    }
}
