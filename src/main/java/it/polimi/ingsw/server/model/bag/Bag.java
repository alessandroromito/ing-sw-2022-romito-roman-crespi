package it.polimi.ingsw.server.model.bag;

import it.polimi.ingsw.server.observer.ObserverLastStudent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Bag {
    private List<Integer> bagStudents;
    private final ObserverLastStudent obsLS;

    public Bag(){
        bagStudents = new ArrayList<>(130);
        for ( int i = 1 ; i <= 130 ; i++)
            this.bagStudents.add(i);
        Collections.shuffle(this.bagStudents);
        obsLS = new ObserverLastStudent(this);
    }

    //ritorna l'ID dello studente
    public int pickSorted(){
        int temp = this.bagStudents.get(0);
        this.bagStudents.remove(0);
        notifyMovingStudents();
        return temp + 58;
    }

    public boolean isStudentsListEmpty(){
        return bagStudents.isEmpty();
    }

    public void notifyMovingStudents(){
        obsLS.onUpdate();
    }
}
