package it.polimi.ingsw.server.model.player;

public interface Scoreboard {

    void setProfessorTrue(int color);
    void setProfessorFalse(int color);
    boolean[] getProfessor();
    int getNumTowers();
    int getNumStudentsFromEntrance();
    int[] getPlayerStudentsFromDining();
    void addStudentsOnEntrance(int[] students);
    void moveFromeEntranceToDining(int[] students);
    TowerColors getTowerColor();

}
