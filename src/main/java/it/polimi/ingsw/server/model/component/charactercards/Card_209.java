package it.polimi.ingsw.server.model.component.charactercards;

import it.polimi.ingsw.server.model.component.StudentDisc;

import java.util.ArrayList;

/**
 * This class represent the character card with id 209.
 */
public class Card_209 extends CharacterCard {
    ArrayList<StudentDisc> students;

    /**
     * Default constructor.
     * @param students to make card effect working.
     */
    public Card_209(ArrayList<StudentDisc> students) {
        super(209);
        this.students = students;
        this.cost = 1; //1
    }

    /**
     * Method that allow the user to apply the card effect.
     * @param studentPos position of the student to remove.
     * @param next student to add.
     * @return the studentDisc removed
     */
    public StudentDisc use(int studentPos, StudentDisc next){
        StudentDisc removed = students.get(studentPos);
        super.addCost();
        students.remove(studentPos);
        students.add(next);
        return removed;
    }

    /**
     * @return the array list of student disc modified from the class.
     */
    public ArrayList<StudentDisc> getStudents() {
        return students;
    }
}
