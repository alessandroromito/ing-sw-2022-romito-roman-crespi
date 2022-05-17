package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.exception.ActiveCardAlreadyExistingException;
import it.polimi.ingsw.server.model.component.charactercards.CharacterCard;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ExpertGameTest {
    ExpertGame expertgame;
    private List<String> players;

    @BeforeEach
    void setUp() {
        players = new ArrayList<>();

        String player1 = "Player 1";
        String player2 = "Player 2";
        String player3 = "Player 3";

        this.players.add(player1);
        this.players.add(player2);
        this.players.add(player3);

        expertgame = new ExpertGame(players);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void useCharacter() {
        try {
            expertgame.useCharacter(1);
        } catch (ActiveCardAlreadyExistingException e) {
            e.printStackTrace();
        }
        assertNotEquals(-1, expertgame.getActiveCardID());
    }

}