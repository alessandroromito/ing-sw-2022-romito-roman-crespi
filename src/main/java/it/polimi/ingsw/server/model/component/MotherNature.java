package it.polimi.ingsw.server.model.component;

import it.polimi.ingsw.server.enumerations.MapPositions;

public class MotherNature extends Component{
    private int motherNaturePos;

    public MotherNature(int id){
        this.ID = id;
    }

    @Override
    public void setPosition(MapPositions island) {
        int islandNum = island.ordinal() - 12;
        if(islandNum > 0 || islandNum < 13) {
            motherNaturePos = islandNum;
        }
        else System.out.println("Invalid islandNum provided!");
    }

    public int getMotherNaturePosPosition() {
        return motherNaturePos;
    }
}
