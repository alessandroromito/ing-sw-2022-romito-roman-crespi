package it.polimi.ingsw.server.model.player;

import it.polimi.ingsw.server.model.component.ProfessorPawn;
import it.polimi.ingsw.server.model.component.StudentDisc;
import it.polimi.ingsw.server.observer.ObserverTower;

public class Scoreboard {
    private StudentDisc[] entrance;
    private StudentDisc[] diningRoom;
    private ProfessorPawn[] ProfessorTable;
    private int towerLine;
    private ObserverTower obsT;

    public void Scoreboard(){
        obsT = new ObserverTower();
    }

    public int getNumTowers(){
        return towerLine;
    }

    public void updateObserver(){
        obsT.onUpdate(getNumTowers());
    }
}
