package it.polimi.ingsw.server.extra;

import it.polimi.ingsw.controller.GameController;

import java.io.Serial;
import java.io.Serializable;

/**
 * This class represents the gameController the persistence additional functionality
 */
public class PersistenceGameController implements Serializable {

    @Serial
    private static final long serialVersionUID = 382104422531955291L;

    private final GameController gameController;

    /**
     * Default constructor.
     * @param gameController gameController to be saved.
     */
    public PersistenceGameController(GameController gameController) {
        this.gameController = gameController;
    }

    /**
     * Getter method.
     * @return gameController.
     */
    public GameController getGameController() {
        return gameController;
    }
}
