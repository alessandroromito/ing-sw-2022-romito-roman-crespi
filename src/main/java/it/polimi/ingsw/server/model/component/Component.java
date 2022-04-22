package it.polimi.ingsw.server.model.component;

import it.polimi.ingsw.server.enumerations.MapPositions;

public abstract class Component {
    protected int ID;
    protected MapPositions positionOnMap;

    public int getID(){
        return this.ID;
    }

    public MapPositions getPosition(){
        return this.positionOnMap;
    }

    public void setPosition(MapPositions positionOnMap){
        this.positionOnMap = positionOnMap;
    }

}
