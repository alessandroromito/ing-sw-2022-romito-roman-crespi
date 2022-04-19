package it.polimi.ingsw.server.model.map;
//implementationPhase
//concept: si crea una ghostIsland per ogni grupid dopo i merging, id_ghost = groupID

import it.polimi.ingsw.server.enumerations.PawnColors;
import it.polimi.ingsw.server.enumerations.TowerColors;
import it.polimi.ingsw.server.exception.AddingWrongColorTowerToIslandException;


public class GhostIsland extends Island{
    private int ID_ghost;
    private Integer[] numberOfColors;
    private int towerNumber;
    private TowerColors towerColor;

    /**
     * Default constructor.
     */
    public GhostIsland(int groupID, Integer[] numberOfColors, int towerNumber, TowerColors towerColor){
        this.ID_ghost = groupID;
        this.numberOfColors = numberOfColors;
        for(int i = 0; i < PawnColors.values().length - 1 ; i++)
            numberOfColors[i] = 0;
        this.towerNumber = towerNumber;
        this.towerColor = towerColor;
    }

    @Override
    public Integer[] getNumberOfColors(){
        return this.numberOfColors;
    }

    @Override
    public int getID(){
        return this.ID_ghost;
    }

    @Override
    public int getInfluence(){
        // da implementare
        return 0;
    }

    @Override
    public int getTowerNumber(){
        return this.towerNumber;
    }

    @Override
    public TowerColors getTowerColor(){
        return this.towerColor;
    }

    @Override
    public void addStudent(PawnColors color){
        numberOfColors[color.ordinal()] ++;
    }

    @Override
    public void addTower(TowerColors color) throws AddingWrongColorTowerToIslandException{
        if(color == towerColor) towerNumber++;
        else throw new AddingWrongColorTowerToIslandException("You cannot add a different tower color to island before switching");
    }

    @Override
    public void switchTowerColor(TowerColors color){
        this.towerColor = color;
    }

    public void addStudents(Integer[] numberOfColors) {
        for(int i = 0; i < PawnColors.values().length; i++)
            this.numberOfColors[i] += numberOfColors[i];
    }

    public void addTowers(int towerNumber) {
        this.towerNumber += towerNumber;
    }
}
