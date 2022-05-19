package it.polimi.ingsw.server.model.player;

import it.polimi.ingsw.server.enumerations.PawnColors;
import it.polimi.ingsw.server.enumerations.TowerColors;
import it.polimi.ingsw.server.model.component.StudentDisc;

import java.util.List;

public interface Scoreboard {

    void setTowerColor(TowerColors t);
    void setProfessorTrue(PawnColors color);
    void setProfessorFalse(PawnColors color);
    boolean getProfessor(PawnColors color);
    int getNumTowers();
    int getNumStudentsFromEntrance();
    int getNumProf();
    int getPlayerStudentFromDining(PawnColors color);
    void addStudentOnEntrance(StudentDisc student);
    void addStudentOnDining(StudentDisc student);
    void moveFromEntranceToDining(StudentDisc student);
    TowerColors getTowerColor();
    void removeTower();
    void addTower();
    List<StudentDisc> getEntrance();
}
