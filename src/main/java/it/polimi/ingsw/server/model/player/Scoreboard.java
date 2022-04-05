package it.polimi.ingsw.server.model.player;

import it.polimi.ingsw.server.exception.EntranceFullException;
import it.polimi.ingsw.server.exception.StudentNotInEntranceException;
import it.polimi.ingsw.server.enumerations.*;
import it.polimi.ingsw.server.model.component.StudentDisc;

public interface Scoreboard {

    void setTowerColor(TowerColors t);
    void setProfessorTrue(PawnColors color);
    void setProfessorFalse(PawnColors color);
    boolean getProfessor(PawnColors color);

    int getNumTowers();
    int getNumStudentsFromEntrance();
    int getPlayerStudentFromDining(PawnColors color);
    void addStudentOnEntrance(StudentDisc student) throws EntranceFullException;
    void moveFromEntranceToDining(StudentDisc student) throws StudentNotInEntranceException;
    TowerColors getTowerColor();
    void removeTower();
    void addTower();
    void notifyMovingTowers();
}
