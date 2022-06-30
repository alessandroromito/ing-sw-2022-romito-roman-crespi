package it.polimi.ingsw.server.model.component.charactercards;

import it.polimi.ingsw.server.model.component.StudentDisc;
import java.util.ArrayList;

/**
 * This class represent the character card with id 215.
 */
public class Card_215 extends CharacterCard{
    private final ArrayList<StudentDisc> students;

    /**
     * Default Constructor
     * @param cardStudents students on the card
     */
    public Card_215(ArrayList<StudentDisc> cardStudents){
        super(215);
        this.students = cardStudents;
        this.cost = 1;
    }

    public ArrayList<StudentDisc> getStudents() {
        return students;
    }

    public void addStudent(StudentDisc stud){
        students.add(stud);
    }
}
