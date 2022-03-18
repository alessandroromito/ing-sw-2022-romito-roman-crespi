package it.polimi.ingsw.server.model.component;

public abstract class Component {
    private int ID;
    private String PositionOnMap;
    private String PositionDetailed;

    public int getID(){
        return this.ID;
    }
}
