package it.polimi.ingsw.server.model;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.controller.TurnController;
import it.polimi.ingsw.server.enumerations.PawnColors;
import it.polimi.ingsw.server.model.bag.Bag;
import it.polimi.ingsw.server.model.component.Component;
import it.polimi.ingsw.server.model.component.NoEntryTile;
import it.polimi.ingsw.server.model.component.ProfessorPawn;
import it.polimi.ingsw.server.model.component.StudentDisc;
import it.polimi.ingsw.server.model.component.charactercards.Card_214;
import it.polimi.ingsw.server.model.component.charactercards.Card_217;
import it.polimi.ingsw.server.model.component.charactercards.CharacterCard;
import it.polimi.ingsw.server.model.map.Map;
import it.polimi.ingsw.server.model.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ExpertGameTest {

    ExpertGame expertgame;
    TurnController turnController;
    GameController gameController;

    Map map;
    Bag bag;

    ArrayList<Component> components;

    Player teo;
    Player ale;
    Player gio;

    private final List<String> playersNicknames = new ArrayList<>();

    @BeforeEach
    void setUp() {
        gameController = new GameController();

        String player1 = "teo";
        String player2 = "ale";
        String player3 = "gio";

        gameController.getPlayersNicknames().add(player1);
        gameController.getPlayersNicknames().add(player2);
        gameController.getPlayersNicknames().add(player3);

        playersNicknames.add(player1);
        playersNicknames.add(player2);
        playersNicknames.add(player3);

        expertgame = new ExpertGame(playersNicknames);
        turnController = new TurnController(gameController, gameController.getVirtualViewMap());
        expertgame.setExpertMode(true);
        expertgame.setTurnController(turnController);
        gameController.setTurnController(turnController);
        gameController.setGame(expertgame);
        turnController.setActivePlayer(player1);

        expertgame.getPool().clear();
        expertgame.getPool().add(new Card_214());
        expertgame.getPool().add(new Card_217());

        this.components = expertgame.getComponents();
        this.map = expertgame.getMap();
        this.bag = expertgame.getBag();

        this.teo = expertgame.getPlayerByNickname("teo");
        this.ale = expertgame.getPlayerByNickname("ale");
        this.gio = expertgame.getPlayerByNickname("gio");
    }

    @Test
    void expertGameInitialization() {
    }

    @Test
    void createComponents() {

    }

    @Test
    void useCharacter() {
        expertgame.useCharacter(217);
        assertEquals(217, expertgame.getActiveCardID());
    }

    @Test
    void deleteActiveCard() {
        expertgame.useCharacter(210);

        expertgame.deleteActiveCard();

        assertNull(expertgame.getActiveCard());
    }

    @Test
    void getActiveCardID() {
        expertgame.useCharacter(217);

        assertEquals(217, expertgame.getActiveCardID());

        expertgame.useCharacter(210);

        assertEquals(217, expertgame.getActiveCardID());
    }

    @Test
    void getPool() {
        for(CharacterCard card : expertgame.getPool()){
            assertTrue(expertgame.getPool().contains(card));
        }
    }

    @Test
    void getActiveCard() {
        expertgame.useCharacter(217);

        assertEquals(217, expertgame.getActiveCard().getID());
    }

    @Test
    void use_209() {

    }

    @Test
    void use_210() {
    }

    @Test
    void endTurn_210() {
    }

    @Test
    void use_211() {
    }

    @Test
    void use_212() {
    }

    @Test
    void use_213() {
    }

    @Test
    void use_214() {
    }

    @Test
    void use_215() {
    }

    @Test
    void use_216() {
    }

    @Test
    void endTurn_216() {
    }

    @Test
    void use_217() {
    }

    @Test
    void use_218() {
    }

    @Test
    void use_219() {
    }

    @Test
    void use_220() {
    }

    @Test
    void disableCardEffects() {
        expertgame.disableCardEffects();
        assertEquals(-1, expertgame.getActiveCardID());
    }

    @Test
    void getCharacterCards() {
        assertEquals(2, expertgame.getCharacterCards().size());
    }

    @Test
    void getCharacterCardByID() {
        assertEquals(217, expertgame.getCharacterCardByID(217).getID());
    }

    @Test
    void checkInfluence() {
        assertTrue(expertgame.useCharacter(217));

        Card_217 card217 = (Card_217) expertgame.getActiveCard();
        card217.setDisColor(PawnColors.RED);
        expertgame.setActiveCard(card217);
        expertgame.setActiveCardID(217);

        teo.getScoreboard().addProfessor(new ProfessorPawn(1, PawnColors.RED));

        map.getIsland(4).addStudent(new StudentDisc(1,PawnColors.RED));
        map.getIsland(4).addStudent(new StudentDisc(1,PawnColors.RED));

        expertgame.checkInfluence(4);

        assertEquals(0, map.getIsland(4).getTowerNumber());

        expertgame.deleteActiveCard();

        map.getIsland(5).addStudent(new StudentDisc(1,PawnColors.RED));
        map.getIsland(5).addStudent(new StudentDisc(1,PawnColors.RED));

        expertgame.checkInfluence(5);

        assertEquals(1, map.getIsland(5).getTowerNumber());

        expertgame.setActiveCardID(214);

        expertgame.checkInfluence(5);

        assertEquals(1, map.getIsland(5).getTowerNumber());

        expertgame.deleteActiveCard();
        map.getIsland(6).addStudent(new StudentDisc(1, PawnColors.RED));
        map.getIsland(6).addNoEntryTile(new NoEntryTile(1));

        expertgame.checkInfluence(6);

        assertEquals(0, map.getIsland(6).getTowerNumber());
    }

    @Test
    void restoreGame() {
    }

    @Test
    void testIDs(){
        int i=0;
        for(Component x: expertgame.getComponents()){
            System.out.println(x.getClass()+", ID: "+x.getID()+", pos: "+i);
            i++;
        }
    }
}