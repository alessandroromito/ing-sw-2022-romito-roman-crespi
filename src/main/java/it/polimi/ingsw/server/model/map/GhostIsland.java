package it.polimi.ingsw.server.model.map;
//implementationPhase
//concept: si crea una ghostIsland per ogni grupid dopo i merging, id_ghost = groupID

import it.polimi.ingsw.server.enumerations.PawnColors;
import it.polimi.ingsw.server.enumerations.TowerColors;
import it.polimi.ingsw.server.exception.AddingWrongColorTowerToIslandException;
import it.polimi.ingsw.server.model.player.Player;
import it.polimi.ingsw.server.model.player.Scoreboard;


public class GhostIsland{
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

    public Integer[] getNumberOfColors(){
        return this.numberOfColors;
    }

    public int getID(){
        return this.ID_ghost;
    }

    public int getTowerNumber(){
        return this.towerNumber;
    }

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

    public TowerColors getTowerColor(){
        return this.towerColor;
    }

    public void addStudent(PawnColors color){
        numberOfColors[color.ordinal()] ++;
    }

    public void addTower(TowerColors color) throws AddingWrongColorTowerToIslandException{
        if(color == towerColor) towerNumber++;
        else throw new AddingWrongColorTowerToIslandException("You cannot add a different tower color to island before switching");
    }

    public void switchTowerColor(TowerColors color){
        this.towerColor = color;
    }

    @Override
    public boolean checkNoEntryTile() {
        //da implementare
    }

    public void addStudents(Integer[] numberOfColors) {
        for(int i = 0; i < PawnColors.values().length; i++)
            this.numberOfColors[i] += numberOfColors[i];
    }

    public void addTowers(int towerNumber) {
        this.towerNumber += towerNumber;
    }
}
