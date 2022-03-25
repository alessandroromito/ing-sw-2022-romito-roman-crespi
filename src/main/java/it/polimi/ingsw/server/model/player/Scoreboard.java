package it.polimi.ingsw.server.model.player;

import it.polimi.ingsw.server.model.component.ProfessorPawn;
import it.polimi.ingsw.server.model.component.StudentDisc;
import it.polimi.ingsw.server.observer.ObserverTower;

public interface Scoreboard {

    void setProfessorTrue(int color);
    void setProfessorFalse(int color);
    int[] getProfessor();
    int getNumTowers();
    int getNumStudentsFromEntrance();
    int[] getPlayerStudentsFromDining();
    void addStudentsOnEntrance(int[] students);
    void moveFromeEntranceToDining(int[] students);

}
