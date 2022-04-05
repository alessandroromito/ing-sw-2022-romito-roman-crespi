package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.exception.EntranceFullException;
import it.polimi.ingsw.server.model.component.CharacterCard;
import it.polimi.ingsw.server.model.component.Coin;
import it.polimi.ingsw.server.model.component.NoEntryTile;
import it.polimi.ingsw.server.model.player.Player;

import java.util.List;

public class ExpertGame extends Game{
    /**
     * Default constructor
     *
     * @param players ArrayList of Player
     */
    public ExpertGame(List<Player> players) {
        super(players);
    }

    @Override
    public void gameInitialization() throws EntranceFullException {
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
