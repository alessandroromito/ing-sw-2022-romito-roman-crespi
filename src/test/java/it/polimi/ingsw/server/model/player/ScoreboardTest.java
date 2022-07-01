package it.polimi.ingsw.server.model.player;

import it.polimi.ingsw.server.enumerations.PawnColors;
import it.polimi.ingsw.server.enumerations.TowerColors;
import it.polimi.ingsw.server.model.component.StudentDisc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ScoreboardTest {

    private ScoreboardX2p scoreboard;

    @BeforeEach
    void setup() {
        this.scoreboard = new ScoreboardX2p(TowerColors.BLACK, new Player("teo"));
    }

    @Test
    void getNumProf() {
    }

    @Test
    void addProfessor() {
    }

    @Test
    void removeProfessor() {
        assertNull(scoreboard.removeProfessor(PawnColors.RED));
    }

    @Test
    void removeStudent() {
    }

    @Test
    void getProfessor() {
    }

    @Test
    void getNumTowers() {
    }

    @Test
    void getNumStudentsFromEntrance() {
    }

    @Test
    void getPlayerStudentFromDining() {
    }

    @Test
    void addStudentOnEntrance() {
        for(int i = 0; i<7; i++)
            scoreboard.addStudentOnEntrance(new StudentDisc(1,PawnColors.RED));
        assertFalse(scoreboard.addStudentOnEntrance(new StudentDisc(1, PawnColors.RED)));
    }

    @Test
    void addStudentOnDining() {
    }

    @Test
    void moveFromEntranceToDining() {
        for(int i = 0; i<7; i++)
            scoreboard.addStudentOnEntrance(new StudentDisc(1,PawnColors.RED));

        assertFalse(scoreboard.moveFromEntranceToDining(new StudentDisc(2, PawnColors.RED)));
        assertTrue(scoreboard.moveFromEntranceToDining(new StudentDisc(1, PawnColors.RED)));
        assertTrue(scoreboard.moveFromEntranceToDining(new StudentDisc(1, PawnColors.RED)));
        assertTrue(scoreboard.moveFromEntranceToDining(new StudentDisc(1, PawnColors.RED)));

    }

    @Test
    void getTowerColor() {
    }

    @Test
    void removeTower() {
    }

    @Test
    void getEntrance() {
    }

    @Test
    void getDiningRoom() {
    }

    @Test
    void getStudentFromDining() {
    }

    @Test
    void getProfessorList() {
    }

    @Test
    void addTowers() {
    }

    @Test
    void removeStudentFromDining() {
    }
}