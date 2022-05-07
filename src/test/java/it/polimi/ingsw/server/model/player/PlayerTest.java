package it.polimi.ingsw.server.model.player;

import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.component.Component;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {

    private Game game;
    private List<Player> players = new ArrayList<>();
    private ArrayList<Component> components;
    private Player p1;

    @BeforeEach
    void setUp() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Player player3 = new Player("Player 3");

        players.add(player1);
        players.add(player2);
        players.add(player3);

        p1 = players.get(0);

        game = new Game(players);
        components = game.getComponents();
    }

    @AfterEach
    void tearDown() {
        game = null;
        components = null;
        players = null;
    }

    @Test
    void createScoreboard() {
        p1.createScoreboard(players.size());
        assertEquals(ScoreboardX3p.class, p1.getScoreboard().getClass());

        players.remove(2);
        p1.createScoreboard(players.size());
        assertEquals(ScoreboardX2p.class, p1.getScoreboard().getClass());

    }

    @Test
    void getNickname() {
    }

    @Test
    void getPlayerCard() {
    }

    @Test
    void setPlayerCards() {
    }

    @Test
    void getScoreboard() {
    }

    @Test
    void getCoin() {
    }

    @Test
    void addCoin() {
    }

    @Test
    void removeCoin() {
    }

    @Test
    void isLastAssistantCard() {
    }

    @Test
    void notifyUsingAssistantCard() {
    }

    @Test
    void setCurrentCard() {
    }

    @Test
    void getCurrentCard() {
    }

    @Test
    void getHand() {
    }

    @Test
    void setAdditionalPoints() {
    }

    @Test
    void isAdditionalPoints() {
    }
}