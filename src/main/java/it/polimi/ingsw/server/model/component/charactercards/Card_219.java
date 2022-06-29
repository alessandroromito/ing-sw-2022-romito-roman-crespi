package it.polimi.ingsw.server.model.component.charactercards;

import it.polimi.ingsw.server.model.component.StudentDisc;
import java.util.ArrayList;

public class Card_219 extends CharacterCard {
    private final ArrayList<StudentDisc> students;

    public Card_219(ArrayList<StudentDisc> studentDiscArray){
        super(219);
        this.students = studentDiscArray;
        this.cost = 3;
    }

    public StudentDisc getStudent(int i){
        if(i>=0 && i<=3) {
            StudentDisc studentToReturn = students.get(i);
            students.remove(i);
            return studentToReturn;
        }
        System.out.println("Invalid Index");
        return null;
    }

    public ArrayList<StudentDisc> getStudents() {
        return students;
    }

    public void addStudent(StudentDisc stud){
        students.add(stud);
    }
}
