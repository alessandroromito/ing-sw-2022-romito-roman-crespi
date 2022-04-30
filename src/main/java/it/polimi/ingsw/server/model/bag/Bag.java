package it.polimi.ingsw.server.model.bag;

import it.polimi.ingsw.controller.GameController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Bag {
    private List<Integer> bagStudents;
    private GameController controller;

    public Bag(){
        bagStudents = new ArrayList<>(130);
        for ( int i = 1 ; i <= 130 ; i++)
            this.bagStudents.add(i);
        Collections.shuffle(this.bagStudents);
    }

    //ritorna l'ID dello studente
    public int pickSorted(){
        int temp = this.bagStudents.get(0);
        this.bagStudents.remove(0);
        notifyMovingStudents();
        notifyMovingStudents();
        return temp + 58;
    }

    public List<Integer> getBagStudents(){ return bagStudents; }

    public boolean isStudentsListEmpty(){
        return bagStudents.isEmpty();
    }

    public void notifyMovingStudents(){
        if(isStudentsListEmpty())
            controller.lastTurn();
    }

    public void addGameController(GameController c) {
        this.controller = c;
    }
}
