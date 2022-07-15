package it.polimi.ingsw.server.model.component.charactercards;

import it.polimi.ingsw.server.model.component.StudentDisc;

import java.util.ArrayList;

public class Card_219 extends CharacterCard {
    private final ArrayList<StudentDisc> students;

    /**
     * Default Constructor
     */
    public Card_219(ArrayList<StudentDisc> studentDiscArray){
        super(219);
        this.students = studentDiscArray;
        this.cost = 2; //2
    }

    /**
     * Getter
     * @param i index
     * @return student at i
     */
    public StudentDisc getStudent(int i){
        if(i>=0 && i<=5) {
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
