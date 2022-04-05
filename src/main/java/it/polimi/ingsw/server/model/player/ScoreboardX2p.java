package it.polimi.ingsw.server.model.player;

import it.polimi.ingsw.server.model.component.PawnColors;
import it.polimi.ingsw.server.observer.ObserverTower;

public class ScoreboardX2p implements Scoreboard{

    private final PawnColors[] entrance;
    private final Integer[] diningRoom;
    private final boolean[] professorTable;
    private int towerLine;
    private TowerColors towerColor;
    private final ObserverTower obsT;


    public ScoreboardX2p(){
        obsT = new ObserverTower(this);
        entrance = new PawnColors[7];
        towerLine = 8;
        professorTable = new boolean[5];
        diningRoom = new Integer[5];
        for(int i=0;i<5;i++){
            diningRoom[i] = 0;
            professorTable[i] = false;
        }
    }

    //manca metodo per fare pick di tower

    @Override
    public void setTowerColor(TowerColors t){
        this.towerColor = t;
    }

    @Override
    public void setProfessorTrue(PawnColors color) {
        this.professorTable[color.ordinal()] = true;
    }

    @Override
    public void setProfessorFalse(PawnColors color) {
        this.professorTable[color.ordinal()] = false;
    }

    @Override
    public boolean getProfessor(PawnColors color) {
        return this.professorTable[color.ordinal()];
    }

    @Override
    public int getNumTowers() {
        return this.towerLine;
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
    public int[] getPlayerStudentsFromDining() {
        int vector[] = new int[7];
        for(int i=0;i<6;i++)    vector[i] = this.diningRoom[i];
        return vector;
    }

    @Override
    public void addStudentOnEntrance(PawnColors student) {
        int k = 0;
        while(entrance[k] == null)  k++;
        entrance[k] = student;
    }

    @Override
    public void moveFromEntranceToDining(int position) {
        diningRoom[entrance[position].ordinal()]++;
        entrance[position] = null;
    }

    @Override
    public TowerColors getTowerColor() {
        return this.towerColor;
    }
}
