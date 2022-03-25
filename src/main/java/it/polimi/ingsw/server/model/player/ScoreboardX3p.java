package it.polimi.ingsw.server.model.player;

import it.polimi.ingsw.server.model.component.ProfessorPawn;
import it.polimi.ingsw.server.model.component.StudentDisc;
import it.polimi.ingsw.server.observer.ObserverTower;

public class ScoreboardX3p implements Scoreboard{

    private int Entrance[];
    private int DiningRoom[];
    private boolean ProfessorTable[];
    private int TowerLine;
    private ObserverTower obsT;

    public ScoreboardX3p(){
        obsT = new ObserverTower(this);
    }

    public void setProfessorTrue(int color) {

    }

    public void setProfessorFalse(int color) {

    }

    public int[] getProfessor() {
        return new int[0];
    }

    public int getNumTowers() {
        return 0;
    }

    public int getNumStudentsFromEntrance() {
        return 0;
    }

    public int[] getPlayerStudentsFromDining() {
        return new int[0];
    }

    public void addStudentsOnEntrance(int[] students) {

    }

    public void moveFromeEntranceToDining(int[] students) {

    }
}
