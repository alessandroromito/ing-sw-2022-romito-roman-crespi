package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.enumerations.PawnColors;
import it.polimi.ingsw.server.exception.ActiveCardAlreadyExistingException;
import it.polimi.ingsw.server.exception.ZeroCoinsException;
import it.polimi.ingsw.server.model.component.CharacterCard;
import it.polimi.ingsw.server.model.component.CharacterCards.*;
import it.polimi.ingsw.server.model.component.Coin;
import it.polimi.ingsw.server.model.component.NoEntryTile;
import it.polimi.ingsw.server.model.component.StudentDisc;
import it.polimi.ingsw.server.model.player.Player;
import it.polimi.ingsw.server.enumerations.MapPositions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ExpertGame extends Game{
    private int activeCardID;
    private CharacterCard activeCard;

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
        super.gameInitialization();
        // Add 1 coin to all Players
        for(Player p: this.getPlayers()){
            p.addCoin();
        }
        // Choose 3 CharacterCards
        List<Integer> vector12 = new ArrayList<>(12);
        for(int i=0;i<12;i++)   vector12.add(i);
        Collections.shuffle(vector12);

        for(int i=0;i<3;i++) {

            // Creare le carte dal json

        }
    }

    @Override
    public void createComponents(){
        super.createComponents();

        for(int i=189; i<=208; i++){
            components.set(i, new Coin());
        }

        for(int i=209; i<=213; i++){
            components.set(i, new NoEntryTile());
        }

        for(int i=214; i<=225; i++){
            components.set(i, new CharacterCard());
        }
    }

    //receive 0,1 or 3
    //if it is a immediate effect card it will end
    public void useCharacter(int character) throws ActiveCardAlreadyExistingException{
        if(activeCardID != -1) throw new ActiveCardAlreadyExistingException("There is an active card already!");

        activeCard = map.getCard(character);
        activeCardID = activeCard.getID();
        activeCard.use();

        for(int i=0;i<activeCard.getCost();i++)
            try{
                currentPlayer.removeCoin();
            } catch (ZeroCoinsException e) {
                e.printStackTrace();
            }

// at the end of the turn activeCardID must be -1
    }

    public void deleteActiveCard(){
        activeCard = null;
        activeCardID = -1;
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

        MapPositions t[] = new MapPositions [5];
        for(int i=2;i<=6;i++)
            t[i-2] = components.get(i).getPositionOnMap();
        temp.updateOldPos(t);

        for(Player p : players)
            if (p != currentPlayer)
                for(int i=0;i<5;i++)
                    if(p.getScoreboard().getProfessor(PawnColors.values()[i]))
                        if(p.getScoreboard().getPlayerStudentFromDining(PawnColors.values()[i]) == currentPlayer.getScoreboard().getPlayerStudentFromDining(PawnColors.values()[i])){
                            temp.updateOnePos(components.get(i+2).getPositionOnMap(),i);
                            components.get(i+2).setPosition();
                        }
    }

    public void endTurn_215(){

    }

    public void use_216 (int islandNumber) throws ActiveCardAlreadyExistingException {
        if(activeCardID != 216) throw new ActiveCardAlreadyExistingException("Trying to use the wrong card");
        map.getIsland(islandNumber).getInfluence();

    }
//da chiamare sempre a fine turno
    public void disableCardEffects (){
        switch(activeCardID){
            case 215: endTurn_215();
        }

        deleteActiveCard();
    }
}
