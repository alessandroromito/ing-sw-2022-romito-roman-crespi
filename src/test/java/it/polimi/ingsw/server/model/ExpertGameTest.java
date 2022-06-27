package it.polimi.ingsw.server.model;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.controller.TurnController;
import it.polimi.ingsw.server.model.component.Component;
import it.polimi.ingsw.server.model.component.charactercards.CharacterCard;
import it.polimi.ingsw.view.VirtualView;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

class ExpertGameTest {
    ExpertGame expertgame;
    TurnController turnController;
    GameController gameController;
    Map<String, VirtualView> virtualViewMap = new HashMap<>();

    private final List<String> playersNicknames = new ArrayList<>();

    @BeforeEach
    void setUp() {
        gameController = new GameController();

        String player1 = "Player 1";
        String player2 = "Player 2";
        String player3 = "Player 3";

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
        turnController.setActivePlayer(player1);

        expertgame.getPool().add(new CharacterCard(210));
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void useCharacter() {
        expertgame.useCharacter(210);
        assertEquals(210, expertgame.getActiveCardID());
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