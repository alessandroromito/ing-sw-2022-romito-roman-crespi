package it.polimi.ingsw.server.model.map;

import it.polimi.ingsw.server.model.component.StudentDisc;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

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

    public ArrayList<StudentDisc> getCloudStudents(){
        return cloudStudents;
    }

    public void reset(){
        cloudStudents.clear();
    }

    public void addStudent(StudentDisc student) {
        cloudStudents.add(student);
    }

    public int getCloudID() {
        return cloudID;
    }
}
