package it.polimi.ingsw.server.model.player;

import it.polimi.ingsw.server.enumerations.PawnColors;
import it.polimi.ingsw.server.enumerations.TowerColors;
import it.polimi.ingsw.server.model.component.ProfessorPawn;
import it.polimi.ingsw.server.model.component.StudentDisc;
import it.polimi.ingsw.server.model.component.Tower;

import java.util.ArrayList;
import java.util.List;

public interface Scoreboard {

    void setTowerColor(TowerColors t);
    void addProfessor(ProfessorPawn professor);
    ProfessorPawn removeProfessor(PawnColors color);
    boolean getProfessor(PawnColors color);
    int getNumTowers();
    int getNumStudentsFromEntrance();
    int getNumProf();
    int getPlayerStudentFromDining(PawnColors color);
    void addStudentOnEntrance(StudentDisc student);
    void addStudentOnDining(StudentDisc student);
    void moveFromEntranceToDining(StudentDisc student);
    TowerColors getTowerColor();
    Tower removeTower();
    void addTower(Tower tower);
    List<StudentDisc> getEntrance();
    ArrayList<ProfessorPawn> getProfessorList();
}
