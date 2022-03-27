package it.polimi.ingsw.server.model.bag;

import it.polimi.ingsw.server.model.component.Colors;
import it.polimi.ingsw.server.model.component.StudentDisc;

import java.util.*;

public class Bag {
    private List<Integer> bagStudents;

    public void Bag(){
        for ( int i = 1 ; i <= 130 ; i++)
            this.bagStudents.add(i);
        Collections.shuffle(this.bagStudents);
    }

    //ritorna l'ID dello studente
    public int getSorted(){
        int temp = this.bagStudents.get(0);
        this.bagStudents.remove(0);
        return temp + 104;
    }

    public void notifyMovingStudents(){

    }

    public int getColored(Colors color) {

    }
}
