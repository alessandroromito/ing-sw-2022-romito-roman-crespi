package it.polimi.ingsw.server.model.component;

public abstract class Component {
    private int ID;
    private MapPositions positionOnMap;
    private String positionDetailed;

    public int getID(){
        return this.ID;
    }

    public MapPositions getPositionOnMap(){
        return this.positionOnMap;
    }

    public String getPositionDetailed(){
        return this.positionDetailed;
    }

    public void setPosition(MapPositions positionOnMap, String positionDetailed){
        this.positionOnMap = positionOnMap;
        this.positionDetailed = positionDetailed;
    }

}
