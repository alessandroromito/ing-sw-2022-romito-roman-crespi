package it.polimi.ingsw.server.model.map;

import it.polimi.ingsw.server.model.component.StudentDisc;
import java.util.ArrayList;

public class Cloud {

    private ArrayList<StudentDisc> cloudStudents;

    /**
     * Default constructor.
     */
    public Cloud(){
        cloudStudents = new ArrayList<>();
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
}
