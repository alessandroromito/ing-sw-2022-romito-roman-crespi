package it.polimi.ingsw.server.model;

import it.polimi.ingsw.controller.TurnController;
import it.polimi.ingsw.server.enumerations.MapPositions;
import it.polimi.ingsw.server.enumerations.PawnColors;
import it.polimi.ingsw.server.enumerations.TowerColors;
import it.polimi.ingsw.server.exception.*;
import it.polimi.ingsw.server.model.bag.Bag;
import it.polimi.ingsw.server.model.component.*;
import it.polimi.ingsw.server.model.map.Cloud;
import it.polimi.ingsw.server.model.map.GhostIsland;
import it.polimi.ingsw.server.model.map.Island;
import it.polimi.ingsw.server.model.map.Map;
import it.polimi.ingsw.server.model.player.Player;

import java.util.*;

public class Game {
    public static final int MAX_PLAYERS = 4;

    protected List<Player> players;
    protected ArrayList<Component> components = new ArrayList<>();
    protected Map map;
    protected Bag bag;

    public static final String SERVER_NAME = "server";

    protected Player currentPlayer;

    protected TurnController turnController;

    /**
     * Default constructor
     */
    public Game(List<String> playersNicknames) {
        for(String nick : playersNicknames){
            this.players.add(new Player(nick));
        }
        for(Player player : players) player.createScoreboard(players.size(),player);

        this.map = new Map(players.size());
        this.bag = new Bag();

        createComponents();

        try {
            gameInitialization();
        } catch (EntranceFullException e) {
            System.out.println("ERROR while gameInitialization()!");
        }
    }

    /**
     * Method to generate all the components needed
     */
    public void createComponents(){
        // Create MOTHER NATURE
        components.add(new MotherNature(1));

        //Create PROFESSORS
        int id = 2;
        for(PawnColors color: PawnColors.values()){
            components.add(new ProfessorPawn(id, color));
            id++;
        }

        //Create TOWER
        for(id = 7; id<=14; id++){
            components.add(new Tower(id, TowerColors.BLACK));
        }
        for(id=15; id<=22; id++){
            components.add(new Tower(id, TowerColors.WHITE));
        }
        if(players.size() == 3){
            for(id=23; id<=28; id++){
                components.add(new Tower(id, TowerColors.GREY));
            }
        }

        // Create ASSISTANT CARD
        id = 29;
        for(int movement=1, val=1; id<=38; val++, movement++, id++ ){
            components.add(new AssistantCard(id, val, movement));
            val++;
            id++;
            components.add(new AssistantCard(id, val, movement));
        }
        id = 39;
        for(int movement=1, val=1; id<=48; val++, movement++, id++ ){
            components.add(new AssistantCard(id, val, movement));
            val++;
            id++;
            components.add(new AssistantCard(id, val, movement));
        }
        id = 49;
        for(int movement=1, val=1; id<=58; val++, movement++, id++ ){
            components.add(new AssistantCard(id, val, movement));
            val++;
            id++;
            components.add(new AssistantCard(id, val, movement));
        }

        // Create STUDENTS
        id = 59;
        for(PawnColors color: PawnColors.values()){
            for(int count=0; count<26; count++){
                components.add(new StudentDisc(id, color));
                id++;
            }
        }
    }

    /**
     * Initialise the game.
     * Creates all the object necessary for the game according to the number of player
     */
    public void gameInitialization() throws EntranceFullException {
        // 1) Place MotherNature to a random island
        MapPositions randPos = MapPositions.getRandomIsland();
        this.getComponent(1).setPosition(randPos);
        map.setMotherNaturePos(randPos.ordinal() - 12);

        // 3) Move 1 student to each island
        ArrayList<StudentDisc> tempArrayStudents = new ArrayList<>();
        for(int i=0; i<10; i++) {
            tempArrayStudents.add((StudentDisc) getComponent(bag.pickSorted()));
        }
        Collections.shuffle(tempArrayStudents);
        for(StudentDisc stud: tempArrayStudents) {
            int islandNum = 0;
            if(!(islandNum == oppositePosition())){
                map.getIsland(islandNum).addStudent(stud.getColor());
                stud.setPosition(MapPositions.valueOf("ISLAND_" + islandNum));
            }

        }
        // Place 7/9 students on scoreboard's entrance
        for(Player p: this.getPlayers()){
            for(int i=0; i<7; i++) {
                StudentDisc stud = (StudentDisc) getComponent(bag.pickSorted());
                p.getScoreboard().addStudentOnEntrance(stud);
            }
            if (players.size() == 3){
                StudentDisc stud1 = (StudentDisc) getComponent(bag.pickSorted());
                StudentDisc stud2 = (StudentDisc) getComponent(bag.pickSorted());
                p.getScoreboard().addStudentOnEntrance(stud1);
                p.getScoreboard().addStudentOnEntrance(stud2);
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
        if(p == null) throw new MissingPlayerNicknameException(nickname);
        return p;
    }

    /**
     * Returns a list of player nicknames that are already in-game.
     *
     * @return a list with all nicknames in the Game
     */
    public List<String> getPlayersNicknames() {
        List<String> playersNicknames = new ArrayList<>();
        for (Player p : players) {
            playersNicknames.add(p.getNickname());
        }
        return playersNicknames;
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
        for(Component component : components){
            if(ID == component.getID()) return component;
        }
        return null;

        /*
        return components.stream()
                .filter(component -> ID == (component.getID()))
                .findFirst()
                .orElse(null);

         */
    }

    /**
     * Refill clouds with students from bag
     */
    public void refillClouds() throws CloudNotEmptyException {
        for(Cloud cloud: map.getClouds()){
            if(cloud.getCloudStudents().isEmpty()) {
                cloud.addStudent((StudentDisc) getComponent(bag.pickSorted()));
                cloud.addStudent((StudentDisc) getComponent(bag.pickSorted()));
                cloud.addStudent((StudentDisc) getComponent(bag.pickSorted()));

                if(players.size() == 3)
                    cloud.addStudent((StudentDisc) getComponent(bag.pickSorted()));
            }
            else throw new CloudNotEmptyException("Cloud is NOT empty before refill!");

        }
    }

    /**
     * Set the assistant card for the turn
     */
    public void setAssistantCard(String nickname, int cardIndex) throws MissingAssistantCardException, DoubleAssistantCardException, NullCurrentCardException, MissingPlayerNicknameException {
        Player player = getPlayerByNickname(nickname);
        AssistantCard chosenCard = player.getPlayerCard(cardIndex);
        if(chosenCard == null) throw new MissingAssistantCardException("AssistantCard not found!");
        if(!validateCard(chosenCard)) throw new DoubleAssistantCardException("Another player already played this card!");
        player.setCurrentCard(chosenCard);
    }

    /**
     *
     * @param numIsland index of the destination island
     * @param studentID id of the student
     */
    public void moveStudentToIsland(int studentID, int numIsland) throws DisabledIslandException {
        StudentDisc stud = (StudentDisc) getComponent(studentID);

        if(map.getIsland(numIsland).isDisabled()) {
            GhostIsland ghostIsland = map.getGhostIsland(numIsland);
            ghostIsland.addStudent(stud.getColor());
            stud.setPosition(MapPositions.valueOf("ISLANDS_" + numIsland));
        }
        else {
            Island island = map.getIsland(numIsland);
            island.addStudent(stud.getColor());
            stud.setPosition(MapPositions.valueOf("ISLANDS_" + numIsland));
        }
    }

    /**
     *
     * @param towerColor
     * @param numIsland
     * @throws DisabledIslandException
     */
    public void moveTowerToIsland(TowerColors towerColor, int numIsland) throws DisabledIslandException {
        Island island = map.getIsland(numIsland);

        if(island.isDisabled()) {
            //check Ghost Island
            if(map.getGhostIsland(numIsland) != null)
                island = map.getGhostIsland(numIsland);
        }
        else throw new DisabledIslandException("This island is disabled!");

        for(Player p : players){
            TowerColors oldColor = p.getScoreboard().getTowerColor();
            if(island.getTowerColor() == oldColor){
                // Add 1 tower to the old dominant player
                p.getScoreboard().addTower();
                // switch island tower color
                island.switchTowerColor(p.getScoreboard().getTowerColor());
                // remove player tower from scoreboard
                p.getScoreboard().removeTower();
            }
        }
    }

    /**
     *
     * @param color professor color
     * @param player player that receive the professor
     * @throws MissingPlayerNicknameException
     */
    public void moveProfessor(PawnColors color, Player player) throws MissingPlayerNicknameException {
        Player currentPlayer = getPlayerByNickname(turnController.getActivePlayer());

        for(Player p : players){
            if(!p.equals(currentPlayer) && p.getScoreboard().getProfessor(color)){
                p.getScoreboard().setProfessorFalse(color);
                currentPlayer.getScoreboard().setProfessorTrue(color);
            }
        }
    }

    /**
     *
     * @param stud id of the student to move
     */
    public void moveStudentToDiningRoom(StudentDisc stud) throws StudentNotInEntranceException, MissingPlayerNicknameException {
        currentPlayer.getScoreboard().moveFromEntranceToDining(stud);
        checkProfessors(stud.getColor());
    }

    public void deleteActiveCard(){

    }

    public void checkInfluence(int islandID) throws MissingPlayerNicknameException {
        int bestInfluence = 0;
        Player currentPlayer = getPlayerByNickname(turnController.getActivePlayer());
        Player dominantPlayer = null;
        Player opponentPlayer = null;

        Island island = map.getIsland(islandID);

        if(island.isDisabled()){
            island = map.getGhostIsland(islandID);
        }

        if(island.checkNoEntryTile()){
            island.removeNoEntryTile().setPosition(MapPositions.CARD_218);
            return;
        }

        // CASE no tower on island
        if(island.getTowerNumber() == 0){
            for(Player p : players){
                int playerInfluence = island.getInfluence(p);
                if(playerInfluence > bestInfluence){
                    bestInfluence = playerInfluence;
                    dominantPlayer = p;
                }
            }
            try {
                moveTowerToIsland(Objects.requireNonNull(dominantPlayer).getScoreboard().getTowerColor(), islandID);
            } catch (DisabledIslandException e) {
                System.out.println("ERROR Moving First Tower to Island!");
            }
        }

        // CASE there is already a tower
        int currentPlayerInfluence = island.getInfluence(getPlayerByNickname(turnController.getActivePlayer()));
        for(Player p : players){
            if(p.getScoreboard().getTowerColor() == island.getTowerColor()){
                opponentPlayer = p;
            }
        }

        if(island.getInfluence(Objects.requireNonNull(opponentPlayer)) > currentPlayerInfluence){
            try {
                moveTowerToIsland(Objects.requireNonNull(dominantPlayer).getScoreboard().getTowerColor(), islandID);
            } catch (DisabledIslandException e) {
                System.out.println("ERROR Switching Tower!");
            }
        }
    }

    private void checkProfessors(PawnColors color) throws MissingPlayerNicknameException {
        Player currentPlayer = getPlayerByNickname(turnController.getActivePlayer());
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
        int motherNaturePos = map.getMotherNaturePosition();
        // Calculate nextIsland
        for(int i=0; i<steps; i++){
            motherNaturePos++;
            if(map.getIsland(motherNaturePos).isDisabled()){
                int groupID = map.getIsland(motherNaturePos).getGroupID();
                do{
                    motherNaturePos++;
                    if((motherNaturePos) == 12) motherNaturePos = 0;
                } while (map.getIsland(motherNaturePos).getGroupID() == groupID);
            }
        }
        // Set the position
        MotherNature MN = (MotherNature) getComponent(1);
        MN.setPosition(MapPositions.valueOf("ISLAND_" + motherNaturePos));
        map.setMotherNaturePos(motherNaturePos);
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
        cloud.reset();
    }

    public Player getActivePlayer(){
        try {
            return getPlayerByNickname(turnController.getActivePlayer());
        } catch (MissingPlayerNicknameException e) {
            System.out.println("ERROR getActivePlayer()!");
            return null;
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
        int motherNaturePosition = map.getMotherNaturePosition();
        return (motherNaturePosition + 12) / 2;
    }

    /**
     *
     * @param card the player's card he wants to choose
     * @return true if it's valid or false if it's not
     */
    private boolean validateCard(AssistantCard card) throws NullCurrentCardException {
        for(Player player: players){
            if(player.equals(currentPlayer)){
                if(player.getCurrentCard().getValue() == card.getValue()) return false;
            }
        }
        return true;
    }

    public int getNumberOfPlayer() {
        return players.size();
    }


}