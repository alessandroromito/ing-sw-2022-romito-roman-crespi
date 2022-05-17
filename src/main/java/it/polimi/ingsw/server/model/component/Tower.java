package it.polimi.ingsw.server.model.component;
import it.polimi.ingsw.server.enumerations.MapPositions;
import it.polimi.ingsw.server.enumerations.TowerColors;

public class Tower extends Component {
    private TowerColors color;

    public Tower(int id, TowerColors color){
        this.ID = id;
        this.color = color;
        //assegna la posizione in base a come è strutturata l'enum MapPosition
        this.positionOnMap = MapPositions.values()[color.ordinal()+12];

    }

    public TowerColors getColor(){
        return color;
    }
}
