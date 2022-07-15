package it.polimi.ingsw.server.model.component.charactercards;

import it.polimi.ingsw.server.enumerations.PawnColors;

/**
 * This class represent the character card with id 217.
 */
public class Card_217 extends CharacterCard{
    private PawnColors disColor;

    /**
     * Default Constructor
     */
    public Card_217(){
        super(217);
        this.cost = 3; //3
    }

    /**
     * Getter
     * @return
     */
    public PawnColors getDisColor() {
        return disColor;
    }

    /**
     * Setter
     * @param disColor
     */
    public void setDisColor(PawnColors disColor) {
        this.disColor = disColor;
    }
}
