package it.polimi.ingsw.server.model.map;

import it.polimi.ingsw.server.model.component.StudentDisc;
import java.util.ArrayList;

public class Cloud {

    private ArrayList<StudentDisc> cloudStudents;
    private int cloudID;

    /**
     * Default constructor.
     */
    public Cloud(int cloudID){
        cloudStudents = new ArrayList<>();
        this.cloudID = cloudID;
    }

    public ArrayList<StudentDisc> getCloudStudents(){
        return this.cloudStudents;
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
