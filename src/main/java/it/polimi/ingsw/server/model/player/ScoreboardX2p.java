package it.polimi.ingsw.server.model.player;

import it.polimi.ingsw.server.enumerations.PawnColors;
import it.polimi.ingsw.server.enumerations.TowerColors;
import it.polimi.ingsw.server.exception.EntranceFullException;
import it.polimi.ingsw.server.exception.StudentNotInEntranceException;
import it.polimi.ingsw.server.model.component.StudentDisc;

import java.util.ArrayList;
import java.util.List;

public class ScoreboardX2p implements Scoreboard{
    private final StudentDisc[] entrance;
    private final Integer[] diningRoom;
    private final boolean[] professorTable;
    private int towerLine;
    private TowerColors towerColor;
    private Player player;


    public ScoreboardX2p(Player player){
        this.player = player;
        entrance = new StudentDisc[7];
        for(int i=0;i<7;i++) entrance[i] = null;
        towerLine = 8;
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
        for(int i=6;i>=0;i--)
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
            int k = 0;
            for(k=0;k<=6;k++)
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
    }

    @Override
    public void moveFromEntranceToDining(StudentDisc student) {
        try {
            for (int i = 0; i < 7; i++)
                if (entrance[i].equals(student)) {
                    diningRoom[entrance[i].getColorInt()]++;
                    entrance[i] = null;
                    return;
                }
            throw new StudentNotInEntranceException("Student not in entrance!");
        } catch (StudentNotInEntranceException e) {
            e.printStackTrace();
        }
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
        for(int i=0; i<7; i++){
            tempList.add(entrance[i]);
        }
        return tempList;
    }

}