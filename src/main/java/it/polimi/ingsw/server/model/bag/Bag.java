package it.polimi.ingsw.server.model.bag;

import it.polimi.ingsw.server.enumerations.PawnColors;
import it.polimi.ingsw.server.model.component.StudentDisc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Bag {
    private List<StudentDisc> bagStudents = new ArrayList<>(130);

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

    public StudentDisc pickSorted(){
        StudentDisc temp = this.bagStudents.get(0);
        this.bagStudents.remove(0);
        return temp;
    }

    public List<StudentDisc> getBagStudents(){
        return bagStudents;
    }

    public boolean isStudentsListEmpty(){
        return bagStudents.isEmpty();
    }
}