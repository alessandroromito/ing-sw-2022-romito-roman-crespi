package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.bag.Bag;
import it.polimi.ingsw.server.model.component.*;
import it.polimi.ingsw.server.model.map.Map;
import it.polimi.ingsw.server.model.player.Player;
import it.polimi.ingsw.server.model.player.TowerColors;

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
        // Create MOTHER NATURE
        components.set(1, new MotherNature());
        // Create NO ENTRY TILE
        for (int i = 2; i <= 5; i++) {
            components.set(i, new NoEntryTile());
        }
        //Create PROFESSORS
        for(int i=6; i<=10; i++){
            components.set(i, new ProfessorPawn());
        }
        //Create COIN
        for (int i = 23; i <= 42; i++) {
            components.set(i, new Coin());
        }
        //Create TOWER
        for(int i=43; i<=50;){
            components.set(i, new Tower(TowerColors.BLACK));
        }
        for(int i=51; i<=58;){
            components.set(i, new Tower(TowerColors.WHITE));
        }
        if(players.size() == 3){
            for(int i=59; i<=64;){
                components.set(i, new Tower(TowerColors.GREY));
            }
        }
        // Create ASSISTANT CARD
        int i = 65;
        for(Animals animal: Animals.values()){
            for(int movement = 1, val = 1; i<=104; val++, movement++, i++ ){
                components.set(i, new AssistantCard(animal, val, movement));
                val++;
                i++;
                components.set(i, new AssistantCard(animal, val, movement));
            }
        }
        // Create STUDENTS
        i = 105;
        for(PawnColors color: PawnColors.values()){
            for(int count=0; count<26; count++){
                components.set(i, new StudentDisc(color));
                i++;
            }
        }
        // Create CHARACTER CARD

    }

    public void useCharacter(int character){

    }
}
