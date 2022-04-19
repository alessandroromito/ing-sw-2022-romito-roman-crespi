package it.polimi.ingsw.server.exception;

public class ZeroCoinsException extends Throwable {
    public ZeroCoinsException(String message) {
        super(message);
    }
}
