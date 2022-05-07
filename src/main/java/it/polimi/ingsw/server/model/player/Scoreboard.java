package it.polimi.ingsw.server.model.player;

import it.polimi.ingsw.server.enumerations.PawnColors;
import it.polimi.ingsw.server.enumerations.TowerColors;
import it.polimi.ingsw.server.exception.EntranceFullException;
import it.polimi.ingsw.server.exception.StudentNotInEntranceException;
import it.polimi.ingsw.server.model.component.StudentDisc;

public interface Scoreboard {

    void setTowerColor(TowerColors t);
    void setProfessorTrue(PawnColors color);
    void setProfessorFalse(PawnColors color);
    boolean getProfessor(PawnColors color);
    int getNumTowers();
    int getNumStudentsFromEntrance();
    int getNumProf();
    int getPlayerStudentFromDining(PawnColors color);
    void addStudentOnEntrance(StudentDisc student) throws EntranceFullException;
    void addStudentOnDining(StudentDisc student);
    void moveFromEntranceToDining(StudentDisc student) throws StudentNotInEntranceException;
    TowerColors getTowerColor();
    void removeTower();
    void addTower();
}
