package it.polimi.ingsw.server.model.component;
import it.polimi.ingsw.server.enumerations.TowerColors;

import java.io.Serializable;

/**
 * This class identifies a component of the game map.
 */
public class Tower extends Component implements Serializable {
    private final TowerColors color;

    /**
     * Default constructor.
     * @param id of the tower.
     * @param color of the tower.
     */
    public Tower(int id, TowerColors color){
        this.ID = id;
        this.color = color;
    }

    /**
     * @return the color of the tower using PawnColors enumeration.
     */
    public TowerColors getColor(){
        return color;
    }
}
