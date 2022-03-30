package it.polimi.ingsw.server.exception;

public class MaxPlayerException extends RuntimeException {
    public MaxPlayerException(String message) {
        super(message);
    }
}
