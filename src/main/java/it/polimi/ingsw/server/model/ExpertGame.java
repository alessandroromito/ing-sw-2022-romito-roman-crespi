package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.enumerations.Animals;
import it.polimi.ingsw.server.enumerations.PawnColors;
import it.polimi.ingsw.server.model.bag.Bag;
import it.polimi.ingsw.server.model.component.*;
import it.polimi.ingsw.server.model.map.Map;
import it.polimi.ingsw.server.model.player.Player;
import it.polimi.ingsw.server.enumerations.TowerColors;

import java.util.ArrayList;
import java.util.List;

public class ExpertGame extends Game{
    /**
     * Default constructor
     *
     * @param players
     * @param components
     * @param map
     * @param bag
     */
    public ExpertGame(List<Player> players, ArrayList<Component> components, Map map, Bag bag) {
        super(players, components, map, bag);
    }

    @Override
    public void gameInitialization() {
        super.gameInitialization();
        // Add 1 coin to all Players
        for(Player p: this.getPlayers()){
            p.addCoin();
        }
    }

    @Override
    public void createComponents(){
        super.createComponents();

        for(int i=189; i<=208; i++){
            components.set(i, new Coin());
        }

        for(int i=209; i<=213; i++){
            components.set(i, new NoEntryTile());
        }

        for(int i=214; i<=221; i++){
            components.set(i, new CharacterCard());
        }
    }

    public void useCharacter(int character){

    }
}
