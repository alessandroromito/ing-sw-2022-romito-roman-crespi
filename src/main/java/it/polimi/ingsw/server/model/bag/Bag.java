package it.polimi.ingsw.server.model.bag;

import it.polimi.ingsw.server.enumerations.PawnColors;
import it.polimi.ingsw.server.model.component.StudentDisc;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * This class represents the bag of the game. 130 student pawn of initial capacity.
 */
public class Bag implements Serializable {
    private List<StudentDisc> bagStudents = new ArrayList<>(130);

    /**
     * Constructor of the class. Initialize all the students and insert them in a list.
     * Shuffle the list to have the students in random positions.
     */
    public Bag(){
        int id = 59;
        for(PawnColors color: PawnColors.values()){
            for(int count=0; count<26; count++){
                bagStudents.add(new StudentDisc(id, color));
                id++;
            }
        }
        Collections.shuffle(bagStudents);
    }

    /**
     * Get a random student from the bag, then remove it.
     *
     * @return studentDisc
     */
    public StudentDisc pickSorted(){
        StudentDisc temp = this.bagStudents.get(0);
        this.bagStudents.remove(0);
        return temp;
    }

    /**
     * return the list of the students of the bag.
     *
     * @return a List of StudentDisc
     */
    public List<StudentDisc> getBagStudents(){
        return bagStudents;
    }

    /**
     * @return true if the list of students of the bag is empty, false otherwise.
     */
    public boolean isStudentsListEmpty(){
        return bagStudents.isEmpty();
    }
}