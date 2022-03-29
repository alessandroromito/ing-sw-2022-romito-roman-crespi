package it.polimi.ingsw.server.observer;

import it.polimi.ingsw.server.model.bag.Bag;

public class ObserverLastStudent extends MainObserver {
    private Bag bag;

    public ObserverLastStudent(Bag bag){
        this.bag = bag;
    }

    public void onUpdate(){
        if(bag.isStudentsListEmpty())   setEndGame();
    }
}
