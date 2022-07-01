package it.polimi.ingsw.server.model;

import it.polimi.ingsw.network.message.GameScenarioMessage;
import it.polimi.ingsw.server.enumerations.ActionPhaseState;
import it.polimi.ingsw.server.enumerations.PawnColors;
import it.polimi.ingsw.server.exception.ActiveCardAlreadyExistingException;
import it.polimi.ingsw.server.model.bag.Bag;
import it.polimi.ingsw.server.model.component.Coin;
import it.polimi.ingsw.server.model.component.Component;
import it.polimi.ingsw.server.model.component.NoEntryTile;
import it.polimi.ingsw.server.model.component.StudentDisc;
import it.polimi.ingsw.server.model.component.charactercards.*;
import it.polimi.ingsw.server.model.map.Island;
import it.polimi.ingsw.server.model.map.Map;
import it.polimi.ingsw.server.model.player.Player;
import it.polimi.ingsw.server.model.player.Scoreboard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Class that extends the normal mode of the game
 * It contains the components of the expert mode of the game and works with that
 * Make that communicate and collaborate each other
 */
public class ExpertGame extends Game {
    private int activeCardID = -1;

    private CharacterCard activeCard = null;
    private ArrayList<CharacterCard> pool = new ArrayList<>(3);

    /**
     * Default constructor
     * @param playersNicknames List of players nicknames
     */
    public ExpertGame(List<String> playersNicknames) {
        super(playersNicknames);
        ExpertGameInitialization();

        System.out.println("ExpertGame Ready!");
    }

    /**
     * Initialize the expert version of the game
     */
    public void ExpertGameInitialization() {
        System.out.println("Starting ExpertGame Initialization");

        // Add 1 coin to all Players
        for(Player p: this.getPlayers()){
            p.addCoin();
        }

        // Choose 3 CharacterCards
        List<Integer> vector12 = new ArrayList<>(12);
        for(int i=0;i<7;i++){
            vector12.add(i);
        }
        Collections.shuffle(vector12);

        for(int i=0;i<3;i++) {
            switch (vector12.get(i)) {
                case 0 -> {
                    Player[] mps = new Player[5];
                    for (Player p : players)
                        for (int k = 0; k < 5; k++)
                            if (p.getScoreboard().getProfessor(PawnColors.values()[k]))
                                mps[k] = p;
                    pool.add(new Card_210(mps));
                }
                case 1 -> pool.add(new Card_211());
                case 2 -> pool.add(new Card_212());
                case 3 -> pool.add(new Card_214());
                case 4 -> pool.add(new Card_216());
                case 5 -> pool.add(new Card_217());
            }
        }

        System.out.println("ExpertGame Initialization Success!");
    }

    @Override
    public void createComponents(){
        super.createComponents();

        for(int i=189; i<=208; i++){
            components.add(new Coin(i));
        }

        for(int i=209; i<=220; i++){
            components.add(new CharacterCard(i));
        }

        for (int j = 221; j <= 224; j++) {
            components.add(new NoEntryTile(j));
        }

        printComponents();

    }

    /**
     * Set the active character card and remove the coin to the player
     * @param characterCardID ID of the card you want to use
     * @return true if the card is set with success, false otherwise
     */
    public boolean useCharacter(int characterCardID){
        for(CharacterCard characterCard : pool)
            if(characterCard.getID() == characterCardID){
                activeCard = characterCard;
                activeCardID = characterCard.getID();

                getActivePlayer().removeCoin(characterCard.getCost());
                characterCard.addCost();

                return true;
            }
        return false;
    }

    /**
     * Delete the actual active card
     */
    @Override
    public void deleteActiveCard(){
        activeCard = null;
        activeCardID = -1;
    }

    /**
     * @return the ID of the active card
     */
    public int getActiveCardID() {
        return activeCardID;
    }

    /**
     * @return the arraylist of the three active card used in this game
     */
    public ArrayList<CharacterCard> getPool() {
        return pool;
    }

    public CharacterCard getActiveCard() {
        return activeCard;
    }

    /**
     * Method used for testing
     */
    public void setCard_210_ForTest(){
        Player[] t = new Player [5];
        for(Player p: players)
            for(int i=0;i<5;i++)
                if(p.getScoreboard().getProfessor(PawnColors.values()[i]))
                    t[i] = p;
        pool.add(new Card_210(t));
    }

    /**
     * Used to activate the effect of the card209
     * @param studentPos
     * @param islandID
     */
    public void use_209 (int studentPos, int islandID) {
        try {
            if(activeCardID != 209)
                throw new ActiveCardAlreadyExistingException("Trying to use the wrong card");
        } catch (ActiveCardAlreadyExistingException e) {
            e.printStackTrace();
        }

        Card_209 card209 = (Card_209) activeCard;

        StudentDisc moving = card209.use(studentPos, bag.pickSorted());
        map.getIsland(islandID).addStudent(moving);

        notifyObserver(new GameScenarioMessage(getGameSerialized()));

        if(turnController.getActionPhaseState() == ActionPhaseState.USE_EFFECT)
            turnController.nextActionPhase();
        else turnController.actionPhase();

    }

    /**
     * Used to activate the effect of the card210
     */
    public void use_210 () {
        try{
            if(activeCardID != 210)
                throw new ActiveCardAlreadyExistingException("Trying to use the wrong card");
        } catch (ActiveCardAlreadyExistingException e) {
            e.printStackTrace();
        }

        Card_210 temp = (Card_210) activeCard;

        Player[] t = new Player [5];
        for(Player p: players)
            for(int i=0;i<5;i++)
                if(p.getScoreboard().getProfessor(PawnColors.values()[i]))
                    t[i] = p;

        temp.updateOldPos(t);

        for(Player p : players)
            if (p != getActivePlayer())
                for(int i=0;i<5;i++)
                    if(p.getScoreboard().getProfessor(PawnColors.values()[i]))
                        if(p.getScoreboard().getPlayerStudentFromDining(PawnColors.values()[i]) == getActivePlayer().getScoreboard().getPlayerStudentFromDining(PawnColors.values()[i])){
                            temp.updateOnePos(p,i);
                            moveProfessor(PawnColors.values()[i], getActivePlayer());
                        }

        notifyObserver(new GameScenarioMessage(new GameSerialized(this)));

        if(turnController.getActionPhaseState() == ActionPhaseState.USE_EFFECT)
            turnController.nextActionPhase();
        else turnController.actionPhase();

    }

    /**
     * Called and the ond of the turn when the active card is the card210
     * It reset the professor used to their original position
     */
    public void endTurn_210() {
        Card_210 temp = (Card_210) activeCard;
        for(int i=0; i<5; i++){
            moveProfessor(PawnColors.values()[i], temp.getOldPos(i));
        }
    }

    /**
     * Used to activate the effect of the card211
     * @param islandNumber
     */
    public void use_211(int islandNumber) {
        try{
            if(activeCardID != 211)
                throw new ActiveCardAlreadyExistingException("Trying to use the wrong card");
        } catch (ActiveCardAlreadyExistingException e) {
            System.out.println(e.getMessage());
        }
        checkInfluence(islandNumber - 1);

        if(turnController.getActionPhaseState() == ActionPhaseState.USE_EFFECT)
            turnController.nextActionPhase();
        else turnController.actionPhase();
    }

    /**
     * Used to activate the effect of the card212
     */
    public void use_212() {
        try {
            if(activeCardID != 212)
                throw new ActiveCardAlreadyExistingException("Trying to use the wrong card");
        } catch (ActiveCardAlreadyExistingException e) {
            e.printStackTrace();
        }
        activeCardID = 212;

        if(turnController.getActionPhaseState() == ActionPhaseState.USE_EFFECT)
            turnController.nextActionPhase();
        else turnController.actionPhase();

    }

    /**
     * Used to activate the effect of the card213
     * @param islandNumber
     */
    public void use_213(int islandNumber) {
        try{
            if(activeCardID != 213)
                throw new ActiveCardAlreadyExistingException("Trying to use the wrong card");
        } catch (ActiveCardAlreadyExistingException  e) {
            e.printStackTrace();
        }

        Card_213 temp = (Card_213) activeCard;
        map.getIsland(islandNumber - 1).addNoEntryTile(temp.use());

        if(turnController.getActionPhaseState() == ActionPhaseState.USE_EFFECT)
            turnController.nextActionPhase();
        else turnController.actionPhase();
    }

    /**
     * Used to activate the effect of the card214
     */
    public void use_214() {
        try {
            if(activeCardID != 214)
                throw new ActiveCardAlreadyExistingException("Trying to use the wrong card");
        } catch (ActiveCardAlreadyExistingException e) {
            e.printStackTrace();
        }

        activeCardID = 214;

        if(turnController.getActionPhaseState() == ActionPhaseState.USE_EFFECT)
            turnController.nextActionPhase();
        else turnController.actionPhase();

    }

    /**
     * Used to activate the effect of the card215
     * @param entrance
     * @param card
     */
    public void use_215(ArrayList<Integer> entrance, ArrayList<Integer> card){
        try {
            if(activeCardID != 215)
                throw new ActiveCardAlreadyExistingException("Trying to use the wrong card");
        } catch (ActiveCardAlreadyExistingException e) {
            e.printStackTrace();
        }

        activeCardID = 215;

        Card_215 card_215 = (Card_215) activeCard;
        Player player = getActivePlayer();

        if(entrance.size() == card.size()){
            for(int i : entrance){
                StudentDisc student = player.getScoreboard().getEntrance().get(i);
                player.getScoreboard().removeStudent(student);

                card_215.addStudent(student);
            }

            for(int i : card){
                StudentDisc student = card_215.getStudents().remove(i);
                player.getScoreboard().addStudentOnEntrance(student);
            }
        }

        notifyObserver(new GameScenarioMessage(new GameSerialized(this)));

        if(turnController.getActionPhaseState() == ActionPhaseState.USE_EFFECT)
            turnController.nextActionPhase();
        else turnController.actionPhase();

    }

    /**
     * EFFECT: During the turn you get +2 additional point while calculating influence
     *
     * @param p player that use the effect
     */
    public void use_216(Player p){
        p.setAdditionalPoints(true);

        if(turnController.getActionPhaseState() == ActionPhaseState.USE_EFFECT)
            turnController.nextActionPhase();
        else turnController.actionPhase();

    }

    /**
     * Disable the effect of the card 216
     */
    public void endTurn_216(Player p){
        p.setAdditionalPoints(false);
    }


    /**
     * Used to activate the effect of the card217
     * @param color
     */
    public void use_217(PawnColors color){
        try{
            if(activeCardID != 217)
                throw new ActiveCardAlreadyExistingException("Trying to use the wrong card");
        } catch (ActiveCardAlreadyExistingException e) {
            e.printStackTrace();
        }

        Card_217 card = (Card_217) activeCard;
        card.setDisColor(color);

        if(turnController.getActionPhaseState() == ActionPhaseState.USE_EFFECT)
            turnController.nextActionPhase();
        else turnController.actionPhase();

    }

    /**
     * Used to activate the effect of the card218
     */
    public void use_218(List<Integer> entrance, List<PawnColors> dining) {
        try{
            if(activeCardID != 218)
                throw new ActiveCardAlreadyExistingException("Trying to use the wrong card");
        } catch (ActiveCardAlreadyExistingException e) {
            e.printStackTrace();
        }

        Player player = getActivePlayer();

        for(int i : entrance){
            StudentDisc student = player.getScoreboard().getEntrance().get(i);
            player.getScoreboard().removeStudent(student);
            player.getScoreboard().addStudentOnDining(student);

            player.getScoreboard().addStudentOnEntrance(player.getScoreboard().getStudentFromDining(dining.get(i)));
            player.getScoreboard().removeStudentFromDining(player.getScoreboard().getStudentFromDining(dining.get(i)));
        }

        if(turnController.getActionPhaseState() == ActionPhaseState.USE_EFFECT)
            turnController.nextActionPhase();
        else turnController.actionPhase();

    }


    /**
     * EFFECT: Take 1 student from this card and place it on your dining room,
     *         then take 1 student from the bag and place it on this card
     *
     * @param number from 0 to 3 which is the number of the 4 student to take
     */
    public void use_219(int number) {
        try{
            if(activeCardID != 219)
                throw new ActiveCardAlreadyExistingException("Trying to use the wrong card");
        } catch (ActiveCardAlreadyExistingException e) {
            e.printStackTrace();
        }

        Scoreboard scoreboard = getActivePlayer().getScoreboard();
        Card_219 card = (Card_219) activeCard;
        scoreboard.addStudentOnDining(card.getStudent(number));
        card.addStudent(bag.pickSorted());

        notifyObserver(new GameScenarioMessage(getGameSerialized()));

        if(turnController.getActionPhaseState() == ActionPhaseState.USE_EFFECT)
            turnController.nextActionPhase();
        else turnController.actionPhase();
    }

    /**
     * Used to activate the effect of the card220
     */
    public void use_220(PawnColors color) {
        try{
            if(activeCardID != 220)
                throw new ActiveCardAlreadyExistingException("Trying to use the wrong card");
        } catch (ActiveCardAlreadyExistingException e) {
            e.printStackTrace();
        }

        for(Player player : players){
            for(int i = 0; i < 3; i++){
                if(player.getScoreboard().getPlayerStudentFromDining(color) > 0){
                    player.getScoreboard().removeStudentFromDining(player.getScoreboard().getStudentFromDining(color));
                }
                else break;
            }
        }

        if(turnController.getActionPhaseState() == ActionPhaseState.USE_EFFECT)
            turnController.nextActionPhase();
        else turnController.actionPhase();

    }

    /**
     * Method to disable the card effect
     * MUST BE CALLED AT EVERY END TURN!
     */
    public void disableCardEffects () {
        switch(activeCardID){
            case 210: endTurn_210();
            case 216: endTurn_216(getActivePlayer());
        }

        deleteActiveCard();
    }


    public List<CharacterCard> getCharacterCards() {
        return pool;
    }

    /**
     * Return the character card by passing his id
     * @param ID ID of the character card you want to return
     * @return the character card
     */
    public CharacterCard getCharacterCardByID(int ID) {
        for(CharacterCard characterCard : pool){
            if(characterCard.getID() == ID)
                return characterCard;
        }
        System.out.println("CharacterCard Not Found");
        return null;
    }

    /**
     * Check the influence of the active player on the island passed
     * @param islandID if of the island you want to calculate the influence
     */
    @Override
    public void checkInfluence(int islandID) {
        Player currentPlayer = getPlayerByNickname(turnController.getActivePlayer());

        Island island = map.getIsland(islandID);
        if(island.isDisabled()) {
            island = map.getGhostIsland(islandID);
        }
        if(island.checkNoEntryTile())
            return;

        if(activeCardID == 214) {
            System.out.println("Checking influence 214");
            int bestInfluence = 0;
            Player dominantPlayer = null;
            Player opponentPlayer = null;

            if(island.checkNoEntryTile()) {
                Card_213 temp = null;
                for(CharacterCard characterCard : pool)
                    if(characterCard.getID() == 213)
                        temp = (Card_213) characterCard;
                assert temp != null;
                temp.recoverTile(map.getIsland(islandID).removeNoEntryTile());
                return;
            }

            // CASE no tower on island
            if (island.getTowerNumber() == 0) {
                for (Player p : players) {
                    int playerInfluence = island.getInfluence_214(p);
                    if (playerInfluence > bestInfluence) {
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

                    notifyObserver(new GameScenarioMessage(getGameSerialized()));
                    return;
                }
            }

            // CASE there is already a tower
            int currentPlayerInfluence = island.getInfluence_214(currentPlayer);
            for (Player p : players) {
                if (p.getScoreboard().getTowerColor() == island.getTowerColor()) {
                    opponentPlayer = p;
                    break;
                }
            }

            if (opponentPlayer != null && currentPlayerInfluence > island.getInfluence_214(Objects.requireNonNull(opponentPlayer))) {
                moveTowerToIsland(currentPlayer.getScoreboard().removeTower(), islandID);
                checkTowerWinner(currentPlayer);

                checkMerge(islandID);

                notifyObserver(new GameScenarioMessage(getGameSerialized()));
            }
        }
        else if(activeCardID == 217){
            System.out.println("Checking influence 217");
            Card_217 card = (Card_217) activeCard;
            int bestInfluence = 0;
            Player dominantPlayer = null;
            Player opponentPlayer = null;

            if (island.checkNoEntryTile()) {
                Card_213 temp = null;
                for(CharacterCard characterCard : pool)
                    if(characterCard.getClass() == Card_213.class)
                        temp = (Card_213) characterCard;

                assert temp != null;
                temp.recoverTile(map.getIsland(islandID).removeNoEntryTile());
                return;
            }

            // CASE no tower on island
            if(island.getTowerNumber() == 0){
                for(Player p : players){
                    int playerInfluence = island.getInfluence_217(p, card.getDisColor());
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
                    super.checkMerge(islandID);

                    notifyObserver(new GameScenarioMessage(getGameSerialized()));
                    return;
                }
            }

            // CASE there is already a tower
            int currentPlayerInfluence = island.getInfluence_217(getPlayerByNickname(turnController.getActivePlayer()),card.getDisColor());
            for(Player p : players){
                if(p.getScoreboard().getTowerColor() == island.getTowerColor() && p.getScoreboard().getTowerColor() != currentPlayer.getScoreboard().getTowerColor()){
                    opponentPlayer = p;
                    break;
                }
            }

            if(opponentPlayer != null && currentPlayerInfluence > island.getInfluence_217(opponentPlayer, card.getDisColor())){
                moveTowerToIsland(currentPlayer.getScoreboard().removeTower(), islandID);
                checkTowerWinner(currentPlayer);

                checkMerge(islandID);

                notifyObserver(new GameScenarioMessage(getGameSerialized()));
            }
        }
        else {
            System.out.println("Checking influence");
            super.checkInfluence(islandID);
        }
    }

    /**
     * Setter
     * @param activeCard card to set
     */
    public void setActiveCard(CharacterCard activeCard) {
        this.activeCardID = activeCard.getID();
        this.activeCard = activeCard;
    }

    /**
     * Setter
     * @param activeCardID id to set
     */
    public void setActiveCardID(int activeCardID) {
        this.activeCardID = activeCardID;
    }

    /**
     * Restore the game from a previous game saved
     * @param restoredMap
     * @param restoredBag
     * @param restoredComponents
     * @param restoredPlayers
     * @param restoredExpertMode
     * @param restoredActiveCardID
     * @param restoreActiveCard
     * @param restorePool
     */
    @Override
    public void restoreGame(Map restoredMap, Bag restoredBag, ArrayList<Component> restoredComponents, List<Player> restoredPlayers, boolean restoredExpertMode, int restoredActiveCardID, CharacterCard restoreActiveCard, ArrayList<CharacterCard> restorePool) {
        this.activeCardID = restoredActiveCardID;
        this.activeCard = restoreActiveCard;
        this.pool = restorePool;
        super.restoreGame(restoredMap, restoredBag, restoredComponents, restoredPlayers, restoredExpertMode, 0, null, null);
    }

}
