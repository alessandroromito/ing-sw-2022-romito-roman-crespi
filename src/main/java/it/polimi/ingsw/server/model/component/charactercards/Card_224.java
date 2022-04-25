package it.polimi.ingsw.server.model.component.charactercards;

import it.polimi.ingsw.server.model.component.CharacterCard;
import it.polimi.ingsw.server.model.component.StudentDisc;

import java.util.ArrayList;

public class Card_224 extends CharacterCard {
    private ArrayList<StudentDisc> students;

    public Card_224(ArrayList<StudentDisc> studentDiscArray){
        super(224);
        this.students = studentDiscArray;
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

    public void addStudent(StudentDisc stud){
        students.add(stud);
    }
}
