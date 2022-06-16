package it.polimi.ingsw.server.model.map;

import it.polimi.ingsw.server.enumerations.PawnColors;
import it.polimi.ingsw.server.enumerations.TowerColors;
import it.polimi.ingsw.server.model.component.NoEntryTile;
import it.polimi.ingsw.server.model.component.StudentDisc;
import it.polimi.ingsw.server.model.component.Tower;
import it.polimi.ingsw.server.model.player.Player;
import it.polimi.ingsw.server.model.player.Scoreboard;

import java.util.ArrayList;

public class Island {

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

    public Integer[] getNumberOfColors(){
        return this.numberOfColors;
    }

    public int getID(){
        return ID;
    }

    public int getGroupID(){
        return this.groupID;
    }

    public ArrayList<StudentDisc> getStudents(){
        return students;
    }

    public ArrayList<Tower> getTowers() {
        return towers;
    }

    public void setGroupID(int groupID){this.groupID = groupID;}

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

    public int getTowerNumber(){
        return towers.size();
    }

    public void disable(){
        disabled = true;
    }

    public boolean isDisabled(){
        return disabled;
    }

    /**
     * Method to add student on the island
     * @param studentDisc
     */
    public void addStudent(StudentDisc studentDisc){
        students.add(studentDisc);
        numberOfColors[studentDisc.getColor().ordinal()]++;
    }

    /**
     *
     * @param tower
     */
    public void addTower(Tower tower) {
        if(getTowerNumber() == 0) {
            towers.add(tower);
        }
        else System.out.println("There is already a tower in this island!");
    }

    public TowerColors getTowerColor(){
        return towers.isEmpty() ? null : towers.get(0).getColor();
    }

    public ArrayList<Tower> switchTowerColor(ArrayList<Tower> towers){
        ArrayList<Tower> temp;
        temp = this.towers;
        this.towers = towers;
        return temp;
    }

    public void deleteComponents(){
        towers = null;
        students = null;
    }

    public void addNoEntryTile (NoEntryTile t){
        noTile = t;
    }

    public boolean checkNoEntryTile (){
        return noTile != null;
    }

    public NoEntryTile removeNoEntryTile (){
        NoEntryTile tile = noTile;
        noTile = null;
        return tile;
    }

}
