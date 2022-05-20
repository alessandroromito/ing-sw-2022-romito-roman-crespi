package it.polimi.ingsw.server.model.component.charactercards;

import it.polimi.ingsw.server.model.component.StudentDisc;

import java.util.ArrayList;

public class Card_209 extends CharacterCard {
    ArrayList<StudentDisc> students = new ArrayList<StudentDisc>();

    public Card_209(ArrayList<StudentDisc> students) {
        super(209);
        this.students = students;
        this.cost = 1;
    }

    public StudentDisc use(int studentPos, StudentDisc next){
        StudentDisc removed = students.get(studentPos);
        super.addCost();
        students.remove(studentPos);
        students.add(next);
        return removed;
    }
}
