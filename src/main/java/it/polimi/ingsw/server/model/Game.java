package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.exception.MissingPlayerNicknameException;
import it.polimi.ingsw.server.model.bag.Bag;
import it.polimi.ingsw.server.model.component.*;
import it.polimi.ingsw.server.model.map.Map;
import it.polimi.ingsw.server.model.player.Player;
import it.polimi.ingsw.server.model.player.TowerColors;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Game {
    protected List<Player> players;
    protected ArrayList<Component> components;
    protected Map map;
    protected Bag bag;

    protected GameState currentState;
    protected Player currentPlayer;

    /**
     * Default constructor
     */
    public Game(List<Player> players, ArrayList<Component> components, Map map, Bag bag) {
        this.players = players;
        this.components = components;
        this.map = map;
        this.bag = bag;

        gameInitialization();
    }

    /**
     * Inizialise the game.
     * Creates all the object necessary for the game according to the number of player
     */
    public void gameInitialization() {
        // 1) Place MotherNature to a random island
        int randPos = getRandomNumber(1, 12);
        this.getComponent(1).setPosition(MapPositions.valueOf("ISLANDS"), randPos);
        // 3) Move 1 student to each island
        ArrayList<Component> tempArrayStudents = new ArrayList<>();
        for(PawnColors color: PawnColors.values()){
            Component student1 = this.getComponent(bag.getColored(color));
            Component student2 = this.getComponent(bag.getColored(color));
            tempArrayStudents.add(student1);
            tempArrayStudents.add(student2);
        }
        Collections.shuffle(tempArrayStudents);
        for (Component stud: tempArrayStudents) {
            int islandNum = 0;
            if(!(islandNum == oppositePosition()))
                stud.setPosition(MapPositions.valueOf("ISLANDS"), islandNum);
        }
        // 8 Take 6/8 towers
        for (Player p: this.getPlayers()) {
            if(players.size() == 2){
                p.getScoreboard().setNumTowers(8);
            }
            else if (players.size() == 3){
                p.getScoreboard().setNumTowers(6);
            }
        }
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
     * @return the current state Game
     */
    public GameState getState() {
        return this.currentState;
    }

    /**
     * Method that sets the current state of the game
     *
     * @param currentState GameState to be changed
     */
    public void setState(GameState currentState) {
        this.currentState = currentState;
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
    public Player getPlayerByNickname(String nickname) throws MissingPlayerNicknameException {
        Player p = players.stream()
                .filter(player -> nickname.equals(player.getNickname()))
                .findFirst()
                .orElse(null);
        if(p.equals(null)) throw new MissingPlayerNicknameException(nickname);
        return p;
    }

    /**
     * @return the instance of component's array
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
    public void playAssistantCard(){
        AssistantCard card = getPlayerByNickname().getPlayerCard();
    }
    public void moveStudentToIsland(int numIsland, int student){
        getComponent(student).setPosition(MapPositions.valueOf("ISLANDS"), numIsland);
    }
    public void moveStudentToDiningRoom(int student){
        // moveStudentToDiningRoom() from scoreboard

        //check professor e nel caso setProfessor(color)
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
        MN.setPosition(MapPositions.ISLANDS, MNpos);
    }
    public void pickAndPlaceFromCloud(){

    }

    public void createComponents(){
        // Create MOTHER NATURE
        components.set(1, new MotherNature());

        //Create PROFESSORS
        for(int i=6; i<=10; i++){
            components.set(i, new ProfessorPawn());
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
    }

    /**
     * @param min minimum number can be generated
     * @param max manximum nuber can be generated
     * @return a random number in range min - max
     */
    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    private int oppositePosition() {
        int motherNaturePosition = this.getComponent(1).getPositionDetailed();
        int oppositePos = (motherNaturePosition + 12) / 2;
        return oppositePos;
    }

}
