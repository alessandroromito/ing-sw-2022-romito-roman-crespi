package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.bag.Bag;
import it.polimi.ingsw.server.model.component.*;
import it.polimi.ingsw.server.model.map.Map;
import it.polimi.ingsw.server.model.player.Player;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private List<Player> players;
    private ArrayList<Component> components;
    private Map map;
    private Bag bag;

    /**
     * Default constructor
     */
    public Game(boolean exMode, int pNumber) {
        this.players = new ArrayList<>();
        this.components = new ArrayList<>();
        // Create map
        this.map = new Map();
        // Create the bag
        this.bag = new Bag();

        createAllComponents(exMode, pNumber);
    }

    /**
     * @return the instance of player's list
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * @return the instance of bag
     */
    public Bag getBag(){
        return bag;
    }

    /**
     * @return the instance of map
     */
    public Map getMap(){
        return map;
    }
    /**
     * @param nickname the nickname of the player to be found.
     * @return the player given his {@code nickname}, {@code null} otherwise.
     */
    public Player getPlayerByNickname(String nickname) {
        return players.stream()
                .filter(player -> nickname.equals(player.getNickname()))
                .findFirst()
                .orElse(null);
    }

    /**
     * @return the instance of components array
     */
    public ArrayList<Component> getComponents() {
        return components;
    }

    /**
     * @param ID the ID of the component to be found
     * @return the component given his {@code ID}, {@code null} otherwise.
     */
    public Component getComponent(int ID){
        return components.stream()
                .filter(component -> ID == (component.getID()))
                .findFirst()
                .orElse(null);
    }

    public void locateStudentsFromBag(){

    }
    public void chooseAndPlayAssistantCard(){
        AssistantCard card = getPlayerByNickname().getPlayerCard();
    }
    public void moveStudentToIsland(int numIsland, int student){
        getComponent(student).setPosition(MapPositions.valueOf("ISLANDS"), numIsland);
    }
    public void moveStudentsToDiningRoom(int student){

    }
    public void moveMotherNature(int steps){
        // Get MotherNature
        Component MN = getComponent(1);
        int MNpos = MN.getPositionDetailed();
        // Calculate nextIsland
        for(int i=0; i<MNpos; i++ ){
            MNpos++;
            if((MNpos) == 13) MNpos = 0;
        }
        // Set the position
        MN.setPosition(MapPositions.valueOf("ISLANDS"), MNpos);
    }
    public void pickAndPlaceFromCloud(){

    }
    public void useCharacter(int character){

    }

    private void createAllComponents(boolean exMode, int pNumber){
        // Create MOTHER NATURE
        components.set(1, new MotherNature());
        // Create NO ENTRY TILE
        if(exMode) {
            for (int i = 2; i <= 5; i++) {
                components.set(i, new NoEntryTile());
            }
        }
        //Create PROFESSORS
        for(int i=6; i<=10; i++){
            components.set(i, new ProfessorPawn());
        }
        //Create COIN
        if(exMode) {
            for (int i = 23; i <= 42; i++) {
                components.set(i, new Coin());
            }
        }
        //Create TOWER
        for(int i=43; i<=50;){
            int towerColor = 0;
            components.set(i, new Tower(towerColor));
        }
        for(int i=51; i<=58;){
            int towerColor = 1;
            components.set(i, new Tower(towerColor));
        }
        if(pNumber == 3){
            for(int i=59; i<=64;){
                int towerColor = 2;
                components.set(i, new Tower(towerColor));
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
    }

}
