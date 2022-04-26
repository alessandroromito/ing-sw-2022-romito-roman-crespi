package it.polimi.ingsw.server.exception;

public class WrongPhaseStateException extends Throwable {
    public WrongPhaseStateException(String message){
        super(message);
    }
}
