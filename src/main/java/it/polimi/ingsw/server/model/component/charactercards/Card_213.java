package it.polimi.ingsw.server.model.component.charactercards;

import it.polimi.ingsw.server.exception.ZeroNoEntryTyleRemainingException;
import it.polimi.ingsw.server.model.component.Component;
import it.polimi.ingsw.server.model.component.NoEntryTile;

import java.util.ArrayList;
import java.util.List;

public class Card_213 extends CharacterCard{
    ArrayList<NoEntryTile> net = new ArrayList<>();
    public Card_213(List<Component> nt) {
        super(213);
        this.cost = 2;
        for(int i=0;i<4;i++)
            this.net.add((NoEntryTile) nt.get(i));
    }

    public NoEntryTile use() throws ZeroNoEntryTyleRemainingException {
        if(!net.isEmpty())
            return net.get(0);
        else throw new ZeroNoEntryTyleRemainingException("No entry tiles available");
    }

    public void recoverTile(NoEntryTile t){
        net.add(t);
    }
}
