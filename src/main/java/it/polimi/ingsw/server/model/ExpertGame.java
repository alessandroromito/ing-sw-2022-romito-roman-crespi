package it.polimi.ingsw.server.model;

import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.server.enumerations.PawnColors;
import it.polimi.ingsw.server.exception.*;
import it.polimi.ingsw.server.model.component.charactercards.CharacterCard;
import it.polimi.ingsw.server.model.component.charactercards.*;
import it.polimi.ingsw.server.model.component.Coin;
import it.polimi.ingsw.server.model.component.NoEntryTile;
import it.polimi.ingsw.server.model.component.StudentDisc;
import it.polimi.ingsw.server.model.map.Island;
import it.polimi.ingsw.server.model.player.Player;
import it.polimi.ingsw.server.enumerations.MapPositions;
import it.polimi.ingsw.server.model.player.Scoreboard;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class ExpertGame extends Game {
    private int activeCardID;
    private CharacterCard activeCard;
    private CharacterCard pool[] = new CharacterCard[3];

    /**
     * Default constructor
     *
     * @param players ArrayList of Player
     */
    public ExpertGame(List<Player> players) {
        super(players);
        activeCardID = -1;
        activeCard = null;
    }

    @Override
    public void gameInitialization() {
        try {
            super.gameInitialization();
        } catch (EntranceFullException e) {
            System.out.println("Entrance Full!");
        }
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

        pool = new CharacterCard[3];
        for(int i=0;i<3;i++) {
            switch(vector12.get(i)){
                case 0:
                    ArrayList<StudentDisc> t = new ArrayList<StudentDisc>();
                    for(int k=0;k<4;k++)    {t.add((StudentDisc) getComponent(bag.pickSorted()));   t.get(k).setPosition(MapPositions.CARD_214);}
                    pool[i] = new Card_214(t);
                    break;
                case 1:
                    Player mps[] = new Player [5];
                    for(int k=0;k<5;k++)    mps[k] = players.get(components.get(i).getPosition().ordinal()/3);
                    pool[i] = new Card_215(mps);
                    break;
                case 2: pool[i] = new Card_216();   break;
                case 3: pool[i] = new Card_217();   break;
                case 4:
                    for(int j=209; j<=213; j++){
                        components.set(j, new NoEntryTile(j));
                    }
                    pool[i] = new Card_218(components.subList(209,212));
                    break;
                case 5: pool[i] = new Card_219();   break;
            }
        }
    }

    @Override
    public void createComponents(){
        super.createComponents();

        for(int i=189; i<=208; i++){
            components.set(i, new Coin(i));
        }

        for(int i=214; i<=225; i++){
            components.set(i, new CharacterCard(i));
        }
    }

    //receive 0,1 or 2
    //if it is a immediate effect card it will end
    public void useCharacter(int character) throws ActiveCardAlreadyExistingException{
        if(activeCardID != -1) throw new ActiveCardAlreadyExistingException("There is an active card already!");

        activeCard = map.getCard(character);
        activeCardID = activeCard.getID();
        activeCard.use();

        //exception will be handled by game controller
        for(int i=0;i<activeCard.getCost();i++)
            try{
                getActivePlayer().removeCoin();
            } catch (ZeroCoinsException e) {
                e.printStackTrace();
            }

// at the end of the turn activeCardID must be -1
    }

    @Override
    public void deleteActiveCard(){
        activeCard = null;
        activeCardID = -1;
    }

    public int getActiveCardID() {
        return activeCardID;
    }

    //aggiornare la visualizzazione per le ghost island
    public void use_214 (int studentPos,MapPositions island) throws ActiveCardAlreadyExistingException {
        if(activeCardID != 214) throw new ActiveCardAlreadyExistingException("Trying to use the wrong card");
        Card_214 temp = (Card_214) activeCard;

        StudentDisc moving = temp.use(studentPos,(StudentDisc)getComponent(bag.pickSorted()));
        moving.setPosition(island);
        deleteActiveCard();
    }
//tener conto di quali prof sono stati spostati e farli tornare nella loro posizione a fine turno
    public void use_215 () throws ActiveCardAlreadyExistingException {
        if(activeCardID != 215) throw new ActiveCardAlreadyExistingException("Trying to use the wrong card");
        Card_215 temp = (Card_215) activeCard;
        activeCardID = 215;

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
    }

    public void endTurn_215(){
        Card_215 temp = (Card_215) activeCard;
        for(int i=0;i<5;i++){
            moveProfessor(PawnColors.values()[i],temp.getOldPos(i));
        }
    }

    public void use_216 (int islandNumber) throws ActiveCardAlreadyExistingException {
        if(activeCardID != 216) throw new ActiveCardAlreadyExistingException("Trying to use the wrong card");
        try{
            checkInfluence(islandNumber);}
        catch (MissingPlayerNicknameException e){
            System.out.println("Wrong player nickname");
        }
    }
    //Sposta mother nature fino a 2 pos in più
    public void use_217 () throws ActiveCardAlreadyExistingException{
        if(activeCardID != 217) throw new ActiveCardAlreadyExistingException("Trying to use the wrong card");
        activeCardID = 217;
        // Da implementare
    }
//difficile
    public void use_218 () throws ActiveCardAlreadyExistingException{
        if(activeCardID != 218) throw new ActiveCardAlreadyExistingException("Trying to use the wrong card");

        //da implementare

    }

    public void use_219 () throws ActiveCardAlreadyExistingException{
        if(activeCardID != 219) throw new ActiveCardAlreadyExistingException("Trying to use the wrong card");
        activeCardID = 219;
    }

    @Override
    public void checkInfluence(int islandID) throws MissingPlayerNicknameException {
        if(activeCardID == 219) {
            int bestInfluence = 0;
            Player currentPlayer = getPlayerByNickname(turnController.getActivePlayer());
            Player dominantPlayer = null;
            Player opponentPlayer = null;

            Island island = map.getIsland(islandID);
            if (island.isDisabled()) {
                island = map.getGhostIsland(islandID);
            }

            if (island.checkNoEntryTile()) {
                island.removeNoEntryTile().setPosition(MapPositions.CARD_218);
                return;
            }

            // CASE no tower on island
            if (island.getTowerNumber() == 0) {
                for (Player p : players) {
                    int playerInfluence = island.getInfluence_219(p);
                    if (playerInfluence > bestInfluence) {
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
            int currentPlayerInfluence = island.getInfluence_219(getPlayerByNickname(turnController.getActivePlayer()));
            for (Player p : players) {
                if (p.getScoreboard().getTowerColor() == island.getTowerColor()) {
                    opponentPlayer = p;
                }
            }

            if (island.getInfluence_219(Objects.requireNonNull(opponentPlayer)) > currentPlayerInfluence) {
                try {
                    moveTowerToIsland(Objects.requireNonNull(dominantPlayer).getScoreboard().getTowerColor(), islandID);
                } catch (DisabledIslandException e) {
                    System.out.println("ERROR Switching Tower!");
                }
            }
        }
        else if(activeCardID == 222){
            Card_222 card = (Card_222) activeCard;
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
                    int playerInfluence = island.getInfluence_222(p,card.getDisColor());
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
            int currentPlayerInfluence = island.getInfluence_222(getPlayerByNickname(turnController.getActivePlayer()),card.getDisColor());
            for(Player p : players){
                if(p.getScoreboard().getTowerColor() == island.getTowerColor()){
                    opponentPlayer = p;
                }
            }

            if(island.getInfluence_222(Objects.requireNonNull(opponentPlayer),card.getDisColor()) > currentPlayerInfluence){
                try {
                    moveTowerToIsland(Objects.requireNonNull(dominantPlayer).getScoreboard().getTowerColor(), islandID);
                } catch (DisabledIslandException e) {
                    System.out.println("ERROR Switching Tower!");
                }
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
    public void use_224(Player p, int number) {
        Scoreboard scoreboard = p.getScoreboard();
        Card_224 card = (Card_224) activeCard;

        scoreboard.addStudentOnDining(card.getStudent(number));
        ((Card_224) activeCard).addStudent((StudentDisc) getComponent(bag.pickSorted()));

    }

    /**
     * EFFECT: During the turn you get +2 additional point while calculating influence
     *
     * @param p player that use the effect
     */
    public void use_221(Player p){
        p.setAdditionalPoints(true);
    }

    /**
     * Disable the effect of the card 221
     *
     * @param p
     */
    public void endTurn_221(Player p){
        p.setAdditionalPoints(false);
    }

    public void use_222 (PawnColors p) throws ActiveCardAlreadyExistingException{
        if(activeCardID != 222) throw new ActiveCardAlreadyExistingException("Trying to use the wrong card");
        activeCardID = 222;
        Card_222 card = (Card_222) activeCard;
        card.setDisColor(p);
    }

    //da chiamare sempre a fine turno
    public void disableCardEffects (){
        switch(activeCardID){
            case 215: endTurn_215();
            case 221: endTurn_221(getActivePlayer());
        }

        deleteActiveCard();
    }


}
