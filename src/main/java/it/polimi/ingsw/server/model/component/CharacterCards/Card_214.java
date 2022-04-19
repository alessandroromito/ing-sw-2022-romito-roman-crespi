package it.polimi.ingsw.server.model.component.CharacterCards;

import it.polimi.ingsw.server.model.component.CharacterCard;
import it.polimi.ingsw.server.model.component.StudentDisc;

import java.util.ArrayList;

public class Card_214 extends CharacterCard {
    ArrayList<StudentDisc> students = new ArrayList<StudentDisc>();

    public Card_214(ArrayList<StudentDisc> students) {
        this.students = students;
    }
    
    public StudentDisc use(int studentPos, StudentDisc next){
        StudentDisc removed = students.get(studentPos);
        super.use();
        students.remove(studentPos);
        students.add(next);
        return removed;
    }
}
