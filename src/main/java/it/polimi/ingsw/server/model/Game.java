package it.polimi.ingsw.server.model;

import it.polimi.ingsw.controller.TurnController;
import it.polimi.ingsw.network.message.GameScenarioMessage;
import it.polimi.ingsw.network.message.LobbyMessage;
import it.polimi.ingsw.network.message.MergeIslandMessage;
import it.polimi.ingsw.network.message.VictoryMessage;
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

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * Class that contains all the components of the normal mode of the game and work with that
 * Make that communicate and collaborate each other
 */
public class Game extends Observable implements Serializable {
    @Serial
    private static final long serialVersionUID = -3718160795129337210L;

    protected List<Player> players = new ArrayList<>();
    protected ArrayList<Component> components = new ArrayList<>();
    protected Map map;
    protected Bag bag;

    public static final String SERVER_NAME = "GAME_SERVER";
    public boolean expertMode;

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

        gameInitialization();

        notifyObserver(new LobbyMessage(getPlayersNicknames(), playersNicknames.size()));

        System.out.println("Game Ready!");
    }

    /**
     * Method to generate all the components needed
     */
    public void createComponents(){
        System.out.println("Creating Components");

        // Create MOTHER NATURE
        components.add(new MotherNature(1));

        //Create PROFESSORS
        int id = 2;
        for(PawnColors color: PawnColors.values()){
            components.add(new ProfessorPawn(id, color));
            id++;
        }

        System.out.println("Components Created Success!");
    }

    /**
     * Initialise the game.
     * Creates all the object necessary for the game according to the number of player
     */
    public void gameInitialization() {
        System.out.println("Starting Game Initialization");

        // Place MotherNature to a random island
        Random r = new Random();
        int pos = r.nextInt(12);
        map.setMotherNaturePos(pos);

        // Move 1 student to each island
        for(Island island: map.getIslands()) {
            if(island.getID() != oppositePosition(pos) && island.getID() != map.getMotherNaturePosition()){
                island.addStudent(bag.pickSorted());
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
        System.out.println("Game Initialization Success!");
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
            if(p == null) throw new MissingPlayerNicknameException(nickname + " is missing!");
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
        flushClouds();

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
     * Delete all the students from clouds before refill
     */
    public void flushClouds() {
        for(Cloud cloud: map.getClouds()){
            Iterator<StudentDisc> iterator = cloud.getCloudStudents().iterator();
            while (iterator.hasNext()) {
                iterator.next();
                iterator.remove();
            }
        }
    }

    /**
     * Set the assistant card for the turn
     */
    public void setAssistantCard(String nickname, int cardID) {
        try{
            Player player = getPlayerByNickname(nickname);
            AssistantCard chosenCard = player.getPlayerCard(cardID);
            if(chosenCard == null) throw new MissingAssistantCardException("AssistantCard not found!");
            player.setCurrentCard(chosenCard);
        }catch (MissingAssistantCardException e) {
            e.printStackTrace();
        }
    }


    /**
     * Move a tower to an island and notify the observer
     * @param tower
     * @param islandID
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
                 System.out.println("Move Tower " + tower.getColor() + " to island " + (islandID+1));
             }
             else{
                 TowerColors oldColor = island.getTowerColor();
                 for(Player p : players){
                     if(p.getScoreboard().getTowerColor() == oldColor){
                         p.getScoreboard().addTowers(island.getTowers());
                         island.removeTowers();

                         island.addTower(tower); //add tower to island
                         System.out.println("Move Tower " + tower.getColor() + " to island " + (islandID+1));
                         System.out.println("Move Tower/s " + oldColor + " Back To " + p.getNickname());
                     }
                 }
             }
        } catch (DisabledIslandException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Move the professor
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
     * Move @param student from the active player scoreboard to the chosen island
     * @param islandID index of the destination island
     * @param student the student
     */
    public void moveStudentToIsland(StudentDisc student, int islandID) {
        getActivePlayer().getScoreboard().removeStudent(student);

        if(map.getIsland(islandID).isDisabled()) {
            GhostIsland ghostIsland = map.getGhostIsland(islandID);
            ghostIsland.addStudent(student);
        }
        else {
            Island island = map.getIsland(islandID);
            island.addStudent(student);
        }

        notifyObserver(new GameScenarioMessage(getGameSerialized()));
    }

    /**
     *
     * @param stud id of the student to move
     */
    public void moveStudentToDiningRoom(StudentDisc stud) {
        getActivePlayer().getScoreboard().moveFromEntranceToDining(stud);
        if(!getActivePlayer().getScoreboard().getProfessor(stud.getColor()))
            checkProfessors(stud.getColor());
        notifyObserver(new GameScenarioMessage(getGameSerialized()));
    }

    /**
     * Method that calculate the influence on an island and who has the best conquer that island
     * @param islandID island ID
     */
    public void checkInfluence(int islandID) {
        int bestInfluence = 0;
        Player currentPlayer = getPlayerByNickname(turnController.getActivePlayer());
        Player dominantPlayer = null;

        Island island = map.getIsland(islandID);
        if(island.isDisabled()){
            island = map.getGhostIsland(islandID);
        }

        // CASE no tower on island
        if(island.getTowerNumber() == 0){
            System.out.println("No tower on Island " + (islandID+1) + " CheckInfluence NO Tower");

            for(Player p : players){
                int playerInfluence = island.getInfluence(p);
                if(playerInfluence > bestInfluence){
                    bestInfluence = playerInfluence;
                    dominantPlayer = p;
                }
                if (dominantPlayer != null && playerInfluence == bestInfluence && !dominantPlayer.equals(p)) {
                    dominantPlayer = null;
                }
            }
            if (dominantPlayer != null) {
                moveTowerToIsland(dominantPlayer.getScoreboard().removeTower(), islandID);
                checkTowerWinner(dominantPlayer);
                checkMerge(islandID);
            }
        } else {
            // CASE there is already a tower
            System.out.println("Already a tower on Island " + (islandID+1) + " CheckInfluence");

            Player activePlayer = getPlayerByColor(island.getTowerColor());
            bestInfluence = island.getInfluence(activePlayer);
            int activePlayerInfluence = island.getInfluence(activePlayer);
            System.out.println("Active player influence: " + activePlayerInfluence);

            for(Player p : players){
                int playerInfluence = island.getInfluence(p);
                if(!p.getNickname().equals(activePlayer.getNickname()) &&  playerInfluence > activePlayerInfluence && playerInfluence > bestInfluence){
                    bestInfluence = island.getInfluence(p);
                    dominantPlayer = p;
                }
            }

            if(dominantPlayer != null){
                System.out.println("Dominant player influence: " + island.getInfluence(dominantPlayer));

                moveTowerToIsland(currentPlayer.getScoreboard().removeTower(), islandID);
                checkTowerWinner(currentPlayer);
                checkMerge(islandID);
            }
        }

        notifyObserver(new GameScenarioMessage(getGameSerialized()));
    }

    /**
     * @param color tower color
     * @return the player that correspond to that color
     */
    private Player getPlayerByColor(TowerColors color) {
        for(Player player : players){
            if(player.getScoreboard().getTowerColor() == color)
                return player;
        }
        return null;
    }

    /**
     * Check if someone has won because has finished the towers
     * @param player
     */
    public void checkTowerWinner(Player player) {
        if(player.getScoreboard().getNumTowers() == 0)
            notifyObserver(new VictoryMessage(player.getNickname()));
    }

    /**
     *
     * @param color
     */
    private void checkProfessors(PawnColors color) {
        Player activePlayer = getActivePlayer();
        int numStudent = activePlayer.getScoreboard().getPlayerStudentFromDining(color);
        boolean addProf = false;
        Player professorPlayer = null;

        for (Player player : players) {
            if (!player.getNickname().equals(activePlayer.getNickname()) && numStudent > player.getScoreboard().getPlayerStudentFromDining(color)) {
                addProf = true;
                if(player.getScoreboard().getProfessor(color)){
                    professorPlayer = player;
                    break;
                }
            } else if (!player.getNickname().equals(activePlayer.getNickname())) {
                return;
            }
        }

        if(addProf) {
            if (professorPlayer != null)
                activePlayer.getScoreboard().addProfessor(professorPlayer.getScoreboard().removeProfessor(color));
            else
                activePlayer.getScoreboard().addProfessor((ProfessorPawn) components.get(1 + color.ordinal()));
        }
    }

    /**
     * Check if it's possible to merge two island
     * @param islandID
     */
    public void checkMerge(int islandID){
        Island island = map.getIsland(islandID);
        Island islandSucc = map.getNext(islandID);
        Island islandPrev = map.getPrev(islandID);

        if(island.getTowerColor() == islandSucc.getTowerColor() && !islandSucc.getTowers().isEmpty()) {
            System.out.println("Merge with Next (" + islandSucc.getID() + ")");
            map.merge(islandID, getNextInt(islandID));

            notifyObserver(new MergeIslandMessage(Game.SERVER_NAME, List.of(islandID, getNextInt(islandID))));
        }

        if(island.getTowerColor() == islandPrev.getTowerColor() && !islandPrev.getTowers().isEmpty()){
            System.out.println("Merge with Previous(" + islandPrev.getID() + ")");
            map.merge(islandID, getPrevInt(islandID));

            notifyObserver(new MergeIslandMessage(Game.SERVER_NAME, List.of(islandID, getPrevInt(islandID))));
        }
    }

    /**
     * Get next number of island
     * @param islandID starting island
     * @return number of the next island
     */
    public int getNextInt(int islandID){
        return islandID == 11 ? 0 : islandID + 1;
    }

    /**
     * Get previous number of island
     * @param islandID starting island
     * @return number of the previous island
     */
    public int getPrevInt(int islandID){
        return islandID == 0 ? 11 : islandID - 1;
    }

    /**
     * Move Mother Nature forward by @param steps
     */
    public void moveMotherNature(int steps){
        int motherNaturePos = map.getMotherNaturePosition();
        // Calculate nextIsland
        for(int i = 0; i < steps; i++){
            motherNaturePos++;
            if((motherNaturePos) == 12) motherNaturePos = 0;
            if(map.getIsland(motherNaturePos).isDisabled()){
                int groupID = map.getIsland(motherNaturePos).getGroupID();
                do{
                    motherNaturePos++;
                    if((motherNaturePos) == 12) motherNaturePos = 0;
                } while (map.getIsland(getNextInt(motherNaturePos)).getGroupID() == groupID);
            }
        }
        System.out.println("Move MotherNature to island " + (motherNaturePos + 1));

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

    /**
     * Method used to restore the game from the saved file
     * @param restoredMap map saved
     * @param restoredBag bag saved
     * @param restoredComponents components saved
     * @param restoredPlayers players saved
     * @param restoredExpertMode mode saved
     * @param restoredActiveCardID activeCardID saved
     * @param restoreActiveCard activeCard saved
     * @param restorePool pool saved
     */
    public void restoreGame(Map restoredMap, Bag restoredBag, ArrayList<Component> restoredComponents, List<Player> restoredPlayers, boolean restoredExpertMode, int restoredActiveCardID, CharacterCard restoreActiveCard, ArrayList<CharacterCard> restorePool) {
        this.map = restoredMap;
        this.bag = restoredBag;
        this.components = restoredComponents;
        this.players = restoredPlayers;
        this.expertMode = restoredExpertMode;
    }

    /**
     * Getter
     * @return activePlayer
     */
    public Player getActivePlayer(){
        return getPlayerByNickname(turnController.getActivePlayer());
    }

    /**
     * Calculate the opposite island
     * @param pos island num you want to calculate the oppsite
     * @return the opposite island number
     */
    public int oppositePosition(int pos) {
        int oppositePos = pos;
        for(int i=0; i < 6; i++){
            oppositePos++;
            if(oppositePos == 12)
                oppositePos = 0;
        }
        return oppositePos;
    }

    /**
     * Getter
     * @return number of players connected or not
     */
    public int getNumberOfPlayer() {
        return players.size();
    }

    /**
     * Getter of the played assistant card in this turn
     * @return
     */
    public List<AssistantCard> getPlayedAssistantCards() {
        List<AssistantCard> playedAssistantCards = new ArrayList<>();

        for(Player player : players){
            if(player.getCurrentCard() != null)
                playedAssistantCards.add(player.getCurrentCard());
        }

        return playedAssistantCards;
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

    public ArrayList<Player> getPlayersConnected(){
        ArrayList<Player> playersConnected = new ArrayList<>();
        for(Player player : players){
            if(player.isConnected())
                playersConnected.add(player);
        }
        return playersConnected;
    }

    public boolean isExpertMode() {
        return expertMode;
    }

    public void setExpertMode(boolean expertMode) {
        this.expertMode = expertMode;
    }

    // -----------------------------------------------------------
    // EXPERT GAME METHODS throws RunTimeException()
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

    public void use_215(ArrayList<Integer> entranceStudents, ArrayList<Integer> cardStudents) {
        throw new RuntimeException();
    }

    public void use_216(Player playerByNickname) {
        throw new RuntimeException();
    }

    public void use_217 (PawnColors p){
        throw new RuntimeException();
    }

    public void use_218(List<Integer> entranceStudents, List<PawnColors> cardStudents) {
        throw new RuntimeException();
    }

    public void use_219(int number) {
        throw new RuntimeException();
    }

    public void use_220(PawnColors color) {
        throw new RuntimeException();
    }

    public void disableCardEffects (){
        throw new RuntimeException();
    }

    public List<CharacterCard> getCharacterCards() {
        throw new RuntimeException();
    }

    public CharacterCard getCharacterCardByID(int ID) {
        throw new RuntimeException();
    }

    public void deleteActiveCard(){
        throw new RuntimeException();
    }

    public int getActiveCardID() {
        throw new RuntimeException();
    }
}