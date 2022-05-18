package it.polimi.ingsw.server.model.player;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.server.exception.EntranceFullException;
import it.polimi.ingsw.server.exception.StudentNotInEntranceException;
import it.polimi.ingsw.server.enumerations.*;
import it.polimi.ingsw.server.model.component.StudentDisc;

import java.util.ArrayList;
import java.util.List;

public class ScoreboardX3p implements Scoreboard{

    private final StudentDisc[] entrance;
    private final Integer[] diningRoom;
    private final boolean[] professorTable;
    private int towerLine;
    private TowerColors towerColor;
    private GameController controller;
    private Player player;

    public ScoreboardX3p(Player player){
        this.player = player;
        entrance = new StudentDisc[9];
        for(int i=0;i<9;i++) entrance[i] = null;
        towerLine = 6;
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
    public void setProfessorTrue(PawnColors color) {
        this.professorTable[color.ordinal()] = true;
    }

    @Override
    public void setProfessorFalse(PawnColors color) {
        this.professorTable[color.ordinal()] = false;
    }

    @Override
    public boolean getProfessor(PawnColors color) {
        return this.professorTable[color.ordinal()];
    }

    @Override
    public int getNumTowers() {
        return this.towerLine;
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
    public void addStudentOnEntrance(StudentDisc student) throws EntranceFullException {
        int k = 0;
        for(k=0;k<=8;k++)
            if(entrance[k] == null){
                entrance[k] = student;
                return;
            }

        throw new EntranceFullException("Entrance is full");

    }

    @Override
    public void addStudentOnDining(StudentDisc student) {
        diningRoom[student.getColorInt()]++;
    }

    @Override
    public void moveFromEntranceToDining(StudentDisc student) throws StudentNotInEntranceException {
        int c=0;
        for(int i=0;i<9;i++)
            if(entrance[c].equals(student)) {
                diningRoom[entrance[c].getColorInt()]++;
                entrance[c] = null;
                return;
            }
        throw new StudentNotInEntranceException("Student not found in entrance");
    }

    @Override
    public TowerColors getTowerColor() {
        return this.towerColor;
    }

    @Override
    public void removeTower() {
        this.towerLine--;
    }

    @Override
    public void addTower() {
        this.towerLine++;
    }

    @Override
    public List<StudentDisc> getEntrance() {
        List<StudentDisc> tempList = new ArrayList<>();
        for(int i=0; i<9; i++){
            tempList.add(entrance[i]);
        }
        return tempList;
    }

}