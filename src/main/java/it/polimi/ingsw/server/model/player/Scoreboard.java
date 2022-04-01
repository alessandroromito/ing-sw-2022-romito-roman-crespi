package it.polimi.ingsw.server.model.player;

import it.polimi.ingsw.server.model.component.PawnColors;

public interface Scoreboard {

    void setTowerColor(TowerColors t);
    void setProfessorTrue(PawnColors color);
    void setProfessorFalse(PawnColors color);
    boolean getProfessor(PawnColors color);
    int getNumTowers();
    int getNumStudentsFromEntrance();
    int[] getPlayerStudentsFromDining();
    void addStudentOnEntrance(PawnColors student);
    void moveFromEntranceToDining(int position);
    TowerColors getTowerColor();

}
