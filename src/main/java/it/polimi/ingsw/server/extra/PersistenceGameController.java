package it.polimi.ingsw.server.extra;

import it.polimi.ingsw.controller.GameController;

import java.io.Serial;
import java.io.Serializable;

/**
 * This class represents the gameController the persistence additional functionality
 */
public record PersistenceGameController(GameController gameController) implements Serializable {

    @Serial
    private static final long serialVersionUID = 382104422531955291L;

    /**
     * Default constructor.
     *
     * @param gameController gameController to be saved.
     */
    public PersistenceGameController {
    }

    /**
     * Getter method.
     *
     * @return gameController.
     */
    @Override
    public GameController gameController() {
        return gameController;
    }
}
