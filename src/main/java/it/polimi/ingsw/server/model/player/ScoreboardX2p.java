package it.polimi.ingsw.server.model.player;

import it.polimi.ingsw.server.enumerations.PawnColors;
import it.polimi.ingsw.server.enumerations.TowerColors;
import it.polimi.ingsw.server.exception.EntranceFullException;
import it.polimi.ingsw.server.exception.MissingProfessorException;
import it.polimi.ingsw.server.exception.StudentNotInEntranceException;
import it.polimi.ingsw.server.model.component.ProfessorPawn;
import it.polimi.ingsw.server.model.component.StudentDisc;
import it.polimi.ingsw.server.model.component.Tower;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class that represents the scoreboard of every player, it contains the entrance section,
 * the dining room section and the professors owned by the player
 */
public class ScoreboardX2p implements Scoreboard, Serializable {
    @Serial
    private static final long serialVersionUID = 3266677119453192198L;

    private final Player player;
    private final boolean[][] availableCoin = new boolean[5][3];

    private final StudentDisc[] entrance = new StudentDisc[7];

    private final Integer[] diningRoom;
    private final ArrayList<StudentDisc> diningRoomList = new ArrayList<>();
    private final boolean[] professorTable;

    private final ArrayList<ProfessorPawn> professorList = new ArrayList<>();
    private final ArrayList<Tower> towers = new ArrayList<>(8);
    private final TowerColors towerColor;

    /**
     * Default Constructor
     * @param towerColor color of the towers
     * @param p player which belongs
     */
    public ScoreboardX2p(TowerColors towerColor, Player p){
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

    /**
     * Getter
     * @return the number of professors owned
     */
    @Override
    public int getNumProf(){
        int count = 0;
        for(PawnColors col:PawnColors.values())
            if(getProfessor(col)) count++;
        return count;
    }

    /**
     * Add a professor
     * @param professor prof to add
     */
    @Override
    public void addProfessor(ProfessorPawn professor) {
        professorList.add(professor);
        professorTable[professor.getColor().ordinal()] = true;
    }

    /**
     * Remove an owned professor
     * @param color color of the prof to remove
     * @return the professor removed
     */
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
            System.out.println("Unable to remove professor");
        }
        return null;
    }

    /**
     * Remove a student from the entrance
     * @param student stud to remove
     * @return the student removed
     */
    @Override
    public StudentDisc removeStudent(StudentDisc student){
        int studentID = student.getID();
        for(int i = 0; i < 7; i++){
            if(entrance[i] != null && entrance[i].getID() == studentID)
                entrance[i] = null;
        }
        return null;
    }

    /**
     * Check if there is the professor asked
     * @param color color of the professor to check
     * @return true if the player has the professor, false otherwise
     */
    @Override
    public boolean getProfessor(PawnColors color) {
        for(ProfessorPawn professor : professorList){
            if(professor.getColor() == color)
                return true;
        }
        return false;
    }

    /**
     * @return the number of the towers lefts
     */
    @Override
    public int getNumTowers() {
        return towers.size();
    }

    /**
     * @return the number of students presents on entrance section
     */
    @Override
    public int getNumStudentsFromEntrance() {
        int c = 0;
        for(int i=6;i>=0;i--)
            if(entrance[i] != null)
                c++;
        return c;
    }

    /**
     * Get the number of students on the dining room for one color
     * @param color color of the students to count
     * @return the total number
     */
    @Override
    public int getPlayerStudentFromDining(PawnColors color) {
        return this.diningRoom[color.ordinal()];
    }

    /**
     * Add a student on the entrance section
     *
     * @param student student to add
     * @return
     */
    @Override
    public boolean addStudentOnEntrance(StudentDisc student) {
        try{
            for(int k=0;k<=6;k++)
                if(entrance[k] == null){
                    entrance[k] = student;
                    return true;
                }
            throw new EntranceFullException("Entrance is full");
        } catch (EntranceFullException e) {
            System.out.println("Entrance is full");
            return false;
        }
    }

    /**
     * Add a student on the dining room section
     * @param student student to add
     */
    @Override
    public void addStudentOnDining(StudentDisc student) {
        diningRoomList.add(student);
        diningRoom[student.getColorInt()]++;
        for(int i = 0; i < 7; i++){
            if(entrance[i].equals(student))
                entrance[i] = null;
        }
    }

    /**
     * Move a student from the entrance to the dining room section if presents
     *
     * @param student student to move
     * @return
     */
    @Override
    public boolean moveFromEntranceToDining(StudentDisc student) {
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
                    return true;
                }
            throw new StudentNotInEntranceException("Student not in entrance!");
        } catch (StudentNotInEntranceException e) {
            System.out.println("Student not in entrance!");
            return false;
        }
    }

    /**
     * Getter
     * @return the tower color of this scoreboard
     */
    @Override
    public TowerColors getTowerColor() {
        return this.towerColor;
    }

    /**
     * Remove a tower
     * @return the tower removed
     */
    @Override
    public Tower removeTower() {
        return towers.remove(towers.size()-1);
    }

    /**
     * Getter
     * @return the entrance section as a List
     */
    @Override
    public List<StudentDisc> getEntrance() {
        return new ArrayList<>(Arrays.asList(entrance).subList(0, 7));
    }

    /**
     * Getter
     * @return the dining room
     */
    @Override
    public Integer[] getDiningRoom() {
        return diningRoom;
    }

    /**
     * Get a student from the dining room
     * @param color color of the student requested
     * @return the student
     */
    @Override
    public StudentDisc getStudentFromDining(PawnColors color){
        for(StudentDisc studentDisc : diningRoomList){
            if(studentDisc.getColor() == color)
                return studentDisc;
        }
        return null;
    }

    /**
     * Getter
     * @return the list of the professors
     */
    @Override
    public ArrayList<ProfessorPawn> getProfessorList() {
        return professorList;
    }

    /**
     * Add @param towers to the scoreboard
     * @param towers towers to be added
     */
    @Override
    public void addTowers(ArrayList<Tower> towers) {
        this.towers.addAll(towers);
    }

    /**
     * Remove a student from the dining room section
     * @param studentDisc student removed
     */
    @Override
    public void removeStudentFromDining(StudentDisc studentDisc) {
        diningRoom[studentDisc.getColor().ordinal()]--;
        diningRoomList.removeIf(student -> student.getColor() == studentDisc.getColor());
    }

}