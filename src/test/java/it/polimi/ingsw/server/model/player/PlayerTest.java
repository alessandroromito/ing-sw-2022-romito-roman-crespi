package it.polimi.ingsw.server.model.player;

import it.polimi.ingsw.server.exception.MissingPlayerNicknameException;
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
    private List<String> players = new ArrayList<>();
    private ArrayList<Component> components;
    private Player p1;

    @BeforeEach
    void setUp() {
        String player1 = "Player 1";
        String player2 = "Player 2";
        String player3 = "Player 3";

        players.add(player1);
        players.add(player2);
        players.add(player3);

        game = new Game(players);
        components = game.getComponents();

        try {
            p1 = game.getPlayerByNickname("Player 1");
        } catch (MissingPlayerNicknameException e) {
            e.printStackTrace();
        }
    }

    @AfterEach
    void tearDown() {
        game = null;
        components = null;
        players = null;
    }

    @Test
    void createScoreboard() {
        try {
            p1.createScoreboard(game.getNumberOfPlayer(), game.getPlayerByNickname("Player 1"));
        } catch (MissingPlayerNicknameException e) {
            e.printStackTrace();
        }
        assertEquals(ScoreboardX3p.class, p1.getScoreboard().getClass());

        players.remove(2);

        try {
            p1.createScoreboard(game.getNumberOfPlayer(), game.getPlayerByNickname("Player 1"));
        } catch (MissingPlayerNicknameException e) {
            e.printStackTrace();
        }
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