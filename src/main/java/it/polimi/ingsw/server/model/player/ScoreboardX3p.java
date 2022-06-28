package it.polimi.ingsw.server.model.player;

import it.polimi.ingsw.server.enumerations.PawnColors;
import it.polimi.ingsw.server.enumerations.TowerColors;
import it.polimi.ingsw.server.exception.EntranceFullException;
import it.polimi.ingsw.server.exception.MissingProfessorException;
import it.polimi.ingsw.server.exception.StudentNotInEntranceException;
import it.polimi.ingsw.server.model.component.ProfessorPawn;
import it.polimi.ingsw.server.model.component.StudentDisc;
import it.polimi.ingsw.server.model.component.Tower;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ScoreboardX3p implements Scoreboard, Serializable {

    private Player player;
    private boolean avaibleCoin[][] = new boolean[5][3];

    private final StudentDisc[] entrance = new StudentDisc[9];

    private final Integer[] diningRoom;
    private final boolean[] professorTable;

    private ArrayList<ProfessorPawn> professorList = new ArrayList<>();
    private ArrayList<Tower> towers = new ArrayList<>(6);
    private TowerColors towerColor;

    public ScoreboardX3p(TowerColors towerColor,Player p){
        this.player = p;
        for(int i=0;i<5;i++)
            for(int k=0;k<3;k++)
                avaibleCoin[i][k] = true;

        for(int i=0; i<9; i++) entrance[i] = null;

        this.towerColor = towerColor;
        for(int i=0; i<6; i++)
            towers.add(new Tower(i, towerColor));

        professorTable = new boolean[5];
        diningRoom = new Integer[5];
        for(int i=0;i<5;i++){
            diningRoom[i] = 0;
            professorTable[i] = false;
        }
    }

    @Override
    public int getNumProf(){
        int count = 0;
        for(PawnColors col:PawnColors.values())
            if(getProfessor(col)) count++;
        return count;
    }

    @Override
    public void setTowerColor(TowerColors t){
        this.towerColor = t;
    }

    @Override
    public void addProfessor(ProfessorPawn professor) {
        professorList.add(professor);
        professorTable[professor.getColorInt()] = true;
    }

    @Override
    public ProfessorPawn removeProfessor(PawnColors color) {
        try{
            professorTable[color.ordinal()] = false;
            for(ProfessorPawn professor : professorList){
                if(professor.getColor() == color) {
                    ProfessorPawn temp = professor;
                    professorList.remove(professor);
                    return temp;
                }
            }
            throw new MissingProfessorException("Unable to remove professor");
        } catch (MissingProfessorException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public StudentDisc removeStudent(StudentDisc student){
        int studentID = student.getID();
        for(int i = 0; i < 9; i++){
            if(entrance[i].getID() == studentID)
                entrance[i] = null;
        }
        return null;
    }

    @Override
    public boolean getProfessor(PawnColors color) {
        //return this.professorTable[color.ordinal()];
        for(ProfessorPawn professor : professorList){
            if(professor.getColor() == color)
                return true;
        }
        return false;
    }

    @Override
    public int getNumTowers() {
        return towers.size();
    }

    @Override
    public int getNumStudentsFromEntrance() {
        int c = 0;
        for(int i=8;i>=0;i--)
            if(entrance[i] != null)
                c++;
        return c;
    }

    @Override
    public int getPlayerStudentFromDining(PawnColors color) {
        return this.diningRoom[color.ordinal()];
    }

    @Override
    public void addStudentOnEntrance(StudentDisc student) {
        try{
            for(int k = 0; k < 9; k++)
                if(entrance[k] == null){
                    entrance[k] = student;
                    return;
                }

            throw new EntranceFullException("Entrance is full");
        } catch (EntranceFullException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addStudentOnDining(StudentDisc student) {
        diningRoom[student.getColorInt()]++;
        for(int i = 0; i<entrance.length; i++){
            if(entrance[i].equals(student))
                entrance[i] = null;
        }
    }

    @Override
    public void moveFromEntranceToDining(StudentDisc student) {
        try {
            for (int i = 0; i < 9; i++)
                if (entrance[i] != null && entrance[i].getID() == student.getID()) {
                    diningRoom[entrance[i].getColorInt()]++;

                    if(diningRoom[entrance[i].getColorInt()] %3 == 0)
                        if(avaibleCoin[ entrance[i].getColorInt() ][ diningRoom[entrance[i].getColorInt()] /3 ]){
                            avaibleCoin[ entrance[i].getColorInt() ][ diningRoom[entrance[i].getColorInt()] /3 ] = false;
                            player.addCoin();
                        }

                    entrance[i] = null;
                    return;
                }
            throw new StudentNotInEntranceException("Student not found in entrance");
        } catch (StudentNotInEntranceException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public TowerColors getTowerColor() {
        return this.towerColor;
    }

    @Override
    public Tower removeTower() {
        return towers.remove(towers.size()-1);
    }

    @Override
    public void addTower(Tower tower) {
        towers.add(tower);
    }

    @Override
    public List<StudentDisc> getEntrance() {
        List<StudentDisc> tempList = new ArrayList<>();
        for(int i=0; i<9; i++){
            tempList.add(entrance[i]);
        }
        return tempList;
    }

    @Override
    public Integer[] getDiningRoom() {
        return diningRoom;
    }

    @Override
    public ArrayList<ProfessorPawn> getProfessorList() {
        return professorList;
    }

    @Override
    public void addTowers(ArrayList<Tower> towers) {
        this.towers.addAll(towers);
    }
}