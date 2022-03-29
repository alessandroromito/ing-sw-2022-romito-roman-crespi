package it.polimi.ingsw.server.model.map;

import it.polimi.ingsw.server.model.component.PawnColors;
import it.polimi.ingsw.server.model.player.TowerColors;

import java.util.ArrayList;

public class Island {

    private int ID;
    private Integer[] numberOfColors;
    private int groupID;
    private int towerNumber;
    private TowerColors towerColor;
    private boolean disabled;

    /**
     * Default constructor.
     */
    public Island(){
        disabled = false;
        //etc
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

    public int getInfluence(){
        // da implementare
    }

    public int getTowerNumber(){
        return this.towerNumber;
    }

    public TowerColors getTowerColor(){
        return this.towerColor;
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

    public void addTower(TowerColors color){
        // da implementare
    }

    public void switchTowerColor(TowerColors color){
        this.towerColor = color;
    }

}
