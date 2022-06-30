package it.polimi.ingsw.server.exception;

/**
 * Specific exception
 */
public class ActiveCardAlreadyExistingException extends Throwable {
    public ActiveCardAlreadyExistingException(String message) {
        super(message);
    }
}
