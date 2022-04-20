package it.polimi.ingsw.server.model.component;

import it.polimi.ingsw.server.enumerations.MapPositions;

public abstract class Component {
    private int ID;
    private MapPositions positionOnMap;

    public int getID(){
        return this.ID;
    }

    public MapPositions getPositionOnMap(){
        return this.positionOnMap;
    }


    public void setPosition(MapPositions positionOnMap){
        this.positionOnMap = positionOnMap;
    }

    public void setPositionOnMap(MapPositions positionOnMap){
        this.positionOnMap = positionOnMap;
    }
}
