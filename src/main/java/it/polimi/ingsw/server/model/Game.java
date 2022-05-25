package it.polimi.ingsw.server.model;

import it.polimi.ingsw.controller.TurnController;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.server.enumerations.PawnColors;
import it.polimi.ingsw.server.enumerations.TowerColors;
import it.polimi.ingsw.server.exception.*;
import it.polimi.ingsw.server.model.bag.Bag;
import it.polimi.ingsw.server.model.component.*;
import it.polimi.ingsw.server.model.component.charactercards.CharacterCard;
import it.polimi.ingsw.server.model.map.Cloud;
import it.polimi.ingsw.server.model.map.GhostIsland;
import it.polimi.ingsw.server.model.map.Island;
import it.polimi.ingsw.server.model.map.Map;
import it.polimi.ingsw.server.model.player.Player;

import java.util.*;

public class Game extends Observable {

    protected List<Player> players = new ArrayList<>();
    protected ArrayList<Component> components = new ArrayList<>();
    protected Map map;
    protected Bag bag;

    public static final String SERVER_NAME = "main server";

    protected Player currentPlayer;

    protected TurnController turnController;

    /**
     * Default constructor
     */
    public Game(List<String> playersNicknames) {
        for(String nick : playersNicknames){
            players.add(new Player(nick));
        }

        players.get(0).createScoreboard(players.size(), TowerColors.BLACK);
        players.get(1).createScoreboard(players.size(), TowerColors.WHITE);
        if(players.size() == 3)
            players.get(2).createScoreboard(players.size(), TowerColors.GREY);

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
    }

    /**
     * Initialise the game.
     * Creates all the object necessary for the game according to the number of player
     */
    public void gameInitialization() throws EntranceFullException {
        System.out.println("STARTING GameInitialization...");
        // 1) Place MotherNature to a random island
        Random r = new Random();
        int pos = r.nextInt( 12);
        map.setMotherNaturePos(pos);

        // 3) Move 1 student to each island
        ArrayList<StudentDisc> tempArrayStudents = new ArrayList<>();
        for(int i=0; i<10; i++) {
            tempArrayStudents.add(bag.pickSorted());
        }
        Collections.shuffle(tempArrayStudents);
        for(StudentDisc stud: tempArrayStudents) {
            int islandNum = 0;
            if(!(islandNum == oppositePosition())){
                map.getIsland(islandNum).addStudent(stud);
            }
        }
        // Place 7/9 students on scoreboard's entrance
        for(Player p: this.getPlayers()){
            for(int i=0; i<7; i++) {
                StudentDisc stud = bag.pickSorted();
                p.getScoreboard().addStudentOnEntrance(stud);
            }
            if (players.size() == 3){
                StudentDisc stud1 = bag.pickSorted();
                StudentDisc stud2 = bag.pickSorted();
                p.getScoreboard().addStudentOnEntrance(stud1);
                p.getScoreboard().addStudentOnEntrance(stud2);
            }
        }
        System.out.println("...ENDED GameInitialization");
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
        try{
            Player p = players.stream()
                    .filter(player -> nickname.equals(player.getNickname()))
                    .findFirst()
                    .orElse(null);
            if(p == null) throw new MissingPlayerNicknameException("Player " + nickname + "is missing!");
            return p;
        }catch (MissingPlayerNicknameException e) {
            throw new RuntimeException(e);
        }

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
    }

    /**
     * Refill clouds with students from bag
     */
    public void refillClouds() {
        for(Cloud cloud: map.getClouds()){
            try {
                if (cloud.getCloudStudents().isEmpty()) {
                    cloud.addStudent(bag.pickSorted());
                    cloud.addStudent(bag.pickSorted());
                    cloud.addStudent(bag.pickSorted());

                    if (players.size() == 3)
                        cloud.addStudent(bag.pickSorted());
                }
                else throw new CloudNotEmptyException("Cloud is NOT empty before refill!");
            } catch (CloudNotEmptyException e) {

            }
        }
    }

    /**
     * Set the assistant card for the turn
     */
    public void setAssistantCard(String nickname, int cardIndex) {
        try{
            Player player = getPlayerByNickname(nickname);
            AssistantCard chosenCard = player.getPlayerCard(cardIndex);
            if(chosenCard == null) throw new MissingAssistantCardException("AssistantCard not found!");
            if(!validateCard(chosenCard)) throw new DoubleAssistantCardException("Another player already played this card!");
            player.setCurrentCard(chosenCard);

            //notifyObserver(new ScoreBoardMessage(ScoreBoard ReducedScoreBoard));
        }catch (DoubleAssistantCardException | MissingAssistantCardException | NullCurrentCardException e) {
            throw new RuntimeException(e);
        }
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
            ghostIsland.addStudent(stud);
        }
        else {
            Island island = map.getIsland(numIsland);
            island.addStudent(stud);
        }
    }

    /**
     *
     * @param tower
     * @param numIsland
     * @throws DisabledIslandException
     */
    public void moveTowerToIsland(Tower tower, int numIsland) {
        try{
             Island island = map.getIsland(numIsland);

             if(island.isDisabled()) {
                //check Ghost Island
                if(map.getGhostIsland(numIsland) != null)
                    island = map.getGhostIsland(numIsland);
                else throw new DisabledIslandException("Error in island");
             }

            for(Player p : players){
                TowerColors oldColor = p.getScoreboard().getTowerColor();
                if(island.getTowerColor() == oldColor){
                    // Add 1 tower to the old dominant player
                    p.getScoreboard().addTower(tower);
                    // switch island tower color
                    island.addTower(p.getScoreboard().removeTower());
                }
            }
        } catch (DisabledIslandException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * @param color professor color
     * @param player player that receive the professor
     */
    public void moveProfessor(PawnColors color, Player player) {
        Player currentPlayer = getPlayerByNickname(turnController.getActivePlayer());

        for(Player p : players){
            if(!p.equals(currentPlayer) && p.getScoreboard().getProfessor(color)){
                currentPlayer.getScoreboard().addProfessor(p.getScoreboard().removeProfessor(color));
            }
        }
    }

    /**
     *
     * @param stud id of the student to move
     */
    public void moveStudentToDiningRoom(StudentDisc stud) throws StudentNotInEntranceException {
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

        // CASE no tower on island
        if(island.getTowerNumber() == 0){
            for(Player p : players){
                int playerInfluence = island.getInfluence(p);
                if(playerInfluence > bestInfluence){
                    bestInfluence = playerInfluence;
                    dominantPlayer = p;
                }
            }
            moveTowerToIsland(Objects.requireNonNull(dominantPlayer).getScoreboard().removeTower(), islandID);
        }

        // CASE there is already a tower
        int currentPlayerInfluence = island.getInfluence(getPlayerByNickname(turnController.getActivePlayer()));
        for(Player p : players){
            if(p.getScoreboard().getTowerColor() == island.getTowerColor()){
                opponentPlayer = p;
            }
        }

        if(island.getInfluence(Objects.requireNonNull(opponentPlayer)) > currentPlayerInfluence){
            moveTowerToIsland(Objects.requireNonNull(dominantPlayer).getScoreboard().removeTower(), islandID);
        }
    }

    private void checkProfessors(PawnColors color) {
        Player currentPlayer = getPlayerByNickname(turnController.getActivePlayer());
        int numStudent = currentPlayer.getScoreboard().getPlayerStudentFromDining(color);

        for(Player player: players){
            if(!player.equals(currentPlayer) && player.getScoreboard().getPlayerStudentFromDining(color) < numStudent){
                currentPlayer.getScoreboard().addProfessor(player.getScoreboard().removeProfessor(color));
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
        map.setMotherNaturePos(motherNaturePos);
    }

    /**
     * Choose a cloud and get its students
     * @param cloudID Number of the cloud chosen
     */
    public void pickAndPlaceFromCloud(int cloudID) {
        try{
            Cloud cloud = map.getCloud(cloudID);
            if(cloud.getCloudStudents() == null) throw new MissingCloudStudentsException("Cloud empty or already chosen!");
            for(StudentDisc student: cloud.getCloudStudents()){
                currentPlayer.getScoreboard().addStudentOnEntrance(student);
            }
            cloud.reset();
        } catch (MissingCloudStudentsException e) {
            throw new RuntimeException(e);
        }

    }

    public Player getActivePlayer(){
        return getPlayerByNickname(turnController.getActivePlayer());
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
            if(!player.getNickname().equals(turnController.getActivePlayer())){
                if(player.getCurrentCard().getValue() == card.getValue()){
                    if(player.getHand().size() == 1) return true;
                    else return false;
                }
            }
        }
        return true;
    }

    public int getNumberOfPlayer() {
        return players.size();
    }

    // -----------------------------------------------------------
    // EXPERT GAME METHODS throw RunTimeException()
    // -----------------------------------------------------------

    public boolean useCharacter(int characterCardID){
        throw new RuntimeException();
    }

    public void use_209 (int studentPos, int islandNumber){
        throw new RuntimeException();
    }

    public void use_210 (){
        throw new RuntimeException();
    }

    public void endTurn_210() {
        throw new RuntimeException();
    }

    public void use_211(int islandNumber){
        throw new RuntimeException();
    }

    public void use_212(){
        throw new RuntimeException();
    }

    public void use_213(int islandNum) {
        throw new RuntimeException();
    }

    public void use_214 (){
        throw new RuntimeException();
    }

    public void use_216(Player playerByNickname) {
        throw new RuntimeException();
    }

    public void use_217 (PawnColors p){
        throw new RuntimeException();
    }

    public void use_219(Player player, int number) {
        throw new RuntimeException();
    }

    public void disableCardEffects (){
        throw new RuntimeException();
    }

    public List<CharacterCard> getCharacterCards() {
        throw new RuntimeException();
    }

}