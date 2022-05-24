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
    private int groupID = -1;
    protected Tower tower = null;
    private boolean disabled = false;
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
        return this.ID;
    }

    public int getGroupID(){
        return this.groupID;
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
        return tower == null ? 0 : 1;
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
            this.tower = tower;
        }
        else System.out.println("There is already a tower in this island!");
    }

    public TowerColors getTowerColor(){
        return tower.getColor();
    }

    public void switchTowerColor(Tower tower){
        this.tower = tower;
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
