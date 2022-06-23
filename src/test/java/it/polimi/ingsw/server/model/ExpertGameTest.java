package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.component.Component;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

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
        expertgame.useCharacter(210);
        assertNotEquals(210, expertgame.getActiveCardID());
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