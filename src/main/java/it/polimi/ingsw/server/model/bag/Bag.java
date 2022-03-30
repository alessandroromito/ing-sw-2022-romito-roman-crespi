package it.polimi.ingsw.server.model.bag;

import it.polimi.ingsw.server.model.component.PawnColors;
import it.polimi.ingsw.server.model.component.StudentDisc;
import it.polimi.ingsw.server.observer.ObserverLastStudent;

import java.util.*;

public class Bag {
    private List<Integer> bagStudents;
    private ObserverLastStudent obsLS;

    public Bag(){
        bagStudents = new ArrayList<>(130);
        for ( int i = 1 ; i <= 130 ; i++)
            this.bagStudents.add(i);
        Collections.shuffle(this.bagStudents);
        obsLS = new ObserverLastStudent(this);
    }

    //ritorna l'ID dello studente
    public int getSorted(){
        int temp = this.bagStudents.get(0);
        this.bagStudents.remove(0);
        notifyMovingStudents();
        return temp + 104;
    }

    public boolean isStudentsListEmpty(){
        return bagStudents.isEmpty();
    }

    public void notifyMovingStudents(){
        obsLS.onUpdate();
    }

    public int getColored(PawnColors color) {
        //da fare
    }
}
