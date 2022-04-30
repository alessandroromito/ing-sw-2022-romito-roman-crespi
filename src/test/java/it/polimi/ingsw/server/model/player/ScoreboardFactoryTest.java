package it.polimi.ingsw.server.model.player;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ScoreboardFactoryTest {
    private ScoreboardFactory s;

    @BeforeEach
    void setup() {
        s = new ScoreboardFactory();
    }

    @Test
    void createScoreboardX2p() {
        Object s2 = s.createScoreboard(2,new Player("gigio"));
        assertTrue(s2 instanceof ScoreboardX2p );
    }

    @Test
    void createScoreboardX3p() {
        Object s2 = s.createScoreboard(3,new Player("dani"));
        assertTrue(s2 instanceof ScoreboardX3p );
    }
}