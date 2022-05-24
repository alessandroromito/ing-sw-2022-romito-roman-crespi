package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.enumerations.MapPositions;
import it.polimi.ingsw.server.enumerations.PawnColors;
import it.polimi.ingsw.server.exception.*;
import it.polimi.ingsw.server.model.component.Coin;
import it.polimi.ingsw.server.model.component.NoEntryTile;
import it.polimi.ingsw.server.model.component.StudentDisc;
import it.polimi.ingsw.server.model.component.charactercards.*;
import it.polimi.ingsw.server.model.map.Island;
import it.polimi.ingsw.server.model.player.Player;
import it.polimi.ingsw.server.model.player.Scoreboard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class ExpertGame extends Game {
    private int activeCardID;
    private CharacterCard activeCard;
    private ArrayList<CharacterCard> pool = new ArrayList<>(3);

    /**
     * Default constructor
     *
     * @param playersNicknames List of players nicknames
     */
    public ExpertGame(List<String> playersNicknames) {
        super(playersNicknames);
        activeCardID = -1;
        activeCard = null;
        ExpertGameInitialization();
    }

    public void ExpertGameInitialization() {
        // Add 1 coin to all Players
        for(Player p: this.getPlayers()){
            p.addCoin();
        }
        // Choose 3 CharacterCards
        List<Integer> vector12 = new ArrayList<>(12);
        for(int i=0;i<12;i++){
            vector12.add(i);
        }
        Collections.shuffle(vector12);

        for(int i=0;i<3;i++) {
            switch(vector12.get(i)){
                case 0:
                    ArrayList<StudentDisc> t = new ArrayList<StudentDisc>();
                    for(int k=0;k<4;k++)    {t.add((StudentDisc) getComponent(bag.pickSorted()));   t.get(k).setPosition(MapPositions.CARD_209);}
                    pool.add(new Card_209(t));
                    break;
                case 1:
                    Player mps[] = new Player [5];
                    for(int k=0;k<5;k++)   mps[k] = players.get(components.get(k+2).getPosition().ordinal()/3);
                    pool.add(new Card_210(mps));
                    break;
                case 2: pool.add(new Card_211());   break;
                case 3: pool.add(new Card_212());   break;
                case 4:
                    for(int j=216; j<=225; j++){
                        components.add(new NoEntryTile(j));
                    }
                    pool.add(new Card_213(components.subList(209,212)));
                    break;
//                case 5: pool[i] = new Card_214();   break;
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
    }

    public void useCharacter(int characterCardID){
        try{
            if(activeCardID != -1)
                throw new ActiveCardAlreadyExistingException("There is an active card already!");

            for(int i=0;i<3;i++)
                if(pool.get(i).getID() == characterCardID){
                    activeCard = pool.get(i);
                    activeCardID = activeCard.getID();
                    activeCard.addCost();
                    getActivePlayer().removeCoin(activeCard.getCost());
                }

        } catch (ActiveCardAlreadyExistingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteActiveCard(){
        activeCard = null;
        activeCardID = -1;
    }

    public int getActiveCardID() {
        return activeCardID;
    }

    public CharacterCard getActiveCard() {
        return activeCard;
    }

    public void setCard_210_ForTest(){
        Player mps[] = new Player [5];
        for(int k=0;k<5;k++)    mps[k] = players.get(components.get(k+2).getPosition().ordinal()/3);
        pool.add(new Card_210(mps));
    }

    //aggiornare la visualizzazione per le ghost island
    public void use_209 (int studentPos,MapPositions island) {
        try {
            if(activeCardID != 209)
                throw new ActiveCardAlreadyExistingException("Trying to use the wrong card");
            Card_209 temp = (Card_209) activeCard;

            StudentDisc moving = temp.use(studentPos,(StudentDisc)getComponent(bag.pickSorted()));
            moving.setPosition(island);
            deleteActiveCard();
        } catch (ActiveCardAlreadyExistingException e) {
            throw new RuntimeException(e);
        }
    }

    //tener conto di quali prof sono stati spostati e farli tornare nella loro posizione a fine turno
    public void use_210 () {
        try{
            if(activeCardID != 210) throw new ActiveCardAlreadyExistingException("Trying to use the wrong card");
            Card_210 temp = (Card_210) activeCard;
            activeCardID = 210;

            Player t[] = new Player [5];
            for(int i=2;i<=6;i++)
                t[i-2] = players.get(components.get(i).getPosition().ordinal()/3);
            temp.updateOldPos(t);

            for(Player p : players)
                if (p != getActivePlayer())
                    for(int i=0;i<5;i++)
                        if(p.getScoreboard().getProfessor(PawnColors.values()[i]))
                            if(p.getScoreboard().getPlayerStudentFromDining(PawnColors.values()[i]) == getActivePlayer().getScoreboard().getPlayerStudentFromDining(PawnColors.values()[i])){
                                temp.updateOnePos(players.get(components.get(i).getPosition().ordinal()/3),i);
                                moveProfessor(PawnColors.values()[i],getActivePlayer());
                            }
        } catch (ActiveCardAlreadyExistingException e) {

        }
    }

    public void endTurn_210() {
        Card_210 temp = (Card_210) activeCard;
        for(int i=0;i<5;i++){
            moveProfessor(PawnColors.values()[i],temp.getOldPos(i));
        }
    }

    public void use_211 (int islandNumber) {
        try{
            if(activeCardID != 211)
                throw new ActiveCardAlreadyExistingException("Trying to use the wrong card");
            checkInfluence(islandNumber);
        } catch (MissingPlayerNicknameException | ActiveCardAlreadyExistingException e) {
            throw new RuntimeException(e);
        }
    }

    //Sposta mother nature fino a 2 pos in più
    public void use_212 () {
        try {
            if(activeCardID != 212)
                throw new ActiveCardAlreadyExistingException("Trying to use the wrong card");
            activeCardID = 212;
        } catch (ActiveCardAlreadyExistingException e) {
            throw new RuntimeException(e);
        }
    }
//difficile
    public void use_213 (int islandNumber) throws ActiveCardAlreadyExistingException, ZeroNoEntryTyleRemainingException {
        if(activeCardID != 213) throw new ActiveCardAlreadyExistingException("Trying to use the wrong card");
        Card_213 temp = (Card_213) activeCard;
        map.getIsland(islandNumber).addNoEntryTile(temp.use());
    }

    public void use_214 () throws ActiveCardAlreadyExistingException{
        if(activeCardID != 214) throw new ActiveCardAlreadyExistingException("Trying to use the wrong card");
        activeCardID = 214;
    }

    @Override
    public void checkInfluence(int islandID) throws MissingPlayerNicknameException {
        if(activeCardID == 214) {
            int bestInfluence = 0;
            Player currentPlayer = getPlayerByNickname(turnController.getActivePlayer());
            Player dominantPlayer = null;
            Player opponentPlayer = null;

            Island island = map.getIsland(islandID);
            if (island.isDisabled()) {
                island = map.getGhostIsland(islandID);
            }

            if (island.checkNoEntryTile()) {
                Card_213 temp = null;
                for(int i=0;i<3;i++)
                    if(pool[i].getClass() == Card_213.class)
                         temp = (Card_213) pool[i];
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
                }
                moveTowerToIsland(Objects.requireNonNull(dominantPlayer).getScoreboard().getTowerColor(), islandID);
            }

            // CASE there is already a tower
            int currentPlayerInfluence = island.getInfluence_214(getPlayerByNickname(turnController.getActivePlayer()));
            for (Player p : players) {
                if (p.getScoreboard().getTowerColor() == island.getTowerColor()) {
                    opponentPlayer = p;
                }
            }

            if (island.getInfluence_214(Objects.requireNonNull(opponentPlayer)) > currentPlayerInfluence) {
                moveTowerToIsland(Objects.requireNonNull(dominantPlayer).getScoreboard().getTowerColor(), islandID);
            }
        }
        else
        if(activeCardID == 217){
            Card_217 card = (Card_217) activeCard;
            int bestInfluence = 0;
            Player currentPlayer = getPlayerByNickname(turnController.getActivePlayer());
            Player dominantPlayer = null;
            Player opponentPlayer = null;

            Island island = map.getIsland(islandID);
            if(island.isDisabled()){
                island = map.getGhostIsland(islandID);
            }

            if(island.checkNoEntryTile()){
                island.removeNoEntryTile().setPosition(MapPositions.CARD_213);
                return;
            }

            // CASE no tower on island
            if(island.getTowerNumber() == 0){
                for(Player p : players){
                    int playerInfluence = island.getInfluence_217(p,card.getDisColor());
                    if(playerInfluence > bestInfluence){
                        bestInfluence = playerInfluence;
                        dominantPlayer = p;
                    }
                }
                moveTowerToIsland(Objects.requireNonNull(dominantPlayer).getScoreboard().getTowerColor(), islandID);
            }

            // CASE there is already a tower
            int currentPlayerInfluence = island.getInfluence_217(getPlayerByNickname(turnController.getActivePlayer()),card.getDisColor());
            for(Player p : players){
                if(p.getScoreboard().getTowerColor() == island.getTowerColor()){
                    opponentPlayer = p;
                }
            }

            if(island.getInfluence_217(Objects.requireNonNull(opponentPlayer),card.getDisColor()) > currentPlayerInfluence){
                moveTowerToIsland(Objects.requireNonNull(dominantPlayer).getScoreboard().getTowerColor(), islandID);
            }
        }
        else super.checkInfluence(islandID);
    }

    /**
     * EFFECT: Take 1 student from this card and place it on your dining room,
     *         then take 1 student from the bag and place it on this card
     *
     * @param p player that use the effect
     * @param number from 0 to 3 which is the number of the 4 student to take
     */
    public void use_219(Player p, int number) {
        Scoreboard scoreboard = p.getScoreboard();
        Card_219 card = (Card_219) activeCard;

        scoreboard.addStudentOnDining(card.getStudent(number));
        ((Card_219) activeCard).addStudent((StudentDisc) getComponent(bag.pickSorted()));

    }

    /**
     * EFFECT: During the turn you get +2 additional point while calculating influence
     *
     * @param p player that use the effect
     */
    public void use_216(Player p){
        p.setAdditionalPoints(true);
    }

    /**
     * Disable the effect of the card 216
     *
     * @param p
     */
    public void endTurn_216(Player p){
        p.setAdditionalPoints(false);
    }

    public void use_217 (PawnColors p){
        try{
            if(activeCardID != 217) throw new ActiveCardAlreadyExistingException("Trying to use the wrong card");
            activeCardID = 217;
            Card_217 card = (Card_217) activeCard;
            card.setDisColor(p);
        } catch (ActiveCardAlreadyExistingException e) {
            e.printStackTrace();
        }

    }

    //da chiamare sempre a fine turno
    public void disableCardEffects () {
        switch(activeCardID){
            case 210: endTurn_210();
            case 216: endTurn_216(getActivePlayer());
        }
        deleteActiveCard();
    }


}
