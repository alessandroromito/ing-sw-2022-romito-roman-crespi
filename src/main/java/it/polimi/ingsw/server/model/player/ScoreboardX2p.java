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
import java.util.Arrays;
import java.util.List;

public class ScoreboardX2p implements Scoreboard, Serializable {

    private final Player player;
    private final boolean[][] availableCoin = new boolean[5][3];

    private final StudentDisc[] entrance = new StudentDisc[7];

    private final Integer[] diningRoom;
    private final ArrayList<StudentDisc> diningRoomList = new ArrayList<>();
    private final boolean[] professorTable;

    private final ArrayList<ProfessorPawn> professorList = new ArrayList<>();
    private final ArrayList<Tower> towers = new ArrayList<>(8);
    private TowerColors towerColor;

    public ScoreboardX2p(TowerColors towerColor,Player p){
        this.player = p;
        for(int i=0;i<5;i++)
            for(int k=0;k<3;k++)
                availableCoin[i][k] = true;

        for(int i=0; i<7; i++) entrance[i] = null;

        this.towerColor = towerColor;
        for(int i=0; i<8; i++)
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
        professorTable[professor.getColor().ordinal()] = true;
    }

    @Override
    public ProfessorPawn removeProfessor(PawnColors color) {
        try{
            professorTable[color.ordinal()] = false;
            for(ProfessorPawn professor : professorList){
                if(professor.getColor() == color) {
                    professorList.remove(professor);
                    return professor;
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
        for(int i = 0; i < 7; i++){
            if(entrance[i] != null && entrance[i].getID() == studentID)
                entrance[i] = null;
        }
        return null;
    }

    @Override
    public boolean getProfessor(PawnColors color) {
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
            for(int k=0;k<=6;k++)
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
        diningRoomList.add(student);
        diningRoom[student.getColorInt()]++;
        for(int i = 0; i < 7; i++){
            if(entrance[i].equals(student))
                entrance[i] = null;
        }
    }

    @Override
    public void moveFromEntranceToDining(StudentDisc student) {
        try {
            for (int i = 0; i < 7; i++)
                if (entrance[i] != null && entrance[i].getID() == student.getID()) {
                    diningRoom[entrance[i].getColorInt()]++;
                    diningRoomList.add(student);

                    if(diningRoom[entrance[i].getColorInt()] %3 == 0)
                        if(availableCoin[ entrance[i].getColorInt() ][ diningRoom[entrance[i].getColorInt()] /3 ]){
                            availableCoin[ entrance[i].getColorInt() ][ diningRoom[entrance[i].getColorInt()] /3 ] = false;
                            player.addCoin();
                        }

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
    public Tower removeTower() {
        return towers.remove(towers.size()-1);
    }

    @Override
    public void addTower(Tower tower) {
        towers.add(tower);
    }

    @Override
    public List<StudentDisc> getEntrance() {
        return new ArrayList<>(Arrays.asList(entrance).subList(0, 7));
    }

    @Override
    public Integer[] getDiningRoom() {
        return diningRoom;
    }

    @Override
    public ArrayList<StudentDisc> getDiningRoomList() {
        return diningRoomList;
    }

    @Override
    public StudentDisc getStudentFromDining(PawnColors color){
        for(StudentDisc studentDisc : diningRoomList){
            if(studentDisc.getColor() == color)
                return studentDisc;
        }
        return null;
    }

    @Override
    public ArrayList<ProfessorPawn> getProfessorList() {
        return professorList;
    }

    @Override
    public void addTowers(ArrayList<Tower> towers) {
        this.towers.addAll(towers);
    }

    @Override
    public void removeStudentFromDining(StudentDisc studentDisc) {
        diningRoom[studentDisc.getColor().ordinal()]--;
        diningRoomList.removeIf(student -> student.getColor() == studentDisc.getColor());
    }

}