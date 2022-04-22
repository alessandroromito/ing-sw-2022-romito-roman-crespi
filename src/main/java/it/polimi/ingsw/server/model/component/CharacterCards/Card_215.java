package it.polimi.ingsw.server.model.component.CharacterCards;

import it.polimi.ingsw.server.enumerations.MapPositions;
import it.polimi.ingsw.server.model.component.CharacterCard;

public class Card_215 extends CharacterCard {
    private MapPositions[] old;

    public Card_215(MapPositions[] old) {
        this.old = old;
    }

    public void updateOldPos (MapPositions[] old) {
        this.old = old;
    }

    public void updateOnePos (MapPositions old, int index){
        this.old[index] = old;
    }

    public MapPositions[] getOldPos() {
        return old;
    }
}
