package it.polimi.ingsw.server.exception;
/**
 * Specific exception
 */
public class CloudNotFoundException extends Throwable {
    public CloudNotFoundException(String message) {
        super(message);
    }
}
