package it.polimi.ingsw.server.model;

import it.polimi.ingsw.network.message.GameScenarioMessage;
import it.polimi.ingsw.network.message.GenericMessage;
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

public class ExpertGame extends Game {
    private int activeCardID = -1;
    private CharacterCard activeCard = null;
    private ArrayList<CharacterCard> pool = new ArrayList<>(3);


    /**
     * Default constructor
     *
     * @param playersNicknames List of players nicknames
     */
    public ExpertGame(List<String> playersNicknames) {
        super(playersNicknames);
        ExpertGameInitialization();
    }

    public void ExpertGameInitialization() {
        // Add 1 coin to all Players
        for(Player p: this.getPlayers()){
            p.addCoin();
        }
        // Choose 3 CharacterCards
        List<Integer> vector12 = new ArrayList<>(12);
        for(int i=0;i<9;i++){
            vector12.add(i);
        }
        Collections.shuffle(vector12);

        for(int i=0;i<3;i++) {
            switch (vector12.get(i)) {
                case 0 -> {
                    ArrayList<StudentDisc> t = new ArrayList<>();
                    for (int k = 0; k < 4; k++)
                        t.add(bag.pickSorted());
                    pool.add(new Card_209(t));
                }
                case 1 -> {
                    Player[] mps = new Player[5];
                    for (Player p : players)
                        for (int k = 0; k < 5; k++)
                            if (p.getScoreboard().getProfessor(PawnColors.values()[k]))
                                mps[k] = p;
                    pool.add(new Card_210(mps));
                }
                case 2 -> pool.add(new Card_211());
                case 3 -> pool.add(new Card_212());
                case 4 -> pool.add(new Card_213(List.of(getComponent(221),getComponent(222),getComponent(223),getComponent(224))));
                case 5 -> pool.add(new Card_214());
                case 6 -> pool.add(new Card_216());
                case 7 -> pool.add(new Card_217());
                case 8 -> {
                    ArrayList<StudentDisc> s = new ArrayList<>();
                    for (int k = 0; k < 4; k++)
                        s.add(bag.pickSorted());
                    pool.add(new Card_219(s));
                }
            }
        }
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
        deleteActiveCard();

        notifyObserver(new GameScenarioMessage(getGameSerialized()));

        if(turnController.getActionPhaseState() == ActionPhaseState.USE_EFFECT)
            turnController.nextActionPhase();
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
    }

    /**
     * EFFECT: During the turn you get +2 additional point while calculating influence
     *
     * @param p player that use the effect
     */
    public void use_216(Player p){
        p.setAdditionalPoints(true);
        notifyObserver(new GenericMessage("CharacterCard Impostata!"));

        if(turnController.getActionPhaseState() == ActionPhaseState.USE_EFFECT)
            turnController.nextActionPhase();
    }

    /**
     * Disable the effect of the card 216
     */
    public void endTurn_216(Player p){
        p.setAdditionalPoints(false);
    }


    /**
     * Used to activate the effect of the card217
     * @param p
     */
    public void use_217(PawnColors p){
        try{
            if(activeCardID != 217) throw new ActiveCardAlreadyExistingException("Trying to use the wrong card");
            Card_217 card = (Card_217) activeCard;
            card.setDisColor(p);
        } catch (ActiveCardAlreadyExistingException e) {
            e.printStackTrace();
        }

        if(turnController.getActionPhaseState() == ActionPhaseState.USE_EFFECT)
            turnController.nextActionPhase();
    }

    /**
     * EFFECT: Take 1 student from this card and place it on your dining room,
     *         then take 1 student from the bag and place it on this card
     *
     * @param player player that use the effect
     * @param number from 0 to 3 which is the number of the 4 student to take
     */
    public void use_219(Player player, int number) {
        Scoreboard scoreboard = player.getScoreboard();
        Card_219 card = (Card_219) activeCard;
        scoreboard.addStudentOnDining(card.getStudent(number));
        ((Card_219) activeCard).addStudent(bag.pickSorted());

        if(turnController.getActionPhaseState() == ActionPhaseState.USE_EFFECT)
            turnController.nextActionPhase();
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
            super.checkInfluence(islandID);
        }
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
