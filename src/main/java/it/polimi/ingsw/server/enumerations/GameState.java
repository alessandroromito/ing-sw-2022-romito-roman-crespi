package it.polimi.ingsw.server.enumerations;

/**
 * State of the game
 */
public enum GameState {
    /**
     * When game not started yet
     */
    GAME_ROOM,
    /**
     * When game already started
     */
    IN_GAME,
    GAME_ENDED
}
