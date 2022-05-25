package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.enumerations.PawnColors;
import it.polimi.ingsw.server.exception.ActiveCardAlreadyExistingException;
import it.polimi.ingsw.server.exception.MissingPlayerNicknameException;
import it.polimi.ingsw.server.model.component.StudentDisc;
import it.polimi.ingsw.server.model.component.charactercards.Card_210;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class ExpertGame_Card_210 {
    ExpertGame expertgame;
    Card_210 card;
    private List<String> players;

    @BeforeEach
    void setUp() {
        String player1 = "Player 1";
        String player2 = "Player 2";
        String player3 = "Player 3";

        players = new ArrayList<String>();

        this.players.add(player1);
        this.players.add(player2);
        this.players.add(player3);

        this.expertgame = new ExpertGame(players);

        // setting player 1 that controls the first professor with 1 student, and player 2 also with 1 student in the same lane
        expertgame.getPlayerByNickname("Player 1").getScoreboard().addStudentOnDining(new StudentDisc(888, PawnColors.RED));
        expertgame.getPlayerByNickname("Player 2").getScoreboard().addStudentOnDining(new StudentDisc(889, PawnColors.RED));
        expertgame.moveProfessor(PawnColors.RED,expertgame.getPlayerByNickname("Player 1"));
        expertgame.setCard_210_ForTest();

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void use_210() {
        expertgame.use_210();
        card = (Card_210) expertgame.getActiveCard();
        assertEquals(Card_210.class ,expertgame.getActiveCard().getClass());
    }

    @Test
    void endTurn_210() {
    }
}