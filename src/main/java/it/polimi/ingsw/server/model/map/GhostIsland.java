package it.polimi.ingsw.server.model.map;

import it.polimi.ingsw.server.enumerations.PawnColors;
import it.polimi.ingsw.server.enumerations.TowerColors;
import it.polimi.ingsw.server.exception.AddingWrongColorTowerToIslandException;


public class GhostIsland extends Island{

    /**
     * Default constructor.
     */
    public GhostIsland(int ghostID, Integer[] numberOfColors, int towerNumber, TowerColors towerColor){
        super(ghostID);
        this.numberOfColors = numberOfColors;
        for(int i = 0; i < PawnColors.values().length - 1 ; i++)
            numberOfColors[i] = 0;
        this.towerNumber = towerNumber;
        this.towerColor = towerColor;
    }

    public void addTower(TowerColors color) throws AddingWrongColorTowerToIslandException{
        if(color == towerColor) towerNumber++;
        else throw new AddingWrongColorTowerToIslandException("You cannot add a different tower color to island before switching");
    }

    public void addStudents(Integer[] numberOfColors) {
        for(int i = 0; i < PawnColors.values().length; i++)
            this.numberOfColors[i] += numberOfColors[i];
    }

    public void addTowers(int towerNumber) {
        this.towerNumber += towerNumber;
    }
}
