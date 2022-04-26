package it.polimi.ingsw.server.model.component.charactercards;

import it.polimi.ingsw.server.enumerations.PawnColors;

public class Card_222 extends CharacterCard{
    private PawnColors disColor;

    public Card_222(){
        super(219);
        this.cost = 3;
        this.disColor = disColor;
    }

    public PawnColors getDisColor() {
        return disColor;
    }

    public void setDisColor(PawnColors disColor) {
        this.disColor = disColor;
    }
}
