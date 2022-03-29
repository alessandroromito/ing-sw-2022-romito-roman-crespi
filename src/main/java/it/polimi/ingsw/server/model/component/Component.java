package it.polimi.ingsw.server.model.component;

public abstract class Component {
    private int ID;
    private MapPositions positionOnMap;
    private int positionDetailed;

    public int getID(){
        return this.ID;
    }

    public MapPositions getPositionOnMap(){
        return this.positionOnMap;
    }

    public int getPositionDetailed(){
        return this.positionDetailed;
    }

    public void setPosition(MapPositions positionOnMap, int positionDetailed){
        this.positionOnMap = positionOnMap;
        this.positionDetailed = positionDetailed;
    }

    public void setPositionOnMap(MapPositions positionOnMap){
        this.positionOnMap = positionOnMap;
    }

    public void setPositionDetailed(int positionDetailed){
        this.positionDetailed = positionDetailed;
    }

}
