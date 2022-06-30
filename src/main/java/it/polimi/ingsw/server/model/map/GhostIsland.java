package it.polimi.ingsw.server.model.map;

import it.polimi.ingsw.server.model.component.StudentDisc;
import it.polimi.ingsw.server.model.component.Tower;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Class that represents two or more island merged
 */
public class GhostIsland extends Island implements Serializable {

    /**
     * Default constructor.
     */
    public GhostIsland(int ghostID, ArrayList<StudentDisc> students , ArrayList<Tower> towers){
        super(ghostID);
        this.groupID = ghostID;
        this.students = students;
        this.towers = towers;
    }
}
