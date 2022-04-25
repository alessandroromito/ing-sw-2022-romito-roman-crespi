package it.polimi.ingsw.server.model.map;

import it.polimi.ingsw.server.enumerations.PawnColors;
import it.polimi.ingsw.server.enumerations.TowerColors;
import it.polimi.ingsw.server.exception.AddingWrongColorTowerToIslandException;
import it.polimi.ingsw.server.model.player.Player;
import it.polimi.ingsw.server.model.player.Scoreboard;

public class Island {

    private final int ID;
    private Integer[] numberOfColors = {0,0,0,0,0};
    private int groupID;
    private int towerNumber = 0;
    private TowerColors towerColor = null;
    private boolean disabled;

    /**
     * Default constructor.
     */
    public Island(int id){
        disabled = false;
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

        return influence;
    }

    public int getTowerNumber(){
        return this.towerNumber;
    }

    public void disable(){
        disabled = true;
    }

    public boolean isDisabled(){
        return disabled;
    }

    public void addStudent(PawnColors color){
        numberOfColors[color.ordinal()] ++;
    }

    public void addTower(TowerColors color) throws AddingWrongColorTowerToIslandException {
        if(getTowerNumber() == 0) {
            towerNumber++;
            towerColor = color;
        }
        else System.out.println("There is already a tower in this island!");
    }

    public TowerColors getTowerColor(){
        return this.towerColor;
    }

    public void switchTowerColor(TowerColors color){
        this.towerColor = color;
    }

}
