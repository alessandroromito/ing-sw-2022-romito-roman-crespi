package it.polimi.ingsw.server.exception;

/**
 * Specific exception
 */
public class GameAlreadyStartedException extends RuntimeException {
    public GameAlreadyStartedException(String message) {
        super(message);
    }
}
