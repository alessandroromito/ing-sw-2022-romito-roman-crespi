package it.polimi.ingsw.server.model.component.charactercards;

import it.polimi.ingsw.server.exception.ZeroNoEntryTyleRemainingException;
import it.polimi.ingsw.server.model.component.Component;
import it.polimi.ingsw.server.model.component.NoEntryTile;

import java.util.ArrayList;
import java.util.List;

public class Card_213 extends CharacterCard{
    ArrayList<NoEntryTile> noEntryTiles = new ArrayList<>();

    public Card_213(List<Component> nt) {
        super(213);
        this.cost = 0; //1

        for(int i=0;i<4;i++)
            this.noEntryTiles.add((NoEntryTile) nt.get(i));
    }

    public NoEntryTile use() {
        try {
            if(!noEntryTiles.isEmpty())
                return noEntryTiles.get(0);
            else throw new ZeroNoEntryTyleRemainingException("No entry tiles available");
        }catch (ZeroNoEntryTyleRemainingException e){
            e.printStackTrace();
        }
        return null;
    }

    public void recoverTile(NoEntryTile noEntryTile){
        noEntryTiles.add(noEntryTile);
    }
}
