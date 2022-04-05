package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.enumerations.*;
import it.polimi.ingsw.server.exception.*;
import it.polimi.ingsw.server.model.bag.Bag;
import it.polimi.ingsw.server.model.component.*;
import it.polimi.ingsw.server.model.map.Cloud;
import it.polimi.ingsw.server.model.map.Island;
import it.polimi.ingsw.server.model.map.Map;
import it.polimi.ingsw.server.model.player.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Game {
    protected List<Player> players;
    protected ArrayList<Component> components;
    protected Map map;
    protected Bag bag;

    protected Player currentPlayer;

    /**
     * Default constructor
     */
    public Game(List<Player> players) {

        this.players = players;
        this.createComponents();
        try {
            this.gameInitialization();
        } catch (EntranceFullException e) {
            e.printStackTrace();
        }
    }

    /**
     * Initialise the game.
     * Creates all the object necessary for the game according to the number of player
     */
    public void gameInitialization() throws EntranceFullException {
        // 1) Place MotherNature to a random island
        int randPos = getRandomNumber(1, 12);
        this.getComponent(1).setPosition(MapPositions.valueOf("ISLANDS"), randPos);
        map.setMotherNaturePos(randPos);

        // 3) Move 1 student to each island
        ArrayList<StudentDisc> tempArrayStudents = new ArrayList<>();
        for(int i=0; i<10; i++) {
            tempArrayStudents.set(i, (StudentDisc) getComponent(bag.pickSorted()));
        }
        Collections.shuffle(tempArrayStudents);
        for(StudentDisc stud: tempArrayStudents) {
            int islandNum = 0;
            if(!(islandNum == oppositePosition())){
                map.getIsland(islandNum).addStudent(stud.getColor());
                stud.setPosition(MapPositions.valueOf("ISLANDS"), islandNum);
            }

        }
        // Place 7/9 students on scoreboard's entrance
        for(Player p: this.getPlayers()){
            if(players.size() == 2) {
                for(int i=0; i<7; i++) {
                    StudentDisc stud = (StudentDisc) getComponent(bag.pickSorted());
                    p.getScoreboard().addStudentOnEntrance(stud);
                }
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

    /**
     * Refill clouds with students from bag
     */
    public void locateStudentsFromBag(){
        for(Cloud cloud: map.getClouds()){
            cloud.refill();
        }
    }

    /**
     *
     */
    public void playAssistantCard(int cardIndex) throws MissingAssistantCardException, DoubleAssistantCardException, NullCurrentCardException {
        AssistantCard chosenCard = currentPlayer.getPlayerCard(cardIndex);
        if(chosenCard.equals(null)) throw new MissingAssistantCardException("AssistantCard not found!");
        if(!validateCard(chosenCard)) throw new DoubleAssistantCardException("Another player already played this card!");
        currentPlayer.setCurrentCard(chosenCard);
    }

    /**
     *
     * @param numIsland index of the destination island
     * @param student id of the student
     */
    public void moveStudentToIsland(int numIsland, int student) throws DisabledIslandException {
        StudentDisc stud = (StudentDisc) getComponent(student);
        Island island = map.getIsland(numIsland);

        if(island.isDisabled()) throw new DisabledIslandException("This island is disabled!");
        island.addStudent(stud.getColor());
        stud.setPosition(MapPositions.valueOf("ISLANDS"), numIsland);
    }

    /**
     *
     * @param stud id of the student to move
     */
    public void moveStudentToDiningRoom(StudentDisc stud) throws StudentNotInEntranceException {
        currentPlayer.getScoreboard().moveFromEntranceToDining(stud);
        //check professor e nel caso setProfessor(color)
        checkProfessors(stud.getColor());
    }

    private void checkProfessors(PawnColors color) {
        int numStudent = currentPlayer.getScoreboard().getPlayerStudentFromDining(color);

        for(Player player: players){
            if(!player.equals(currentPlayer) && player.getScoreboard().getPlayerStudentFromDining(color) < numStudent){
                currentPlayer.getScoreboard().setProfessorTrue(color);
            }
        }
    }

    /**
     * Move Mother Nature forward by @param steps
     */
    public void moveMotherNature(int steps){
        // Get MotherNature
        Component MN = (MotherNature) getComponent(1);
        int MNpos = MN.getPositionDetailed();
        // Calculate nextIsland
        for(int i=0; i<steps; i++){
            if(map.getIsland(MNpos).isDisabled()){
                int groupID = map.getIsland(MNpos).getGroupID();
                do{
                    MNpos++;
                } while (map.getIsland(MNpos).getGroupID() == groupID);
            }
            else{
                MNpos++;
            }
            if((MNpos) == 13) MNpos = 0;
        }
        // Set the position
        MN.setPositionDetailed(MNpos);
        map.setMotherNaturePos(MNpos);
    }

    /**
     * Choose a cloud and get its students
     * @param cloudID Number of the cloud chosen
     */
    public void pickAndPlaceFromCloud(int cloudID) throws MissingCloudStudentsException, EntranceFullException {
        Cloud cloud = map.getCloud(cloudID);
        if(cloud.getCloudStudents() == null) throw new MissingCloudStudentsException("Cloud empty or already chosen!");
        for(StudentDisc student: cloud.getCloudStudents()){
            currentPlayer.getScoreboard().addStudentOnEntrance(student);
        }
    }

    public void createComponents(){
        // Create MOTHER NATURE
        components.set(1, new MotherNature());

        //Create PROFESSORS
        for(PawnColors color: PawnColors.values()){
            int i=2;
            components.set(i, new ProfessorPawn(color));
            i++;
        }

        //Create TOWER
        for(int i=7; i<=14;){
            components.set(i, new Tower(TowerColors.BLACK));
        }
        for(int i=15; i<=22;){
            components.set(i, new Tower(TowerColors.WHITE));
        }
        if(players.size() == 3){
            for(int i=23; i<=28;){
                components.set(i, new Tower(TowerColors.GREY));
            }
        }
        // Create ASSISTANT CARD
        int i = 29;
        for(int movement = 1, val = 1; i<=38; val++, movement++, i++ ){
            components.set(i, new AssistantCard(val, movement));
            val++;
            i++;
            components.set(i, new AssistantCard(val, movement));
        }
        i = 39;
        for(int movement = 1, val = 1; i<=48; val++, movement++, i++ ){
            components.set(i, new AssistantCard(val, movement));
            val++;
            i++;
            components.set(i, new AssistantCard(val, movement));
        }
        i = 49;
        for(int movement = 1, val = 1; i<=58; val++, movement++, i++ ){
            components.set(i, new AssistantCard(val, movement));
            val++;
            i++;
            components.set(i, new AssistantCard(val, movement));
        }

        // Create STUDENTS
        i = 59;
        for(PawnColors color: PawnColors.values()){
            for(int count=0; count<26; count++){
                components.set(i, new StudentDisc(color));
                i++;
            }
        }
    }

    /**
     * @param min minimum number can be generated
     * @param max maximum number can be generated
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

    private boolean validateCard(AssistantCard card) throws NullCurrentCardException {
        for(Player player: players){
            if(player.equals(currentPlayer) || !player.getCurrentCard().equals(null)){
                if(player.getCurrentCard().getValue() == card.getValue()) return false;
            }
        }
        return true;
    }

}
