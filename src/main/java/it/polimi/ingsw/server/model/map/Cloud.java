package it.polimi.ingsw.server.model.map;

import it.polimi.ingsw.server.model.component.StudentDisc;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Class that represents a cloud of the game
 */
public class Cloud implements Serializable {
    @Serial
    private static final long serialVersionUID = 1274892365976434L;

    private final ArrayList<StudentDisc> cloudStudents;
    private final int cloudID;

    /**
     * Default constructor.
     */
    public Cloud(int cloudID){
        cloudStudents = new ArrayList<>();
        this.cloudID = cloudID;
    }

    /**
     * Get the students on this cloud
     * @return the students
     */
    public ArrayList<StudentDisc> getCloudStudents(){
        return cloudStudents;
    }

    /**
     * Delete all the students on this cloud
     */
    public void reset(){
        cloudStudents.clear();
    }

    /**
     * Add a students on this cloud
     * @param student
     */
    public void addStudent(StudentDisc student) {
        cloudStudents.add(student);
    }

    /**
     * Getter
     * @return id
     */
    public int getCloudID() {
        return cloudID;
    }
}
