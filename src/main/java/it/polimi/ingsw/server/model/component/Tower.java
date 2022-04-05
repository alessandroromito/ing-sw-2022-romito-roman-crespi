package it.polimi.ingsw.server.model.component;
import it.polimi.ingsw.server.enumerations.TowerColors;

public class Tower extends Component {
    private TowerColors color;

    public Tower(TowerColors color){
        this.color = color;
    }

    public TowerColors getColor(){
        return color;
    }
}
