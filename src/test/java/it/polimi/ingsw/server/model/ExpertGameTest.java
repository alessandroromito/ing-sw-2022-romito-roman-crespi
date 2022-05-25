package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.exception.ActiveCardAlreadyExistingException;
import it.polimi.ingsw.server.model.component.Component;
import it.polimi.ingsw.server.model.component.charactercards.CharacterCard;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ExpertGameTest {
    ExpertGame expertgame;
    private List<String> players = new ArrayList<>();

    @BeforeEach
    void setUp() {

        String player1 = "Player 1";
        String player2 = "Player 2";
        String player3 = "Player 3";

        players.add(player1);
        players.add(player2);
        players.add(player3);

        expertgame = new ExpertGame(players);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void useCharacter() {
        expertgame.useCharacter(1);
        assertNotEquals(-1, expertgame.getActiveCardID());
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