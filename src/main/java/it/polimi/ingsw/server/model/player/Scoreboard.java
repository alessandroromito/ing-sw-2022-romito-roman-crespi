package it.polimi.ingsw.server.model.player;

import it.polimi.ingsw.server.enumerations.PawnColors;
import it.polimi.ingsw.server.enumerations.TowerColors;
import it.polimi.ingsw.server.model.component.ProfessorPawn;
import it.polimi.ingsw.server.model.component.StudentDisc;
import it.polimi.ingsw.server.model.component.Tower;

import java.util.ArrayList;
import java.util.List;

public interface Scoreboard {

    void addProfessor(ProfessorPawn professor);
    ProfessorPawn removeProfessor(PawnColors color);
    Tower removeTower();
    StudentDisc removeStudent(StudentDisc student);
    boolean getProfessor(PawnColors color);
    int getNumTowers();
    int getNumStudentsFromEntrance();
    int getNumProf();
    int getPlayerStudentFromDining(PawnColors color);
    void addStudentOnEntrance(StudentDisc student);
    void addStudentOnDining(StudentDisc student);
    void moveFromEntranceToDining(StudentDisc student);
    TowerColors getTowerColor();
    List<StudentDisc> getEntrance();
    Integer[] getDiningRoom();
    StudentDisc getStudentFromDining(PawnColors color);
    ArrayList<ProfessorPawn> getProfessorList();
    void addTowers(ArrayList<Tower> towers);
    void removeStudentFromDining(StudentDisc studentFromDining);
}
