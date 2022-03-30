package it.polimi.ingsw.server.model;

public enum GameState {
    /**
     * When game not started yet
     */
    GAME_ROOM,
    /**
     * When game already started
     */
    GAME_STARTED,
    PIANIFICATION_PHASE,
    ACTION_PHASE,
    GAME_ENDED
}
