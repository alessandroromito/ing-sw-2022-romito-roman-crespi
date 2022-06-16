package it.polimi.ingsw.server.model;

import it.polimi.ingsw.controller.TurnController;
import it.polimi.ingsw.network.message.GameScenarioMessage;
import it.polimi.ingsw.network.message.LobbyMessage;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Game extends Observable {

    protected List<Player> players = new ArrayList<>();
    protected ArrayList<Component> components = new ArrayList<>();
    protected Map map;
    protected Bag bag;

    public static final String SERVER_NAME = "GAME_SERVER";

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

        notifyObserver(new LobbyMessage(getPlayersNicknames(), playersNicknames.size()));
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
        // Place MotherNature to a random island
        Random r = new Random();
        int pos = r.nextInt(12);
        map.setMotherNaturePos(pos);

        // Move 1 student to each island
        ArrayList<StudentDisc> tempArrayStudents = new ArrayList<>();
        for(int i=0; i<12; i++) {
            tempArrayStudents.add(bag.pickSorted());
        }
        Collections.shuffle(tempArrayStudents);

        for(Island island: map.getIslands()) {
            if(island.getID() != oppositePosition() || island.getID() != map.getMotherNaturePosition()){
                island.addStudent(tempArrayStudents.remove(0));
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
    public Component getComponent(int ID) {
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
            cloud.addStudent(bag.pickSorted());
            cloud.addStudent(bag.pickSorted());
            cloud.addStudent(bag.pickSorted());

            if (players.size() == 3)
                cloud.addStudent(bag.pickSorted());
        }

        notifyObserver(new GameScenarioMessage(getGameSerialized()));
    }

    /**
     * Set the assistant card for the turn
     */
    public void setAssistantCard(String nickname, int cardID) {
        try{
            Player player = getPlayerByNickname(nickname);
            AssistantCard chosenCard = player.getPlayerCard(cardID);
            if(chosenCard == null) throw new MissingAssistantCardException("AssistantCard not found!");
            if(validateCard(chosenCard))
                player.setCurrentCard(chosenCard);
            else throw new DoubleAssistantCardException("Another player already played this card!");
        }catch (DoubleAssistantCardException | MissingAssistantCardException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * @param islandID index of the destination island
     * @param student the student
     */
    public void moveStudentToIsland(StudentDisc student, int islandID) {
        if(map.getIsland(islandID).isDisabled()) {
            GhostIsland ghostIsland = map.getGhostIsland(islandID);
            ghostIsland.addStudent(student);
        }
        else {
            Island island = map.getIsland(islandID);
            island.addStudent(student);
        }
        checkInfluence(islandID);

        notifyObserver(new GameScenarioMessage(getGameSerialized()));
    }

    /**
     *
     * @param tower
     * @param islandID
     * @throws DisabledIslandException
     */
    public void moveTowerToIsland(Tower tower, int islandID) {

        try{
             Island island = map.getIsland(islandID);

             if(island.isDisabled()) {
                if(map.getGhostIsland(islandID) != null)
                    island = map.getGhostIsland(islandID);
                else throw new DisabledIslandException("Error in island");
             }

             if(island.getTowers().isEmpty()) {
                 island.addTower(tower);
                 System.out.println("Move Tower " + tower.getColor() + " To Island " + islandID);
             }
             else{
                 for(Player p : players){
                     TowerColors oldColor = island.getTowerColor();
                     if(p.getScoreboard().getTowerColor() == oldColor){
                         p.getScoreboard().addTower(island.getTowers().get(0)); // Add 1 tower to the old dominant player
                         island.addTower(tower); //add tower to island
                         System.out.println("Move Tower " + tower.getColor() + " To Island " + islandID);
                         System.out.println("Move Tower " + tower.getColor() + " Back To " + p.getNickname());
                     }
                 }
             }

            notifyObserver(new GameScenarioMessage(getGameSerialized()));

        } catch (DisabledIslandException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * @param color professor color
     */
    public void moveProfessor(PawnColors color, Player player) {
        for(Player p : players){
            if(!p.equals(player) && p.getScoreboard().getProfessor(color)){
                player.getScoreboard().addProfessor(p.getScoreboard().removeProfessor(color));
            }
        }
    }

    /**
     *
     * @param stud id of the student to move
     */
    public void moveStudentToDiningRoom(StudentDisc stud) {
        getActivePlayer().getScoreboard().moveFromEntranceToDining(stud);
        if(!getPlayerByNickname(turnController.getActivePlayer()).getScoreboard().getProfessor(stud.getColor()))
            checkProfessors(stud.getColor());
    }

    public void checkInfluence(int islandID) {
        int bestInfluence = -1;
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
            if (dominantPlayer != null) {
                moveTowerToIsland(dominantPlayer.getScoreboard().removeTower(), islandID);
                checkMerge(islandID);

                notifyObserver(new GameScenarioMessage(getGameSerialized()));
                return;
            }
        }

        // CASE there is already a tower
        int currentPlayerInfluence = island.getInfluence(currentPlayer);
        for(Player p : players){
            if(p.getScoreboard().getTowerColor() == island.getTowerColor() && p.getScoreboard().getTowerColor() != currentPlayer.getScoreboard().getTowerColor()){
                opponentPlayer = p;
                break;
            }
        }
        if(opponentPlayer != null && currentPlayerInfluence > island.getInfluence(opponentPlayer)){
            moveTowerToIsland(currentPlayer.getScoreboard().removeTower(), islandID);
            checkMerge(islandID);

            notifyObserver(new GameScenarioMessage(getGameSerialized()));
        }
    }


    private void checkProfessors(PawnColors color) {
        Player activePlayer = getActivePlayer();
        int numStudent = activePlayer.getScoreboard().getPlayerStudentFromDining(color);

        for (Player player : players) {
            if (!player.equals(activePlayer) && numStudent > player.getScoreboard().getPlayerStudentFromDining(color) && player.getScoreboard().getProfessor(color)) {
                activePlayer.getScoreboard().addProfessor(player.getScoreboard().removeProfessor(color));
            } else if((!player.equals(activePlayer) && numStudent > player.getScoreboard().getPlayerStudentFromDining(color) && !player.getScoreboard().getProfessor(color)))
                activePlayer.getScoreboard().addProfessor((ProfessorPawn) components.get(1 + color.ordinal()));
        }
    }

    private void checkMerge(int islandID){
        Island island = map.getIsland(islandID);
        Island islandSucc = map.getNext(islandID);
        Island islandPrev = map.getPrev(islandID);

        if(island.getTowerColor() == islandSucc.getTowerColor() && !islandSucc.getTowers().isEmpty())
            map.merge(island.getID(), islandSucc.getID());

        if(island.getTowerColor() == islandPrev.getTowerColor() && !islandPrev.getTowers().isEmpty())
            map.merge(island.getID(), islandPrev.getID());
    }

    /**
     * Move Mother Nature forward by @param steps
     */
    public void moveMotherNature(int steps){
        int motherNaturePos = map.getMotherNaturePosition();
        // Calculate nextIsland
        for(int i=0; i < steps; i++){
            motherNaturePos++;
            if((motherNaturePos) == 12) motherNaturePos = 0;
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

        checkInfluence(motherNaturePos);
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
                getPlayerByNickname(turnController.getActivePlayer()).getScoreboard().addStudentOnEntrance(student);
            }
            cloud.reset();
        } catch (MissingCloudStudentsException e) {
            e.printStackTrace();
        }

        notifyObserver(new GameScenarioMessage(getGameSerialized()));
    }

    public Player getActivePlayer(){
        return getPlayerByNickname(turnController.getActivePlayer());
    }

    private int oppositePosition() {
        int motherNaturePosition = map.getMotherNaturePosition();
        for(int i=0; i<6; i++){
            motherNaturePosition++;
            if(motherNaturePosition == 12)
                motherNaturePosition = 0;
        }
        return motherNaturePosition;
    }

    /**
     *
     * @param card the player's card he wants to choose
     * @return true if it's valid or false if it's not
     */
    private boolean validateCard(AssistantCard card) {
        for(Player player: players){
            if(!(player.getNickname().equals(turnController.getActivePlayer())) && player.getCurrentCard() != null) {
                if(player.getCurrentCard().getValue() == card.getValue()){
                    return false;
                }
            }
        }
        return true;
    }

    public int getNumberOfPlayer() {
        return players.size();
    }

    public GameSerialized getGameSerialized() {
        return new GameSerialized(this);
    }

    public void setTurnController(TurnController turnController) {
        this.turnController = turnController;
    }

    public void printComponents(){
        for(Component component : components){
            System.out.println("ID: " + component.getID() + "Type: " + component.getClass());
        }
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
        try{
            throw new RuntimeException();
        }catch (RuntimeException e){

        }
    }

    public List<CharacterCard> getCharacterCards() {
        throw new RuntimeException();
    }

    public void deleteActiveCard(){
        //ExpertGame method
        try{
            throw new RuntimeException();
        }catch (RuntimeException e){

        }
    }
}