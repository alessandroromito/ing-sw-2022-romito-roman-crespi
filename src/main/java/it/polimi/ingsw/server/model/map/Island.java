package it.polimi.ingsw.server.model.map;

import it.polimi.ingsw.server.enumerations.PawnColors;
import it.polimi.ingsw.server.enumerations.TowerColors;
import it.polimi.ingsw.server.model.component.NoEntryTile;
import it.polimi.ingsw.server.model.component.StudentDisc;
import it.polimi.ingsw.server.model.component.Tower;
import it.polimi.ingsw.server.model.player.Player;
import it.polimi.ingsw.server.model.player.Scoreboard;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Class that represents an Island of the game
 */
public class Island implements Serializable {

    protected final int ID;
    protected Integer[] numberOfColors = {0,0,0,0,0};
    protected ArrayList<StudentDisc> students = new ArrayList<>();
    protected ArrayList<Tower> towers = new ArrayList<>();

    protected int groupID = -1;
    protected boolean disabled = false;
    protected NoEntryTile noTile = null;

    /**
     * Default constructor.
     */
    public Island(int id){
        this.ID = id;
    }

    /**
     * Getter of the number of the students
     * @return the array with the number of students for every color
     */
    public Integer[] getNumberOfColors(){
        return this.numberOfColors;
    }

    /**
     * Getter
     * @return id
     */
    public int getID(){
        return ID;
    }

    /**
     * Getter
     * @return id
     */
    public int getGroupID(){
        return this.groupID;
    }

    /**
     * Getter
     * @return students
     */
    public ArrayList<StudentDisc> getStudents(){
        return students;
    }

    /**
     * Getter
     * @return towers
     */
    public ArrayList<Tower> getTowers() {
        return towers;
    }

    /**
     * Setter
     * @param groupID groupID to be set
     */
    public void setGroupID(int groupID){
        this.groupID = groupID;
    }

    /**
     * Calculate the player influence on this island
     * @param p player
     * @return the influence
     */
    public int getInfluence(Player p){
        int influence = 0;
        Scoreboard scoreboard = p.getScoreboard();
        TowerColors playerTowerColor = scoreboard.getTowerColor();

        for(PawnColors color: PawnColors.values()){
            if(scoreboard.getProfessor(color) ){
                influence += numberOfColors[color.ordinal()];
            }
        }
        if(playerTowerColor == getTowerColor())
            influence += getTowerNumber();

        if(p.isAdditionalPoints()) influence += 2;

        return influence;
    }

    /**
     * Calculate the player influence on this island when the card214 is active
     * @param p player
     * @return the influence
     */
    public int getInfluence_214 (Player p){
        int influence = 0;
        Scoreboard scoreboard = p.getScoreboard();

        for(PawnColors color: PawnColors.values()){
            if(scoreboard.getProfessor(color) ){
                influence += numberOfColors[color.ordinal()];
            }
        }

        return influence;
    }

    /**
     * Calculate the player influence on this island when the card217 is active
     * @param p player
     * @return the influence
     */
    public int getInfluence_217 (Player p,PawnColors disabled){
        int influence = 0;
        Scoreboard scoreboard = p.getScoreboard();
        TowerColors playerTowerColor = scoreboard.getTowerColor();

        for(PawnColors color: PawnColors.values()){
            if(color != disabled)
                if(scoreboard.getProfessor(color) ){
                    influence += numberOfColors[color.ordinal()];
                }
        }
        if(playerTowerColor == getTowerColor())
            influence += getTowerNumber();

        if(p.isAdditionalPoints()) influence += 2;

        return influence;
    }

    /**
     * Getter
     * @return number of tower
     */
    public int getTowerNumber(){
        return towers.size();
    }

    /**
     * Set disabled this island
     */
    public void disable(){
        disabled = true;
    }

    /**
     * Getter
     * @return if is disabled
     */
    public boolean isDisabled(){
        return disabled;
    }

    /**
     * Method to add student on the island
     * @param studentDisc student to add
     */
    public void addStudent(StudentDisc studentDisc){
        students.add(studentDisc);
        numberOfColors[studentDisc.getColor().ordinal()]++;
    }

    /**
     * Adda a tower on this island
     * @param tower tower to be added
     */
    public void addTower(Tower tower) {
        if(getTowerNumber() == 0) {
            towers.add(tower);
        } else System.out.println("There is already a tower in this island!");
    }

    /**
     * Get the tower color of the tower on this island if present
     * @return
     */
    public TowerColors getTowerColor(){
        return towers.isEmpty() ? null : towers.get(0).getColor();
    }

    /**
     * Add a no entry tile on this island
     * @param t
     */
    public void addNoEntryTile (NoEntryTile t){
        noTile = t;
    }

    /**
     * Check if there is a no entry tile
     * @return true if there is, false otherwise
     */
    public boolean checkNoEntryTile (){
        return noTile != null;
    }

    /**
     * Method to remove a no entry tile
     * @return the no entry tile removed
     */
    public NoEntryTile removeNoEntryTile (){
        NoEntryTile tile = noTile;
        noTile = null;
        return tile;
    }

    /**
     * Remove all the towers on this island
     */
    public void removeTowers(){
        towers = new ArrayList<>();
    }
}
