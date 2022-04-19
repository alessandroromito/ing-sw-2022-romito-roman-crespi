package it.polimi.ingsw.server.extra;

import it.polimi.ingsw.controller.GameController;

import java.io.Serializable;

public class PersistenceGameController implements Serializable {
    private static final long serialVersionUID = 382104422531955291L;
    private final GameController gameController;

    public PersistenceGameController(GameController gameController) {
        this.gameController = gameController;
    }

    public GameController getGameController() {
        return gameController;
    }
}
